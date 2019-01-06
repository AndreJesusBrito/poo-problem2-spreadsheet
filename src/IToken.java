/**  Interface used to register a possible pattern for parsing
 *  Also used to get the right content if the pattern matches
 */
public interface IToken {
    /**
     * @return returns an instance of the associated parse result.
     */
    public ICellContent createCellContent(int pos, Parser parser) throws UnsupportedTokenTypeException;

    /**
     * @return returns a regular expression to try to match in the parser.
     */
    public String getParsePattern();
}
