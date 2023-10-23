package com.example.cbleecher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkExtractor {
    public static String link1;
    public static String link2;

    public static void extractLinks(String inputString) {

        String regex = "(https?://[^\\s,\"\']+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputString);


        List<String> links = new ArrayList<>();


        while (matcher.find()) {
            links.add(matcher.group());
        }



            link1 = links.get(1);
            link2 = links.get(2);

    }

}
