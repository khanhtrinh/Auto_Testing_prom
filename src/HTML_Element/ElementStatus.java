package HTML_Element;

public class ElementStatus {
	int id;
	String status;
	
	public ElementStatus(int _id, String _status){
		id=_id;
		status=_status;
	}
	
	public String getStatus(){
		return status;
	}
	
	public int getId(){
		return id;
	}
	
	
}
