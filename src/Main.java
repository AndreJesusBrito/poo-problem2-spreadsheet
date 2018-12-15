import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        SpreadSheet ss = new SpreadSheet();
        Interpreter interp = new Interpreter(ss);


        while(scan.hasNext()) {
            interp.readInput(scan.nextLine());
        }
        scan.close();
        System.out.println(interp.run());
    }
}
