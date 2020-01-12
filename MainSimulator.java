package myRestaurant;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class MainSimulator {
	
	public MainSimulator(ArrayBlockingQueue<Guest> queue, String name) {
		super();
	}

	public static void main(String[] args) {
		
		ArrayList<Itimetick> workers = new ArrayList<Itimetick>(10);
		ArrayList<Thread> workerThread = new ArrayList<Thread>(10);
		
		int SizeQueue = 200; 
		int doneQueueSize = 1000; 
		int orderAndFoodQueueSize = 200; 
		
		// waiter
		ArrayBlockingQueue<Guest> waitForTable = new ArrayBlockingQueue<Guest> (SizeQueue); 
		ArrayBlockingQueue<Guest> waitForOrder = new ArrayBlockingQueue<Guest> (SizeQueue);
		ArrayBlockingQueue<Guest> waitForFood = new ArrayBlockingQueue<Guest> (SizeQueue);
		ArrayBlockingQueue<Guest> eat = new ArrayBlockingQueue<Guest> (SizeQueue);
		ArrayBlockingQueue<Guest> waitForCheckout = new ArrayBlockingQueue<Guest> (SizeQueue);
		ArrayBlockingQueue<Guest> sit = new ArrayBlockingQueue<Guest> (SizeQueue);
		
		// cook and waiter
		ArrayBlockingQueue<Orders> orderQueue = new ArrayBlockingQueue<Orders> (orderAndFoodQueueSize);
		ArrayBlockingQueue<Orders> foodQueue = new ArrayBlockingQueue<Orders> (orderAndFoodQueueSize);
		ArrayBlockingQueue<Orders> cookingQueue =new ArrayBlockingQueue<Orders> (orderAndFoodQueueSize);
		
		// done
		ArrayBlockingQueue<Ireport> done = new ArrayBlockingQueue<Ireport> (doneQueueSize);

		WaiterImpl ww = new WaiterImpl ("William"); 
		CookImpl cook = new CookImpl("Cora");
		
		ww.setWaitForTable(waitForTable);
		ww.setWaitForOrder(waitForOrder);
		ww.setWaitForFood(waitForFood);
		ww.setEat(eat);
		ww.setWaitForCheckout(waitForCheckout);
		ww.setDone(done);
		ww.setOrderQueue(orderQueue);
		ww.setFoodQueue(foodQueue);
		
		cook.setOrderQueue(orderQueue);
		cook.setCookingQueue(cookingQueue);
		cook.setFoodQueue(foodQueue);
		
		workers.add((Itimetick) ww); 
		workers.add((Itimetick) cook);
		workerThread.add((Thread) ww);
		workerThread.add((Thread) cook);
	
		for (int i = 0; i <= 10; i++) {
			Guest guest1 = new Guest ("Guest " + String.valueOf(i));
			try {
				waitForTable.put(guest1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	
		Iterator<Itimetick> iterator = workers.iterator();
		Iterator<Thread> threadIterator = workerThread.iterator();
		
		while (threadIterator.hasNext()) {
			threadIterator.next().start();
		}
		
		for (int i = 0; i <= 60; i++) {
			ww.tick(i);
			cook.tick(i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(ww.getOutput() + " do nothing: " + ww.getDoNothing());
		System.out.println(cook.getCookName() + " do nothing: " + cook.getDoNothing());
	
		Ireport temp = null;
		while (done.size() > 0) {
			try {
				temp = done.take();
				System.out.println(temp.getReport()); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ww.myThreadStop();
		cook.myThreadStop();
		}
}


	