
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E extends Comparable<E>> implements List<E> {

  public static final int CAPACITY=1000;

  private E[][] data;
  private int size = 0;
  public ArrayList() { this(CAPACITY); }
  public ArrayList(int capacity) {
    data = (E[][]) new Comparable[capacity][5];
  }

  public int size() { return size; }
  public boolean isEmpty() { return size == 0; }

  public E[] get(int i) throws IndexOutOfBoundsException {
    checkIndex(i, size);
    return data[i];
  }

  public E set(int i, E id, E time, E  place, E coordinates, E magnitude) throws IndexOutOfBoundsException {
    checkIndex(i, size);
    E temp = data[i][0];
    data[i][0] = magnitude ;
    data[i][1] = id;
    data[i][2] = time;
    data[i][3] = place;
    data[i][4] = coordinates;
    return temp;
  }

  public int add(E id, E time, E  place, E coordinates, E magnitude, int option) throws IndexOutOfBoundsException {
    int index;
    if (size == data.length)
      resize(2 * data.length);

    if(size == 0) {
      data[0][0] = magnitude ;
      data[0][1] = id;
      data[0][2] = time;
      data[0][3] = place;
      data[0][4] = coordinates;
      index = 0;
    }else{
      index = addBinarySearch(data, 0, size, id, time, place, coordinates, magnitude);
    }
    size++;
    if(option == 1)
      System.out.println("Earthquake "+ place + " is inserted into the magnitude-ordered-list.");

    return index;
  }

  public int addBinarySearch(E arr[][], int l, int r, E id, E time, E  place, E coordinates, E magnitude) {
    if (r > l) {
      int mid = (l + r) / 2;

      if (arr[mid][0].compareTo(magnitude) > 0)
        return addBinarySearch(arr, l, mid, id, time, place, coordinates, magnitude);
      else if(arr[mid][0].compareTo(magnitude) < 0)
        return addBinarySearch(arr, mid + 1, r, id, time, place, coordinates, magnitude);

    }
       for (int k = size - 1; k >= l; k--) {
         data[k + 1][0] = data[k][0];
         data[k + 1][1] = data[k][1];
         data[k + 1][2] = data[k][2];
         data[k + 1][3] = data[k][3];
         data[k + 1][4] = data[k][4];
       }
       data[l][0] = magnitude;
       data[l][1] = id;
       data[l][2] = time;
       data[l][3] = place;
       data[l][4] = coordinates;

       return l;
  }

  public E remove(int i) throws IndexOutOfBoundsException {
    checkIndex(i, size);
    E temp = data[i][0];
    for (int k=i; k < size-1; k++) {
      data[k][0] = data[k+1][0];
      data[k][1] = data[k+1][1];
      data[k][2] = data[k+1][2];
      data[k][3] = data[k+1][3];
      data[k][4] = data[k+1][4];
    }
    data[size-1][0] = null;
    data[size-1][1] = null;
    data[size-1][2] = null;
    data[size-1][3] = null;
    data[size-1][4] = null;
    size--;
    return temp;
  }

  protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
    if (i < 0 || i >= n)
      throw new IndexOutOfBoundsException("Illegal index: " + i);
  }

  protected void resize(int capacity) {
    E[][] temp = (E[][]) new Object[capacity];
    for (int k=0; k < size; k++)
      temp[k] = data[k];
    data = temp;
  }

  public void largestEarthquake() {
    System.out.println("Magnitude " + data[size-1][0] + " at " + data[size-1][3]);
  }

  //---------------- nested ArrayIterator class ----------------

  private class ArrayIterator implements Iterator<E> {
    private int j = 0;                   // index of the next element to report
    private boolean removable = false;   // can remove be called at this time?

    public boolean hasNext() { return j < size; }

    public E next() throws NoSuchElementException {
      if (j == size) throw new NoSuchElementException("No next element");
      removable = true;
      return data[j++][0];   // post-increment j, so it is ready for future call to next
    }

     public void remove() throws IllegalStateException {
      if (!removable) throw new IllegalStateException("nothing to remove");
      ArrayList.this.remove(j-1);
      j--;
      removable = false;
      } 
  } //------------ end of nested ArrayIterator class ------------

  @Override
  public Iterator<E> iterator() {
    return new ArrayIterator();     // create a new instance of the inner class
  }

  public String toString() {
    StringBuilder sb = new StringBuilder("(");
    for (int j = 0; j < size; j++) {
      if (j > 0) sb.append(", ");
      sb.append(data[j]);
    }
    sb.append(")");
    return sb.toString();
  }
}
