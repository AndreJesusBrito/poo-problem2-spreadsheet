
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

    /**Returns the output of the executed commands
     * @return the output of the interpreted input as a String
     */
    public String getOutput() {
        return output.trim();
    }

    /**Adds a command to the commands Queue
     * @param cmd String to be added to the commands Queue
     */
    public void readInput(String cmd) {
    	commands.add(cmd);
    }

    /**Executes the commands Queue*/
    public void run() {
        for(String cmd : commands)
            exeCmd(cmd);
    }

    /**Executes one command
     * @param in command to be executed
     */
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
                    Cell c = new Cell(cmdParts[0], parser.startParsing());
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

    /**Adds the spreadsheet toString to the output*/
    private void printSsCmd() {
        String out = spreadsheet.toString();
        output += out + "\n";
    }

    /**Adds the Cell toString to the output
     * @param ref key of the Cell
     */
    private void printCellCmd(String ref) {
        boolean checkExist = spreadsheet.containsKey(ref);
        String out = spreadsheet.getCell(ref).toString();
        output += out + "\n";
        if(!checkExist)
            spreadsheet.delCell(ref);
    }

    /**Deletes a row, column, cell, or the entire spreadsheet
     * @param ref String that indicates either a row, column, cell or is empty
     */
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
