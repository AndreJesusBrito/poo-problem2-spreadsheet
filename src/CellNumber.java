public class CellNumber implements ICellContent {
    private double value;
    private boolean isDouble;

    public CellNumber(double value, boolean isDouble) {
        setValue(value);
        setDouble(isDouble);
    }

    @Override
    public double getValue() {
        return value;
    }

    private void setValue(double value) {
        this.value = value;
    }

	public boolean isDouble() {
		return isDouble;
	}

	public void setDouble(boolean isDouble) {
		this.isDouble = isDouble;
	}

    @Override
    public String getContent() {
        return (isDouble ? value : ((Number) value).intValue()) + "";
    }

    @Override
    public void onDelete() {}
}
