/**
 * username: ailinz1
 * Student number: 874810
 * name: Ailin Zhang
 */

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Operation{
    private ConcurrentHashMap<String,String> dictionary;
    private int wordCount;

    public Operation(ServerWindow serverWindow,String file) {

        this.dictionary = loadDict(file);
        this.wordCount = dictionary.size();
        serverWindow.setDictNumber(wordCount);
    }

    private ConcurrentHashMap<String,String> loadDict(String file) {
        ConcurrentHashMap<String,String> dictionary = new ConcurrentHashMap<String,String>();
        int wordCount;
        String[] string = file.split("@#&");
        if (string.length > 1) {
            for (int i = 0; i < string.length; i = i + 2) {
                dictionary.put(string[i], string[i + 1]);
            }
        }
        return dictionary;
    }
//    convert dictionary into String and return
    public String updateFile(ServerWindow serverWindow){
        String newfile = "";
        Set<String> keys = dictionary.keySet();
        for (String key : keys){
            String addString = key + "@#&" + dictionary.get(key) + "@#&";
            newfile = newfile.concat(addString);
        }
        serverWindow.setDictNumber(wordCount);
        return newfile;
    }


    public String search(String word){
        return dictionary.getOrDefault(word, "@#&");
//        if (dictionary.containsKey(word)){
//            return dictionary.get(word);
//        }
//        else {
//            return "@#&";
//        }
    }
    public Boolean add(String word, String meaning){
        if(dictionary.containsKey(word)){

            return false;
        }else {
            dictionary.put(word,meaning);

        }
        return true;
    }


    public Boolean remove (String word){
        if(dictionary.containsKey(word)){
            dictionary.remove(word);
            return true;
        }else {
            return false;

        }
    }

}