package com.example.teamsup.utils;

import com.example.teamsup.R;

public class ConstantsUtils {
    public static final int TYPE_OTHER = 0;
    public static final int TYPE_FOOTBALL = 1;
    public static final int TYPE_BASKET = 2;
    public static final int TYPE_PADEL = 3;
    public static final int TYPE_TENIS = 4;
    public static final int TYPE_HOCKEY = 5;
    public static final int TYPE_BADMINTON = 6;
    public static final int TYPE_VOLEY = 7;
    public static final int TYPE_PINGPONG = 8;
    public static final int TYPE_BASEBALL = 9;
    public static final int TYPE_BOWLING = 10;
    public static final int TYPE_BOXING = 11;
    public static final String KEY_SPORT = "deportes";
    public static final String KEY_EMAIL = "mail";
    public static final String KEY_DIRECTION = "direction";
    public static final String KEY_USERNAME = "username";


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

    public static int recoverEventName(int sport_type) {
        switch (sport_type) {
            case TYPE_FOOTBALL:
                return R.string.football;
            case TYPE_BASKET:
                return R.string.basket;
            case TYPE_PADEL:
                return R.string.padel;
            case TYPE_TENIS:
                return R.string.tenis;
            case TYPE_HOCKEY:
                return R.string.hockey;
            case TYPE_BADMINTON:
                return R.string.badminton;
            case TYPE_VOLEY:
                return R.string.voley;
            case TYPE_PINGPONG:
                return R.string.pingpong;
            case TYPE_BASEBALL:
                return R.string.baseball;
            case TYPE_BOWLING:
                return R.string.bowling;
            case TYPE_BOXING:
                return R.string.boxing;
            default:
                return R.string.other;
        }
    }

    public static int recoverEventImage(int sport_type) {
        switch (sport_type) {
            case TYPE_FOOTBALL:
                return R.drawable.sportico_football;
            case TYPE_BASKET:
                return R.drawable.sportico_basketball;
            case TYPE_HOCKEY:
                return R.drawable.sportico_hockey;
            case TYPE_BADMINTON:
                return R.drawable.sportico_badminton;
            case TYPE_VOLEY:
                return R.drawable.sportico_volleyball;
            case TYPE_PINGPONG:
                return R.drawable.sportico_ping_pong;
            case TYPE_BASEBALL:
                return R.drawable.sportico_baseball;
            case TYPE_BOWLING:
                return R.drawable.sportico_bowling;
            case TYPE_BOXING:
                return R.drawable.sportico_boxing;

            default:
                return R.drawable.sportico_other;
        }
    }
}
