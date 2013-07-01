package HTML_Element;

import java.util.ArrayList;

public class Elem_html_list {
	ArrayList<Element_html> arrElement;
	
	public Elem_html_list(){
		arrElement = new ArrayList<Element_html>();
	}
	
	public void addElement(Element_html _e){
		arrElement.add(_e);
	}
	
	public Element_html getElementById(int id){
		
		for (int i=0; i<arrElement.size(); i++){
			if (arrElement.get(i).getId()==id){
				return arrElement.get(i);
			}
		}
		
		return null;
		
	}
	
	public Element_html getElementByIndex(int index){
		return arrElement.get(index);
	}
	
	public int getSize(){
		return arrElement.size();
	}
	
}
