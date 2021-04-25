package com.example.teamsup.utils;

public class ConstantsUtils {
    public static final int TYPE_OTHER = 0;
    public static final int TYPE_FOOTBALL = 1;
    public static final int TYPE_BASKET = 2;
    public static final int TYPE_PADEL = 3;
    public static final int TYPE_TENIS = 4;
    public static final int TYPE_HOCKEY = 5;
    public static final int TYPE_BADMINTON = 6;

    public static int recoverEventType(int eventPosition) {
        switch (eventPosition) {
            case 1:
                return TYPE_FOOTBALL;
            case 2:
                return TYPE_BASKET;
            case 3:
                return TYPE_PADEL;
            case 4:
                return TYPE_TENIS;
            case 5:
                return TYPE_HOCKEY;
            case 6:
                return TYPE_BADMINTON;
            default:
                return TYPE_OTHER;
        }
    }
}
