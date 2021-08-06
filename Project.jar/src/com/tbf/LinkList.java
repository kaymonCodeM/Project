package com.tbf;

import java.util.Comparator;
import java.util.Iterator;

public class LinkList<T> implements Iterable<T> {
	/**
	 * This class is used to create an ADT that is LinkList base.
	 * The list will only add in element by giving a comparator as well.
	 */

	private Node<T> head;
	private int size;

	public LinkList() {
		this.head = null;
	}

	public int size() {
		return this.size;
	}
	
/**
 * This method will remove an item at an given index.
 * @param index
 */
	public void remove(int index) {
		if (index < 0 || index >= this.size()) {
			throw new IllegalArgumentException("Index:" + index + " Is out of bounds");
		}
		if (index == 0) {
			this.head = this.head.getNext();
			this.size--;
			return;
		}
		Node<T> previous = this.getNode(index-1);
		Node<T> current = previous.getNext();
		previous.setNext(current.getNext());
	}
/**
 * This method will return an item at an given index.
 * @param index
 * @return
 */
	public T getItem(int index) {
		return this.getNode(index).getItem();
	}

	private Node<T> getNode(int index) {
		if (index < 0 || index >= this.size()) {
			throw new IllegalArgumentException("Index:" + index + " Is out of bounds");
		}
		Node<T> current = head;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current;
	}
/**
 * This is a private method that only belongs to the class.
 * This method will insert an element on a given index.
 * @param item
 * @param index
 */
	private void insertElement(T item, int index) {
		if (index < 0 || index > this.size()) {
			throw new IllegalArgumentException("Index:" + index + " Is out of bounds");
		}

		Node<T> newNode = new Node<T>(item);
		if (this.head == null) {
			this.head = newNode;
			this.size++;
			return;
		} else if (index == 0) {
			newNode.setNext(this.head);
			this.head = newNode;
			this.size++;
			return;
		}
		Node<T> previous = this.getNode(index - 1);
		newNode.setNext(previous.getNext());
		previous.setNext(newNode);
		this.size++;
	}
/**
 * This method will return a copy of a sorted list based on the given compiler.
 * @param c
 * @return
 */
	public LinkList<T> sortList(Comparator<T> c) {
		LinkList<T> result = new LinkList<T>();
		for (T item : this) {
			result.addSort(item, c);
		}
		return result;
	}
/**
 * This method will add an element into the list.
 * Based on the comparator the element will by added to the the right index to keep the sort intact.
 * @param item
 * @param c
 */

	public void addSort(T item, Comparator<T> c) {
		Node<T> current = this.head;
		if (current == null) {
			this.insertElement(item, 0);
		} else {
			int i = 0;
			while (current != null) {
				if (c.compare(item, current.getItem()) < 0) {
					this.insertElement(item, i);
					return;
				}
				i++;
				current = current.getNext();
			}
			this.insertElement(item, this.size);
		}
		return;
	}

	@Override
	public Iterator<T> iterator() {
		return new IterateList<T>(head);
	}

}
