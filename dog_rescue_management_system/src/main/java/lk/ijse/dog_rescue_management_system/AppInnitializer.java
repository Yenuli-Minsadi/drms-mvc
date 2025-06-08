package lk.ijse.dog_rescue_management_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInnitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/view/style.css").toExternalForm());

        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("/images/logo2.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
    }


}
