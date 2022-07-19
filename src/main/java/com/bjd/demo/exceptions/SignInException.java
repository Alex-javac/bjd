package com.bjd.demo.exceptions;

public class SignInException extends RuntimeException {
    public SignInException() {
        super(String.format("Введен неверный логин или пароль"));
    }
}