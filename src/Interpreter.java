import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Interpreter {
    private Spreadsheet spreadsheet;
    private Queue<String> commands = new LinkedList<String>();
    private String output = "";

    public Interpreter(Spreadsheet spreadsheet) {
        setSs(spreadsheet);
    }

    public Spreadsheet getSs() {
        return spreadsheet;
    }

    public void setSs(Spreadsheet spreadsheet) {
        this.spreadsheet = spreadsheet;
    }

    public String getOutput() {
        return output.trim();
    }

    public void readInput(String cmd) {
    	commands.add(cmd);
    }

    public void run() {
        for(String cmd : commands)
            exeCmd(cmd);
    }

    private void exeCmd(String in) {
        String[] cmdParts = in.split("\\s+");

        if(cmdParts[0].equals("p*")) {
            printSsCmd();
        } else if(cmdParts[0].equals("d*")) {
            deleteCmd(cmdParts.length > 1 ? cmdParts[1] : "");
        } else {
            if(cmdParts.length == 1) {
                printCellCmd(cmdParts[0]);
            } else {
                try {
                    String[] test = Arrays.copyOfRange(cmdParts, 1, cmdParts.length);
                    Parser parser = new Parser(spreadsheet, test);
                    Cell c = new Cell(cmdParts[0], (ICellContent) parser.expr(0));
                    spreadsheet.setCell(cmdParts[0], c);
                } catch(UnsupportedTokenTypeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //---------------------------------------------
    // commands
    //---------------------------------------------


    private void printSsCmd() {
        String out = spreadsheet.toString();
        output += out + "\n";
    }

    private void printCellCmd(String ref) {
        String out = spreadsheet.get(ref).toString();
        output += out + "\n";
    }

    private void deleteCmd(String ref) {
        if(ref.matches("([A-Z]+)(\\d+)")) {
            spreadsheet.delCell(ref);
        }
        else if(ref.matches("\\d+")) {
            spreadsheet.delRow(ref);
        }
        else if(ref.matches("[A-Z]+")) {
            spreadsheet.delCol(ref);
        }
        else {
            spreadsheet.clear();
        }
    }
}
