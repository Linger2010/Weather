package weather;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class WeatherView extends Application {

    /**
     * Represents a States that is loaded from the server.
     */
    private static final States STATES = States.load();

    /**
     * A combobox to display all states.
     */
    private ComboBox<String> statesCombobox = new ComboBox<>();

    /**
     * A combobox to display all stations of a state.
     */
    private ComboBox<Station> stationsCombobox = new ComboBox<>();

    /**
     * A button to get the current observation of a station.
     */
    private Button getButton = new Button("Get");

    /**
     * A text area to display a current observation.
     */
    private TextArea currentObservationTextArea = new TextArea();

    /**
     * A Label with explaining note for a button.
     */
    private Label refreshLabel = new Label("Refresh Local Data");

    /**
     * A button to refresh all local data.
     */
    private Button refreshButton = new Button("Refresh");

    /**
     * Represents a Logger retrieved by the Manager class.
     */
    private static final Logger LOG = Manager.getLogger("WeatherView");

    /**
     * Sets a model and a view for this controller.
     */
    private void setController() {
        var model = new WeatherModel();
        var controller = new WeatherController(this, model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) {
        this.setController();
        var root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);

        var getBox = new HBox();
        getBox.setPadding(new Insets(10));
        getBox.setSpacing(5);
        this.statesCombobox.getItems().addAll(STATES.getStatesList());
        this.statesCombobox.getSelectionModel().selectFirst();
        getBox.getChildren().add(this.statesCombobox);
        this.stationsCombobox.getItems().addAll(WeatherModel.getStations(this.getStateQuery()));
        this.stationsCombobox.getSelectionModel().selectFirst();
        getBox.getChildren().add(this.stationsCombobox);
        getBox.getChildren().add(this.getButton);
        root.getChildren().add(getBox);

        root.getChildren().add(this.currentObservationTextArea);

        var refreshBox = new HBox();
        refreshBox.setPadding(new Insets(10));
        refreshBox.setSpacing(5);
        refreshBox.getChildren().add(this.refreshLabel);
        refreshBox.getChildren().add(this.refreshButton);
        root.getChildren().add(refreshBox);

        var scene = new Scene(root);
        stage.setTitle("Weather");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds an event listener to the combobox containing state list.
     *
     * @param listener a event listener.
     */
    public void addLoadListener(EventHandler<ActionEvent> listener) {
        this.statesCombobox.setOnAction(listener);
    }

    /**
     * Adds an event listener to the button to get a current observation.
     *
     * @param listener a event listener.
     */
    public void addGetListener(EventHandler<ActionEvent> listener) {
        this.getButton.setOnAction(listener);
    }

    /**
     * Adds an event listener to refresh all local data.
     *
     * @param listener a event listener.
     */
    public void addRefreshListener(EventHandler<ActionEvent> listener) {
        this.refreshButton.setOnAction(listener);
    }

    /**
     * Displays the given message in the text area of current
     * observation.
     *
     * @param message a given message.
     */
    private void updateCurrentObservation(String message) {
        this.currentObservationTextArea.clear();
        this.currentObservationTextArea.appendText(message);
    }

    /**
     * Displays the list of stations in the combobox for stations.
     *
     * @param stations a list of stations.
     */
    private void updateStations(List<Station> stations) {
        this.stationsCombobox.getItems().clear();
        this.stationsCombobox.getItems().addAll(stations);
    }

    /**
     * Displays the given message.
     *
     * @param message a given message.
     */
    public void onException(String message) {
        this.updateCurrentObservation(message);
    }

    /**
     * Updates this view according to the given model.
     *
     * @param model a Weather model
     */
    public void onModelUpdate(final WeatherModel model) {
        this.updateStations(model.getStations());
        this.updateCurrentObservation(model.getCurrentObservation().toString());
    }

    /**
     * Gets the value of the selected item in the combobox for
     * states.
     *
     * @return the value of the selected item.
     */
    public String getStateQuery() {
        return this.statesCombobox.getValue();
    }

    /**
     * Gets the value of the selected item in the combobox for
     * stations.
     *
     * @return the value of the selected item.
     */
    public Station getStationQuery() {
        return this.stationsCombobox.getValue();
    }

    /**
     * Gets all the values in the combobox for stations
     *
     * @return a list of stations.
     */
    public List<Station> getStationsQuery() {
        return this.stationsCombobox.getItems();
    }

    /**
     * Creates a table in the database if it does not exist.
     */
    private static void onCreate() {

        var queryBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS STATIONS(");
        queryBuilder.append("ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        queryBuilder.append("STATION_ID TEXT NOT NULL, ");
        queryBuilder.append("STATE TEXT, ");
        queryBuilder.append("STATION_NAME TEXT)");

        try (var connection = DriverManager.getConnection(WeatherModelDAOSqlImpl.CONNECTION_STRING);
             var statement = connection.createStatement()) {
            statement.executeUpdate(queryBuilder.toString());
        } catch (SQLException sQLEx) {
            System.err.println("Unable to get access to create data.");
        }
    }

    public static void main(String[] args) {
        onCreate();

        launch();


    }

}
