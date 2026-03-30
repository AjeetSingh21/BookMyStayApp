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
gi
        class BookingHistory {
            private Map<String, Reservation> confirmedBookings = new HashMap<>();

            public void addReservation(Reservation reservation) {
                confirmedBookings.put(reservation.getReservationId(), reservation);
                System.out.println("Confirmed: " + reservation);
            }

            public Reservation getReservation(String reservationId) {
                return confirmedBookings.get(reservationId);
            }

            public void removeReservation(String reservationId) {
                confirmedBookings.remove(reservationId);
            }

            public void printHistory() {
                System.out.println("\n--- Booking History ---");
                for (Reservation r : confirmedBookings.values()) {
                    System.out.println(r);
                }
                System.out.println("-----------------------");
            }
        }

        class CancellationService {
            private Map<String, Integer> inventory = new HashMap<>();
            private Stack<String> rollbackStack = new Stack<>();
            private BookingHistory history;

            public CancellationService(BookingHistory history) {
                this.history = history;
                inventory.put("Deluxe", 2);
                inventory.put("Suite", 1);
                inventory.put("Standard", 3);
            }

            public void confirmReservation(Reservation reservation) {
                String roomType = reservation.getRoomType();
                if (inventory.get(roomType) != null && inventory.get(roomType) > 0) {
                    inventory.put(roomType, inventory.get(roomType) - 1);
                    history.addReservation(reservation);
                    rollbackStack.push(reservation.getReservationId());
                } else {
                    System.out.println("No rooms available for type: " + roomType);
                }
            }

            public void cancelReservation(String reservationId) {
                Reservation reservation = history.getReservation(reservationId);
                if (reservation == null) {
                    System.out.println("Cancellation failed: Reservation not found or already cancelled.");
                    return;
                }
                String roomType = reservation.getRoomType();
                inventory.put(roomType, inventory.get(roomType) + 1);
                history.removeReservation(reservationId);
                rollbackStack.push(reservationId);
                System.out.println("Cancelled reservation: " + reservation);
                System.out.println("Inventory rolled back for room type: " + roomType);
            }

            public void printInventory() {
                System.out.println("\n--- Current Inventory ---");
                for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
                System.out.println("-------------------------");
            }
        }

        BookingHistory history = new BookingHistory();
        CancellationService service = new CancellationService(history);

        Reservation r1 = new Reservation("R001", "Alice", "Deluxe");
        Reservation r2 = new Reservation("R002", "Bob", "Suite");
        Reservation r3 = new Reservation("R003", "Charlie", "Standard");

        service.confirmReservation(r1);
        service.confirmReservation(r2);
        service.confirmReservation(r3);

        history.printHistory();
        service.printInventory();

        service.cancelReservation("R002");
        service.cancelReservation("R004"); // invalid cancellation

        history.printHistory();
        service.printInventory();
    }
}
