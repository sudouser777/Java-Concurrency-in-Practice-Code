package chapter11;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

@ThreadSafe
public class ServerStatus {

    @GuardedBy("this")
    public final Set<String> users;

    @GuardedBy("this")

    public final Set<String> queries;

    public ServerStatus() {
        users = new HashSet<>();
        queries = new HashSet<>();
    }

    public synchronized void addUser(String user) {
        users.add(user);
    }

    public synchronized void addQuery(String query) {
        queries.add(query);
    }

    public synchronized void removeUser(String user) {
        users.remove(user);
    }

    public synchronized void removeQuery(String query) {
        queries.remove(query);
    }
}
