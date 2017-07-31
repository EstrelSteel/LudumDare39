package com.estrelsteel.ld39.events;

public abstract class MonthlyEvent {

	private String desc;
	private EventScope scope;
	private EventType type;
	private boolean done;
	
	public MonthlyEvent(String desc, EventScope scope, EventType type) {
		this.desc = desc;
		this.scope = scope;
		this.type = type;
		this.done = false;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public EventScope getScope() {
		return scope;
	}
	
	public EventType getType() {
		return type;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public abstract void answerEvent(int option);
	
	public void setDescription(String desc) {
		this.desc = desc;
	}
	
	public void setScope(EventScope scope) {
		this.scope = scope;
	}
	
	public void setType(EventType type) {
		this.type = type;
	}
	
	public void setDone(boolean done) {
		this.done = done;
	}
}
