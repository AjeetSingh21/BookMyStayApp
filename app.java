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

            public String getRoomType() {
                return roomType;
            }

            @Override
            public String toString() {
                return "Reservation{id=" + reservationId + ", guest=" + guestName + ", roomType=" + roomType + "}";
            }
        }

        class ConcurrentBookingProcessor {
            private Map<String, Integer> inventory = new HashMap<>();

            public ConcurrentBookingProcessor() {
                inventory.put("Deluxe", 2);
                inventory.put("Suite", 1);
                inventory.put("Standard", 3);
            }

            public synchronized void processBooking(Reservation reservation) {
                String roomType = reservation.getRoomType();
                if (inventory.get(roomType) != null && inventory.get(roomType) > 0) {
                    inventory.put(roomType, inventory.get(roomType) - 1);
                    System.out.println("Confirmed: " + reservation);
                } else {
                    System.out.println("Failed: No rooms available for " + reservation);
                }
            }

            public void printInventory() {
                System.out.println("\n--- Current Inventory ---");
                for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
                System.out.println("-------------------------");
            }
        }

        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();

        Runnable guest1 = () -> processor.processBooking(new Reservation("R001", "Alice", "Deluxe"));
        Runnable guest2 = () -> processor.processBooking(new Reservation("R002", "Bob", "Deluxe"));
        Runnable guest3 = () -> processor.processBooking(new Reservation("R003", "Charlie", "Suite"));
        Runnable guest4 = () -> processor.processBooking(new Reservation("R004", "David", "Suite"));
        Runnable guest5 = () -> processor.processBooking(new Reservation("R005", "Eve", "Standard"));

        Thread t1 = new Thread(guest1);
        Thread t2 = new Thread(guest2);
        Thread t3 = new Thread(guest3);
        Thread t4 = new Thread(guest4);
        Thread t5 = new Thread(guest5);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        processor.printInventory();
    }
}
