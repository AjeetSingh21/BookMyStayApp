import java.util.*;

public class app {

    // Custom Exception
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // Reservation Model
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    static class BookingService {

        private Map<String, Integer> inventory = new HashMap<>();

        public BookingService() {
            inventory.put("Single", 5);
            inventory.put("Double", 3);
            inventory.put("Suite", 2);
        }
        public synchronized void bookRoom(Reservation reservation) {
            String type = reservation.getRoomType();

            if (inventory.get(type) != null && inventory.get(type) > 0) {
                int roomNumber = getBookedCount(type) + 1;

                inventory.put(type, inventory.get(type) - 1);

                System.out.println("Booking confirmed for Guest: "
                        + reservation.getGuestName()
                        + ", Room ID: " + type + "-" + roomNumber);
            } else {
                System.out.println("No rooms available for " + type);
            }
        }

        // Helper to calculate booked count
        private int getBookedCount(String type) {
            int total;
            switch (type) {
                case "Single": total = 5; break;
                case "Double": total = 3; break;
                case "Suite": total = 2; break;
                default: total = 0;
            }
            return total - inventory.get(type);
        }

        public void printInventory() {
            System.out.println("\nRemaining Inventory:");
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    // Runnable Task
    static class BookingTask implements Runnable {
        private BookingService service;
        private Reservation reservation;

        public BookingTask(BookingService service, Reservation reservation) {
            this.service = service;
            this.reservation = reservation;
        }

        @Override
        public void run() {
            service.bookRoom(reservation);
        }
    }

    // Main Method
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Concurrent Booking Simulation");

        BookingService service = new BookingService();

        Thread t1 = new Thread(new BookingTask(service, new Reservation("Abhi", "Single")));
        Thread t2 = new Thread(new BookingTask(service, new Reservation("Vanmathi", "Double")));
        Thread t3 = new Thread(new BookingTask(service, new Reservation("Kural", "Suite")));
        Thread t4 = new Thread(new BookingTask(service, new Reservation("Subha", "Single")));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Wait for all threads
        t1.join();
        t2.join();
        t3.join();
        t4.join();

        service.printInventory();
    }
}