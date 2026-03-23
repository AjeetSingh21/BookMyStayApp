import java.util.LinkedList;
import java.util.Queue;

public class App {

    public static void main(String[] args) {
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("R001", "Alice"));
        queue.addRequest(new Reservation("R002", "Bob"));
        queue.addRequest(new Reservation("R003", "Charlie"));

        queue.displayRequests();
    }
}

class Reservation {
    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String toString() {
        return reservationId + " - " + guestName;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation processRequest() {
        return requestQueue.poll();
    }

    public void displayRequests() {
        for (Reservation r : requestQueue) {
            System.out.println(r);
        }
    }
}