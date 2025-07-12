package com.BankDomain.util;
import java.util.Random;

public class AccountNumberGenerator
{
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int DIGIT_LENGTH = 9;
    private static final Random RANDOM = new Random();

    public static String generateAccountNumber(){
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < 2; i++){
            prefix.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
        }
        StringBuilder digit = new StringBuilder();
        for (int i = 0; i < DIGIT_LENGTH; i++){
            digit.append(RANDOM.nextInt(10));
        }
        return prefix.toString() + digit.toString();
    }
}
