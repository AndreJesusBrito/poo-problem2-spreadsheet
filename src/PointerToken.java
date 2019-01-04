

public class PointerToken implements IToken {

	@Override
	public ICellContent createToken(String pattern, Spreadsheet spreadsheet) {
		return new CellPointer(spreadsheet.get(pattern));
	}

	@Override
	public String getParsePattern() {
		return "^([A-Z]+)(\\d+)$";
	}

}
