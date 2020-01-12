package myRestaurant;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Hashtable;

public class WaiterImpl extends WaiterAbstract implements Itimetick {
	
	public WaiterImpl(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public synchronized boolean sit() {
		// TODO Auto-generated method stub
		if (waitForTable.size() == 0) {
			System.out.println("No guests for Waiter " + this.getOutput() + " are in the queue.");
			return false;
		} 
		if (waitForOrder.size() + waitForFood.size() + eat.size() + waitForCheckout.size() >= 20) {
			System.out.println("Waiter " + this.getOutput() + " says the tables are full");
			return false;
		}
		try { 
			Guest guest = waitForTable.take();
			guest.setTimeForTable(tickTime - guest.getCurrentQueueTime());
			guest.setCurrentQueueTime(tickTime); 
			waitForOrder.add(guest); 
			System.out.println("Guest " + guest.getGuestName() + " has gotten a table"); 
			System.out.println("The current queue time after the guest has been seated is " + guest.getCurrentQueueTime()); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	// 1. Check waitForOrder queue size, and if waitForOrder queue size is zero, return false.
	// 2. Instantiate Orders.
	// 3. Take guest from waitForOrder queue, and add to waitForFood.
	// 4. Add instantiated orders to orderQueue.
	public synchronized boolean order() {
		// TODO Auto-generated method stub
		if (waitForOrder.size() == 0) { 
			System.out.println("No guests for Waiter " + this.getOutput() + " are in the queue.");
			return false;
		}
		try {
			Guest guest = waitForOrder.take();
			guest.setTimeForOrder(tickTime - guest.getCurrentQueueTime());
			guest.setCurrentQueueTime(tickTime);
			waitForFood.add(guest); 
			System.out.println("The current queue time after the guest has ordered is " + guest.getCurrentQueueTime()); 
			System.out.println(guest.getGuestName() + " has started to be waited and the order has gone to the cook.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("**");
		Orders orders = new Orders("Order");
		try {
			orders.setCurrentQueueTime(tickTime);
			orderQueue.put(orders);
			System.out.println("**** order was replaced in orderQueueuee.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return true;
	}

	@Override
	// 1. Check waitForFood queue size, and if waitForFood queue size is zero, return false.
	// 2. Check if foodQueue is empty, and if so, return false.
	// 3. Take guest from waitForFood, and add to eat.
	// 4. take orders from foodQueue, and cast to Ireport, then put into done queue.
	public synchronized boolean foodServed() {
		System.out.println("the waitingForFood queue size is " + waitForFood.size());
		if (waitForFood.size() == 0) { 
			System.out.println("No guests for Waiter " + this.getOutput() + " are in the queue.");
			return false;
		}
		System.out.println("the foodQueue queue size is " + foodQueue.size());
		if (foodQueue.size() == 0) {
			System.out.println("No food is ready yet.");
			return false;
		}
		try {
			Orders orders = foodQueue.take();
			done.add(orders);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("Food has been received from cooking!!!");
		try {
			Guest guest = waitForFood.take();
			guest.setTimeForFood(tickTime - guest.getCurrentQueueTime());
			guest.setCurrentQueueTime(tickTime);
			System.out.println("The current queue time after the food has been served is " + guest.getCurrentQueueTime());
			eat.add(guest);
			System.out.println(guest.getGuestName() + " has been served food by Waiter " + this.getOutput());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	// 1. Check eat queue size, and if eat queue size is zero, return false.
	// 2. Get currentQueueTime of when it was all happening.
	// 3. Take guest from eat and get time. Add to waitForCheckout
	public synchronized boolean checkout() {
		if (eat.size() == 0) { 
			System.out.println("No guests for Waiter " + this.getOutput() + " are in the queue.");
			return false;
		}
		int currentQforEat = eat.peek().getCurrentQueueTime();
		if (tickTime - currentQforEat < 10) {
			return false;
		}
		try {
			Guest guest = eat.take();
			guest.setTimeForEat(tickTime - guest.getCurrentQueueTime());
			guest.setCurrentQueueTime(tickTime);
			System.out.println("The current queue time after the guest has finished and started waiting for check is " + guest.getCurrentQueueTime());
			waitForCheckout.add(guest);
			// TODO Auto-generated method stub
			System.out.println(guest.getGuestName() + " has started waiting for their check by Waiter " + this.getOutput());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public synchronized boolean pay() {
		// TODO Auto-generated method stub
		if (waitForCheckout.size() == 0) { 
			System.out.println("No guests for Waiter " + this.getOutput() + " are in the queue.");
			return false;
		}
		try {
			Guest guest = waitForCheckout.take();
			guest.setTimeForCheckout(tickTime - guest.getCurrentQueueTime());
			guest.setCurrentQueueTime(tickTime);
			System.out.println("The current queue time after the guest has paid their check is " + guest.getCurrentQueueTime());
			System.out.println(guest.getGuestName() + " has checked out and has left the restaurant!");
			System.out.println("The current tick time is " + tickTime);
			done.add(guest);
			guest.setTimeForDone(tickTime - guest.getCurrentQueueTime());
			guest.setCurrentQueueTime(tickTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}

	