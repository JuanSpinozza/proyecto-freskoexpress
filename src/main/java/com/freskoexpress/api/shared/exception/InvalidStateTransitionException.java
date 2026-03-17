package com.freskoexpress.api.shared.exception;

public class InvalidStateTransitionException extends RuntimeException {
    public InvalidStateTransitionException(String from, String to) {
        super("Transición inválida de estado: " + from + " → " + to);
    }
}
