public interface Queue<E> {

  int size();
  boolean isEmpty();
  void enqueue(E e1, E e2, E  e3, E e4, E e5, int option);
  E first();
  int dequeue();
  E peek();
}
