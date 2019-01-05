

public class PointerToken implements IToken {

	@Override
	public ICellContent createToken(int pos, Parser parser) {
		return new CellPointer(parser.getSpreadsheet().get(parser.getTokenAtPos(pos)));
	}

	@Override
	public String getParsePattern() {
		return "^([A-Z]+)(\\d+)$";
	}

}
