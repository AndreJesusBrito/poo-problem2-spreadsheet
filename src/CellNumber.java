public class CellNumber implements ICellContent {
    private double n;
    private String numType;
    public CellNumber(double n, String type) {
        this.n = n;
        this.numType = type;
    }

    public Object getValue() {
        return n;
    };

    public String getContent() {
        return numType.equals("double") ? Double.toString(n) : Integer.toString((int) n);
    };

    public void onDelete() {};
}
