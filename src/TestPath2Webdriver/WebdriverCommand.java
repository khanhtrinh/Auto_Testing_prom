package TestPath2Webdriver;

import java.io.File;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import jxl.Sheet;
import jxl.Workbook;



import org.openqa.selenium.WebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;


import FSM_GRAPH.FSM_G;
import HTML_Element.Elem_html_list;
import HTML_Element.ElementStatus;
import HTML_Element.ElementStatus_list;
import HTML_Element.Element_html;

public class WebdriverCommand {

	FSM_G fsm; // do thi bieu dien state machine
	WebDriver driver; // webdriver object
	Elem_html_list elemHtmlList; // danh sach cac html element
	State_list stateList; // danh sach cac sate
	Event_list eventList; // danh sach cac event
	Transition_list transitionList; // danh sach cac transition
	int numOfTest;
	
	String name;
	
	
	public WebdriverCommand(String _name, File _file) throws Exception{
		
		name = _name;
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
		
		elemHtmlList = new Elem_html_list();
		stateList = new State_list();
		eventList = new Event_list();
		transitionList = new Transition_list();
		
		inputData(_file);
		
	}
	
	
	public boolean runTestCaseWithUrl(String url, int nSleep, boolean log){
		
		for (int i=0; i<numOfTest; i++){
			System.out.println("\n\nStart run test case with values: "+(i+1));
			if (!run_withURL(url, nSleep, log, i)){
				return false;
			}
			System.out.print("PASS testing with values: "+(i+1)+"\n");
		}
		
		return true;
	}
	
	public boolean run_withURL(String url, int nsleep, boolean log, int test_c){
		boolean res = true;
		System.out.println("runing:");
		TransitionSequences_list transqlist = fsm.getPath_DFS();
		
		
		for (int i=0; i<transqlist.getSize(); i++){
			TransitionSequences transq =transqlist.getTransitionByIndex(i);
			
			driver.get(url);
			
			
			
			if (log){
				
				System.out.print("Test path "+(i+1)+": ");
				
				System.out.print(transq.getTransitionByIndex(0).getBeginState().getName());
				
				for (int j=0; j<transq.getSize(); j++){
					Transition tran = transq.getTransitionByIndex(j);
					Event e = tran.getEvent();
					//State s1 = tran.getBeginState();
					State s2 = tran.getEndState();
					
					System.out.print("*"+e.name+"="+s2.name);
				}
				
				System.out.println();
			}
			
			if (!transq.getTransitionByIndex(0).getBeginState().checkState(driver, test_c)){
				return false;
			}
			boolean passone = true;
			for (int j=0; j<transq.getSize(); j++){
				
				Transition tran = transq.getTransitionByIndex(j);
				Event e = tran.getEvent();
				State s1 = tran.getBeginState();
				State s2 = tran.getEndState();
				System.out.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
				
				// thuc thi event
				try{
					e.doEvent(driver, test_c);
				} catch (Exception err){
					passone = false;
					break;
				}
				// check state sau do
				if (!s2.checkState(driver, test_c)){
					passone = false;
					break;
				}
				System.out.println("\tOK");
				try{
					Thread.sleep(nsleep);
				} catch (Exception ex){
					ex.printStackTrace();
				}
			}
			
			if (passone == false){
				res = false;
				System.out.println("FAILT HERE:");
				System.out.print("Test path "+(i+1)+": ");
				
				System.out.print(transq.getTransitionByIndex(0).getBeginState().getName());
				
				for (int j=0; j<transq.getSize(); j++){
					Transition tran = transq.getTransitionByIndex(j);
					Event e = tran.getEvent();
					//State s1 = tran.getBeginState();
					State s2 = tran.getEndState();
					
					System.out.print("*"+e.name+"="+s2.name);
				}
				
				System.out.println();
			}
			
		}
		
		return res;
	}
	
	public void test_DFS(){
		TransitionSequences_list transqlist = fsm.getPath_DFS();
		System.out.println(transqlist.getSize());
		
		for (int i=0; i<transqlist.getSize(); i++){
			TransitionSequences transq =transqlist.getTransitionByIndex(i);
			for (int j=0; j<transq.getSize(); j++){
				Transition tran = transq.getTransitionByIndex(j);
				Event e = tran.getEvent();
				State s1 = tran.getBeginState();
				State s2 = tran.getEndState();
				
				System.out.println(s1.name+"*"+e.name+"="+s2.name);
			}
			System.out.println("---------------------------------");
		}
		
		
	}
	
	// ham input du lieu tu file excel
	public void inputData(File file) throws Exception{
		System.out.println("Input data: ");
		Workbook workbook = Workbook.getWorkbook(file);
        Sheet sheet = workbook.getSheet(0);
        
        // so cac html element
        int nelem = Integer.valueOf(sheet.getCell(0, 0).getContents().trim()).intValue();
        numOfTest = Integer.valueOf(sheet.getCell(4, 0).getContents().trim()).intValue();
       
        for (int i=0; i<nelem; i++){
        	
        	int id = Integer.valueOf(sheet.getCell(1, i+2).getContents().trim()).intValue();
        	String html_id = sheet.getCell(2, i+2).getContents().trim();
        	String type = sheet.getCell(3, i+2).getContents().trim();
        	//String defaultV = sheet.getCell(4, i+2).getContents().trim();
        	
        	ArrayList<String> values = new ArrayList<String>();
        	for (int j=0; j<numOfTest; j++){
        		String tvalue = sheet.getCell(4+j, i+2).getContents().trim();
        		if (tvalue.length()==0){ Exception e = new Exception(); throw e;} // kiem tra chuan du lieu
        		values.add(tvalue);
        	}
        	elemHtmlList.addElement(new Element_html(id, html_id, type, values));
        	
        }
        
        System.out.println("Number of html element: "+elemHtmlList.getSize());
        System.out.println("Number of testing values: "+numOfTest);
        
        int nstate = Integer.valueOf(sheet.getCell(0, nelem+2).getContents().trim()).intValue();
        
        //System.out.println(nstate);
        
        for (int i=0; i<nstate; i++){
        	String name = sheet.getCell(1, i+2+nelem+2).getContents().trim();
        	//System.out.println(name);
        	ElementStatus_list elemStList = new ElementStatus_list();
        	for (int j=0; j<nelem; j++){
        		String id = sheet.getCell(j+2, nelem+2+1).getContents().trim();
        		String st = sheet.getCell(j+2, i+2+nelem+2).getContents().trim();
        		//System.out.println(id+"-"+st);
        		elemStList.addElement(new ElementStatus(Integer.valueOf(id).intValue(), st));
        	}
        	
        	stateList.addState(new State(name, elemStList, elemHtmlList));
        	
        }
        System.out.println("Number of states: "+stateList.getSize());
        
        int nevent = Integer.valueOf(sheet.getCell(0, nelem+2+nstate+2).getContents().trim()).intValue();
        
        //System.out.println(nevent);
        
        for (int i=0; i<nevent; i++){
        	
        	String name = sheet.getCell(1, i+2+nelem+2+nstate+2).getContents().trim();
        	String elem_id = sheet.getCell(2, i+2+nelem+2+nstate+2).getContents().trim();
        	String action = sheet.getCell(3, i+2+nelem+2+nstate+2).getContents().trim();
        	
        	eventList.addEvent(new Event(name, Integer.valueOf(elem_id).intValue(), action, elemHtmlList));
        	
        }
        
        System.out.println("Number of events: "+eventList.getSize());
        
        for (int i=0; i<nstate; i++){
        	for (int j=0; j<nstate; j++){
        		String ename = sheet.getCell(j+2,i+2+nevent+2+nelem+2+nstate+2).getContents().trim();
        		String s1name = sheet.getCell(1,i+2+nevent+2+nelem+2+nstate+2).getContents().trim();
        		String s2name = sheet.getCell(j+2,1+nevent+2+nelem+2+nstate+2).getContents().trim();
        		
        		if (ename.length()==0 || ename.compareTo("_")==0){
        			continue;
        		}
        		
        		//System.out.println(ename);
        		
        		transitionList.addTransition(new Transition(
        				eventList.getEventByName(ename), 
        				stateList.getStateByName(s1name), 
        				stateList.getStateByName(s2name)));
        		
        	}
        	
        }
        
        System.out.println("Number of transitions: "+transitionList.getSize());
        
        /////////////////////////////////////
        
        
        fsm = new FSM_G(this.name, stateList, transitionList);
        	
	}
	
	
		
	public void quitDriver(){
		driver.quit();
	}

}
