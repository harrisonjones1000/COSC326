public class CaseGen {
    public static void main(String[] args){

        for (int s = 0; s < 3; s++) { // subject
            String[] subjects;
        
            if (s == 0) {
                subjects = new String[]{"I", "You", "He", "She"};
            } else if (s == 2) {
                subjects = new String[]{"We (2 incl)", "We (2 excl)", "You (2 incl)", "You two", "They (2 excl)"};
            } else {
                subjects = new String[]{"We (3 incl)", "We (3 excl)", "You (3 incl)", "They (3 excl)"};
            }
        
            for (int ss = 0; ss < subjects.length; ss++) {
                String starter = subjects[ss];
        
                for (int o = 0; o < 4; o++) { // object
                    String[] objects;
                    if (o == 0) {
                        objects = new String[]{""};
                    } else if (o == 1) {
                        objects = new String[]{"me", "you", "him", "her"};
                    } else if (o == 2) {
                        objects = new String[]{"us (2 incl)", "us (2 excl)", "you two", "you (2 incl)", "them (2 excl)"};
                    } else {
                        objects = new String[]{"us (3 incl)", "us (3 excl)", "you (3 incl)", "them (3 excl)"};
                    }
        
                    for (int t = 0; t < 3; t++) { // tense
                        String phraseStarter = starter;
        
                        if (t==1) {
                            if (phraseStarter.equals("I")) {
                                phraseStarter = "I am";
                            } else if (phraseStarter.equals("He") || phraseStarter.equals("She")) {
                                phraseStarter = "He is";
                            } else {
                                phraseStarter = phraseStarter + " are";
                            }
                        }
        
                        String[] verbs;
                        if(t == 0){ // past
                            verbs = new String[]{"went", "made", "wanted", "saw", "called", "asked", "read", "learnt"};
                        }else if (t == 1){ // present
                            verbs = new String[]{"going", "making", "seeing", "wanting", "calling", "asking", "reading", "learning"};
                        }else{ // future
                            verbs = new String[]{"will go", "will make", "will see", "will want", "will call", "will ask", "will read", "will learn"};
                        }
        
                        for(int oo = 0; oo < objects.length; oo++) {
                            String end = objects[oo];
        
                            for(int v = 0; v < verbs.length; v++){
                                System.out.println(phraseStarter + " " + verbs[v] + " " + end);
                            }
                        }
                    }
                }
            }
        }        
    }
}
