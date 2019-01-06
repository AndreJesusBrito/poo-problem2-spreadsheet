
public class CellNumber<T extends Number> implements ICellContent {
    private T value;
    public CellNumber(T value) {
        setValue(value);
    }

    @Override
    public T getValue() {
        return value;
    }

    private void setValue(T value) {
        this.value = value;
    }

    @Override
    public String getContentString() {
        return getValue().toString();
    }

    @Override
    public void onDelete() {}
}
