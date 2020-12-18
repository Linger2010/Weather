package weather;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class WeatherController {
    /**
     * Represents a view for the user interface that display weather data
     * from the weather model.
     */
    private WeatherView view;

    /**
     * Represents a weather model that contains data to be reflected in the
     * view.
     */
    private WeatherModel model;

    /**
     * Represents the event listener for loading stations.
     */
    private final LoadListener LOAD_LISTENER = new LoadListener();

    /**
     * Represents the event listener for getting a current observation.
     */
    private final GetListener GET_LISTENER = new GetListener();

    /**
     * Represents the event listener for refreshing the local data.
     */
    private final RefreshListener REFRESH_LISTENER = new RefreshListener();

    /**
     * Represents a Logger retrieved by the Manager class.
     */
    private static final Logger LOG = Manager.getLogger("WeatherController");

    /**
     * Constructs a WeatherController with a given view and a given model.
     *
     * @param view a given view for the controller.
     *
     * @param model a given model for the controller.
     */
    public WeatherController(WeatherView view, WeatherModel model) {
        this.view = view;
        this.model = model;
        this.view.addLoadListener(LOAD_LISTENER);
        this.view.addGetListener(GET_LISTENER);
        this.view.addRefreshListener(REFRESH_LISTENER);
    }

    /**
     * Displays the given message on the user interface.
     *
     * @param message the given message to be displayed.
     */
    private void onException(String message) {
        Objects.requireNonNull(message);
        this.view.onException(message);
    }

    /**
     * Displays a waring message caused by the given exception
     * on the user interface.
     *
     * @param ex the given exception.
     */
    private void onException(Exception ex) {
        Objects.requireNonNull(ex);
        this.view.onException(ex.getMessage());
    }

    /**
     * Initializes the user interface with data.
     */
    public void initializeModel() {
        this.model.setState(this.view.getStateQuery());
        this.model.setStations(this.view.getStationsQuery());
    }

    private class LoadListener implements EventHandler<ActionEvent> {

        /**
         * {@inheritDoc}
         */
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                var state = WeatherController.this.view.getStateQuery();
                var stations = WeatherModel.getStations(state);
                WeatherController.this.model.setState(state);
                WeatherController.this.model.setStations(stations);
                WeatherController.this.model.setCurrentObservation(CurrentObservation.EMPTY);
                WeatherController.this.view.onModelUpdate(WeatherController.this.model);
            } catch (DataAccessException ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                WeatherController.this.onException(ex);
            }
        }
    }

    private class GetListener implements EventHandler<ActionEvent> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void handle(ActionEvent actionEvent) {

            try{
                if(WeatherController.this.model.getState().isEmpty()) {
                    initializeModel();
                } // else, the model represents the current data doNothing();
                var station = WeatherController.this.view.getStationQuery();
                var currentObservation = WeatherModel.getCurrentObservation(station.getStationId());
                WeatherController.this.model.setCurrentObservation(currentObservation);
                WeatherController.this.view.onModelUpdate(WeatherController.this.model);
            } catch (DataAccessException ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                WeatherController.this.onException(ex);
            }
        }
    }

    private class RefreshListener implements EventHandler<ActionEvent> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                var states = WeatherModel.getAllLocalStates();
                WeatherModel.deleteAllLocalData();
                for(var state : states) {
                    WeatherModel.getStations(state);
                }
                WeatherController.this.model.setCurrentObservation(CurrentObservation.EMPTY);
                WeatherController.this.onException("All local data are refreshed.");
            } catch (DataAccessException ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                WeatherController.this.onException(ex);
            }
        }
    }
}
