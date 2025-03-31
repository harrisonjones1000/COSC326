import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class E7 {
    public static void main(String[] args){
        String[] verbs = {"haere", "hanga", "kite", "hiahia", "karanga", "patai", "panui", "ako"};

        String sentenceStarter = ""; //past - I, present - Kei te, future - Ka
        String verb = "";
        String object = "";

        String[] sentence;

        Map<String, String> starters = new HashMap<>();
        starters.put("I", "au");
        starters.put("You", "koe");
        starters.put("He", "ia");
        starters.put("She", "ia");
        starters.put("We (2 incl)", "taua");
        starters.put("We (2 excl)", "maua");
        starters.put("You (2 incl)", "korua");
        starters.put("They (2 excl)", "raua");
        starters.put("We (3 incl)", "tatou");
        starters.put("We (3 excl)", "matou");
        starters.put("You (3 incl)", "koutou");
        starters.put("They (3 excl)", "ratou");

        Scanner testScan = new Scanner(System.in);
        while (testScan.hasNext()){
            sentence = testScan.nextLine().split(" ");
            System.out.println(Arrays.toString(sentence));
        }
        testScan.close();
    }
}