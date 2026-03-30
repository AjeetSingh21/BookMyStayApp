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

        class BookingHistory {
            private List<Reservation> history = new ArrayList<>();

            public void addConfirmedReservation(Reservation reservation) {
                history.add(reservation);
                System.out.println("Confirmed reservation added to history: " + reservation);
            }

            public List<Reservation> getHistory() {
                return history;
            }
        }

        class BookingReportService {
            public void generateReport(List<Reservation> reservations) {
                System.out.println("\n--- Booking Report ---");
                System.out.println("Total Confirmed Bookings: " + reservations.size());
                for (Reservation r : reservations) {
                    System.out.println(r);
                }
                System.out.println("----------------------");
            }
        }

        Reservation r1 = new Reservation("R001", "Alice", "Deluxe");
        Reservation r2 = new Reservation("R002", "Bob", "Suite");
        Reservation r3 = new Reservation("R003", "Charlie", "Standard");

        BookingHistory history = new BookingHistory();
        history.addConfirmedReservation(r1);
        history.addConfirmedReservation(r2);
        history.addConfirmedReservation(r3);

        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getHistory());
    }
}
