

import java.util.Set;

public class CellSumUnary extends CellUnaryFunction {
    protected Spreadsheet spreadsheet;
	
    public CellSumUnary(Spreadsheet spreadsheet, ICellContent line) {
    	setSpreadsheet(spreadsheet);
        setArg1(line);
    }

	@Override
    public Object getValue() {
        Double value = 0.0;
        Set<String> keySet;
        if(((String) arg1.getValue()).matches("\\d+"))
            keySet = spreadsheet.getRow((String) arg1.getValue());
        else
            keySet = spreadsheet.getCol((String) arg1.getValue());

        boolean isDouble = false;
        for(String key : keySet) {
        	Number num = (Number) spreadsheet.get(key).getValue();
        	if(num instanceof Integer) {
        		value += (Integer) num;
        	} else {
        		value += (Double) num;
        		isDouble = true;
        	}
        }
        return isDouble ? value : value.intValue();
    }

	public Spreadsheet getSpreadsheet() {
        return spreadsheet;
    }

    public void setSpreadsheet(Spreadsheet spreadsheet) {
        this.spreadsheet = spreadsheet;
    }
	
	@Override
    public void onDelete() {
        Set<String> keySet;
        if(((String) arg1.getValue()).matches("\\d+"))
            keySet = spreadsheet.getRow((String) arg1.getValue());
        else
            keySet = spreadsheet.getCol((String) arg1.getValue());
        for(String key : keySet) {
            spreadsheet.get(key).onDelete();
        }
    }
}
