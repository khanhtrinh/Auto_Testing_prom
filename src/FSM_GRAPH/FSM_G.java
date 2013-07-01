package FSM_GRAPH;

import java.util.ArrayList;

import TestPath2Webdriver.State_list;
import TestPath2Webdriver.Transition;
import TestPath2Webdriver.TransitionSequences;
import TestPath2Webdriver.TransitionSequences_list;
import TestPath2Webdriver.Transition_list;

public class FSM_G {
	
	private String name;
	
	private State_list stateList;
	private Transition_list transitionList;
	
	public FSM_G(String _name, State_list _stateList, Transition_list _transitionList){
		name = _name;
		stateList=_stateList;
		transitionList=_transitionList;
		
	}
	
	
	public int getNumberOfState(){
		return stateList.getSize();
	}
	
	// tim tat ca even mot state
	public ArrayList<String> getAllPathOfState(String name){
		ArrayList<String> ret = new ArrayList<String>();
		
		for (int i=0; i<transitionList.getSize(); i++){
			if (transitionList.getTransitionByIndex(i).getBeginState().getName().compareTo(name)==0){
				ret.add(transitionList.getTransitionByIndex(i).getName());
			}
		}
		
		return ret;
	}
	
	
	public int getIndexOfState(String name) {
		for (int i=0; i<stateList.getSize(); i++){
			if (name.compareTo(stateList.getStateByIndex(i).getName())==0){
				return i;
			}
		}
		return -1;
	}
	
	
	
	
	public int getIndexBeginStateOfTransition(int i){
		return getIndexOfState(transitionList.getTransitionByIndex(i).getBeginState().getName());
	}
	public int getIndexEndStateOfTransition(int i){
		return getIndexOfState(transitionList.getTransitionByIndex(i).getEndState().getName());
	}
	public String getNameOfTransitioin(int i){
		return transitionList.getTransitionByIndex(i).getName();
	}
	public int getNumberOfTransition(){
		return transitionList.getSize();
	}
	
		
	public String getName(){
		return name;
	}
	
	///////////////////////////////////////
	public TransitionSequences_list conVertFromPath(path PATH){
		TransitionSequences_list transqlist = new TransitionSequences_list();
		
		for (int i=0; i<PATH.getSize(); i++){
			TransitionSequences transq = new TransitionSequences();
			
			ArrayList<Integer> arr1 = PATH.getListByIndex(i);
//			System.out.print("----->");
//			for (int j=0; j<arr1.size(); j++){
//				System.out.print(stateList.getStateByIndex(j).getName()+"^");
//			}
//			System.out.println();
			for (int j=0; j<arr1.size()-1; j++){
				//System.out.println("convert: "+arr1.get(j)+" "+arr1.get(j+1));
				//String s1name = stateList.getStateByIndex(arr1.get(j)).getName();
				//String s2name = stateList.getStateByIndex(arr1.get(j+1)).getName();
				//System.out.println("s1 = "+s1name+" s2name="+s2name);
				
				Transition tran1 = transitionList.findBy2S(
						stateList.getStateByIndex(arr1.get(j)), 
						stateList.getStateByIndex(arr1.get(j+1)));
				
				transq.addTransition(tran1);
				
			}
			
			transqlist.addTransitionsq(transq);
		}
		
		return transqlist;
	}
	
	
	public TransitionSequences_list getPath_DFS(){
		//System.out.println("chua bao search");
		Search_FSM searcher = new Search_FSM(this);
		//System.out.println("Da khai bao search");
		path PATH = searcher.DFS();
		
		return conVertFromPath(PATH);
		
		
	}
	
	
	
	
	
	
}
