import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StoreApplication extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
        primaryStage.setScene(new Scene(root));
        Image icon = new Image("logo.jpg");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Centennial Online Store");
        primaryStage.setWidth(620);
        primaryStage.setHeight(430);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}

