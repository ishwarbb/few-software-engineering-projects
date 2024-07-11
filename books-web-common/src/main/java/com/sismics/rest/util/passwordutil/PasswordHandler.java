package com.sismics.rest.util.passwordutil;

import org.codehaus.jettison.json.JSONException;

public interface PasswordHandler {
    void setNextHandler(PasswordHandler nextHandler);
    void handleRequest(String password, String name) throws JSONException;
}

