

import java.util.Set;

public class CellSumUnary extends CellUnaryFunction {
    protected Spreadsheet spreadsheet;
	
    public CellSumUnary(Spreadsheet spreadsheet, ICellContent line) {
    	setSpreadsheet(spreadsheet);
        setArg1(line);
    }

    @Override
    public void setArg1(ICellContent content) {
    	if(content instanceof CellCollection)
    		arg1 = content;
//    	else
//    		throw new Exception();
    }

    @Override
    public Object getValue() {
	  Double result = 0.0;
	
	  boolean isDouble = false;
	  for(ICellContent cellContent : (CellCollection) arg1) {
	  	Number num = (Number) cellContent.getValue();
	  	if(num instanceof Integer) {
	  		result += num.intValue();
	  	} else {
	  		result += num.doubleValue();
	  		isDouble = true;
	  	}
	  }
      return isDouble ? result : result.intValue();
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
