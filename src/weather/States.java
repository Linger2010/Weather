package weather;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class States {

    /**
     * Represents a list of states.
     */
    private List<String> states = new ArrayList<>();

    /**
     * It is used to access the data to initialize a weather model update.
     */
    private static final StatesDAO DAO = new StatesDAOWebImpl();

    /**
     * Represents a Logger retrieved by the Manager class.
     */
    private static final Logger LOG = Manager.getLogger("States");

    /**
     * Constructs a States with a given list of states.
     *
     * @param states a given list of states.
     */
    public States(List<String> states) {
        this.setStates(states);
    }

    /**
     * Retrieves a States with data from the server.
     *
     * @return a States.
     */
    public static States load() {
        try {
            return DAO.load();
        } catch (DAOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new DataAccessException(ex.getMessage());
        }
    }

    /**
     * Gets the list of states of this States.
     *
     * @return the list of states of this States.
     */
    public List<String> getStatesList() {
        return this.states;
    }

    /**
     * Sets the list of states of this States with the given list.
     *
     * @param states a given list of states.
     */
    private void setStates(List<String> states) {
        Objects.requireNonNull(states);
        Collections.sort(states);
        this.states = Collections.unmodifiableList(states);
    }
}
