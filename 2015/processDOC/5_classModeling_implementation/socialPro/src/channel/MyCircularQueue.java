package channel;


public class MyCircularQueue {

	private int front;
	private int rear;
	private Object[] queue;
	private int qSize;
	public MyCircularQueue(int qSize) {
		this.front = 0;
		this.rear = 0;
		this.qSize = qSize;
		this.queue = new Object[qSize];
	}
	
	public MyCircularQueue(){
		
		this.front = 0;
		this.rear = 0;
		this.qSize = 11;
		this.queue = new Object[11];
	}
	
	
	public synchronized void put(Object data){
		
		if((rear+1)%qSize == front){
			return;
		}
		queue[rear] = data;
		rear++;
		rear %= qSize; 
		
	}
	
	public synchronized Object get(){
		System.out.println("Queue... st");
		Object res= null;
		
		if (front!=rear){
			res = queue[front];
			
			
			//queue[front]=null;
			front++;
			front %= qSize;
		}
		if(res==null){
			System.out.println("Q front is null");
		}else{
			System.out.println("Q front is not null..");
		}
		
		return res;
	}
	
	public boolean isEmpty(){
		if(front==rear){
			return true;
		}return false;
	}
}
