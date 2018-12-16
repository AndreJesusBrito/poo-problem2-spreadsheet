import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class Spreadsheet {
	private SortedMap<String, Cell> ss;

	public Spreadsheet() {
		Comparator<String> comparator = new Comparator<String>() {
			@Override
			public int compare(String key1, String key2) {
				String pattern = "([a-zA-Z]+)(\\d+)";
				String key1Col = key1.replaceAll(pattern, "$1");
				String key1Row = key1.replaceAll(pattern, "$2");
				String key2Col = key2.replaceAll(pattern, "$1");
				String key2Row = key2.replaceAll(pattern, "$2");
				return key1Row.equals(key2Row)
						? key1Col.compareTo(key2Col)
						: key1Row.compareTo(key2Row);
			}
		};
		ss = new TreeMap<String, Cell>(comparator);
	}
	
	public SortedMap<String, Cell> getSs() {
		return ss;
	}

	public void setSs(SortedMap<String, Cell> ss) {
		this.ss = ss;
	}
	
	public void addCell(Cell c, String ref) {
		ss.put(ref, c);
	}
	
	public void delCell(String ref) {
		if(ss.get(ref).getIsReferenced() > 0) {
			ss.get(ref).onDelete();
			ss.get(ref).setContent(new CellNumber(0));
		}
		else {
			ss.get(ref).onDelete();
			ss.remove(ref);
		}
	}
	
	public void delRow(int row) {
		Iterator<String> iter = ss.keySet().iterator();
		String pattern = "[a-zA-Z]+" + row;
		String ref;
		while(iter.hasNext()) {
			ref = iter.next();
			if(ref.matches(pattern)) {
				ss.remove(ref);
			}
		}
	}
	
	public void delColumn(String col) {
		Iterator<String> iter = ss.keySet().iterator();
		String pattern = col + "\\d+";
		String ref;
		while(iter.hasNext()) {
			ref = iter.next();
			if(ref.matches(pattern)) {
				ss.remove(ref);
			}
		}
	}
	
	public void printSpreadsheet() {
		Iterator<String> iter = ss.keySet().iterator();
		String ref;
		while(iter.hasNext()) {
			ref = iter.next();
			System.out.println(ref + " " + ss.get(ref).getContent() 
					           + " " + ss.get(ref).getValue());
		}
	}
}
