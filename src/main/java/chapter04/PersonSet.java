package chapter04;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

@ThreadSafe
public class PersonSet {

    @GuardedBy("this")
    private final Set<Person> mySet = new HashSet<>();

    public synchronized void add(Person p) {
        mySet.add(p);
    }

    public synchronized boolean contains(Person p) {
        return mySet.contains(p);
    }

}
