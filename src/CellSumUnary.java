import java.util.Set;

public class CellSumUnary implements ICellContent {
    private String line;
    private Spreadsheet spreadsheet;
    
    public CellSumUnary(String line, Spreadsheet spreadsheet) {
        setLine(line);
        setSpreadsheet(spreadsheet);
    }
    
    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = new String(line);
    }

    public Spreadsheet getSpreadsheet() {
        return spreadsheet;
    }

    public void setSpreadsheet(Spreadsheet spreadsheet) {
        this.spreadsheet = spreadsheet;
    }

    @Override
    public Number getValue() {
        double value = 0.0;
        Set<String> keySet;
        if(line.matches("\\d+"))
            keySet = spreadsheet.getRow(line);
        else
            keySet = spreadsheet.getCol(line);
        for(String key : keySet) {
            value += (Double) spreadsheet.get(key).getValue();
        }
        if(isDouble())
            return value;
        else
            return (int) value;
    }

    @Override
    public String getContent() {
        String content = "=SUM ";
        Set<String> keySet;
        if(line.matches("\\d+"))
            keySet = spreadsheet.getRow(line);
        else
            keySet = spreadsheet.getCol(line);
        for(String key : keySet) {
            content += spreadsheet.get(key).getContent().getContent() + " ";
        }
        content = content.trim();
        return content;
    }

    @Override
    public void onDelete() {
        Set<String> keySet;
        if(line.matches("\\d+"))
            keySet = spreadsheet.getRow(line);
        else
            keySet = spreadsheet.getCol(line);
        for(String key : keySet) {
            spreadsheet.get(key).onDelete();
        }
    }
    
    @Override
    public boolean isDouble() {
        boolean isDouble = false;
        Set<String> keySet;
        if(line.matches("\\d+"))
            keySet = spreadsheet.getRow(line);
        else
            keySet = spreadsheet.getCol(line);
        for(String key : keySet) {
            isDouble = isDouble || spreadsheet.get(key).getContent().isDouble();
        }
        return isDouble;
    }
}
