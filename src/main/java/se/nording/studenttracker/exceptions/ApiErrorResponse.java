package se.nording.studenttracker.exceptions;

public class ApiErrorResponse {

    private int status;
    private String message;
    private long timestamp;

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis(); // Sätter tiden för när felet inträffade
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
