package com.example.model.CommonUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class StringUtils {

    // 生成订单号，时间格式 + 6位随机数
    public static String generateOrderId() {
        // 时间部分（精确到毫秒）
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
        String timePart = LocalDateTime.now().format(formatter);

        // 随机数部分（6位）
        int randomNum = ThreadLocalRandom.current().nextInt(100000, 999999);

        return timePart + randomNum; // 示例：250718202228712473233
    }

}
