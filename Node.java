
public class Node<T> {

	private Node<T> left, right;
	private T data;
	private int height, bf;
	
	public Node(T data){
		this.data = data;
		height=0;
		bf=0;
	}
	
	public Node<T> getLeft(){
		return left;
	}
	
	public Node<T> getRight(){
		return right;
	}
	
	public void setLeft(Node<T> left){
		this.left = left;
	}
	
	public void setRight(Node<T> right){
		this.right = right;
	}
	
	public void setData(T data){
		this.data = data;
	}
	
	public T getData(){
		return data;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public void setBf(int bf){
		this.bf = bf;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getBf(){
		return bf;
	}
}
