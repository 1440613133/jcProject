package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.Result;
import com.tencent.wxcloudrun.config.ResultCode;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.util.OrcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);
  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public Result register(@RequestBody User user) {
    log.info("用户注册信息: {}", user);
    int res = userService.register(user);
    if (res > 0 ){
      log.info("用户注册成功，userId:{}", user.getCarNumber());
      return Result.success();
    }
    return Result.failed(ResultCode.FAILED, "用户注册失败");
  }


  @PostMapping("/getCarInfo")
  public Result getCarInfo(@RequestBody Map<String, String> params) throws IOException {
    String imageUrl = params.get("imageUrl");
    if (imageUrl == null || imageUrl.isEmpty()) {
      return Result.failed(ResultCode.SYSTEM_ERROR, "图片路径不能为空");
    }
    // 图片base64格式
    String base64ImageUrl = URLEncoder.encode(imageUrl, "utf-8");
    String carNumber = OrcUtil.getCarNumber(base64ImageUrl);
    if (!carNumber.isEmpty()) {
      //根据车牌号获取用户信息
      User user = userService.findByCarNumber(carNumber);
      if (user != null) {
        log.info("车牌号匹配成功，用户信息: {}", user);
        return Result.success(user);
      } else {
        log.info("未找到对应的车牌号{}的用户", carNumber);
        return Result.failed(ResultCode.DATA_NOT_FOUND, "未找到对应的车牌号的用户");
      }
    }
    return Result.failed(ResultCode.FAILED, "车牌号识别失败");
  }
}
