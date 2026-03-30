import java.util.*;

public class app {
    public static void main(String[] args) {
        class InvalidBookingException extends Exception {
            public InvalidBookingException(String message) {
                super(message);
            }
        }

        class Reservation {
            private String reservationId;
            private String guestName;
            private String roomType;

            public Reservation(String reservationId, String guestName, String roomType) {
                this.reservationId = reservationId;
                this.guestName = guestName;
                this.roomType = roomType;
            }

            @Override
            public String toString() {
                return "Reservation{id=" + reservationId + ", guest=" + guestName + ", roomType=" + roomType + "}";
            }
        }

        class BookingValidator {
            private Set<String> validRoomTypes = new HashSet<>(Arrays.asList("Deluxe", "Suite", "Standard"));
            private Map<String, Integer> inventory = new HashMap<>();

            public BookingValidator() {
                inventory.put("Deluxe", 2);
                inventory.put("Suite", 1);
                inventory.put("Standard", 3);
            }

            public void validateReservation(String roomType) throws InvalidBookingException {
                if (!validRoomTypes.contains(roomType)) {
                    throw new InvalidBookingException("Invalid room type: " + roomType);
                }
                if (inventory.get(roomType) == null || inventory.get(roomType) <= 0) {
                    throw new InvalidBookingException("No rooms available for type: " + roomType);
                }
            }

            public void confirmReservation(String roomType) {
                inventory.put(roomType, inventory.get(roomType) - 1);
            }
        }

        BookingValidator validator = new BookingValidator();

        try {
            validator.validateReservation("Deluxe");
            Reservation r1 = new Reservation("R001", "Alice", "Deluxe");
            validator.confirmReservation("Deluxe");
            System.out.println("Confirmed: " + r1);
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            validator.validateReservation("Penthouse");
            Reservation r2 = new Reservation("R002", "Bob", "Penthouse");
            validator.confirmReservation("Penthouse");
            System.out.println("Confirmed: " + r2);
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            validator.validateReservation("Suite");
            Reservation r3 = new Reservation("R003", "Charlie", "Suite");
            validator.confirmReservation("Suite");
            System.out.println("Confirmed: " + r3);

            validator.validateReservation("Suite");
            Reservation r4 = new Reservation("R004", "David", "Suite");
            validator.confirmReservation("Suite");
            System.out.println("Confirmed: " + r4);
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
