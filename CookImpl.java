package myRestaurant;

public class CookImpl extends Cook implements Itimetick {

	public CookImpl(String nameA) {
		super(nameA);
		// TODO Auto-generated constructor stub
	}
	//Orders orders;
	//int i;
	Orders orders;
	
	// 1. If none in orderQueue, return false.
	// 2. Put into cookingQueue.
	public synchronized boolean foodOrdered() {
		System.out.println("Size of orderQueue: " + orderQueue.size());
		if (orderQueue.size() == 0) { 
			System.out.println("No order found in queue.");
			return false;
		}
		try {
			orders = orderQueue.take();
			orders.setTimeForOrdering(tickTime - orders.getCurrentQueueTime());
			orders.setCurrentQueueTime(tickTime);
			System.out.println("********* Current queue time when in order queue: " + orders.getCurrentQueueTime());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cookingQueue.add(orders);
		System.out.println("The food for this guest has been sent to cooking (cookingQueue)");
		return true;
	}
	
	public synchronized boolean foodMade() {
		if (cookingQueue.size() == 0) { 
			System.out.println("No food found in queue.");
			return false;
		}
		int currentQ = cookingQueue.peek().getCurrentQueueTime();
		//orders.setActualCookingTime(tickTime - orders.getCurrentQueueTime());
		System.out.println("Current queue time when in cooking: " + orders.getCurrentQueueTime());
		if (tickTime - currentQ < 5) {
			return false;
		}
		try {
			orders = cookingQueue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orders.setActualCookingTime(tickTime - orders.getCurrentQueueTime());
		orders.setCurrentQueueTime(tickTime);
		System.out.println("Current queue time when food is finished and ready to be served: " + orders.getCurrentQueueTime());
		foodQueue.add(orders);
		System.out.println("The food has been finished cooking!");
		return true;
	}
}