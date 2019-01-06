
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final static List<IToken> GENERAL_CONTEXT_LEXICAL = new ArrayList<IToken>();
    private final static List<IToken> GENERAL_CONTEXT_FUNC_LEXICAL = new ArrayList<IToken>();

    static {
        GENERAL_CONTEXT_LEXICAL.add(new NumberToken());
        GENERAL_CONTEXT_LEXICAL.add(new PointerToken());

        GENERAL_CONTEXT_FUNC_LEXICAL.add(new SumFuncToken());
    }



    private Spreadsheet spreadsheet;
    private List<String> tokens;

    /** Parser Constructor
     *  @param spreadsheet spreadsheet to apply cell operations
     *  @param strParts array of string representing tokens from the input separeted by spaces
     */
    public Parser(Spreadsheet spreadsheet, String[] strParts) throws UnsupportedTokenTypeException {
        tokens = new ArrayList<String>();
        for(String token : strParts) {
            tokens.add(token);
        }
        this.spreadsheet = spreadsheet;
    }

    /** Parser Constructor Variation, token spliting done automatically
     *   @param spreadsheet spreadsheet to apply cell operations
     *   @param strParts string with input.
     */
    public Parser(Spreadsheet spreadsheet, String strLine) throws UnsupportedTokenTypeException {
        this(spreadsheet, strLine.split("\\s+"));
    }

    public Spreadsheet getSpreadsheet() {
        return spreadsheet;
    }
    public String getTokenAtPos(int pos) {
        return tokens.get(pos);
    }
    public List<String> getTokens() {
        return tokens;
    }


    /** Support function : checks if a token represents a function
     *   @param pattern string to check
     */
    private boolean isFunc(String pattern) {
        return pattern.charAt(0) == '=';
    }

    /** starts parsing
     *  @return returns the ICellContent and all its subcontents if possible, otherwise stops execution
     */
    public ICellContent startParsing() {
        try {
            return (ICellContent) expr(0, GENERAL_CONTEXT_LEXICAL);
        } catch (UnsupportedTokenTypeException e) {
            System.err.println("Error in parsing!");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    /** tokenizer, converts tokens into ICellContents
     *  @param pos index of the token in the tokens list to be tokenized
     *  @return retuns the matched Content if possible, otherwise throws an UnsupportedTokenTypeException
     */
    public ICellContent expr(int pos, List<IToken> contextLexical) throws UnsupportedTokenTypeException {
        String input = tokens.get(pos);
        List<IToken> lexical = isFunc(input) ? GENERAL_CONTEXT_FUNC_LEXICAL : contextLexical;
        for(IToken identifier : lexical) {
            if(input.matches(identifier.getParsePattern())) {
                return identifier.createCellContent(pos, this);
            }
        }
        throw new UnsupportedTokenTypeException();
    }
}
