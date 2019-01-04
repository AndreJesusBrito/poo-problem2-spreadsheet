

public class NumberToken implements IToken {
	public String getParsePattern() {
		return "^\\d*[.]?\\d+$";
	}

	@Override
	public ICellContent createToken(String pattern, Spreadsheet spreadsheet) {
		if(pattern.matches("^\\d+[.]\\d*$")) {
			return new CellNumber<Double>(Double.parseDouble(pattern));
        }
		return new CellNumber<Integer>(Integer.parseInt(pattern));
	}
}
