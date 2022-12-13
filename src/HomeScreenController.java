import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeScreenController {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    @FXML
    TextField nameUpdate, addressUpdate, emailUpdate, phoneUpdate;
    @FXML
    PasswordField passwordUpdate;

    public void updateCustomer(ActionEvent e) throws Exception {
        String name = nameUpdate.getText();
        String address = addressUpdate.getText();
        String email = emailUpdate.getText();
        String password = passwordUpdate.getText();
        String phone = phoneUpdate.getText();
        DatabaseHandler.updateCustomerDB(name, address, password, phone, email);
    }

    public void showFruitDetails(ActionEvent e) throws Exception {
        DatabaseHandler.printFruitDB();
    }

    @FXML
    TextField orderOrderID, orderCustomerID, orderFruitID;
    @FXML
    RadioButton debitCard, creditCard, cash;
    public void insertOrder(ActionEvent e) throws Exception {
        try{
            int orderID = Integer.parseInt(orderOrderID.getText());
            int customerID = Integer.parseInt(orderCustomerID.getText());
            int fruitID = Integer.parseInt(orderFruitID.getText());
            String paymentType;
            if(debitCard.isSelected()) {
                paymentType = "Debit Card";
            } else if(creditCard.isSelected()) {
                paymentType = "Credit Card";
            } else {
                paymentType = "Cash";
            }

            DatabaseHandler.insertOrderDB(orderID, customerID, fruitID, paymentType);

        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void showOrderDetails(ActionEvent e) throws Exception {
        try {
            DatabaseHandler.printOrderDB();
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    @FXML
    TextField deleteFruitID;
    public void deleteFruit(ActionEvent e) throws  Exception {
        try {
            int fruitID = Integer.parseInt(deleteFruitID.getText());
            DatabaseHandler.deleteFruitDB(fruitID);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void logout(ActionEvent e) throws IOException {
        DatabaseHandler.closeConnection();
        root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    // The login information (email) will be passed to the home screen so that the user can only update their own information.
    // Also, in the order section, the id will automatically appear so that users can only order for themselves.
    public void setUserInfo(String email){
        emailUpdate.setText(email);
        orderCustomerID.setText(DatabaseHandler.getUserID(email));
    }

    // The order ID will be automatically assigned based on the recent order. The previous order ID + 1
    public void setOrderID(){
        orderOrderID.setText(String.valueOf(DatabaseHandler.setOrderID()));
    }


}
