package com.codechallenge.a20230303_joshuahand_nycschools.util;

public class StringUtil {

    public static boolean isStringValid(String string){
        return string != null && !string.trim().isEmpty();
    }

    public static boolean areStringsValid(String ... strings){
        for (String string : strings){
            if(!isStringValid(string)){
                return false;
            }
        }
        return true;
    }
}
