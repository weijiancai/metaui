package com.metaui.core.parser.mobile;

import java.util.Random;

/**
 * ����
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class Bank {
    public static final String[] BANK_LIST = {"�й���������", "�й�ũҵ����", "�й���������", "��������", "�й�������������", "�й���������", "��ҵ����", "��������", "�й��������", "��������", "�Ϻ�����", "��������", "�й�����", "ƽ������", "��������", "��������", "�㷢����", "�ַ�����", "��ͨ����", "�ɶ�����", "����ũ������", "�Ϻ�ũ������", "��������"};

    public static String randomBank() {
        return BANK_LIST[new Random().nextInt(BANK_LIST.length)];
    }

    // 19λ���п�
    public static String randomBankNo() {
        return random(19);
    }

    // 6λ����
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
