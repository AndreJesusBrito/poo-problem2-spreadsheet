
public interface IToken {
	public ICellContent createToken(int pos, Parser parser) throws UnsupportedTokenTypeException;
	public String getParsePattern();
}
