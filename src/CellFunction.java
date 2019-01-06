
public abstract class CellFunction implements ICellFunction {
    public String getContentString() {
        return "=" + this.getFuncName() + " ";
    }

    public void onDelete() {}
}
