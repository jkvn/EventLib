package at.jkvn.eventlib.exception;

import java.lang.String;
import java.lang.Exception;

public class AutomaticListenerException extends Exception {
    public AutomaticListenerException(String message) {
        super(message);
    }
}