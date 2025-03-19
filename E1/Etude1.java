//Harrison Jones

import java.util.Scanner;

public class Etude1 {
    public static void main(String[] args) {
        Scanner testScan = new Scanner(System.in);
        while (testScan.hasNext()) {
            System.out.print(method(testScan.nextLine()));
        }
        testScan.close();
    }

    public static String method(String email){
        int mailbox = -1; //location of @ symbol index
        String print = "";
        String emailCopy = "";
        int count = email.length() - email.replace("@", "").length(); //the number of @ symbols

        if(count>1){
            return email + " <-- Email cannot have multiple @ symbols\n";
        }else if(count==0){ //Handles no @ and adding _at_
            for(int i=0; i<email.length(); i++){
                try{
                    if(email.substring(i,i+4).equals("_at_")){
                        if(mailbox==-1){
                            i+=3;
                            emailCopy+="@";
                            mailbox=emailCopy.length()-1;
                        }else{
                            emailCopy = emailCopy.substring(0, mailbox) + "_at_" + emailCopy.substring(mailbox+1, emailCopy.length()) + "@";
                            i+=3;
                            mailbox=emailCopy.length()-1;
                        }
                    }else{
                        emailCopy+=email.charAt(i);
                    }
                }catch(IndexOutOfBoundsException e){
                    emailCopy+=email.charAt(i);
                }
            }
            if(mailbox==-1) return email + " <-- Email must have an @ symbol\n";
            mailbox=-1;
        }else{//email has 1 @
            emailCopy=email;
        }

        //If gotten to this point, emailCopy is our email with 1 @ in it

        for(int i = 0; i<emailCopy.length(); i++){
            if(!Character.isLetterOrDigit(emailCopy.charAt(i))){ //if not alphanumeric
                if(i==0){ //if nonalphanumeric is first symbol
                    return email + " <-- Email cannot start with that symbol\n";

                }else if(".-_@[".indexOf(emailCopy.charAt(i))!=-1){ //if character is an allowed non alphanumeric
                    if(emailCopy.charAt(i)=='['){
                        if(print.endsWith("@") && emailCopy.endsWith("]")){
                            //if valid domain
                            String domain = validIPv4(emailCopy.substring(i+1, emailCopy.length()-1));
                            if(domain.equals("")){
                                return email + " <-- Invalid domain\n"; //else invalid domain
                            }else{
                                return print + "[" + domain + "]\n";
                            }                            
                        }else if(mailbox==-1){ //if '[' in mailbox name
                            return email + " <-- Email does not allow square brackets in the mailbox name\n";
                        }else{ //if '[' somewhere in domain / domain extension
                            return email + " <-- Invalid domain\n";
                        }

                    }else if(".-_@".indexOf(print.charAt(print.length()-1))!=-1){ //if any of these characters are next to eachother
                        return email + " <-- Email cannot have repeating non alphanumeric characters\n";
                    
                    }else if(emailCopy.charAt(i)=='@'){ 
                        mailbox=print.length();
                        print+="@";
                    }else if(emailCopy.charAt(i)=='-'){
                        if(mailbox!=-1) return email + " <-- Email domain cannot use hyphens\n";
                        print+="-";
                    }else if(emailCopy.charAt(i)=='.'){
                        print+=".";
                    }else{  
                        print += "_";
                    }

                }else{ //if character isnt an allowed alphanumeric
                    return email + " <-- Email does not allow the character at position " + i +"\n";
                }
            }else{
                print += emailCopy.substring(i,i+1).toLowerCase(); //adds lowercase alphanumeric
            }
        }

        for(int i=0; i<print.length()-4; i++){
            
            try{
                if(print.substring(i,i+5).equals("_dot_")){ 
                    print = print.substring(0, i) + "." + print.substring(i+5,print.length());
                    
                }else if(i>mailbox & print.charAt(i)=='_'){
                    return email + " <-- Email domain cannot use underscores\n";
                }
            }catch(IndexOutOfBoundsException e){}
        }

        if(!(print.endsWith("co.nz")||print.endsWith("com.au")||print.endsWith("co.ca")||print.endsWith("com")||print.endsWith("co.us")||print.endsWith("co.uk"))){
            return email + " <-- Invalid Domain Extension\n";
        }

        if(!(print.endsWith(".co.nz")||print.endsWith(".com.au")||print.endsWith(".co.ca")||print.endsWith(".com")||print.endsWith(".co.us")||print.endsWith(".co.uk"))){
            return email + " <-- Dot must be after the domain extension and be after the domain name\n";
        }

        return print + "\n";
    
    }

    public static String validIPv4(String ip){
        for(int i=0; i<ip.length()-3; i++){
            try{
                if(ip.substring(i,i+5).equals("_dot_")){ 
                    ip = ip.substring(0, i) + "." + ip.substring(i+5,ip.length());
                    i+=4;
                }
            }catch(IndexOutOfBoundsException e){}
        }

        String[] parts = ip.split("\\.");

        if (parts.length != 4) return "";

        for(int i=0; i<4; i++){
            try{
                int n = Integer.parseInt(parts[i]);
                if(n<0 || n>255) return "";
            }catch(NumberFormatException e){
                return "";
            } 
        }

        return ip;
    }
    
}