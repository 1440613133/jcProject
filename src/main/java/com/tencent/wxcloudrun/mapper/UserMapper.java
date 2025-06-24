package com.tencent.wxcloudrun.mapper;


import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (openId, nickName, avatarUrl, phone,carNumber,carColor, balance, status) " +
            "VALUES (#{openId}, #{nickName}, #{avatarUrl}, #{phone}, #{carNumber},#{carColor},#{balance}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "carNumber")
    int insert(User user);

    @Select("SELECT * FROM user WHERE open_id = #{openId}")
    User findByOpenId(String openId);

    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User findByPhone(String phone);

    @Update("UPDATE user SET nickname = #{nickname}, avatar_url = #{avatarUrl}, " +
            "phone = #{phone}, balance = #{balance}, status = #{status} " +
            "WHERE user_id = #{userId}")
    int update(User user);

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    User findById(Integer userId);


    User findByCarNumber(String carNumber);
}
