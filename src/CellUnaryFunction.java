

public abstract class CellUnaryFunction extends CellFunction {
	protected ICellContent arg1;   
  
    public ICellContent getArg1() {
        return arg1;
    }
	public void setArg1(ICellContent arg) throws CellFunctionInvalidArgException {
        this.arg1 = arg;
    }
	
    @Override
    public String getContent() {
        return super.getContent() + arg1.getContent();
    }
}
