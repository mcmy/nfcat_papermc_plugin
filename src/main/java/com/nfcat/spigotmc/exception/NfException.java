package com.nfcat.spigotmc.exception;

import lombok.Getter;

@Getter
public class NfException extends Exception{

    private final String msg;

    public NfException(String message) {
        super(message);
        this.msg = message;
    }
}
