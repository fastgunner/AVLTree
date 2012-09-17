import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class AVLTree<T extends Comparable<T>> implements BSTInterface<T>{

	public Node<T> root;
	private Node<T> leaf;
	private int height;
	private int size;
	private static List orderList, preorderList, postOrderList;

	public AVLTree(){
		root=null;
		size=0;
		orderList = new ArrayList();
		preorderList = new ArrayList();
		postOrderList=new ArrayList();
	}
	@Override
	public void add(T data) {
		if(root==null){
			root=new Node<T>(data);
			size++;
		}
		else {
			add(root, data);
			size++;
		}
		
	}

	private Node<T> add(Node<T> current, T data){
		if(current==null){
			current=new Node<T>(data);
			calcHeightAndBF(current);
		}
		else if(comp.compare(data, null)==0){//check if data==null
			current.setRight(add(current.getRight(), data));
			calcHeightAndBF(current);
			rotate();
		}
		else if(comp.compare(current.getData(), data)>0){//check if data<node_data check left
			if(current.getLeft()== null){//check if left node==null
				current.setLeft(new Node<T>(data));
				calcHeightAndBF(current);
				rotate();
				}
			else
				current.setLeft(add(current.getLeft(), data));//recurse left
			calcHeightAndBF(current);
			rotate();
		}
		else if(comp.compare(current.getData(), data)<0){//check if data<node_data check right
				current.setRight(add(current.getRight(), data));
				calcHeightAndBF(current);
				rotate();
		}

		return current;
	}

	@Override
	public T remove(T data) {
		if(root==null){
			return null;
		}
		else{
			Object[] rootArray = remove(root, data);
			setRoot((Node<T>)rootArray[0]);
			return (T)rootArray[1];
		}
	}
	
	private Object[] remove(Node<T> current, T data){
		Object [] a;
		if(current == null){
			return null;
		}
		else if(comp.compare(current.getData(), data)>0){
			a=remove(current.getLeft(), data);
			current.setLeft((Node<T>)a[0]);
			a[0]=current;
			rotate();
			return a;
		}
		else if(comp.compare(current.getData(), data)<0){
			a = remove(current.getRight(), data);
			current.setRight((Node<T>)a[0]);
			a[0]=current;
			return a;
		}
		else{
			if(current.getLeft()!=null&&current.getRight()!=null){
				a = removeMax(current.getLeft());
				T temp = current.getData();
				current.setData((T) a[1]);
				current.setLeft((Node<T>)a[0]);
				System.out.println("left and right not null");
				Object[] newArray = new Object[2];
				newArray[0]=current;
				newArray[1]=temp;
				return newArray;
			}
			else if(current.getLeft()==null&&current.getRight()!=null){
				a = new Object[2];
				a[1]=current.getData();
				a[0]=current.getRight();
				System.out.println("LEft null, right not null");
				return a;
			}
			else if(current.getLeft()!=null&&current.getRight()==null){
				a = new Object[2];
				a[1]=current.getData();
				a[0]=current.getLeft();
				System.out.println("Left not null, right null");
				return a;
			}
			
		}
		return null;
	}
	
	private Object[] removeMax(Node<T> node){
		Object[] a;
		if(node.getRight()!=null){
			a=removeMax(node.getRight());
			node.setRight((Node<T>)a[0]);
			a[0] = node;
		}
		else{
			a = new Object[2];
			a[0]=node.getLeft();
			a[1]=node.getData();
		}
		return a;
			
	}
	
	
	
	@Override
	public void clear() {
		root=null;
		size=0;
	}

	@Override
	public boolean isEmpty() {
		if(root==null)
			return true;
		else	
			return false;
	}

	@Override
	public T get(T data) {
		if(root==null){
			return null;
		}
		else
			return get(root, data);
	}

	private T get(Node<T> current, T data){
		if(current==null){
			return null;
		}
		else if(comp.compare(current.getData(),  data)==0){
			return data;
		}
		else if(comp.compare(current.getData(), data)<0){
			return get(current.getRight(), data);
		}
		else if(comp.compare(current.getData(), data)>0){
			return get(current.getLeft(), data);
		}
		return null;
	}

	@Override
	public List<T> asSortedList() {
		orderList.clear();
		if(root!=null){
			inOrder(root);
		}
		return orderList;
	}
	
	public void inOrder(Node<T> node){
		if(node!=null){
			if(node.getLeft()!=null){
				inOrder(node.getLeft());
			}
			orderList.add(node.getData());
			if(node.getRight()!=null){
				inOrder(node.getRight());
			}
		}
		
	}
	
	public void postOrder(Node<T> node){
		if(node.getLeft()!=null){
			postOrder(node.getLeft());
		}
		if(node.getRight()!=null){
			postOrder(node.getRight());
		}
		if((node.getLeft()==null)&&(node.getRight()==null)){
			height=0;
			node.setHeight(height);
		}
		else{
			height++;
			node.setHeight(height);
		}
	}
	
	private void calcHeightAndBF(Node<T> node){
		postOrder(node);
		
		int leftHeight;
		int rightHeight;
		
		if(node.getRight()==null){
			rightHeight=-1;
		}
		else{
			rightHeight=node.getRight().getHeight();
		}
		if(node.getLeft()==null){
			leftHeight=-1;
		}
		else{
			leftHeight=node.getLeft().getHeight();
		}
		node.setBf(leftHeight-rightHeight);
		
	}
	
	public void setRoot(Node<T> node){
		root=node;
	}
	
	public void rotate(){
		rotate(root, null, 0);
	}
	
	private void rotate(Node<T>node, Node<T> parent, int leftOrRight){
		if(node.getLeft()!=null){
			rotate(node.getLeft(), parent, -1);
		}
		if(node.getRight()!=null){
			rotate(node.getRight(), parent, 1);
		}
		calcHeightAndBF(node);
		if(node.getBf()>1){
			if(node.getLeft().getBf()>0){
				node = right(node);
			}
			else if(node.getLeft().getBf()<0){
				node = leftright(node);
			}
		}
		else if(node.getBf()<1){
			if(node.getRight()!=null&&(Object)node.getRight().getBf()!=null&&node.getRight().getBf()==0){
				node=rightleft(node);
			}
			else if(node.getBf()>1){
				node=right(node);
			}
		}
		if(leftOrRight==-1){
			parent.setLeft(node);
		}
		else if(leftOrRight==1){
			parent.setRight(node);
		}
		else if(leftOrRight==0){
			setRoot(node);
		}
	}
	
	public Node<T> left(Node<T> node){
		Node temp = node.getRight();
		temp.setRight(node.getLeft());
		temp.setLeft(node);
		return temp;
	}
	
	public Node<T> right(Node<T> node){
		Node temp = node.getLeft();
		temp.setLeft(node.getRight());
		temp.setRight(node);
		return temp;
	}
	
	public Node<T> leftright(Node<T> node){
		Node<T> left = node.getLeft();
		Node<T> rightOfLeft = left.getRight();
		left.setRight(rightOfLeft.getLeft());
		rightOfLeft.setLeft(left);
		node.setLeft(rightOfLeft);
		return node;
	}
	
	public Node<T> rightleft(Node<T> node){
		Node<T> right = node.getRight();
		Node<T> leftOfRight = right.getLeft();
		right.setLeft(leftOfRight.getRight());
		leftOfRight.setRight(right);
		node.setRight(leftOfRight);
		return node;
	}

	@Override
	public int size() {
		return size;
	}

	private Comparator <T> comp = new Comparator <T >() {
		@Override
		public int compare (T ths , T tht ) {
			if (ths == tht ) {
				return 0;
			} else if (ths == null ) {
				return 1;
			}	 else if (tht == null ) {
				return -1;
			} 
			else {
				return ths . compareTo (tht );
			}
		}
	};

	public static void main(String[] args){
		AVLTree avl = new AVLTree();
		System.out.println("Is the tree Empty" + avl.isEmpty());
		System.out.println("Is the size zero" + avl.size());
		System.out.println(avl.asSortedList());
		avl.add(42);
		avl.add(36);
		avl.add(8);
		avl.add(63);
		avl.add(37);
		avl.add(null);
		System.out.println("Is the tree Empty" + avl.isEmpty());
		System.out.println("Is the size zero" + avl.size());
		System.out.println("preorder " + preorderList);
		System.out.println(avl.get(42));
		System.out.println(avl.get(36));
		System.out.println(avl.get(8));
		System.out.println(avl.get(63));
		System.out.println(avl.get(null));
		System.out.println(avl.asSortedList());
		System.out.println(avl.remove(36));
		System.out.println("the 8 cleared");
		System.out.println(avl.asSortedList());
		avl.clear();
		System.out.println("Is the tree Empty" + avl.isEmpty());
		System.out.println("Is the size zero" + avl.size());
		System.out.println(avl.asSortedList());
		
	}
}
