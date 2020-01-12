package myRestaurant;

import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class WaiterAbstract extends Thread {
	private String output;
	private boolean running;
	protected ArrayBlockingQueue<Guest> waitForTable, waitForOrder, waitForFood, eat, waitForCheckout;
	protected ArrayBlockingQueue<Ireport> done;
	protected ArrayBlockingQueue<Orders> orderQueue; 
	protected ArrayBlockingQueue<Orders> cookingQueue;
	protected ArrayBlockingQueue<Orders> foodQueue;
	private boolean idle;
	protected int doNothing;
	protected int tickTime;
	
	public WaiterAbstract(String aa) {
		output = aa;
		running = true;
		idle = true;
		doNothing = 0;
	}
	
	public void tick(int i) {
		tickTime = i;
		idle = false;
	}

	public String getOutput() {
		return output;
	}
	
	public void setWaitForTable(ArrayBlockingQueue<Guest> queue) {
		 waitForTable = queue;
	}
	
	public void setWaitForOrder(ArrayBlockingQueue<Guest> queue) {
		 waitForOrder = queue;
	}
	
	public void setWaitForFood(ArrayBlockingQueue<Guest> queue) {
		 waitForFood = queue;
	}
	
	public void setEat(ArrayBlockingQueue<Guest> queue) {
		 eat = queue;
	}
	
	public void setWaitForCheckout(ArrayBlockingQueue<Guest> queue) {
		 waitForCheckout = queue;
	}
	
	public void setDone(ArrayBlockingQueue<Ireport> queue) {
		 done = queue;
	}
	
	public void setOrderQueue(ArrayBlockingQueue<Orders> queue) {
		orderQueue = queue;
	}
	
	public void setFoodQueue(ArrayBlockingQueue<Orders> queue) {
		foodQueue = queue;
	}
	
	public void setCookingQueue(ArrayBlockingQueue<Orders> queue) {
		cookingQueue = queue;
	}

	public void myThreadStop() {
		running = false;
		System.out.println("I died " + output);
		idle = true;
		doNothing = 0;
		run();
	}
	
	public int getDoNothing() {
		return doNothing;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(running) {
			if (!idle) {
				if (sit()) {idle = true;}
				else if (order()) {idle = true;}
				else if (foodServed()) {idle = true;}
				else if (checkout()) {idle = true;}
				else if (pay()) {idle = true;}
				else {
					idle = true;
					doNothing = doNothing +1;
				}
			}
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public abstract boolean sit();
	public abstract boolean order();
	public abstract boolean foodServed();
	public abstract boolean checkout();
	public abstract boolean pay();
}
