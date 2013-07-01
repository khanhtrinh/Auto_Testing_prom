package HTML_Element;

import java.util.ArrayList;

public class Element_html {
	private int id;
	private String html_id;
	private String type;
	private ArrayList<String> values;
	
	
	public Element_html(int _id, String _html_id, String _type, ArrayList<String> _values){
		id=_id;
		html_id=_html_id;
		type=_type;
		
		values = _values;
	}
	
	public int getId(){
		return id;
	}
	public String getHtml_id(){
		return html_id;
	}
	public String getType(){
		return type;
	}
	public String getValueAt(int i){
		return values.get(i);
	}
	

}
