import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashSet;

public class SpreadsheetTest extends TreeMap<String, Cell> {
	private static final long serialVersionUID = 6622418703791895248L;
    Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String key1, String key2) {
            String pattern = "([A-Z]+)(\\d+)";
            String key1Col = key1.replaceAll(pattern, "$1");
            String key1Row = key1.replaceAll(pattern, "$2");
            String key2Col = key2.replaceAll(pattern, "$1");
            String key2Row = key2.replaceAll(pattern, "$2");
            return key1Row.equals(key2Row)
                    ? key1Col.compareTo(key2Col)
                    : key1Row.compareTo(key2Row);
        }
    };

    public SpreadsheetTest(Comparator<String> comparator) {
        super(comparator);
    }

    @Override
    public Cell remove(Object key) {
        get(key).onDelete();
        if(get(key).isReferenced()) {
        	get(key).setContent(new CellNumber(0.0, false));
        	return get(key);
        }
        else
            return super.remove(key);
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
            remove(iter.next());
    }

    public void delCol(String colKey) {
        Iterator<String> iter = getCol(colKey).iterator();
        while(iter.hasNext())
            remove(iter.next());
    }

    @Override
    public String toString() {
        Iterator<String> iter = keySet().iterator();
        String pattern = "([A-Z]+)(\\d+)";
        String result = "";

        while(iter.hasNext()) {
            boolean syncIter = false;
            String key = iter.next();
            String rowKey = key.replaceAll(pattern, "$2");
            while(iter.hasNext() && rowKey.equals(key.replaceAll(pattern, "$2"))) {
                if(syncIter)
                    key = iter.next();
                result += get(key).toString() + " ";
                syncIter = true;
            }
            result = result.trim() + "\n";
        }
        return result;
    }
}
