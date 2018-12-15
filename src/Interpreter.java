public class Interpreter {
    private SpreadSheet ss;
    private Queue commands = new Queue<String>();
    public Interpreter(SpreadSheet ss) {
        this.settSpreadSheet(ss);
    }

    public void readInput(String in) {
        commands.add(in);
    }

    public SpreadSheet getSpreadSheet() {
        return ss;
    }

    public void settSpreadSheet() {
        this.ss = ss;
    }

    public String run() {
        out = "";
        for(String cmd : commands) {
            out += exeCmd(cmd) + "\n";
        }
        return out;
    }

    private String exeCmd(String in) {
        String[] cmdParts = ola.split(" ");

        if(cmdParts[0].equals("p*")) {
            printCmd();
            return ss.toString();
        } else if(cmdParts[0].equals("d*")) {
            deleteCmd();
        } else {
            //set cell
            if(cmdParts.length == 0) {
                // print cell
            } else if(formula) {

            } else if(reference) {

            } else {
                // value
            }
        }
    }


    private void deleteCmd() {

    }
    private void printCmd() {

    }
}
