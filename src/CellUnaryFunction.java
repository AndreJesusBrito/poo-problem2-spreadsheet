
import java.util.Set;

public abstract class CellUnaryFunction extends CellFunction {
	protected String arg1;
    protected Spreadsheet spreadsheet;
    
    @Override
    public void onDelete() {
        Set<String> keySet;
        if(arg1.matches("\\d+"))
            keySet = spreadsheet.getRow(arg1);
        else
            keySet = spreadsheet.getCol(arg1);
        for(String key : keySet) {
            spreadsheet.get(key).onDelete();
        }
    }
    
    @Override
    public Number getValue() {
        double value = 0.0;
        Set<String> keySet;
        if(arg1.matches("\\d+"))
            keySet = spreadsheet.getRow(arg1);
        else
            keySet = spreadsheet.getCol(arg1);
        for(String key : keySet) {
            value += ((Number) spreadsheet.get(key).getValue()).doubleValue();
        }
        if(isDouble())
            return value;
        else
            return (int) value;
    }

    
    
    public CellUnaryFunction(Spreadsheet spreadsheet, String line) {
    	setSpreadsheet(spreadsheet);
        setLine(line);
    }
    
    public void setLine(String reference) {
        this.arg1 = new String(reference);
    }

    public Spreadsheet getSpreadsheet() {
        return spreadsheet;
    }

    public void setSpreadsheet(Spreadsheet spreadsheet) {
        this.spreadsheet = spreadsheet;
    }

    @Override
    public String getContent() {
        return super.getContent() + arg1;
    }

    
    
    
    public String getArg1() {
        return arg1;
    }
    
    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }
    
    
    @Override
    public boolean isDouble() {
        boolean isDouble = false;
        Set<String> keySet;
        if(arg1.matches("\\d+"))
            keySet = spreadsheet.getRow(arg1);
        else
            keySet = spreadsheet.getCol(arg1);

        for(String key : keySet) {
            isDouble = isDouble || spreadsheet.get(key).getContent().isDouble();
        }
        return isDouble;
    }

}
