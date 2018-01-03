package com.example.daomy.foodguide.ultil;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by WIN7 on 05/24/2015.
 */
public class ConvertString {
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
