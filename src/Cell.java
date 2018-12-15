public class Cell {
    private int referenceCount;
    private ICellContent content;

    public Cell(double n) {
        referenceCount = 0;
        setContent(new CellNumber(n, "CHANGE HERE"));
    }
    public Cell(ICellContent content) {
        referenceCount = 0;
        setContent(content);
    }

    public void setContent(ICellContent content) {
        this.content = content;
    }

    public Object getValue() {
        return content.getValue();
    }

    public String getContent() {
        return content.getContent();
    }

    public void onDelete() {
        content.onDelete();
    }

    public boolean isReferenced() {
        return referenceCount > 0;
    }
}
