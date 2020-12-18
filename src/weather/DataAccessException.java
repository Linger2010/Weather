package weather;

public class DataAccessException extends RuntimeException {
    /**
     * Constructs a DataAccessException with a given message.
     *
     * @param message the given message.
     */
    public DataAccessException(String message) {
        super(message);
    }
}
