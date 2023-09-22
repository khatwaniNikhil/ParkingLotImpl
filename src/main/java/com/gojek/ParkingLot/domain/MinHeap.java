package com.gojek.ParkingLot.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MinHeap {

  // Use an array list to back the heap instead of an array so that
  // we don't have to deal with resizing.
  private List<Integer> a;

  // Constructor - just create the array list.
  public MinHeap() {
    a = new ArrayList<>();
  }

  // Define functions for determining the parent, left, and right
  // node from any given node.
  private int par(int n) {
    return n == 0 ? -1 : (n - 1) >>> 1;
  }

  private int left(int n) {
    return n * 2 + 1;
  }

  private int right(int n) {
    return n * 2 + 2;
  }

  public int size() {
    return a.size();
  }

  // Determine the index of the lesser-value child of a node taking
  // into account that a node may not have children or may just have
  // one child.
  private int minChildIndex(int n) {
    if (left(n) > a.size() - 1)
      return -1;
    if (right(n) > a.size() - 1)
      return left(n);
    return a.get(left(n)) <= a.get(right(n)) ? left(n) : right(n);
  }

  // Add a new element to the end and bubble it up to the appropriate
  // position in the heap.
  public void add(int n) {
    a.add(n);
    bubbleUp(a.size() - 1);
  }

  // Remove the element at the root, move the last element up, and
  // bubble it down to the appropriate position.
  public int remove() {

    if (a.size() == 0)
      throw new NoSuchElementException();

    if (!isHeap()) {
      System.err.println("Heap property broken!");
    }

    int result = a.get(0);
    a.set(0, a.get(a.size() - 1));
    a.remove(a.size() - 1);
    bubbleDown(0);

    if (!isHeap()) {
      System.err.println("Heap property broken!");
    }

    return result;
  }

  // Move the element up until it is less than its parent or
  // until it is at the root.
  private void bubbleUp(int n) {
    int parIndex = par(n);
    while (n > 0 && a.get(parIndex) > a.get(n)) {
      swap(parIndex, n);
      n = parIndex;
      parIndex = par(n);
    }
  }

  // Move the element down, switching it with its lesser child
  // until it is lower than both of its children.
  private void bubbleDown(int n) {
    int minChildIndex = minChildIndex(n);
    while (minChildIndex != -1 && a.get(minChildIndex) < a.get(n)) {
      swap(minChildIndex, n);
      n = minChildIndex;
      minChildIndex = minChildIndex(n);
    }
  }

  // Assert the current structure is a heap.
  public boolean isHeap() {
    for (int i = 1; i < a.size(); ++i) {
      if (par(i) >= 0) {
        if (a.get(par(i)) > a.get(i)) {
          return false;
        }
      }
    }
    return true;
  }

  // Utility function to swap two elements in the array list.
  private void swap(int i, int j) {
    int tmp = a.get(i);
    a.set(i, a.get(j));
    a.set(j, tmp);
  }

  // Just print out the underlying array list.
  @Override
  public String toString() {
    return a.toString();
  }
}
