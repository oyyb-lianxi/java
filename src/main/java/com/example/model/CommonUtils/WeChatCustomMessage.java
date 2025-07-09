package com.example.model.CommonUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WeChatCustomMessage {

    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    public static void main(String[] args) {
        String appid = "your_appid";
        String secret = "your_secret";
        String openid = "user_openid";

        try {
            // 获取 access_token
            String accessToken = getAccessToken(appid, secret);
            if (accessToken == null) {
                System.out.println("Failed to get access token");
                return;
            }

            // 发送用户信息卡片
            String response = sendCustomMessage(accessToken, openid);
            System.out.println("Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getAccessToken(String appid, String secret) throws Exception {
        String url = TOKEN_URL.replace("APPID", appid).replace("APPSECRET", secret);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // 解析 JSON 响应
        String jsonResponse = response.toString();
        if (jsonResponse.contains("access_token")) {
            return jsonResponse.split(",")[0].split(":")[1].replace("\"", "");
        } else {
            return null;
        }
    }

    private static String sendCustomMessage(String accessToken, String openid) throws Exception {
        String url = MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setDoOutput(true);

        // 构造请求体
        String jsonInputString = String.format(
            "{\n" +
            "  \"touser\": \"%s\",\n" +
            "  \"msgtype\": \"miniprogrampage\",\n" +
            "  \"miniprogrampage\": {\n" +
            "    \"title\": \"用户信息卡片\",\n" +
            "    \"pagepath\": \"pages/index/index\",\n" +
            "    \"thumb_media_id\": \"MEDIA_ID\"\n" +
            "  }\n" +
            "}", openid);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
