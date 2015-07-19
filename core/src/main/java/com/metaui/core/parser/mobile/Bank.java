package com.metaui.core.parser.mobile;

import java.util.Random;

/**
 * 银行
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class Bank {
    public static final String[] BANK_LIST = {"中国工商银行", "中国农业银行", "中国建设银行", "招商银行", "中国邮政储蓄银行", "中国民生银行", "兴业银行", "中信银行", "中国光大银行", "杭州银行", "上海银行", "宁波银行", "中国银行", "平安银行", "富滇银行", "北京银行", "广发银行", "浦发银行", "交通银行", "成都银行", "北京农商银行", "上海农商银行", "其他银行"};

    public static String randomBank() {
        return BANK_LIST[new Random().nextInt(BANK_LIST.length)];
    }

    // 19位银行卡
    public static String randomBankNo() {
        return random(19);
    }

    // 6位密码
    public static String randomPassword() {
        return random(6);
    }

    private static String random(int count) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < count; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
