
public class Cell {
    private String key;
    private int nReferences;
    private ICellContent content;

    /** Constructor for Cell objects
     * The constructor initializes the Cell with nReferences = 0,
     * since when a Cell is created it doesn't have any references.
     * @param key coordinates in the spreadsheet where the cell is.
     * @param content content to initialize the new cell.
     */
    public Cell(String key, ICellContent content) {
        this.nReferences = 0;
        this.content = content;
        this.key = key;
    }

    public ICellContent getContent() {
        return content;
    }

    /** Changes the current content with a new one
     *  Calls onDelete event since the old content goes out of the user scope
     */
    public void setContent(ICellContent content) {
        this.content.onDelete();
        this.content = content;
    }

    /**
     * @return returns the number of references to this cell
     */
    public int getReferences() {
        return nReferences;
    }

    public String getKey() {
        return key;
    }

    /** Check is the cell is referenced by another cell or not
     * @return <code>true</code> if it is referenced, <code>false</code> if not
     */
    public boolean isReferenced() {
        return nReferences > 0;
    }

    /** Increases the amount of times this cell is referenced*/
    public void incrementRef() {
        nReferences++;
    }

    /** Decreases the amount of times this cell is referenced*/
    public void decrementRef() {
        nReferences--;
    }

    /** Returns the cell's value, which is stored in its content
     * @return the value as an Object
     */
    public Object getValue() {
        return content.getValue();
    }

    /** Event method, called when the cell is removed from the spreadsheet */
    public void onDelete() {
        content.onDelete();
    }

    @Override
    public String toString() {
        return key + " " + content.getContentString() + " " + getValue();
    }
}
