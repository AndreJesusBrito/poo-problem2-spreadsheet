import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.HashSet;

public class Spreadsheet {
    private SortedMap<String, Cell> ss;
    private Comparator<String> comparator = new Comparator<String>() {
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
                    : key1Row.compareTo(key2Row);
        }
    };

    public Spreadsheet() {
        ss = new TreeMap<String, Cell>(comparator);
    }

    public SortedMap<String, Cell> getSs() {
        return ss;
    }

    public void setSs(SortedMap<String, Cell> ss) {
        this.ss = ss;
    }

    public void setCell(String key, Cell c) {
        if(ss.containsKey(key)) {
            changeCellContent(key, c.getContent());
        } else {
            ss.put(key, c);
        }
    }

    public void changeCellContent(String key, ICellContent content) {
        ss.get(key).setContent(content);
    }

    public void delCell(String key) {
        ss.get(key).onDelete();
        if(ss.get(key).isReferenced())
            changeCellContent(key, new CellNumber(0.0, false));
        else
            ss.remove(key);
    }

    public Set<String> getRow(String rowKey) {
        Set<String> row = new HashSet<String>();
        Iterator<String> iter = ss.keySet().iterator();
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
        Iterator<String> iter = ss.keySet().iterator();
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
            ss.remove(iter.next());
    }

    public void delCol(String colKey) {
        Iterator<String> iter = getCol(colKey).iterator();
        while(iter.hasNext())
            ss.remove(iter.next());
    }

    public String toString() {
        Iterator<String> iter = ss.keySet().iterator();
        String pattern = "([A-Z]+)(\\d+)";
        String result = "";
        String lastRowKey = "";
        String key = "";
        if(iter.hasNext()) {
            key = iter.next();
            lastRowKey = key.replaceAll(pattern, "$2");
            result += ss.get(key).toString() + " ";
        }
        while(iter.hasNext()) {
            key = iter.next();
            if(lastRowKey.equals(key.replaceAll(pattern, "$2")))
                result += ss.get(key).toString() + " ";
            else {
                result = result.trim();
                result += "\n";
                result += ss.get(key).toString() + " ";
                lastRowKey = key.replaceAll(pattern, "$2");
            }
        }
        result = result.trim();
        return result;
    }
}
