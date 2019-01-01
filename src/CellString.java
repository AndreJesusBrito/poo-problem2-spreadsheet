
public class CellString implements ICellContent {
    private String value;
    public CellString(String value) {
        setValue(value);
    }
    
    @Override
    public String getValue() {
    	return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	@Override
    public String getContent() {
    	return value;
    }

    @Override
    public void onDelete() {}
}
