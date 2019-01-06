

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CellRowPointer extends CellCollection {
	private String rowID;
	private Spreadsheet spreadsheet;
	
	public CellRowPointer(Spreadsheet ss, String rowID) {
		this.spreadsheet = ss;
		this.rowID = rowID;
	}
	
	@Override
    public Set<String> getValue() {
        return spreadsheet.getRow(rowID);
    }

	@Override
	public String getContentString() {
		return rowID;
	}

	// note: the iterator should be used before the spreadsheet is modified since this func is called 
	@Override
	public Iterator<ICellContent> iterator() {
		List<ICellContent> contents = new ArrayList<ICellContent>();
		Set<String> keySet = getValue();
		for(String key : keySet) {
			contents.add(spreadsheet.getCell(key).getContent());
		}
		return contents.iterator();
	}

}
