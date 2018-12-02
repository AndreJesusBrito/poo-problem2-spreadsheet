public class Cell {
    private int referenceCount;
    private ICellContent content;

    public Cell(Number n) {
        setContent(new CellNumber(n));
    }
    public Cell(ICellContent content) {
        referenceCount = 0;
        setContent(content);
    }

    public setContent() {

    }

    public Object getValue() {
        return content.getValue();
    }

    public String getContent() {
        return content.getContent();
    }

    public boolean isReferenced() {
        return referenceCount > 0;
    }
}
