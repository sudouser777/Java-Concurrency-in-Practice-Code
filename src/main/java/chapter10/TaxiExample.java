package chapter10;

import chapter04.Point;
import net.jcip.annotations.GuardedBy;

import java.util.HashSet;
import java.util.Set;

// warning: deadlock prone!
public class TaxiExample {

    class Taxi {
        @GuardedBy("this")
        private Point location, destination;

        private final Dispatcher dispatcher;


        Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        public synchronized Point getLocation() {
            return location;
        }

        public synchronized void setLocation(Point location) {
            this.location = location;
            if (location.equals(destination)) {
                dispatcher.notifyAvailable(this);
            }
        }
    }

    class Dispatcher {

        @GuardedBy("this")
        private final Set<Taxi> availableTaxis;

        @GuardedBy("this")
        private final Set<Taxi> taxis;

        Dispatcher() {
            taxis = new HashSet<>();
            availableTaxis = new HashSet<>();
        }

        public synchronized void notifyAvailable(Taxi taxi) {
            availableTaxis.add(taxi);
        }

        public synchronized Image getImage() {
            Image image = new Image();
            for (Taxi taxi : taxis) {
                image.drawMarker(taxi.getLocation());
            }
            return image;
        }
    }

    class Image {

        void drawMarker(Point p) {
        }
    }
}
