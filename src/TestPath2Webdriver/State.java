package TestPath2Webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import arg.HTML_StatusValue;
import arg.HTML_Type;
import arg.Webdriver_getType;

import HTML_Element.Elem_html_list;
import HTML_Element.ElementStatus;
import HTML_Element.ElementStatus_list;
import HTML_Element.Element_html;


public class State {
	
	
	String name;
	ElementStatus_list elem_st_list; // for this state
	Elem_html_list elem_html_list;
	
	public State(String _name, ElementStatus_list _elem_st_list, Elem_html_list _elem_html_list){
		name=_name;
		elem_st_list=_elem_st_list;
		elem_html_list=_elem_html_list;
	}
	
	public String getName(){
		return name;
	}
	
	private String getStringFromHtmlById(WebElement webelem, Element_html eh){
		
		try{
			String value="";
			if (eh.getType().compareTo(HTML_Type.TEXTBOX)==0){
				//System.out.println("is textbox");
				value=webelem.getAttribute(Webdriver_getType.ATT_VALUE);
				//value=webelem.getAttribute("");
			} else if (eh.getType().compareTo(HTML_Type.CHECKBOX)==0){
				//System.out.println("is checkbox");
				if (webelem.isSelected()){
					value = "1";
				} else {
					value = "0";
				}
				//System.out.println(value);
			} else {
				//System.out.println("not other");
				value=webelem.getText();
			}
			return value;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	// test_current la bien chi xem test lan thu may, tuong ung voi bo gia tri nao
	public boolean checkState(WebDriver driver, int test_current){
		try{
			
			for (int i=0; i<elem_st_list.getSize(); i++){
				ElementStatus e = elem_st_list.getElementByIndex(i);
				if (e.getStatus().compareTo(HTML_StatusValue.IGNORE)==0){
					continue;
				}
				Element_html eh = elem_html_list.getElementById(e.getId());
				if (eh.getValueAt(test_current).compareTo(HTML_StatusValue.IGNORE)==0){
					continue;
				}
				WebElement webelem = driver.findElement(By.id(eh.getHtml_id()));
				
				String value = getStringFromHtmlById(webelem, eh);
					
				if (e.getStatus().compareTo(HTML_StatusValue.EMPTY)==0){
					if (value.length()!=0){
						//System.out.println("f1");
						return false;
					}
				} else if (e.getStatus().compareTo(HTML_StatusValue.DEFAULT)==0){ 
					if (value.compareTo(eh.getValueAt(test_current))!=0){
						//System.out.println("f2");
						if (eh.getValueAt(test_current).compareTo(HTML_StatusValue.IGNORE)==0){
							continue;
						}
						return false;
					}
				} else if (value.compareTo(e.getStatus())!=0){
					System.out.println("Strings not compare("+value+"):"+eh.getHtml_id()+"_"+e.getStatus());
					//System.out.println("f3");
					return false;
				}
			
			}
			
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	
	
	
	
}
