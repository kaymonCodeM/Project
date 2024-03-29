package com.tbf;

public class Node<T> {
	/**
	 * This class is used to for the linkList ADT to setup nodes.
	 */

	private Node<T> next;
	private T item;

	public Node(T item) {
		this.item = item;
		this.next = null;
	}

	public T getItem() {
		return item;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}
}
