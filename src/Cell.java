public class Cell {
    private String key;
    private int nReferences;
    private ICellContent content;

    public Cell(String key, ICellContent content) {
        this.nReferences = 0;
        this.content = content;
        this.key = key;
    }

    public ICellContent getContent() {
        return content;
    }

    public void setContent(ICellContent content) {
        this.content.onDelete();
    	this.content = content;
    }

    public int getReferences() {
        return nReferences;
    }

    public String getKey() {
        return key;
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
        return key + " " + content.getContent() + " " + getValue();
    }
}
