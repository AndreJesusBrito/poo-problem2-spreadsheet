
public class RowPointerToken implements IToken {
    @Override
    public ICellContent createCellContent(int pos, Parser parser) {
        return new CellRowPointer(parser.getSpreadsheet(), parser.getTokenAtPos(pos));
    }

    @Override
    public String getParsePattern() {
        return "^\\d+$";
    }
}
