
public class PointerToken implements IToken {
    @Override
    public ICellContent createCellContent(int pos, Parser parser) {
        return new CellPointer(parser.getSpreadsheet().getCell(parser.getTokenAtPos(pos)));
    }

    @Override
    public String getParsePattern() {
        return "^([A-Z]+)(\\d+)$";
    }
}
