package com.example.bankapi.utli;

import java.util.Random;

public class CardNumberGenerator {

    public static String generate(int length) {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            builder.append(digit);
        }
        return builder.toString();
    }
}
