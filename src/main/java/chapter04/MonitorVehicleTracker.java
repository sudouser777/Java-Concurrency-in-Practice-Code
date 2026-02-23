package chapter04;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public record MonitorVehicleTracker(@GuardedBy("this") Map<String, MutablePoint> locations) {

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    @Override
    public synchronized Map<String, MutablePoint> locations() {
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id, int x, int y) {
        MutablePoint loc = locations.get(id);
        return loc == null ? null : new MutablePoint(loc);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint loc = locations.get(id);
        if (loc == null) {
            throw new IllegalArgumentException("No such ID: " + id);
        }
        loc.x = x;
        loc.y = y;
    }

    private Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> original) {
        Map<String, MutablePoint> result = new HashMap<>();
        for (Map.Entry<String, MutablePoint> entry : original.entrySet()) {
            result.put(entry.getKey(), new MutablePoint(entry.getValue()));
        }
        return Collections.unmodifiableMap(result);
    }
}
