
import java.util.Iterator;
public interface List<E> extends Iterable<E> {

  int size();
  boolean isEmpty();
  E[] get(int i) throws IndexOutOfBoundsException;
  E set(int i, E id, E time, E  place, E coordinates, E magnitude) throws IndexOutOfBoundsException;
  int add(E id, E time, E  place, E coordinates, E magnitude, int option) throws IndexOutOfBoundsException;
  E remove(int i) throws IndexOutOfBoundsException;
  Iterator<E> iterator();
}
