package com.company;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class htmlActions {

    public void action(){
        Map<String, Integer> map;

        while(true){
            try{
                map = this.countUrlTags(new getHTML().getHTML(this.getUserURL()));
                System.out.println("Result: " + map);
                break;
            } catch (IOException e) {
                System.out.println("Something went wrong: " + e.toString());
            }
        }
        while(true){
            System.out.println("\n1. Sort by quantity.\n" +
                    "2. Sort by name\n" +
                    "3. Exit");
            switch (this.getUserInputLine().trim()) {
                case "1" : {
                    this.sortByFrequency(map);
                }
                case "2" : {
                    this.sortByLexis(map);
                }
                case "3" : {
                    System.exit(1);
                }
                default : {
                    System.out.println("Incorrect input.\n");
                }
            }
        }
    }

    public String getUserInputLine(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    public URL getUserURL(){
        URL url;
        while(true){
            try{
              System.out.println("Please, enter chosen URL: ");
              url = new URL(this.getUserInputLine());
              break;
            }catch (MalformedURLException e){
                System.out.println("You write incorrect URL " + e.getMessage());
            }
        }
        return url;
    }

    public Map<String, Integer> countUrlTags(String text){
        String cons = "<[a-z]+[\\s>]";
        Pattern pattern = Pattern.compile(cons);
        Matcher matcher = pattern.matcher(text);
        Map<String, Integer> map = new HashMap<>();
        String tags;

        while(matcher.find()){
            tags = text.substring(matcher.start() + 1, matcher.end() - 1);
            if(map.containsKey(tags)) {
                map.compute(tags, (key, value) -> ++value); //сопоставление для указанного ключа и его тек сопоставленного значения
            }else map.put(tags, 1);
        }
        return map;
    }

    public void sortByLexis(Map<String, Integer> unsorted){
        Map<String, Integer> res = unsorted.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        System.out.println(res);
    }

    public void sortByFrequency(Map<String, Integer> unsorted){
        Map<String, Integer> res = unsorted.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        System.out.println(res);
    }
}
