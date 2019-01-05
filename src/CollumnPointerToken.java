

public class CollumnPointerToken implements IToken {

	@Override
	public ICellContent createToken(int pos, Parser parser) {
		return new CellColumnPointer(parser.getSpreadsheet(), parser.getTokenAtPos(pos));
	}

	@Override
	public String getParsePattern() {
		return "^[A-Z]+$";
	}

}
