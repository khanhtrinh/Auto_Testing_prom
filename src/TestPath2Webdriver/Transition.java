package TestPath2Webdriver;

public class Transition {
	Event event;
	State beginState;
	State endState;
	public Transition(Event _e, State s1, State s2){
		event=_e;
		beginState=s1;
		endState=s2;
		
	}
	
	public String getName(){
		return event.getName();
	}
	
	public State getBeginState(){
		return beginState;
	}
	public State getEndState(){
		return endState;
	}
	public Event getEvent(){
		return event;
	}
	
	
	
	
	
}
