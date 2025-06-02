package Controller;

import concert.Concert;
import concert.ConcertServiceInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class AdminController {
    private ConcertServiceInterface concertService;

    @FXML
    private ListView<Concert> listView;

    public void setConcertService(ConcertServiceInterface concertService) {
        this.concertService = concertService;
    }

    public void addConcertToList(Concert concert) {
        listView.getItems().add(concert);
    }

    public void updateConcertToList(Concert concert) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        listView.getItems().set(selectedIndex, concert);
    }

    public void populateConcerts() {
        listView.setCellFactory(concert -> new ListCell<>(){
            @Override
            protected void updateItem(Concert concert, boolean b) {
                super.updateItem(concert, b);
                setText((concert == null || b) ? null : String.format("%s - %s - %s -%s ", concert.getName(), concert.getLocation(), concert.getArtist(), concert.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            }
        });

        List<Concert> concerts = concertService.getConcerts();
        ObservableList<Concert> concertList = FXCollections.observableList(concerts);
        listView.setItems(concertList);
    }

    @FXML
    private void addConcert(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("addConcert-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            AddConcertController controller = fxmlLoader.getController();
            controller.setConcertService(concertService);
            controller.setAdminController(this);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Concert");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void removeConcert(ActionEvent event) {
        try {
            Concert concert = listView.getSelectionModel().getSelectedItem();

            if(concert == null) {
                showError("Please select a concert!", "No Concert Selected");
                return;
            }

            Optional<Concert> result = concertService.removeConcert(concert.getId());

            if(result.isEmpty()) {
                showError("Concert could not be removed", "Concert Not Removed");
                return;
            }

            listView.getItems().remove(concert);
        } catch (Exception e) {
            showError(e.getMessage(), "Concert Not Removed");
        }
    }

    @FXML
    private void updateConcert(ActionEvent event) {
        try {
            Concert concert = listView.getSelectionModel().getSelectedItem();

            if(concert == null) {
                showError("Please select a concert!", "No Concert Selected");
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("updateConcert-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            UpdateConcertController controller = fxmlLoader.getController();
            controller.setConcertService(concertService);
            controller.setAdminController(this);
            controller.setConcert(concert);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update Concert");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            showError(e.getMessage(), "Concert Not Updated");
        }
    }

    public void showError(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}