public class CellSumUnary extends CellUnaryFunction {
    public CellSumUnary(ICellContent line) throws CellFunctionInvalidArgException {
        setArg1(line);
    }

    @Override
    public void setArg1(ICellContent content) throws CellFunctionInvalidArgException {
        if(content instanceof CellCollection)
            arg1 = content;
        else
            throw new CellFunctionInvalidArgException();
    }

    @Override
    public Number getValue() {
        Double counter = 0.0;

        boolean isDouble = false;
        for(ICellContent cellContent : (CellCollection) arg1) {
            Number num = (Number) cellContent.getValue();
            if(num instanceof Integer) {
                counter += num.intValue();
            } else {
                counter += num.doubleValue();
                isDouble = true;
            }
        }
        if(isDouble) {
            return counter;
        } else {
            return counter.intValue();
        }
    }

    @Override
    public String getFuncName() {
        return "SUM";
    }
}
