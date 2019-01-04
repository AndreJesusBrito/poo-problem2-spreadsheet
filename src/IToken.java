

public interface IToken {
	public ICellContent createToken(String pattern, Spreadsheet spreadsheet);
	public String getParsePattern();
}
