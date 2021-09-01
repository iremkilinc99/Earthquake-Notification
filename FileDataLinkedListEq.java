public class FileDataLinkedListEq<E> {
    //Deprem dosyasını okumak icin kullandim
        private static class Node<E> {

            private E id;
            private E time;
            private E place;
            private E coordinates;
            private E magnitude;
            private int index;

            private Node<E> next;
            public Node(E id, E time, E place, E coordinates, E magnitude, int magnitudeIndex, Node<E> n) {
                this.id = id;
                this.time = time;
                this.place = place;
                this.coordinates = coordinates;
                this.magnitude = magnitude;
                index = magnitudeIndex;
                next = n;
            }

            public E getId() { return id; }

            public E getTime() { return time; }

            public E getPlace() { return  place; }

            public E getCoordinates() { return coordinates; }

            public E getMagnitude() { return  magnitude; }

            public int getIndex() { return  index;}

            public Node<E> getNext() { return next; }

            public void setNext(Node<E> n) { next = n; }
        } //----------- end of nested Node class -----------

        private Node<E> head = null;

        private Node<E> tail = null;

        private int size = 0;

        public FileDataLinkedListEq() {}

        public int size() { return size; }

        public boolean isEmpty() { return size == 0; }

        public E firstId() {
            if (isEmpty()) return null;
            return head.getId();
        }

        public E firstTime() {
            if (isEmpty()) return null;
            return head.getTime();
        }

        public E firstPlace() {
            if (isEmpty()) return null;
            return head.getPlace();
        }

        public E firstCoordinates() {
            if (isEmpty()) return null;
            return head.getCoordinates();
        }

        public E firstMagnitude() {
            if (isEmpty()) return null;
            return head.getMagnitude();
        }

        public void addFirst(E id, E time, E place, E coordinates, E magnitude, int magnitudeIndex) {
            head = new Node<>(id, time, place, coordinates, magnitude, magnitudeIndex, head);
            if (size == 0)
                tail = head;
            size++;
        }

        public void addLast(E id, E time, E  place, E coordinates, E Magnitude, int magnitudeIndex) {
            Node<E> newest = new Node<>(id, time, place, coordinates, Magnitude, magnitudeIndex,null);
            if (isEmpty())
                head = newest;
            else
                tail.setNext(newest);
            tail = newest;
            size++;
        }

        public int removeFirst() {
            if (isEmpty()) return 0;
            int answer = head.getIndex();
            head = head.getNext();
            size--;
            if (size == 0)
                tail = null;
            return answer;
        }

        public String[] getFirstData() {
            String[] data = new String[5];
            data[0] = firstId().toString();
            data[1] = firstTime().toString();
            data[2] = firstPlace().toString();
            data[3] = firstCoordinates().toString();
            data[4] = firstMagnitude().toString();

            return data;
        }

        public boolean equals(Object o) {
            if (o == null) return false;
            if (getClass() != o.getClass()) return false;
            FileDataLinkedListEq other = (FileDataLinkedListEq) o;   // use nonparameterized type
            if (size != other.size) return false;
            Node walkA = head;                               // traverse the primary list
            Node walkB = other.head;                         // traverse the secondary list
            while (walkA != null) {
                if (!walkA.getId().equals(walkB.getId())) return false; //mismatch
                walkA = walkA.getNext();
                walkB = walkB.getNext();
            }
            return true;   // if we reach this, everything matched successfully
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("(");
            Node<E> walk = head;
            while (walk != null) {
                sb.append(walk.getId());
                if (walk != tail)
                    sb.append(", ");
                walk = walk.getNext();
            }
            sb.append(")");
            return sb.toString();
        }

}


