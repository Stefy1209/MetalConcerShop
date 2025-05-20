package Controller;

import concert.ConcertServiceInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import user.UserRole;
import user.UserServiceInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogInController {
    private UserServiceInterface userService;
    private ConcertServiceInterface concertService;

    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<UserRole> accountComboBox;
    @FXML
    private TextField usernameTextField;

    public void populateComboBox() {
        accountComboBox.setCellFactory(role -> new ListCell<>(){
            @Override
            protected void updateItem(UserRole userRole, boolean b) {
                super.updateItem(userRole, b);
                setText((userRole == null || b) ? null : userRole.toString());
            }
        });

        accountComboBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(UserRole userRole, boolean b) {
                super.updateItem(userRole, b);
                setText((userRole == null || b) ? null : userRole.toString());
            }
        });

        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(UserRole.Client);
        userRoles.add(UserRole.Admin);
        ObservableList<UserRole> observableList = FXCollections.observableList(userRoles);
        accountComboBox.setItems(observableList);
    }

    public void setUserService(UserServiceInterface userService) {
        this.userService = userService;
    }

    public void setConcertService(ConcertServiceInterface concertService) {
        this.concertService = concertService;
    }

    @FXML
    private void logIn(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        UserRole userRole = accountComboBox.getSelectionModel().getSelectedItem();

        if(username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a valid username and password");
            alert.show();
            return;
        }

        if(userRole == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a Type Account");
            alert.show();
            return;
        }

        boolean logged = userService.logIn(username, password, userRole);
        if(!logged) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Username or Password");
            alert.show();
            return;
        }

        loadScene(userRole);
    }

    @FXML
    private void signUp(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        UserRole userRole = accountComboBox.getSelectionModel().getSelectedItem();

        if(username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a valid username and password");
            alert.show();
            return;
        }

        if(userRole == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a Type Account");
            alert.show();
            return;
        }

        boolean signed = userService.signUp(username, password, userRole);
        if(!signed) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username already used");
            alert.show();
            return;
        }

        loadScene(userRole);
    }

    private void loadScene(UserRole userRole) {
        if(userRole == UserRole.Admin) {
            loadAdminScene();
        }
    }

    private void loadAdminScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("admin-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            AdminController controller = fxmlLoader.getController();
            controller.setConcertService(concertService);
            controller.populateConcerts();
            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Metal Concert Shop - Admin");
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}