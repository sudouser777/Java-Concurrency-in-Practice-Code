package chapter11;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class ServerStatusWithSplitLocking {

    @GuardedBy("this")
    public final Set<String> users;

    @GuardedBy("this")

    public final Set<String> queries;

    public ServerStatusWithSplitLocking() {
        users = new HashSet<>();
        queries = new HashSet<>();
    }

    public void addUser(String user) {
        synchronized (users) {
            users.add(user);
        }
    }

    public void addQuery(String query) {
        synchronized (queries) {
            queries.add(query);
        }
    }

    public void removeUser(String user) {
        synchronized (users) {
            users.remove(user);
        }
    }

    public void removeQuery(String query) {
        synchronized (queries) {
            queries.remove(query);
        }
    }
}
