public class Upper {
    static void main(String[] args){
        int[] a = {6,4,1};
        System.out.println(a); //25

        int[] b = {6,1,4};
        System.out.println(b); //24

        int[] c = {1,3,1,6};
        System.out.println(c); //19
    }

    public static long upper(int[] parts){ 
        long upper=0;
        int consec=0;

        

        for(int i=0; i<parts.length; i++){
            
            //upper limit calc is harder than i thought

            if(consec==0){
                if(parts[i]==1){
                    upper+=1;
                }else{
                    consec=parts[i];
                }
            }else{ 
                if(parts[i]==1){
                    if(upper==0){ 
                        consec++;
                    }else{
                        upper+=consec+1;
                        consec=0; 
                    }
                }else{
                    consec=consec*parts[i];
                }
            }
        }
        upper+=consec;
        return upper;
    }
}
