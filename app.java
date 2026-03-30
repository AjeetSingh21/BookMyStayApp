import java.util.*;

public class app {
    public static void main(String[] args) {
        class Reservation {
            private String reservationId;
            private String guestName;
            private String roomType;

            public Reservation(String reservationId, String guestName, String roomType) {
                this.reservationId = reservationId;
                this.guestName = guestName;
                this.roomType = roomType;
            }

            public String getReservationId() {
                return reservationId;
            }

            @Override
            public String toString() {
                return "Reservation{id=" + reservationId + ", guest=" + guestName + ", roomType=" + roomType + "}";
            }
        }

        class Service {
            private String name;
            private double cost;

            public Service(String name, double cost) {
                this.name = name;
                this.cost = cost;
            }

            public double getCost() {
                return cost;
            }

            @Override
            public String toString() {
                return name + " (₹" + cost + ")";
            }
        }

        class AddOnServiceManager {
            private Map<String, List<Service>> serviceMap = new HashMap<>();

            public void addServiceToReservation(String reservationId, Service service) {
                serviceMap.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
                System.out.println("Added service " + service + " to reservation " + reservationId);
            }

            public List<Service> getServicesForReservation(String reservationId) {
                return serviceMap.getOrDefault(reservationId, Collections.emptyList());
            }

            public double calculateTotalCost(String reservationId) {
                return getServicesForReservation(reservationId).stream()
                        .mapToDouble(Service::getCost)
                        .sum();
            }
        }

        Reservation r1 = new Reservation("R001", "Alice", "Deluxe");
        Reservation r2 = new Reservation("R002", "Bob", "Suite");

        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa", 1500);
        Service airportPickup = new Service("Airport Pickup", 1000);

        AddOnServiceManager manager = new AddOnServiceManager();
        manager.addServiceToReservation(r1.getReservationId(), breakfast);
        manager.addServiceToReservation(r1.getReservationId(), spa);
        manager.addServiceToReservation(r2.getReservationId(), airportPickup);

        System.out.println("\nReservation Details:");
        System.out.println(r1);
        System.out.println("Services: " + manager.getServicesForReservation(r1.getReservationId()));
        System.out.println("Total Add-On Cost: ₹" + manager.calculateTotalCost(r1.getReservationId()));

        System.out.println(r2);
        System.out.println("Services: " + manager.getServicesForReservation(r2.getReservationId()));
        System.out.println("Total Add-On Cost: ₹" + manager.calculateTotalCost(r2.getReservationId()));
    }
}
