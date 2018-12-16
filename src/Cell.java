public class Cell {
    private String reference;
    private int nReferences;
    private ICellContent content;

    public Cell(String reference, ICellContent content) {
        setReferences(0);
        setContent(content);
        setReference(reference);
    }

    public ICellContent getContent() {
        return content;
    }

    public void setContent(ICellContent content) {
        this.content = content;
    }

    public int getReferences() {
        return nReferences;
    }

    public void setReferences(int nReferences) {
        this.nReferences = nReferences;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isReferenced() {
        return nReferences > 0;
    }

    public void incrementRef() {
        nReferences++;
    }

    public void decrementRef() {
        nReferences--;
    }

    public Object getValue() {
        return content.getValue();
    }

    public void onDelete() {
        content.onDelete();
    }

    @Override
    public String toString() {
        return reference + " " + content.getContent() + " " + content.getValue();
    }
}
