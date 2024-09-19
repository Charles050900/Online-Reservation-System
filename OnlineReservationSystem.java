import java.sql.Connection;

public class OnlineReservationSystem {
    public static void main(String[] args) throws Exception{

        LogInForm logInForm = new LogInForm();
        
        DBConnection dbConnection = new DBConnection();
        dbConnection.connect();
        Connection con = dbConnection.getConnection();
        
        logInForm.entryOption(con);
        
    }
}