import java.util.Random;

public class CaseGen {
    public static void main(String[] args){
        String print;
        Random rs = new Random();

        String starter;
        String end;

        for(int s=0; s<3; s++){ // subject
            String[] subjects;
        
            if(s==0){
                subjects = new String[]{"I", "You", "He", "She"};
            } else if(s==2){
                subjects = new String[]{"We (2 incl)", "We (2 excl)", "They (2 excl)", "You two"};
            } else {
                subjects = new String[]{"We (3 incl)", "We (3 excl)", "They (3 excl)", "You (3 excl)"};
            }

            for(int ss=0; ss<subjects.length; ss++){
                starter = subjects[ss];

                for(int o=0; o<4; o++){ //object
                    String[] objects;
                    if(o==0){
                        objects = new String[]{""};
                    }else if(o==1){
                        objects = new String[]{"me", "you", "him", "her"};
                    }else if(o==2){
                        objects = new String[]{"you two", "them"};
                    }else{
                        objects = new String[]{"us", "you", "them"};
                    }
                    
                    for(int oo=0; oo<objects.length; oo++){
                        end = objects[oo];

                        for(int t=0; t<3; t++){//tense
                            String[] verbs;

                            //go, make, see, want, call, ask, read, learn
                            if(t==0){//past
                                verbs = new String[]{"went", "made", "wanted", "saw", "called", "asked", "read", "learnt"};
                            }else if(t==1){ //present
                                verbs = new String[]{"go", "make", "see", "want", "call", "ask", "read", "learn"};
                            }else{ //future
                                verbs = new String[]{"will go", "will make", "will see", "will want", "will call", "will ask", "will read", "will learn"};
                            }

                            for(int v=0; v<8; v++){
                                if(starter.equals(end)){
                                    System.out.println("err");
                                }else{
                                    //System.out.println(starter + " " + verbs[v] + " " + end + " : " + s + " " + ss + " " + o + " " + oo);
                                    System.out.println(starter + " " + verbs[v] + " " + end);

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
