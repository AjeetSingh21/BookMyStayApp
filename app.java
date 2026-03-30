import java.io.*;
import java.util.*;

public class app {
    public static void main(String[] args) {
        class Reservation implements Serializable {
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

        class SystemState implements Serializable {
            private Map<String, Integer> inventory;
            private List<Reservation> bookingHistory;

            public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
                this.inventory = inventory;
                this.bookingHistory = bookingHistory;
            }

            public Map<String, Integer> getInventory() {
                return inventory;
            }

            public List<Reservation> getBookingHistory() {
                return bookingHistory;
            }
        }

        class PersistenceService {
            private String filename = "system_state.ser";

            public void saveState(SystemState state) {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                    out.writeObject(state);
                    System.out.println("System state saved successfully.");
                } catch (IOException e) {
                    System.out.println("Error saving state: " + e.getMessage());
                }
            }

            public SystemState loadState() {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
                    SystemState state = (SystemState) in.readObject();
                    System.out.println("System state loaded successfully.");
                    return state;
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("No previous state found. Starting fresh.");
                    return null;
                }
            }
        }

        PersistenceService persistence = new PersistenceService();
        SystemState loadedState = persistence.loadState();

        Map<String, Integer> inventory;
        List<Reservation> bookingHistory;

        if (loadedState != null) {
            inventory = loadedState.getInventory();
            bookingHistory = loadedState.getBookingHistory();
        } else {
            inventory = new HashMap<>();
            inventory.put("Deluxe", 2);
            inventory.put("Suite", 1);
            inventory.put("Standard", 3);
            bookingHistory = new ArrayList<>();
        }

        Reservation r1 = new Reservation("R001", "Alice", "Deluxe");
        Reservation r2 = new Reservation("R002", "Bob", "Suite");

        if (inventory.get(r1.getRoomType()) > 0) {
            inventory.put(r1.getRoomType(), inventory.get(r1.getRoomType()) - 1);
            bookingHistory.add(r1);
            System.out.println("Confirmed: " + r1);
        }

        if (inventory.get(r2.getRoomType()) > 0) {
            inventory.put(r2.getRoomType(), inventory.get(r2.getRoomType()) - 1);
            bookingHistory.add(r2);
            System.out.println("Confirmed: " + r2);
        }

        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\n--- Booking History ---");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }

        persistence.saveState(new SystemState(inventory, bookingHistory));
    }
}
