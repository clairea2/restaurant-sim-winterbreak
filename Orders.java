package myRestaurant;

import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;

public class Orders implements Ireport {
	private int timeForOrdering;
	private int timeForServing;
	private int actualCookingTime;
	private int currentQueueTime;
	public boolean running;
	public String name;
	private boolean idle;
	protected int doNothing;
	protected int tickTime;

	public Orders(String nameA) { // can only use the ones in the member variables, so you need to make it equal, see below name = nameA
		// TODO Auto-generated constructor stub
		name = nameA;
		timeForOrdering = 0;
		timeForServing = 0;
		actualCookingTime = 0;
		running = true;
		idle = true;
		doNothing = 0;

	}

	public int getTimeForOrdering() {
		return timeForOrdering;
	}
	
	public void setTimeForOrdering(int OrderingTime) { // note to self: this is a constructor, and it returns the same type as itself
		timeForOrdering = OrderingTime;
	}
	
	public int getTimeForServing() {
		return timeForServing;
	}
	
	public void setTimeForServing(int ServingTime) { // food queue time
		timeForServing = ServingTime;
	}
	
	public int getActualCookingTime() {
		return actualCookingTime;
	}
	
	public void setActualCookingTime(int cookTiming) {
		actualCookingTime = cookTiming;
	}
	
	public int getCurrentQueueTime() {
		return currentQueueTime;
	}
	
	public void setCurrentQueueTime(int queueTime) {
		currentQueueTime = queueTime;
	}

	public String getReport() {
		// TODO Auto-generated method stub
		// how much you waited (string that reports this)
		return "Order: " + timeForOrdering + ", "  + timeForServing + ", and "  + actualCookingTime;
		
	}
}