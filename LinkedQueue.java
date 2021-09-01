public class LinkedQueue<E extends Comparable<E>> implements Queue<E> {

  private SinglyLinkedList<E> earthquakeQueue = new SinglyLinkedList<>();
  private ArrayList<E> magnitudeOrderedList;
  private DoublyLinkedList<E> watcherList;

  public LinkedQueue(ArrayList<E> mor, DoublyLinkedList<E> watchers) {
    magnitudeOrderedList = mor;
    watcherList = watchers;
  }

  @Override
  public int size() { return earthquakeQueue.size(); }

  @Override
  public boolean isEmpty() { return earthquakeQueue.isEmpty(); }

  @Override
  public void enqueue(E id, E time, E  place, E coordinates, E magnitude, int option) {
    int index = magnitudeOrderedList.add(id, time, place, coordinates, magnitude, option);
    if(size() == 0)
        earthquakeQueue.addLast(id, time, place, coordinates, magnitude, index);
    else{
        earthquakeQueue.addLast(id, time, place, coordinates, magnitude, index);
    }
       earthquakeQueue.setIndex(magnitudeOrderedList);
       checkTimestamp(time);
    closeToEarthquake(coordinates, magnitude, place);
  }

  @Override
  public E first() { return earthquakeQueue.firstTime(); }

  @Override
  public int dequeue() { return earthquakeQueue.removeFirst(); }

  @Override
  public E peek() {
    return earthquakeQueue.firstTime();
  }

  public void checkTimestamp(E time) {
      //user ve earthquake listelerine son girenlerin zamanı ile queuenun ilk(top olan) elemanının zamanını kontrol ettim
          while (size() != 0 &&(!(Double.parseDouble(time.toString()) -
                  Double.parseDouble(peek().toString()) <= 6) |
                  !(watcherList.lastTimestamp() - Double.parseDouble(time.toString()) <= 6))) {
              int magnitude = dequeue();
              magnitudeOrderedList.remove(magnitude);
              earthquakeQueue.setIndex(magnitudeOrderedList);
          }
  }

  private void closeToEarthquake(E coordinates, E magnitude, E place) {
      //euclidian distance ile hesaplayip bastirdim

      String[] temp = coordinates.toString().split(", ");
      double[] coords = {Double.parseDouble(temp[0]), Double.parseDouble(temp[1])};

      E[][]  list = watcherList.getWatcherList();
      for (int i = 0; i < list.length; i++) {
          if(Math.sqrt(Math.pow(Math.abs(Double.parseDouble(list[i][1].toString()) - coords[0]), 2) + Math.pow(Math.abs(coords[1] - Double.parseDouble(list[i][2].toString())) ,2)) <
                  2* Math.pow(Double.parseDouble(magnitude.toString()), 3)) {
                    System.out.println("Earthquake " + place.toString() + " is close to " + list[i][0]);
          }
      }
  }

  public String toString() {
    return earthquakeQueue.toString();
  }
}
