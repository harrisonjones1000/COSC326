import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class E72 {
    public static void main(String[] args){
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Scanner testScan = new Scanner(System.in);

        String[] sentence;
        int num;
        String object="";
        int pos = -1;
        
        String tense = "";
        String verb = "";

        Map<String, String> one = new HashMap<>();
        one.put("I", "au");
        one.put("You", "koe");
        one.put("He", "ia");
        one.put("She", "ia");

        Map<String, String> two = new HashMap<>();
        two.put("We (2 incl)", "tāua");
        two.put("We (2 excl)", "māua");
        two.put("They (2 excl)", "rāua");
        //two.put("You two", "kōrua"); 

        Map<String, String> three = new HashMap<>();
        three.put("We (3 incl)", "tāua");
        three.put("We (3 excl)", "māua");
        three.put("They (3 excl)", "rāua");
        three.put("You (3 excl)", "kōrua"); 

        Map<String, String> present = new HashMap<>();
        present.put("go", "haere");
        present.put("make", "hanga");
        present.put("see", "kite");
        present.put("want", "hiahia");
        present.put("call", "karanga");
        present.put("ask", "pātai");
        present.put("read", "pānui");
        present.put("learn", "ako");

        Map<String, String> past = new HashMap<>();
        past.put("went", "haere");
        past.put("made", "hanga");
        past.put("saw", "kite");
        past.put("wanted", "hiahia");
        past.put("called", "karanga");
        past.put("asked", "pātai");
        past.put("read", "pānui");
        past.put("learnt", "ako");

        while (testScan.hasNext()){
            sentence = testScan.nextLine().split(" ");
            try{
                num = Integer.parseInt(sentence[1].substring(1));
                if(num<=1){
                    System.out.println("INVALID1");
                    continue;
                }else if(num==2){//Two people
                    object = two.get(sentence[0] + " " + sentence[1] + " " + sentence[2]);
                }else if(num>2){//Three people
                    sentence[1] = "(3";
                    object = three.get(sentence[0] + " " + sentence[1] + " " + sentence[2]);
                }
                pos=3;
            }catch(NumberFormatException e){ //One person
                if("You two".equals(sentence[0] + " " + sentence[1])){
                    object = "kōrua";
                    pos = 2;
                }else{
                    object = one.get(sentence[0]);
                    pos=1;
                }
            }catch(IndexOutOfBoundsException e){
                System.out.println("INVALID2");
                continue;
            }

            if(object==null){
                System.out.println("INVALID3");
                continue;
            }

            try{
                if(sentence[pos].equals("will")){
                    tense = "Ka ";
                    verb = present.get(sentence[pos+1]);
                }else if(present.get(sentence[pos])!=null){
                    tense = "Kei te ";
                    verb = present.get(sentence[pos]);
                }else if(past.get(sentence[pos])!=null){
                    tense = "I ";
                    verb = past.get(sentence[pos]);
                }
            }catch(IndexOutOfBoundsException e){
                System.out.println("INVALID4");
                continue;
            }
            
            System.out.println(tense + verb);
            
        }

        testScan.close();
    }
}
