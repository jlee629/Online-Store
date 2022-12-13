import java.sql.*;

public class DatabaseHandler {
    static final String DRIVER = "oracle.jdbc.OracleDriver";
    static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
    static final String USERNAME = "COMP228_F22_ya_30";
    static final String PASSWORD = "password";
    static Connection connection;


    // Login Feature
    public static boolean attemptToConnect(String email, String password) throws Exception {
        try{
            System.out.println(">> Starting Program!");

            Class.forName(DRIVER);
            System.out.println(">> Driver Loaded Successfully!");
            connection = DriverManager.getConnection(DATABASE_URL,USERNAME, PASSWORD);

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CUSTOMER WHERE EMAIL='"+email+"' AND PASSWORD='"+password+"'");
            ResultSetMetaData md = rs.getMetaData();

            if(rs.next()){
                System.out.println(">> Database Connected Successfully!");
                return true;
            } else {
                connection.close();
                System.out.println("Fail to login");
                return false;
            }

        } catch (Exception e){
            System.out.println("Exception Found!");
            e.printStackTrace();
            return false;
        }
    }

    // Update Customer Details Section
    // Based on the login information email will be automatically set so that user can only update their own information.
    // customer ID will not be changed in this section.
    public static void updateCustomerDB(String name, String address, String password, String phone, String email){
        try{
            String update = "update customer set customer_name=?, address=?, password=?, phone_number=? where email=?";
            PreparedStatement pst = connection.prepareStatement(update);
            pst.setString(1, name);
            pst.setString(2, address);
            pst.setString(3, password);
            pst.setString(4, phone);
            pst.setString(5, email);
            pst.executeUpdate();
            System.out.println("Customer Updated! \nName: '"+name+"' \nAddress: '"+address+"'\nPassword: '"+password+"'\nPhone Number: '"+phone+"'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Product Details Section
    public static void printFruitDB(){
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM FRUIT");
            ResultSetMetaData md = rs.getMetaData();
            System.out.println("-------------------Fruit DB-------------------");
            int row=0;
            String info="";
            while(rs.next())
            {
                for( int i=1;i <= md.getColumnCount();i++)
                {
                    info+=md.getColumnName(i)+": "+rs.getObject(i)+"\t";
                }
                row+=1;
                info+="\n";
            }
            System.out.println(info);}
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // Place an Order Section
    // order ID and customer ID will be automatically assigned and user can only enter fruitID and the paymentTyoe
    public static void insertOrderDB(int orderID, int customerID, int fruitID, String paymentType) throws SQLException {
        PreparedStatement pst = connection.prepareStatement("Insert into Orders VALUES(?,?,?,?)");
        pst.setInt(1, orderID);
        pst.setInt(2, customerID);
        pst.setInt(3, fruitID);
        pst.setString(4, paymentType);
        pst.executeUpdate();
        System.out.println("Order Inserted! \nOrder ID: '"+orderID+"' \nCustomer ID: '"+customerID+"'\nFruit ID: '"+customerID+"'\nPaymentType: '"+paymentType+"'");
    }

    // Orders Section
    public static void printOrderDB() throws SQLException {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ORDERS");
            ResultSetMetaData md = rs.getMetaData();
            System.out.println("-------------------Orders DB-------------------");
            int row = 0;
            String info = "";
            while (rs.next()) {
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    info += md.getColumnName(i) + ": " + rs.getObject(i) + "\t";
                }
                row += 1;
                info += "\n";
            }
            System.out.println(info);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Product Section
    public static void deleteFruitDB(int fruitID){
        try{
            Statement st = connection.createStatement();
            st.executeQuery("DELETE FROM Fruit where fruit_id = '"+fruitID+"'");
            System.out.println("Deleted Fruit ID ('"+fruitID+"')");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // The login information (email) will be passed to the home screen so that the user can only update their own information.
    // Also, in the order section, the id will automatically appear so that users can only order for themselves.
    public static String getUserID(String email){
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT customer_id FROM customer where email='"+email+"'");
            ResultSetMetaData md = rs.getMetaData();
            while(rs.next()){
                String id = rs.getString(1);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    // The order ID will be automatically assigned based on the recent order. The previous order ID + 1
    public static int setOrderID(){
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT customer_order_id FROM orders order by customer_order_id desc fetch first 1 row only");
            ResultSetMetaData md = rs.getMetaData();
            while(rs.next()){
                int orderID = rs.getInt(1);
                return orderID+1;
            }
        } catch(Exception e){
            e.printStackTrace();
        } return 0;
    }


    // Optional Feature of Logout
    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection Closed Successfully!");
    }
}
