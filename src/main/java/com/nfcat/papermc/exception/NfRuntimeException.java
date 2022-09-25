package com.nfcat.papermc.exception;

import lombok.Getter;

@Getter
public class NfRuntimeException extends RuntimeException {
    private final String msg;

    public NfRuntimeException(String message) {
        super(message);
        this.msg = message;
    }
}