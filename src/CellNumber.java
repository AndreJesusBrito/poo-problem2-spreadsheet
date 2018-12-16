public class CellNumber implements ICellContent {
    private double n;
    private boolean isDouble;
    public CellNumber(double n, boolean isDouble) {
        this.n = n;
        this.isDouble = isDouble;
    }

    public Object getValue() {
        return n;
    };

    public String getContent() {
        return isDouble ? Double.toString(n) : Integer.toString((int) n);
    };

    public void onDelete() {};
}
