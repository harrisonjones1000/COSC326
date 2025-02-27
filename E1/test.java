package E1;

import java.io.*;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        try{
            Scanner testScan = new Scanner(new File("E1/E1_tests.txt"));
            int mailbox = 0;
            String print = "";

            while(testScan.hasNext()){
                String email = testScan.nextLine();
                mailbox=0;
                print = "";
                
                for(int i = 0; i<email.length(); i++){
                    if(!Character.isLetterOrDigit(email.charAt(i))){ //if not alphanumeric

                        if(i==0){ //if nonalphanumeric is first symbol
                            System.out.println(email + " <-- Email cannot start with that symbol");
                            break;

                        }else if(".-_@".indexOf(email.charAt(i))!=-1){ //if character is an allowed non alphanumeric
                            try{
                                if(".-_@".indexOf(email.charAt(i+1))!=-1){ //if any of these characters are next to eachother
                                    System.out.println(email + " <-- Email cannot have repeating non alphanumeric characters");
                                    break;
                                }else if(email.charAt(i)=='@'){
                                    mailbox+=1;
                                    print+="@";
                                    if(mailbox>1){
                                        System.out.println(email + " <-- Email cannot have multiple @ symbols");
                                        break;
                                    }
                                }else if(email.charAt(i)=='_'){
                                    if(email.substring(i,i+4).equals("_at_")){
                                        mailbox+=1;
                                        i+=3;
                                        print+="@";
                                    }else if(email.substring(i,i+5).equals("_dot_")){
                                        i+=4;
                                        print+=".";
                                    }
                                }

                            }catch (IndexOutOfBoundsException e){
                                System.out.println(email + " <-- Email is not complete");
                            }

                        }else{ //if character isnt an allowed alphanumeric
                            System.out.println(email + " <-- Email does not allow the character at position " + i );
                        }
                    }else{
                        print += email.substring(i,i+1).toLowerCase(); //adds lowercase alphanumeric
                    }
                    

                }

                System.out.println(print);

            }
            testScan.close();
        }catch(FileNotFoundException e){
            System.out.println("No file of that name found");
        }
    }
    
}