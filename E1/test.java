package E1;

import java.io.*;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        try{
            Scanner testScan = new Scanner(new File("E1/E1_tests.txt"));
            while(testScan.hasNext()) System.out.println(method(testScan.nextLine()));
            testScan.close();

        }catch(FileNotFoundException e){
            System.out.println("No file of that name found");
        }
    }

    public static String method(String email){
        int mailbox = -1;
        String print = "";

        for(int i = 0; i<email.length(); i++){
            if(!Character.isLetterOrDigit(email.charAt(i))){ //if not alphanumeric
                if(i==0){ //if nonalphanumeric is first symbol
                    return email + " <-- Email cannot start with that symbol";

                }else if(".-_@[".indexOf(email.charAt(i))!=-1){ //if character is an allowed non alphanumeric
                    if(email.charAt(i)=='['){
                        if(print.endsWith("@") && email.endsWith("]")){
                            
                            if(validIPv4(email.substring(i+1, email.length()-1))) return print + email.substring(i, email.length());
                            
                            return email + " <-- Invalid domain";
                            
                        }else if(mailbox==-1){ //if '[' in mailbox name
                            return email + " <-- Email does not allow square brackets in the mailbox name";
                        }else{ //if '[' somewhere in domain / domain extension
                            return email + " <-- Invalid domain";
                        }

                    }else if(".-_@".indexOf(print.charAt(print.length()-1))!=-1){ //if any of these characters are next to eachother
                        return email + " <-- Email cannot have repeating non alphanumeric characters";
                    
                    }else if(email.charAt(i)=='@'){ 
                        if(mailbox!=-1){ //if multiple @ symbols
                            return email + " <-- Email cannot have multiple @ symbols";
                        }
                        mailbox=print.length();
                        print+="@";
                    }else if(email.charAt(i)=='-'){
                        if(mailbox!=-1) return email + " <-- Email domain cannot use hyphens";
                        print+="-";
                    }else if(email.charAt(i)=='.'){
                        print+=".";
                    }else{ //symbol is "_"
                        try{
                            if(email.substring(i,i+4).equals("_at_")){
                                if(mailbox!=-1) return email + " <-- Email cannot have multiple @ symbols";
                                mailbox=print.length();
                                i+=3;
                                print+="@";
                            }else if(email.substring(i,i+5).equals("_dot_")){
                                i+=4;
                                print+=".";
                            }else if(mailbox!=-1){
                                return email + " <-- Email domain cannot use underscores";
                            }else{
                                print += "_";
                            }
                            
    
                        }catch (IndexOutOfBoundsException e){
                            if(mailbox==-1) return email + " <--- Email requires an @ symbol";
                            return email + " <-- Email domain is incomplete";
                        }
                    }
                    

                }else{ //if character isnt an allowed alphanumeric
                    return email + " <-- Email does not allow the character at position " + i;
                }
            }else{
                print += email.substring(i,i+1).toLowerCase(); //adds lowercase alphanumeric
            }
        }
        if(mailbox==-1) return print + " <-- Email requires an @ symbol";

        if(print.endsWith("com.au")||print.endsWith("co.au")||print.endsWith("co.ca")||print.endsWith("co.nz")||print.endsWith("co.uk")||print.endsWith("com")){
            if(print.substring(mailbox, print.length()-3).indexOf(".")!=-1) return print;
            return print + "<-- Email requires domain name";
        }else{
            return print + " <-- Invalid Extension";
        }
    }

    public static boolean validIPv4(String ip){
        String[] parts = ip.split("\\.");

        if (parts.length != 4) return false;

        for(int i=0; i<4; i++){
            try{
                int n = Integer.parseInt(parts[i]);
                if(n<0 || n>255) return false;
            }catch(NumberFormatException e){
                return false;
            } 
        }

        return true;
    }
    
}