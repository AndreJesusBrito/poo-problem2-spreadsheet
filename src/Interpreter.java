import java.util.LinkedList;
import java.util.Queue;

public class Interpreter {
    private Spreadsheet spreadsheet;
    private Queue<String> commands = new LinkedList<String>();

    public Interpreter(Spreadsheet spreadsheet) {
        setSs(spreadsheet);
    }

    public Spreadsheet getSs() {
        return spreadsheet;
    }

    public void setSs(Spreadsheet spreadsheet) {
        this.spreadsheet = spreadsheet;
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
                /*if(formula) {
                    // send help!
                    // spreadsheet.setCell(cmdParts[0], cmdParts);
                }
                else*/
                if(cmdParts[1].matches("[A-Z]+\\d+")) { // reference
                    if(!spreadsheet.containsKey(cmdParts[1])) {
                        createCellWithNumber(cmdParts[1], 0, false);
                    }
                    createCellWithPointer(cmdParts[0], cmdParts[1]);
                }
                else { // number
                    boolean isDouble = cmdParts[1].matches("\\d*[.]\\d+") ? true : false;
                    createCellWithNumber(cmdParts[0], Double.parseDouble(cmdParts[1]), isDouble);
                }
            }
        }
    }

    private void createCellWithNumber(String key, double value, boolean isDouble) {
        ICellContent num = new CellNumber(value, isDouble);
        Cell c = new Cell(key, num);
        spreadsheet.setCell(key, c);
    }

    private void createCellWithPointer(String key, String ref) {
        ICellContent pointer = new CellPointer(spreadsheet.get(ref));
        Cell c = new Cell(key, pointer);
        spreadsheet.setCell(key, c);
    }

    //---------------------------------------------
    // commands
    //---------------------------------------------


    private void printSsCmd() {
        System.out.println(spreadsheet.toString());
    }

    private void printCellCmd(String ref) {
        System.out.println(spreadsheet.get(ref).toString());
    }

    private void deleteCmd(String ref) {
        if(ref.matches("([A-Z]+)(\\d+)")) {
            spreadsheet.delCell(ref);
        }
        else if(ref.matches("\\d")) {
            spreadsheet.delRow(ref);
        }
        else if(ref.matches("[A-Z]")) {
            spreadsheet.delCol(ref);
        }
        else {
            spreadsheet.clear();
        }
    }
}
