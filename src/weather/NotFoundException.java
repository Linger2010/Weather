package weather;

public class NotFoundException extends DataAccessException {
    /**
     * Constructs a NotFoundException with the given message.
     *
     * @param message the given message.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
