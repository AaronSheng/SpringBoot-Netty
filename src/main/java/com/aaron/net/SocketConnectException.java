package com.aaron.net;

public class SocketConnectException extends Exception {
    String msg = null;

    public SocketConnectException() {
        super();
        msg = "";
    }

    public SocketConnectException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMessage() {
        return this.msg;
    }
}
