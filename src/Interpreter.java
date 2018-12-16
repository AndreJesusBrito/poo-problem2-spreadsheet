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
                    Cell numCell;
                    if(spreadsheet.getSs().containsKey(cmdParts[1]))

                    else {
                        ICellContent num = new CellNumber(0, false);
                        Cell referencedCell = new Cell(cmdParts[1], num);
                    }

                    ICellContent pointer = new CellPointer(spreadsheet.getSs().get(cmdParts[1]));
                    Cell pointerCell = new Cell(cmdParts[0], pointer);
                    spreadsheet.setCell(cmdParts[0], pointerCell);
                }
                else { // number
                    boolean isDouble = cmdParts[1].matches("\\d*[.]\\d+") ? true : false;
                    createCellWithNumber();
                }
            }
        }
    }

    private createCellWithNumber() {
        ICellContent num = new CellNumber(Double.parseDouble(cmdParts[1]), isDouble);
        Cell c = new Cell(cmdParts[0], num);
        spreadsheet.setCell(cmdParts[0], c);
    }



    //---------------------------------------------
    // commands
    //---------------------------------------------


    private void printSsCmd() {
        System.out.println(spreadsheet.toString());
    }

    private void printCellCmd(String ref) {
        System.out.println(spreadsheet.getSs().get(ref).toString());
    }

    private void deleteCmd(String ref) {
        if(ref.matches("([A-Z]+)(\\d+)"))
            spreadsheet.delCell(ref);
        else if(ref.matches("\\d"))
            spreadsheet.delRow(ref);
        else if(ref.matches("[A-Z]"))
            spreadsheet.delCol(ref);
        else
            spreadsheet.getSs().clear();
    }
}
