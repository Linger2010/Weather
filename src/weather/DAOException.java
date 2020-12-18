package weather;

public final class DAOException extends Exception{
    /**
     * Constructs a DAOException with a given message.
     *
     * @param message the given message of the exception.
     */
    public DAOException(String message) {
        super(message);
    }
}
