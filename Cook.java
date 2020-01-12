package myRestaurant;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Hashtable;

public abstract class Cook extends Thread {
	private boolean running;
	private boolean idle;
	public int i = 0;
	protected Guest guest;
	public boolean cooking;
	protected Orders orders;
	protected String name;
	protected int doNothing;
	protected int tickTime;
	protected ArrayBlockingQueue<Orders> orderQueue; 
	protected ArrayBlockingQueue<Orders> cookingQueue;
	protected ArrayBlockingQueue<Orders> foodQueue;
	
	public abstract boolean foodOrdered();
	public abstract boolean foodMade();
	
	public Cook(String nameA) {
		//super(nameA); // the nameA there can be used here
		name = nameA;
		doNothing = 0;
		running = true;
		idle = true;
	}
	
	public String getCookName() {
		return name;
	}
	
	public void setOrderQueue(ArrayBlockingQueue<Orders> queue) {
		 orderQueue = queue;
	}
	
	public void setCookingQueue(ArrayBlockingQueue<Orders> queue) {
		 cookingQueue = queue;
	}
	
	public void setFoodQueue(ArrayBlockingQueue<Orders> queue) {
		 foodQueue = queue;
	}

	public void tick(int i) {
		tickTime = i;
		idle = false;
	}
	
	public void myThreadStop() {
		running = false;
		System.out.println("This has ended, thread has stopped.");
		idle = true;
		doNothing = 0;
	}
	
	public int getDoNothing() {
		return doNothing;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(running) {
		//	System.out.println("382938293 cook is running " + idle);
			if (!idle) {
				if (foodOrdered()) {idle = true;}
				else if (foodMade()) {idle = true;}
				else {
					idle = true;
					doNothing = doNothing +1;
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}