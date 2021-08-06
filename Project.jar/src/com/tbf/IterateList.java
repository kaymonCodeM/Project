package com.tbf;

import java.util.Iterator;

public class IterateList<T> implements Iterator<T> {
	/**
	 * This class is used to iterate any list that is implemented into its class.
	 */
	
	private Node<T> current;
	
	public IterateList(Node<T> head) {
		this.current = head;
	}

	public boolean hasNext() {
		return current !=null;
	}
	
	public T next() {
		 Node<T> temp = current;
		 current = current.getNext();
		 return temp.getItem();
	}
}
