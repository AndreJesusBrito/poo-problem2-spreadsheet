import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Spreadsheet ss = new Spreadsheet();
        Interpreter interp = new Interpreter(ss);
        while(scan.hasNext()) {
            String in = scan.nextLine();
            interp.readInput(in);
        }
        scan.close();
        interp.run();
        System.out.println(interp.getOutput());
    }
}
