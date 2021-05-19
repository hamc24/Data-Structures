package treap;

import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable<E>> {
	
	private static class Node<E>{
		//Data fields
		public E data;
		public int priority;
		public Node<E> left;
		public Node<E> right;	
		
		//Constructors
		/**
		 * Constructor for a new Node
		 * Sets the initial data for its children as null
		 * @param data
		 * @param priority
		 */
		public Node(E data, int priority) {
			if(data == null) {
				throw new IllegalArgumentException();
			}
			this.data = data;
			this.priority = priority;
			this.left = null;
			this.right = null;
		}
		
		//Methods
		/**
		 * Performs a right rotation
		 * @return the new root
		 */
		Node<E> rotateRight(){
			Node<E> head = this.left;
			Node<E> left = head.right;
			head.right = this;
			this.left = left;
			return head;
		}
	
		/**
		 * Performs a left rotation
		 * @return the new root
		 */
		Node<E> rotateLeft(){
			Node<E> head = this.right;
			Node<E> right = head.left;
			head.left = this;
			this.right = right;
			return head;
		}
	}
	
	//Data fields
	private Random priorityGenerator;
	private Node<E> root;
	
	//Constructors
	public Treap() {
		root = null;
		priorityGenerator = new Random();
	}
	
	public Treap(long seed) {
		root = null;
		priorityGenerator = new Random(seed);
	}
	
	//Methods
	/**
	 * An add function that calls on the add(E key, int priority) function
	 * @param key the item being inserted
	 * @return true if key can be inserted to the Treap else false
	 */
	boolean add(E key) {
		return add(key, priorityGenerator.nextInt());
	}
	
	/**
	 * The main add method that uses reheap(Node<E> curr, Stack<Node<E>> path)
	 * After inserting the node in the proper place following BST laws,
	 * reheaps the treap based on priority
	 * @param key the item being inserted
	 * @param priority the randomly assigned value, returns false if another node with priority value exists
	 * @return true or false based on if the node can be added or not
	 */
	public boolean add(E key, int priority) {
		if (root == null) {
            root = new Node<E>(key, priority);
            return true;
        }
		
		if(find(key)) {
			return false;
		}

        Node<E> current = root;
        Node<E> temp = new Node<E>(key, priority);
        Stack<Node<E>> trace = new Stack<Node<E>>();

        while (current != null) {
            if (current.data.compareTo(key) == 0) {
                break;
            }
            if (current.data.compareTo(key) > 0) {
                trace.push(current);
                if (current.left == null) {
                    current.left = temp;
                    current = current.left;
                } else {
                    current = current.left;
                }
            } else {
                trace.push(current);
                if (current.right == null) {
                    current.right = temp;
                    current = current.right;
                } else {
                    current = current.right;
                }
            }
        }
        reheap(current, trace);
        return true;
	}
	
	/**
	 * Helper method for the Add method, properly rearranges the treap based on heap priority after the node has been 
	 * placed in the proper space following BST rules
	 * @param curr
	 * @param path
	 */
	public void reheap(Node<E> current, Stack<Node<E>> trace) {
		if (trace.isEmpty() == true) {
            root = current;
            return;
        } else if (current.priority < trace.peek().priority) { 
            return;
        } else {
            Node<E> parent = trace.pop();
            if (parent.left != null && parent.left.equals(current)) {
                parent.rotateRight();
            } else {
                parent.rotateLeft();
            }
            if (trace.isEmpty() == false) {
                if (trace.peek().left != null && trace.peek().left.equals(parent)) {
                    trace.peek().left = current;
                } else {
                    trace.peek().right = current;
                }
            }
            reheap(current, trace);
        }
	}
	
	/**
	 * Deletes the Node with that has the key. Uses trickle down rotation until the 
	 * Node becomes a leaf and removed
	 * @param key
	 * @return true if found and removed, false if the node doesn't exist
	 */
	boolean delete(E key) {
		if(root == null) {
			return false;
		}
		if(find(key) == false) {
			return false;
		} else {
			root = delete(key, root);
			return true;
		}
	}
	
	
	/**
	 * Helper function for delete
	 * @param key data value being removed
	 * @param curr the current node being visited
	 * @return The treap without the given node
	 */
	private Node<E> delete(E key, Node<E> curr) {
		if(curr == null) {
			return curr;
		} else {
			int i = curr.data.compareTo(key); 
			if(i < 0) {
				curr.right = delete(key, curr.right);
			} else if(i > 0) {
				curr.left = delete(key, curr.left);
				} else if(i == 0) {
					if(curr.right == null) {
						curr = curr.left;
					} else if(curr.left == null) {
						curr = curr.right;
					} else if(curr.right.priority < curr.left.priority) {
						curr= curr.rotateRight();
						curr.right = delete(key, curr.right);
					} else {
						curr = curr.rotateLeft();
						curr.left = delete(key, curr.left);
					}
				}
			}
		return curr;
	}
	
	/**
	 * Finds a node with the given key in the treap rooted at root 
	 * @param root The node being looked at
	 * @param key The key being looked for
	 * @return true if it finds the node, else false
	 */
	private boolean find(Node<E> root, E key) {
		if(root == null) {
			return false;
		}
		if(root.data.compareTo(key) == 0) {
			return true;
		} else if(root.data.compareTo(key) > 0) {
			return find(root.left, key);
		} else {
			return find(root.right, key);
		}
	}
	
	/**
	 * Main method for finding wanted Node
	 * @param key the value of the wanted node
	 * @return true or false based on the node exists in the treap
	 */
	public boolean find(E key) {
		return find(root, key);
	}
	
	/**
	 * main method for turning the treap into a string form
	 */
	public String toString() {
		return toString(root, 0);
	}
	
	/**
	 * Helper function for toString
	 * @param curr
	 * @param depth
	 * @return treap in string form
	 */
	public String toString(Node<E> curr, int depth) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < depth; i++) {
			sb.append("  ");
		}
		if(curr == null) {
			sb.append("null");
		} else {
			sb.append("(key=" + curr.data +", priority=" + curr.priority + ")" );
			sb.append("\n");
			sb.append(toString(curr.left, depth + 1));
			sb.append("\n");
			sb.append(toString(curr.right, depth + 1));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Treap<Integer> testTree = new Treap<Integer>();
		testTree.add(4, 19);
		testTree.add(2, 31);
		testTree.add(6, 70);
		testTree.add(1, 84);
		testTree.add(3, 12);
		testTree.add(5, 83);
		testTree.add(7, 26);
		testTree.add(4, 19);
		System.out.println(testTree.toString());
		System.out.println(testTree.find(1));
	}
}
