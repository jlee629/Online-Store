import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginScreenController {

    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;
    @FXML
    Text invalidTextLabel;
    @FXML
    Button loginBtn;

    public void authenticate(ActionEvent e) throws Exception {
        try{
            String email = emailField.getText();
            String password = passwordField.getText();
            System.out.println("Attempt to Login");
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            if(DatabaseHandler.attemptToConnect(email, password)){
                goToHomeScreen(e);
                invalidTextLabel.setOpacity(0);
            } else {
                invalidTextLabel.setOpacity(1);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void goToHomeScreen(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
        root = loader.load();
        HomeScreenController homeScreenController = loader.getController();
        homeScreenController.setUserInfo(emailField.getText());
        homeScreenController.setOrderID();
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setWidth(600);
        stage.setHeight(450);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
