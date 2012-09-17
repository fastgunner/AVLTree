import java.util.List;


public interface BSTInterface<T extends Comparable<T>> {
	
	/**
	 * Adds a new node to the tree.
	 * 
	 * @param data the data to add
	 */
	public void add(T data);
	
	/**
	 * If a node that equals "data" is in the tree, that
	 * element is removed (and, when required, replaced 
	 * with its successor, smallest node larger than curr).
	 * Otherwise, return null.
	 * 
	 * @param data the data of the node to be removed
	 * @return the element removed from the tree, or null if not found.
	 */
	public T remove(T data);
	
	/**
	 * Removes all elements from the tree.
	 */
	public void clear();

	/**
	 * 
	 * @return true if and only if the tree is empty.
	 */
	public boolean isEmpty();
	
	/**
	 * 
	 * @param data the element to retrieve from the tree
	 * @return the element from the tree that equals the given data, if it
	 * 			exists. Otherwise, null.
	 */
	public T get(T data);
	
	/**
	 * Returns all data found in the tree in the form
	 * of a sorted list. Note that this will be an
	 * O(n) operation.
	 * 
	 * @return a sorted list of all elements from the BST
	 */
	public List<T> asSortedList();
	
	/**
	 * 
	 * @return the number of elements in the tree
	 */
	public int size();
	
}
