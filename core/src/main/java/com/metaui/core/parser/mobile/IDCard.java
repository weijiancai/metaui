package com.metaui.core.parser.mobile;

import java.util.Random;

/**
 * 身份证号
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class IDCard {
    public static String random() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // 行政区号 6位
        String[] cantonNos = CantonNo.getCodes();
        sb.append(cantonNos[random.nextInt(cantonNos.length)]);
        // 生日 8位
        // 年 1950 ~ 2050年
        sb.append(1950 + random.nextInt(100));
        // 月
        sb.append(String.format("%02d", (random.nextInt(12) + 1)));
        // 日
        sb.append(String.format("%02d", (random.nextInt(31) + 1)));
        // 顺序码 3位
        sb.append(String.format("%03d", (random.nextInt(1000))));
        // 校验码 1位
        sb.append(random.nextInt(10));

        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(random());
        }
    }
}
