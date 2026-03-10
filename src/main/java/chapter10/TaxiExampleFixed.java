package chapter10;

import chapter04.Point;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

public class TaxiExampleFixed {

    @ThreadSafe
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
            boolean resetDestination;
            synchronized (this) {
                this.location = location;
                resetDestination = location.equals(destination);
            }
            if (resetDestination) {
                dispatcher.notifyAvailable(this);
            }
        }
    }

    @ThreadSafe
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
            HashSet<Taxi> taxisCopy;
            synchronized (this) {
                taxisCopy = new HashSet<>(taxis);
            }

            Image image = new Image();
            for (Taxi taxi : taxisCopy) {
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
