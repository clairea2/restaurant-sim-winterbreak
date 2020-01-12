package myRestaurant;

public class Guest implements Ireport {
	
	private int timeForTable;
	private int timeForOrder;
	private int timeForEat;
	private int timeForCheckout;
	private int currentQueueTime;
	private int doneTime;
	private int timeForFood;
	public String name;

	public Guest(String guestName) { // note to self: this is a constructor, and it returns the same type as itself
			name = guestName;
			timeForTable = 0;
			timeForOrder = 0;
			timeForEat = 0;
			timeForCheckout = 0;
			timeForFood = 0;
			currentQueueTime = 0;
			doneTime = 0;
	}

	public String getGuestName() {
		return name;
	}
	
	public int getTimeForTable() {
		return timeForTable;
	}
	
	public void setTimeForTable(int tableTime) {
		timeForTable = tableTime;
	}
	
	public int getTimeForOrder() {
		return timeForOrder;
	}
	
	public void setTimeForOrder(int orderTime) {
		timeForOrder = orderTime;
	}
	
	public void setTimeForFood(int foodTime) {
		timeForFood = foodTime;
	}
	
	public int getTimeForFood() {
		return timeForFood;
	}
	
	public int getTimeForEat() {
		return timeForEat;
	}
	
	public void setTimeForEat(int eatTime) {
		timeForEat = eatTime;
	}
	
	public int getTimeForCheckout() {
		return timeForCheckout;
	}
	
	public void setTimeForCheckout(int checkoutTime) {
		timeForCheckout = checkoutTime;
	}
	
	public int getCurrentQueueTime() {
		return currentQueueTime;
	}
	
	public void setCurrentQueueTime(int queueTime) {
		currentQueueTime = queueTime;
	}
	
	public int getTimeForDone() {
		return doneTime;
	}
	
	public void setTimeForDone(int timeForDone) {
		doneTime = timeForDone;
	}

	@Override
	public String getReport() {
		// TODO Auto-generated method stub
		// how much you waited (string that reports this)
		return "Guest: " + timeForTable + ", "  + timeForOrder + ", "  + timeForEat + ", "  + timeForCheckout + ", and "  + doneTime;
	}
}
