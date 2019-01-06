
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CellColumnPointer extends CellCollection {
    private String colID;
    private Spreadsheet spreadsheet;

    public CellColumnPointer(Spreadsheet ss, String rowID) {
        this.spreadsheet = ss;
        this.colID = rowID;
    }

    @Override
    public Set<String> getValue() {
        return spreadsheet.getCol(colID);
    }

    @Override
    public String getContentString() {
        return colID;
    }

    /**
      * @return returns iterator of all cell's contents in the column specified.
      * <p><i>note: the iterator will not be updated if the the spreadsheet its changed.</i></p>
      */
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
