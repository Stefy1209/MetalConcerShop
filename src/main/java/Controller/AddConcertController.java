package Controller;

import concert.Concert;
import concert.ConcertServiceInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class AddConcertController {
    private ConcertServiceInterface concertService;
    private AdminController adminController;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField locationTextField;
    @FXML
    private TextField artistTextField;
    @FXML
    private DatePicker datePicker;

    public void setConcertService(ConcertServiceInterface concertService) {
        this.concertService = concertService;
    }

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    @FXML
    private void addConcert(ActionEvent event) {
        String name = nameTextField.getText();
        String location = locationTextField.getText();
        String artist = artistTextField.getText();
        LocalDate date = datePicker.getValue();
        LocalDateTime dateTime = date.atTime(12, 0);

        Optional<Concert> concert = concertService.addConcert(name, location, artist, dateTime);
        concert.ifPresent(value -> adminController.addConcertToList(value));
    }
}
