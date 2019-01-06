
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.SortedMap;

public class Spreadsheet {
    private SortedMap<String, Cell> spreadsheet;

    /** Constructor for class spreadsheet
    * it creates the spreadsheet as a TreeMap with String keys and Cell contents,
    * using a comparator to sort the values correctly.
    */
    public Spreadsheet() {
        spreadsheet = new TreeMap<String, Cell>(new Comparator<String>() {
            @Override
            public int compare(String key1, String key2) {
                String pattern = "([A-Z]+)(\\d+)";
                String key1Col = key1.replaceAll(pattern, "$1");
                String key1Row = key1.replaceAll(pattern, "$2");
                String key2Col = key2.replaceAll(pattern, "$1");
                String key2Row = key2.replaceAll(pattern, "$2");
                return key1Row.equals(key2Row)
                        ? key1Col.length() == key2Col.length()
                            ? key1Col.compareTo(key2Col)
                            : key1Col.length() - key2Col.length()
                        : key1Row.length() == key2Row.length()
                            ? key1Row.compareTo(key2Row)
                            : key1Row.length() - key2Row.length();
            }
        });
    }

    /** Creates a new cell with the value of 0 (Integer)
     *   @param key coordinates of the new cell in the spreadsheet
     */
    public void initCell(String key) {
        setCell(key, new Cell(key, new CellNumber<Integer>(0)));
    }


    /** Returns the cell in the spreadsheet with the same key
     *  @param key coordinates of the cell in the spreadsheet
     *  @return returns matched cell
     */
    public Cell getCell(String key) {
        if(!spreadsheet.containsKey(key)) {
            initCell(key);
        }
        return spreadsheet.get(key);
    }

    /** Sets the cell specified by the key
     *  @param key coordinates of the cell in the spreadsheet
     *  @param c new cell to set.
     */
    public void setCell(String key, Cell c) {
        if(spreadsheet.containsKey(key)) {
            spreadsheet.get(key).setContent(c.getContent());
        } else {
            spreadsheet.put(key, c);
        }
    }

    /** Deletes the Cell that is in the key param. calls ondelete on the cell
     *  @param key the key of the Cell as a String
     */
    public void delCell(String key) {
        Cell cell = getCell(key);
        if(cell.isReferenced()) {
             getCell(key).setContent(new CellNumber<Integer>(0));
        } else {
            cell.onDelete();
            spreadsheet.remove(key);
        }
    }

    /** Gets a row of the spreadsheet
     *  @param rowKey the key of the row as a String
     *  @return a Set of Strings with the keys of the existing cells of the specified row
     */
    public Set<String> getRow(String rowKey) {
        Set<String> row = new HashSet<String>();
        Iterator<String> iter = spreadsheet.keySet().iterator();
        String pattern = "[A-Z]+" + rowKey;
        while(iter.hasNext()) {
            String key = iter.next();
            if(key.matches(pattern))
                row.add(key);
        }
        return row;
    }

    /** Gets a column of the spreadsheet
     *  @param colKey the key of the column as a String
     *  @return a Set of Strings with the keys of the existing cells of the specified column
     */
    public Set<String> getCol(String colKey) {
        Set<String> col = new HashSet<String>();
        Iterator<String> iter = spreadsheet.keySet().iterator();
        String pattern = colKey + "\\d+";
        while(iter.hasNext()) {
            String key = iter.next();
            if(key.matches(pattern))
                col.add(key);
        }
        return col;
    }

    /** Deletes a row of the spreadsheet
     *  @param rowKey the key of the row as a String
     */
    public void delRow(String rowKey) {
        Iterator<String> iter = getRow(rowKey).iterator();
        while(iter.hasNext()) {
            delCell(iter.next());
        }
        Iterator<String> iter2 = getRow(rowKey).iterator();
        while(iter2.hasNext()) {
            delCell(iter2.next());
        }
    }

    /** Deletes a column of the spreadsheet
    *  @param colKey the key of the column as a String
    */
    public void delCol(String colKey) {
        Iterator<String> iter = getCol(colKey).iterator();
        while(iter.hasNext()) {
            delCell(iter.next());
        }
        Iterator<String> iter2 = getCol(colKey).iterator();
        while(iter2.hasNext()) {
            delCell(iter2.next());
        }
    }

    @Override
    public String toString() {
        Iterator<String> iter = spreadsheet.keySet().iterator();
        String pattern = "([A-Z]+)(\\d+)";
        String result = "";
        String lastRowKey = "";
        String key = "";
        if(iter.hasNext()) {
            key = iter.next();
            lastRowKey = key.replaceAll(pattern, "$2");
            result += getCell(key).toString() + " ";
        }
        while(iter.hasNext()) {
            key = iter.next();
            if(lastRowKey.equals(key.replaceAll(pattern, "$2")))
                result += getCell(key).toString() + " ";
            else {
                result = result.trim();
                result += "\n";
                result += getCell(key).toString() + " ";
                lastRowKey = key.replaceAll(pattern, "$2");
            }
        }
        return result.trim();
    }

    public void clear() {
        spreadsheet.clear();
    }

    public boolean containsKey(String ref) {
        return spreadsheet.containsKey(ref);
    }
}
