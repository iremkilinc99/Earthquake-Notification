public class DoublyLinkedList<E extends Comparable<E>> {

  //---------------- nested Node class ----------------
  private static class Node<E extends Comparable<E>> {

    private double timestamp;
    private E  latitude;
    private E  longtitude;
    private E user;

    private Node<E> prev;
    private Node<E> next;

    /**
     * Creates a node with the given element and next node.
     *
     * @param lat  the element to be stored
     * @param lng  the element to be stored
     * @param usr  the element to be stored
     * @param p  reference to a node that should precede the new node
     * @param n  reference to a node that should follow the new node
     */
    public Node(double time, E lat, E lng, E usr, Node<E> p, Node<E> n) {
      timestamp = time;
      latitude = lat;
      longtitude = lng;
      user = usr ;
      prev = p;
      next = n;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public E getLatitude() {
    	return latitude;
    }

    public E getLongitude() {
        return longtitude;
    }

    public  E getUser() { return  user;}

    public Node<E> getPrev() { return prev; }

    public Node<E> getNext() { return next; }

    public void setPrev(Node<E> p) { prev = p; }

    public void setNext(Node<E> n) { next = n; }

    public int compareTo(E e1,E e2) {
      int a;
      if ((a = e1.compareTo(e2)) == 0) return 0;
      return a > 0 ? 1 : -1 ;
    }
  } //----------- end of nested Node class -----------

  private Node<E> header;
  private Node<E> trailer;
  private ArrayList<E> magnitudeOrderedList;
  private int size = 0;

  public DoublyLinkedList(ArrayList<E> mol) {
    header = new Node<>(-1,null, null, null, null, null);      // create header
    trailer = new Node<>(-1,null, null, null, header, null);   // trailer is preceded by header
    header.setNext(trailer);                    // header is followed by trailer
    magnitudeOrderedList = mol;
  }

  public int size() { return size; }

  public boolean isEmpty() { return size == 0; }

  public E firstLatitude() {
    if (isEmpty()) return null;
    return header.getNext().getLatitude();   // first data is beyond header
  }

  public E firstLongtitude() {
    if (isEmpty()) return null;
    return header.getNext().getLongitude();   // first data is beyond header
  }

  public E firstUser() {
    if (isEmpty()) return null;
    return header.getNext().getUser();   // first user is beyond header
  }

  public E lastLatitude() {
    if (isEmpty()) return null;
    return trailer.getPrev().getLatitude();    // last data is before trailer
  }

  public E lastLongtitude() {
    if (isEmpty()) return null;
    return trailer.getPrev().getLongitude();    // last data is before trailer
  }

  public E lastUser() {
    if (isEmpty()) return null;
    return trailer.getPrev().getUser();    // last user is before trailer
  }

  public double lastTimestamp() {
    return  trailer.getPrev().getTimestamp();
  }

  public void queryLargest() {
      if(magnitudeOrderedList.isEmpty()) {
         System.out.println("No record on list.");
      }else{
        System.out.println("Largest earthquake in past 6 hours:");
        magnitudeOrderedList.largestEarthquake();
      }
  }

  public void addFirst(double time, E e, E e1, E e2) {
    addBetween(time, e, e1, e2, header, header.getNext());    // place just after the header
  }

  public void addLast(double time, E e, E e2, E e3) {
    addBetween(time, e, e2, e3, trailer.getPrev(), trailer);  // place just before the trailer
  }

  public E removeFirst() {
    if (isEmpty()) return null;                  // nothing to remove
    return remove(header.getNext());             // first element is beyond header
  }

  public E removeLast() {
    if (isEmpty()) return null;                  // nothing to remove
    return remove(trailer.getPrev());            // last element is before trailer
  }

  private void addBetween(double time, E e1, E e2, E e3, Node<E> predecessor, Node<E> successor) {
    // create and link a new node
    Node<E> newest = new Node<>(time, e1, e2, e3, predecessor, successor);
    predecessor.setNext(newest);
    successor.setPrev(newest);
    size++;
    System.out.println(e3 +" is added to the watchers list.");
  }

  private E remove(Node<E> node) {
    Node<E> predecessor = node.getPrev();
    Node<E> successor = node.getNext();
    predecessor.setNext(successor);
    successor.setPrev(predecessor);
    size--;
    System.out.println(node.getUser() + " is removed from the watcher-list");
    return node.getUser();
  }

  public String toString() {
    StringBuilder sb = new StringBuilder("(");
    Node<E> walk = header.getNext();
    while (walk != trailer) {
      sb.append(walk.getUser());
      walk = walk.getNext();
      if (walk != trailer)
        sb.append(", ");
    }
    sb.append(")");
    return sb.toString();
  }

  private void addBefore(Node<E> node, double time, E newLat, E newLong, E newUser) {
    addBetween(time, newLat, newLong, newUser, node.getPrev(), node);
  }

  private void removeBefore(Node<E> node) {
    remove(node.getPrev());
  }

  private Node<E> getNode(int i) {
    Node<E> walker;
    int currentIndex = 0;
    if ( size() <= i | i < 0) throw new NullPointerException();

     if(i < size() / 2) { // starting from header
       walker  = header.getNext();
       while (currentIndex != i) {
         walker = walker.getNext();
         currentIndex ++ ;
       }
       return walker;

     } else { //starting from trailer
       walker = trailer.getPrev();
       currentIndex = size() -1 ;
       while (currentIndex != i ) {
         walker = walker.getPrev();
         currentIndex--;
       }
       return walker;
     }
  }

  public E get(int i) {
    return getNode(i).getUser();
  }

  public void add(int i, double time, E newLat, E newLong, E newUser) {
    addBefore(getNode(i), time, newLat, newLong, newUser);
  }

  public E remove(int i) {
    E deletedData = getNode(i).getUser();
    removeBefore(getNode(i).getNext());
    return deletedData;
  }

  public int find(E data) {
    Node<E> walker = header.getNext();
    int indexOfNode = 0;

    while (walker != trailer) {
      if(walker.getUser().compareTo(data) == 0) return indexOfNode;
      walker = walker.getNext();
      indexOfNode++;
    }
    return -1;
  }

  public E[][] getWatcherList() {
    Node<E> walker = header.getNext();
    E[][] watchers = (E[][]) new Comparable[size()][3];
    int i = 0;
    while (walker != trailer) {
        watchers[i][0] = walker.getUser();
        watchers[i][1] = walker.getLatitude();
        watchers[i++][2] = walker.getLongitude();
        walker = walker.getNext();
    }
    return watchers;
  }

  public String[] getFirstData() {
    String[] data = new String[3];
    data[0] = firstUser().toString();
    data[1] = firstLatitude().toString();
    data[2] = firstLongtitude().toString();

    return data;
  }
}
