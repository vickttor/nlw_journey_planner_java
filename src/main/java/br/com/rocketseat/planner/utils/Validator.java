package br.com.rocketseat.planner.utils;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

public class Validator {
    public static Boolean isValidEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$";
        return Pattern.matches(regex, email);
    }
}
