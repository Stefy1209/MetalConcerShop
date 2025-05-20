package Controller;

import concert.Concert;
import concert.ConcertServiceInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminController {
    private ConcertServiceInterface concertService;

    @FXML
    private ListView<Concert> listView;

    public void setConcertService(ConcertServiceInterface concertService) {
        this.concertService = concertService;
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

    public void addConcertToList(Concert concert) {
        listView.getItems().add(concert);
    }
}