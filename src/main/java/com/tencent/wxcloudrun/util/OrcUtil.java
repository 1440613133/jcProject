package com.tencent.wxcloudrun.util;


import com.alibaba.fastjson.JSONObject;
import okhttp3.*;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

public class OrcUtil {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OrcUtil.class);
    private static final ConcurrentHashMap<String, CacheItem> tokenCache = new ConcurrentHashMap<>();

    public static final String APP_ID = "24323492";
    public static final String API_KEY = "oyWssYfjAWdWtiGdGzcVjqEy";
    public static final String SECRET_KEY = "6QRgoZmyL3kjo8ngzbL7VShGyZfWVXGB";
    private static final long CACHE_EXPIRATION_TIME = 30 * 24 * 60 * 60 * 1000L; // access_token存放30天
    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    /**
     * 获取AccessToken
     * @return
     * @throws IOException
     */
    public static String getAccessToken() throws IOException{
        // 检查缓存中是否有有效的token
        CacheItem cacheItem = tokenCache.get("access_token");
        if (cacheItem != null && !cacheItem.isExpired()) {
            log.info("使用缓存中的AccessToken:"+ cacheItem.accessToken);
            return cacheItem.accessToken;
        }
        String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=" + API_KEY + "&client_secret=" + SECRET_KEY;
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        JSONObject jsonObject = JSONObject.parseObject(response.body().string());
        String accessToken = jsonObject.getString("access_token");
        log.info("应用的AccessToken: " + accessToken);
        // 缓存新的token
        long expirationTime = System.currentTimeMillis() + CACHE_EXPIRATION_TIME;
        tokenCache.put("access_token", new CacheItem(accessToken, expirationTime));
        return accessToken;
    }
    public static String getCarNumber(String Base64Image) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String param ="image="+ Base64Image+"&multi_detect=false&multi_scale=false&detect_complete=false&detect_risk=false";
        RequestBody body = RequestBody.create(mediaType, param);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        JSONObject carJson = JSONObject.parseObject(response.body().string());
        log.info("车辆识别信息:{}",carJson);
        JSONObject wordsResult = carJson.getJSONObject("words_result");
        String carNumber = wordsResult.getString("number");
        String carColor = wordsResult.getString("color");
        log.info("车牌信息:{};车牌颜色:{}",carNumber,carColor);
        return carNumber;
    }



//    public static void main(String[] args) throws IOException {
//      String imagePath = "E:\\相册\\car1.jpg";
//        String contentAsBase64 = getFileContentAsBase64(imagePath, true);
////      获取照片并将相片转成base64
//        String result = getCarNumber(contentAsBase64);
//        System.out.println("车牌信息:"+result);
//    }
    /**
     * 获取文件base64编码
     *
     * @param path      文件路径
     * @param urlEncode 如果Content-Type是application/x-www-form-urlencoded时,传true
     * @return base64编码信息，不带文件头
     * @throws IOException IO异常
     */
    static String getFileContentAsBase64(String path, boolean urlEncode) throws IOException {
        byte[] b = Files.readAllBytes(Paths.get(path));
        String base64 = Base64.getEncoder().encodeToString(b);
        if (urlEncode) {
            base64 = URLEncoder.encode(base64, "utf-8");
        }
        return base64;
    }

    private static class CacheItem {
        String accessToken;
        long expirationTime;

        public CacheItem(String accessToken, long expirationTime) {
            this.accessToken = accessToken;
            this.expirationTime = expirationTime;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }
}
