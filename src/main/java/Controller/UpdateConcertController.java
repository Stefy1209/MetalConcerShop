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

public class UpdateConcertController {
    private ConcertServiceInterface concertService;
    private AdminController adminController;
    private Concert concert;

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

    public void setConcert(Concert concert) {
        this.concert = concert;

        nameTextField.setText(concert.getName());
        locationTextField.setText(concert.getLocation());
        artistTextField.setText(concert.getArtist());
        datePicker.setValue(concert.getDate().toLocalDate());
    }

    @FXML
    private void updateConcert(ActionEvent event) {
        String name = nameTextField.getText();
        String location = locationTextField.getText();
        String artist = artistTextField.getText();
        LocalDate date = datePicker.getValue();
        LocalDateTime dateTime = date.atTime(12, 0);

        Optional<Concert> concert =  concertService.updateConcert(this.concert, name, location, artist, dateTime);
        concert.ifPresent(value -> adminController.updateConcertToList(value));
    }
}
