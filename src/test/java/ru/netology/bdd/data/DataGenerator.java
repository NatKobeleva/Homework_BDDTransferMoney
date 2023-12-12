package ru.netology.bdd.data;

import lombok.Value;

import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        private String number;
        private String testId;
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559000000000001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559000000000002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    public static int validAmount(int balance) {
        return new Random().nextInt(Math.abs(balance)) + 1;
    }
    public static int invalidAmount(int balance) {
        return Math.abs(balance) + new Random().nextInt(10000);
    }
}
