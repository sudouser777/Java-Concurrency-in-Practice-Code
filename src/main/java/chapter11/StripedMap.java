package chapter11;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class StripedMap {

    // Synchronization policy: buckets[n] guarded by locks[n%N_LOCKS]
    private static final int N_LOCKS = 16;
    private final Object[] locks;
    private final Node[] buckets;

    public StripedMap(int numBuckets) {
        buckets = new Node[numBuckets];
        locks = new Object[N_LOCKS];

        for (int i = 0; i < locks.length; i++) {
            locks[i] = new Object();
        }
    }

    private static final class Node {
        public Node next;
        public Object key;
        public Object value;
    }

    private final int hash(Object key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    public Object get(Object key) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
           for(Node m = buckets[hash]; m != null; m = m.next){
               if(m.key.equals(key)){
                   return m.value;
               }
           }
        }
        return null;
    }

    public void clear() {
        for (int i = 0; i < locks.length; i++) {
            synchronized (locks[i]) {
                buckets[i] = null;
            }
        }
    }
}
