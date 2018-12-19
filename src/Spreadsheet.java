import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashSet;

public class Spreadsheet extends TreeMap<String, Cell> {
	private static final long serialVersionUID = 6622418703791895248L;
    private static Comparator<String> comparator = new Comparator<String>() {
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
    };

    public Spreadsheet() {
        super(comparator);
    }

    public void setCell(String key, Cell c) {
        if(containsKey(key)) {
            changeCellContent(key, c.getContent());
        } else {
            put(key, c);
        }
    }

    public void changeCellContent(String key, ICellContent content) {
        get(key).setContent(content);
    }

    public void delCell(String key) {
        get(key).onDelete();
        if(get(key).isReferenced())
            changeCellContent(key, new CellNumber(0.0, false));
        else
            remove(key);
    }

    public Set<String> getRow(String rowKey) {
        Set<String> row = new HashSet<String>();
        Iterator<String> iter = keySet().iterator();
        String pattern = "[A-Z]+" + rowKey;
        while(iter.hasNext()) {
            String key = iter.next();
            if(key.matches(pattern))
                row.add(key);
        }
        return row;
    }

    public Set<String> getCol(String colKey) {
        Set<String> col = new HashSet<String>();
        Iterator<String> iter = keySet().iterator();
        String pattern = colKey + "\\d+";
        while(iter.hasNext()) {
            String key = iter.next();
            if(key.matches(pattern))
                col.add(key);
        }
        return col;
    }

    public void delRow(String rowKey) {
        Iterator<String> iter = getRow(rowKey).iterator();
        while(iter.hasNext())
            delCell(iter.next());
    }

    public void delCol(String colKey) {
        Iterator<String> iter = getCol(colKey).iterator();
        while(iter.hasNext())
            delCell(iter.next());
    }

    public String toString() {
        Iterator<String> iter = keySet().iterator();
        String pattern = "([A-Z]+)(\\d+)";
        String result = "";
        String lastRowKey = "";
        String key = "";
        if(iter.hasNext()) {
            key = iter.next();
            lastRowKey = key.replaceAll(pattern, "$2");
            result += get(key).toString() + " ";
        }
        while(iter.hasNext()) {
            key = iter.next();
            if(lastRowKey.equals(key.replaceAll(pattern, "$2")))
                result += get(key).toString() + " ";
            else {
                result = result.trim();
                result += "\n";
                result += get(key).toString() + " ";
                lastRowKey = key.replaceAll(pattern, "$2");
            }
        }
        result = result.trim();
        return result;
    }
}
