package com.example.SDE;

public class config {

    public static final String DB_URL = "jdbc:postgresql://localhost:5433/sde_project";
    public static final String DB_USER = "sde";
    public final static String DB_PASSWORD = "project";
    public static final String SECRET_KEY = "eaHqvOaWS0JIHE6luas5Rxfj4aushORT";
    public static final String JWT_HEADER = "Authorization";
    public final static int EXPIRATION_TIME = 3600000; // 1 hour in ms
}
