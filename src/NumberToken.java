
public class NumberToken implements IToken {
    public String getParsePattern() {
        return "^\\d*[.]?\\d+$";
    }

    @Override
    public ICellContent createCellContent(int pos, Parser parser) {
        String token = parser.getTokenAtPos(pos);
        if(token.matches("^\\d*[.]\\d+$")) {
            return new CellNumber<Double>(Double.parseDouble(token));
        }
        return new CellNumber<Integer>(Integer.parseInt(token));
    }
}
