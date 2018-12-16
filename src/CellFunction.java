public abstract class CellFunction implements ICellContent {
	private String content;
	
	public CellFunction(String content) {
		setContent(content);
	}
	
    public String getContent() {
    	return content;
    }
    
    public void setContent(String content) {
    	this.content = content;
    }
    
    public Object getArgValue(Object arg) {
    	if(arg instanceof Cell)
    		return  ((Cell) arg).getValue();
    	else
    		return arg;
    }
    
    public void onDelete() {
    	
    }
}
