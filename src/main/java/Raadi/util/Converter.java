package Raadi.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Converter {

    public static HashSet<String> StopWordsJsonToHashSet() {

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/src/main/java/Raadi/util/StopWords.json"));
            String stopWordsString = new String(encoded, Charset.defaultCharset());
            stopWordsString = stopWordsString.substring(1, stopWordsString.length() - 1);

            String[] items = stopWordsString.split(",");
            for (int i = 0; i < items.length; i++)
                items[i] = items[i].substring(1, items[i].length() - 1);

            return new HashSet<>(Arrays.asList(items));
        }
        catch (IOException ignored) {
        }
        return null;
    }

    public static HashMap<String, String> SynonymsCSVToHashMap() {

        HashMap<String, String> synonymsHashMap = new HashMap<>();

        String fileName = System.getProperty("user.dir") + "/src/main/java/Raadi/util/Synonyms.csv";
        List<String> list = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            list = stream.collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String str : list) {
            String[] items = str.split(",");
            for (int i = 1; i < items.length; i++)
                synonymsHashMap.put(items[i], items[0]);
        }
        
        return synonymsHashMap;
    }
}