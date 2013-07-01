package TestPath2Webdriver;

import java.util.ArrayList;

public class TransitionSequences_list {
	ArrayList<TransitionSequences> arrTransq;
	
	public TransitionSequences_list(){
		arrTransq = new ArrayList<TransitionSequences>();
	}
	
	public void addTransitionsq(TransitionSequences _tsq){
		arrTransq.add(_tsq);
	}
	
	public TransitionSequences getTransitionByIndex(int i){
		return arrTransq.get(i);
	}
	
	
	public int getSize(){
		return arrTransq.size();
	}
	
	

}
