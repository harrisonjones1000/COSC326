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
        String subject="";
        int pos = -1;
        
        String tense = "";
        String verb = "";

        boolean start, end;

        String object = "";

        Map<String, String> one = new HashMap<>();
        one.put("I", "ahau");
        one.put("You", "koe");
        one.put("He", "ia");
        one.put("She", "ia");

        one.put("me", "ahau");
        one.put("you", "ia");
        one.put("him", "ia");
        one.put("her", "ia");

        Map<String, String> two = new HashMap<>();
        two.put("We (2 incl)", "tāua");
        two.put("We (2 excl)", "māua");
        two.put("You (2 incl)", "kōrua"); 
        two.put("They (2 excl)", "rāua");

        two.put("us (2 incl)", "tāua"); 
        two.put("us (2 excl)", "māua"); 
        two.put("you (2 incl)", "kōrua"); 
        two.put("you two", "kōrua"); 
        two.put("them (2 excl)", "rāua"); 

        Map<String, String> three = new HashMap<>();
        three.put("We (3 incl)", "tātou");
        three.put("We (3 excl)", "mātou");
        three.put("You (3 incl)", "koutou"); 
        three.put("They (3 excl)", "rātou");

        three.put("us (3 incl)", "tātou"); 
        three.put("us (3 excl)", "mātou"); 
        three.put("you", "koutou"); 
        three.put("them (3 excl)", "rātou"); 

        Map<String, String> future = new HashMap<>();
        future.put("go", "haere ");
        future.put("make", "hanga ");
        future.put("see", "kite ");
        future.put("want", "hiahia ");
        future.put("call", "karanga ");
        future.put("ask", "pātai ");
        future.put("read", "pānui ");
        future.put("learn", "ako ");

        Map<String, String> present = new HashMap<>();
        present.put("going", "haere ");
        present.put("making", "hanga ");
        present.put("seeing", "kite ");
        present.put("wanting", "hiahia ");
        present.put("calling", "karanga ");
        present.put("asking", "pātai ");
        present.put("reading", "pānui ");
        present.put("learning", "ako ");

        Map<String, String> past = new HashMap<>();
        past.put("went", "haere ");
        past.put("made", "hanga ");
        past.put("saw", "kite ");
        past.put("wanted", "hiahia ");
        past.put("called", "karanga ");
        past.put("asked", "pātai ");
        past.put("read", "pānui ");
        past.put("learnt", "ako ");

        while(testScan.hasNext()){
            start=false;
            end=false;

            sentence = testScan.nextLine().split(" ");

            if (sentence[0].equals("I") | sentence[0].equals("We")) start = true;

            try{
                num = Integer.parseInt(sentence[1].substring(1));
                if(num<=1){
                    System.out.println("INVALID1");
                    continue;
                }else if(num==2){//Two people
                    subject = two.get(sentence[0] + " " + sentence[1] + " " + sentence[2]);
                }else if(num>2){//Three people
                    sentence[1] = "(3";
                    subject = three.get(sentence[0] + " " + sentence[1] + " " + sentence[2]);
                }
                pos=3;
            }catch(NumberFormatException e){ //One person
                if("You two".equals(sentence[0] + " " + sentence[1])){
                    subject = "kōrua";
                    pos = 2;
                }else{
                    subject = one.get(sentence[0]);
                    pos=1;
                }
            }catch(IndexOutOfBoundsException e){
                System.out.println("INVALID2");
                continue;
            }

            if(subject==null){
                System.out.println("INVALID3");
                continue;
            }

            try{
                if(sentence[pos].equals("will")){
                    tense = "Ka ";
                    verb = future.get(sentence[pos+1]);
                    pos+=2;
                }else if(past.get(sentence[pos])!=null){
                    tense = "I ";
                    verb = past.get(sentence[pos]);
                    pos++;
                }else if(pos==1){
                    if((sentence[pos-1].equals("I") & sentence[pos].equals("am")) | (sentence[pos-1].equals("You") & sentence[pos].equals("are")) | (((sentence[pos-1].equals("She")) | (sentence[pos-1].equals("He"))) & sentence[pos].equals("is"))){
                        tense = "Kei te ";
                        verb = present.get(sentence[pos+1]);
                        pos+=2;
                    }else{
                        System.out.println("INVALID4");
                        continue;
                    }
                }else if(sentence[pos].equals("are")){
                    tense = "Kei te ";
                    verb = present.get(sentence[pos+1]);
                    pos+=2;
                }else{
                    System.out.println("INVALID5");
                    continue;
                }
            }catch(IndexOutOfBoundsException e){
                System.out.println("INVALID6");
                continue;
            }
   
            try{
                object = sentence[pos];
                if((object.equals("me") | object.equals("us")) & start){
                    System.out.println("INVALID7"); //subject and object shouldnt be the same
                    continue;
                }else if(verb.equals("haere ") | verb.equals("pānui ") | verb.equals("ako ")){
                    System.out.println("INVALID8"); //objects for these verbs don't make sense
                    continue;
                }
            }catch(IndexOutOfBoundsException e){
                System.out.println(tense + verb + subject);
                continue;
            } 
            
            if(!object.isEmpty()){
                if(!Character.isLowerCase(object.charAt(0))){
                    System.out.println("INVALID9"); //allows us to keep using same HashMaps
                    continue; 
                }
            }

            try{
                if(sentence.length-pos == 1){
                    object = one.get(object);
                }else if(sentence.length-pos == 3){
                    num = Integer.parseInt(sentence[pos+1].substring(1));
                    if(num<=1){
                        System.out.println("INVALID11");
                        continue;
                    }else if(num==2){//Two people
                        object = two.get(object + " " + sentence[pos+1] + " " + sentence[pos+2]);

                    }else if(num>2){//Three people
                        sentence[pos+1] = "(3";
                        object = three.get(object + " " + sentence[pos+1] + " " + sentence[pos+2]);

                    }
                }else if((object + " " + sentence[pos+1]).equals("you two")){
                    object = "kōrua";
                }
            }catch(NumberFormatException e){
                System.out.println("INVALID12");
                continue; 
            }

            System.out.println(tense + verb + subject + " " + object);

        }
        testScan.close();
    }
}
