package com.example.cbleecher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageNameFinder {

    private static final Pattern PACKAGE_NAME_PATTERN = Pattern.compile("(([a-z0-9]+\\.)+[a-z0-9]+)");

    public static String findPackageName(String text) {
        Matcher matcher = PACKAGE_NAME_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String removeLinkPrefix(String text) {

        int linkPrefixIndex = text.indexOf("/app/");
        if (linkPrefixIndex != -1) {
            text = text.substring(linkPrefixIndex + "/app/".length());
        }
        return text;
    }

    public static String main(String text) {


        text = removeLinkPrefix(text);

        String packageName = findPackageName(text);
        System.out.println("Package name: " + packageName);
        return packageName;
    }
}
