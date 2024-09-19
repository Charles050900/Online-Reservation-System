import java.util.*;
import java.sql.*;

public class LogInForm{

    public void entryOption(Connection con) throws Exception{

        ReservationSystem reservationSystem = new ReservationSystem();
    
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("*** Welcome to Online Reservation System! *** ");
        System.out.println();
        System.out.println("1. Registration || 2. Login || 3. Exit");
        System.out.println();
        int option =sc.nextInt();
        sc.nextLine();

            if(option==1){
                Registration(con);
                LogIn(con,reservationSystem); 
            }
            else if(option==2){
                LogIn(con,reservationSystem);

            }
            else if(option==3){
                System.out.println();
                System.out.println(">>> Thank You...Come Again!... <<<");
                System.out.println();
            }
            else{
                System.out.println("Choose Proper Option 1 or 2 ");
            }
            sc.close();
        }

    public static void Registration(Connection con) throws Exception{
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("*** Registration Page ***");
        System.out.print("Enter Your user name : ");
        String RegisterUserName =  sc.nextLine();
        System.out.print("Enter Your password : ");
        String RegisterPassword =  sc.nextLine();

        Statement st = con.createStatement();
        st.execute("use Online_Reservation");
        String RegisterQuery = " insert into Users values ('"+RegisterUserName+"','"+RegisterPassword+"')";
        
        st.execute("use Online_Reservation");
        st.executeUpdate(RegisterQuery);
        System.out.println(">>> SignUp Successfull! <<<");
    }

    public void LogIn(Connection con,ReservationSystem reservationSystem) throws Exception {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("*** LogIn Page  ***");
        System.out.print("Enter Your user name : ");
        String LogInusername =  sc.nextLine();
        System.out.print("Enter Your password : ");
        String LogInPassword =  sc.nextLine();
        try {
            Statement st = con.createStatement();
            st.execute("use Online_Reservation");
            ResultSet rs = st.executeQuery("select * from Users where userName ='"+LogInusername+"'");
            rs.next();
            String LUsername = rs.getString("userName");
            String LPassword = rs.getString("userPassword");
            if(LogInusername.equals(LUsername) && LogInPassword.equals(LPassword)){
                System.out.println();
                System.out.println(">>> LogIn Successfull! <<<");
                System.out.println();
                System.out.println("1. Ticket Booking || 2. Ticket Cancellation || 3. Show Tickets ");
                System.out.println();
                int option = sc.nextInt();

                if(option==1){
                    reservationSystem.tickerBooking(con);
                }
                else if(option==2){
                    reservationSystem.ticketCancellation(con);
                }
                else{
                    reservationSystem.showTickets(con);
                }   
            }
            else{
                System.out.println();
                System.out.println(">>> User Name or Password Incorrect. <<<");
                System.out.println();
            } 
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
