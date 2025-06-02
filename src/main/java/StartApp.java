import Controller.LogInController;
import concert.ConcertService;
import concert.ConcertServiceInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.concert.ConcertHibernateRepository;
import repository.concert.ConcertRepository;
import repository.user.UserHibernateRepository;
import repository.user.UserRepository;
import user.UserService;
import user.UserServiceInterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class StartApp extends Application {
    private static UserServiceInterface userService;
    private static ConcertServiceInterface concertService;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("logIn-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LogInController logInController = fxmlLoader.getController();
        logInController.setUserService(userService);
        logInController.setConcertService(concertService);
        logInController.populateComboBox();
        stage.setScene(scene);
        stage.setTitle("Metal Concert Shop");
        stage.show();
    }

    public static void main(String[] args) {
        Properties dbProperties = new Properties();
        try {
            dbProperties.load(new FileInputStream("db.properties"));
        } catch (IOException e) {
            return;
        }

        UserRepository userRepository = new UserHibernateRepository();
        userService = new UserService(userRepository);

        ConcertRepository concertRepository = new ConcertHibernateRepository();
        concertService = new ConcertService(concertRepository);

        launch();
    }
}