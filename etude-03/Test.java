public class Test {
    public static void main(String[] args) {
        if(args[0].matches("-?[0-9]*\\.[0-9]*")) {
            System.out.println("matches");
        } else {
            System.out.println("does not match");
        }
    }
}
