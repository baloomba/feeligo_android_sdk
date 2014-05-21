package com.baloomba.feeligo;

import java.util.regex.Pattern;

public class FeeligoHelper {

    // <editor-fold desc="VARIABLES">

    private final static String REG_EXP = "\\[s:([a-zA-Z0-9\\/\\.\\?\\=]+[a-zA-Z0-9]*)\\]";

    // </editor-fold>

    // <editor-fold desc="METHODS">

    public static boolean containsSticker(String text) {
        return Pattern.compile(REG_EXP).matcher(text).find();
    }

    // </editor-fold>

}
