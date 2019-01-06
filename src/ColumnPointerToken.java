
public class ColumnPointerToken implements IToken {
    @Override
    public ICellContent createCellContent(int pos, Parser parser) {
        return new CellColumnPointer(parser.getSpreadsheet(), parser.getTokenAtPos(pos));
    }

    @Override
    public String getParsePattern() {
        return "^[A-Z]+$";
    }
}
