

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CellColumnPointer extends CellCollection {
	private String rowID;
	private Spreadsheet spreadsheet;
	
	public CellColumnPointer(Spreadsheet ss, String rowID) {
		this.spreadsheet = ss;
		this.rowID = rowID;
	}
	
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContent() {
		return rowID;
	}

	// note: the iterator should be used before the spreadsheet is modified since this func is called 
	@Override
	public Iterator<ICellContent> iterator() {
		List<ICellContent> contents = new ArrayList<ICellContent>();
		Set<String> keySet = spreadsheet.getCol(getContent());
		for(String key : keySet) {
			contents.add(spreadsheet.get(key).getContent());
		}
		return contents.iterator();
	}

}
