package com.shiv.grpc.server.context;


public class AppContext {

    private static final ThreadLocal<String> requestIdThreadSafe = new ThreadLocal<>();

    public static String getRequestId() {
        return requestIdThreadSafe.get();
    }

    public static void setRequestId(String requestId) {
        requestIdThreadSafe.set(requestId);
    }
}
