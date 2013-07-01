package TestPath2Webdriver;

import java.util.ArrayList;

public class State_list {
	ArrayList<State> arrState;
	
	public State_list(){
		arrState = new ArrayList<State>();
	}
	
	public void addState(State _state){
		arrState.add(_state);
	}
	
	
	public State getStateByIndex(int index){
		return arrState.get(index);
	}
	
	public State getStateByName(String _name){
		for (int i=0; i<arrState.size(); i++){
			if (arrState.get(i).getName().compareTo(_name)==0){
				return arrState.get(i);
			}
		}
		return null;
	}
	
	
	public int getSize(){
		return arrState.size();
	}
	

}
