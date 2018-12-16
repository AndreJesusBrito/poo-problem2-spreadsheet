
public class CellNumber implements ICellContent {
	private Number value;
	private String content;
	
	public CellNumber(Number value) {
		setValue(value);
		setContent(value);
	}
	
	@Override
	public Number getValue() {
		return value;
	}
	
	private void setValue(Number value) {
		this.value = value;
	}

	@Override
	public String getContent() {
		return content;
	}
	
	private void setContent(Number value) {
		content = getValue().toString();
	}

	public void onDelete() {}
}
