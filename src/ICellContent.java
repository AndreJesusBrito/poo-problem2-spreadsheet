/** Interface used to represent the content that goes inside each cell.
 * Its also used to represent subcontent of other ICellContent, e.g.: arguments in functions
 */
public interface ICellContent {
    /** Returns the value of this instance
     * @return value of the instance, used in functions.
     * The return type varies of the content type that implements the interface.
     */
    public Object getValue();

    /** Returns the content as a String
     * @return content as a String
     */
    public String getContentString();

    /** Event method called when the instance is removed from the cell */
    public void onDelete();
}
