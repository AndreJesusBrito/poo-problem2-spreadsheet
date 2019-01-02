
public abstract class CellFunction implements ICellFunction {
	public String getContent() {
		return "=" + this.getFuncName() + " ";
	}
	
	public void onDelete() {}
}
