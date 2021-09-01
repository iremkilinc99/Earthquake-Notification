public class FileDataLinkedListWatchers<E> {
    //user dosyasını okumak icin kullandim
        private static class Node<E> {
            private double timestamp;
            private E query;
            private E  latitude;
            private E  longtitude;
            private E user;

            private Node<E> next;

            public Node(double time, E q, E lat, E lng, E usr, Node<E> n) {
                timestamp = time;
                query = q;
                latitude = lat;
                longtitude = lng;
                user = usr ;
                next = n;
            }

            public double getTimestamp() {
                return timestamp;
            }

            public E getQuery() {
                return query;
            }

            public E getLongtitude() {
                return longtitude;
            }

            public E getLatitude() {
                return latitude;
            }

            public E getUser() {
                return user;
            }

            public Node<E> getNext() { return next; }

            public void setNext(Node<E> n) { next = n; }

        } //----------- end of nested Node class -----------

        private Node<E> head = null;

        private Node<E> tail = null;

        private int size = 0;

        public FileDataLinkedListWatchers() {}

        public int size() { return size; }

        public boolean isEmpty() { return size == 0; }

        public double firstTimestamp() {
            if (isEmpty()) return 0;
            return head.getTimestamp();
        }

        public E firstQuery() {
            if (isEmpty()) return null;
            return head.getQuery();
        }

        public E firstLat() {
            if (isEmpty()) return null;
            return head.getLatitude();
        }

        public E firstLong() {
            if (isEmpty()) return null;
            return head.getLongtitude();
        }

        public E firstUser() {
            if (isEmpty()) return null;
            return head.getUser();
        }

        public void addFirst(double time, E q, E lat, E lng, E usr) {
            head = new Node<>(time, q, lat, lng, usr, head);
            if (size == 0)
                tail = head;
            size++;
        }

        public void addLast(double time, E q, E lat, E lng, E usr) {
            Node<E> newest = new Node<>(time, q, lat, lng, usr, null);
            if (isEmpty())
                head = newest;
            else
                tail.setNext(newest);
            tail = newest;
            size++;
        }

        public double removeFirst() {
            if (isEmpty()) return 0;
            double answer = head.getTimestamp();
            head = head.getNext();
            size--;
            if (size == 0)
                tail = null;
            return answer;
        }

        public String[] getFirstData() {
            String[] data = new String[5];
            data[0] = firstTimestamp()+ "";
            data[1] = firstQuery().toString();
            data[2] = firstLat().toString();
            data[3] = firstLong().toString();
            data[4] = firstUser().toString();

            return data;
        }

        public boolean equals(Object o) {
            if (o == null) return false;
            if (getClass() != o.getClass()) return false;
            FileDataLinkedListWatchers other = (FileDataLinkedListWatchers) o;   // use nonparameterized type
            if (size != other.size) return false;
            Node walkA = head;                               // traverse the primary list
            Node walkB = other.head;                         // traverse the secondary list
            while (walkA != null) {
                if (!walkA.getUser().equals(walkB.getUser())) return false; //mismatch
                walkA = walkA.getNext();
                walkB = walkB.getNext();
            }
            return true;   // if we reach this, everything matched successfully
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("(");
            Node<E> walk = head;
            while (walk != null) {
                sb.append(walk.getUser());
                if (walk != tail)
                    sb.append(", ");
                walk = walk.getNext();
            }
            sb.append(")");
            return sb.toString();
        }

}
