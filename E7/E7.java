import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class E7 {
    public static void main(String[] args){

        Map<String, String> verbsPast = new HashMap<>();
        verbsPast.put("went", "haere");
        verbsPast.put("made", "hanga");
        verbsPast.put("saw", "kite");
        verbsPast.put("wanted", "hiahia");
        verbsPast.put("called", "karanga");
        verbsPast.put("asked", "patai");
        verbsPast.put("read", "panui");
        verbsPast.put("learnt", "ako");

        Map<String, String> verbsPresent = new HashMap<>();
        verbsPresent.put("going", "haere");
        verbsPresent.put("making", "hanga");
        verbsPresent.put("seeing", "kite");
        verbsPresent.put("wanting", "hiahia");
        verbsPresent.put("calling", "karanga");
        verbsPresent.put("asking", "patai");
        verbsPresent.put("reading", "panui");
        verbsPresent.put("learning", "ako");

        Map<String, String> verbsFuture = new HashMap<>();
        verbsFuture.put("go", "haere");
        verbsFuture.put("make", "hanga");
        verbsFuture.put("see", "kite");
        verbsFuture.put("want", "hiahia");
        verbsFuture.put("call", "karanga");
        verbsFuture.put("ask", "patai");
        verbsFuture.put("read", "panui");
        verbsFuture.put("learn", "ako");

        Map<String, String> starters = new HashMap<>();
        starters.put("We (2 incl)", "taua"); //have, are, will
        starters.put("We (3 incl)", "tatou"); //have, are, will

        starters.put("We (2 excl)", "maua"); //have, are, will
        starters.put("We (3 excl)", "matou"); //have, are, will

        starters.put("You", "koe"); //have, are, will
        starters.put("You (2 excl)", "korua"); //have, are, will
        starters.put("You (3 excl)", "koutou"); //have, are, will

        starters.put("They (2 excl)", "raua"); //have, are, will
        starters.put("They (3 excl)", "ratou"); //have, are, will

        starters.put("I", "au"); //have, am, will

        starters.put("He", "ia"); //has, is, will
        starters.put("She", "ia"); //has, is, will

        String object = "";
        String[] sentence;
        int pos;
        int tense;
        String verb;
        String[] a = {"I ", "Kei te ", "Ka "};

        Scanner testScan = new Scanner(System.in);
        while (testScan.hasNext()){
            sentence = testScan.nextLine().split(" ");

            try{
                if(starters.get(sentence[0]+" "+sentence[1]+" "+sentence[2])!=null){
                    object=starters.get(sentence[0]+" "+sentence[1]+" "+sentence[2]);
                    pos=3;
                }else if(starters.get(sentence[0])!=null){
                    object=starters.get(sentence[0]);
                    pos=1;
                }else{
                    System.out.println("INVALID");
                    continue;
                }
                  
            }catch(IndexOutOfBoundsException e){
                if(starters.get(sentence[0])!=null){
                    object=starters.get(sentence[0]);
                    pos=1;
                }else{
                    System.out.println("INVALID");
                    continue;
                }
            }

            
            verb = sentence[sentence.length-1];

            if(verbsPast.get(verb)!=null){ //past
                tense=0;
                verb = verbsPast.get(verb);

                if(!(pos==sentence.length-1)){
                    System.out.println("INVALID");
                    continue;
                }else{

                }

            }else if(verbsPresent.get(verb)!=null){ //present
                tense=1;
                verb = verbsPresent.get(verb);

                if(object.equals("au")){ //am
                    if(!sentence[pos].equals("am") || sentence.length-2!=pos){
                        System.out.println("INVALID");
                        continue;
                    }
                }else if(object.equals("ia")){ //is
                    if(!sentence[pos].equals("is") || sentence.length-2!=pos){
                        System.out.println("INVALID");
                        continue;
                    }
                }else{ //are
                    if(!sentence[pos].equals("are") || sentence.length-2!=pos){
                        System.out.println("INVALID");
                        continue;
                    }
                }

            }else if(verbsFuture.get(verb)!=null){ //Future done
                tense=2;
                verb = verbsFuture.get(verb);

                if(!sentence[pos].equals("will") || sentence.length-2!=pos){
                    System.out.println("INVALID");
                    continue;
                }

            }else{
                System.out.println("INVALID");
                continue;
            }

            System.out.println(a[tense] + verb + " " + object);

        }
        testScan.close();
    }
}