import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class TicketDetails {

    public void showTickets(Connection con) throws Exception{

        ReservationSystem reservationSystem = new ReservationSystem();
        CancellationForm cancellationForm = new CancellationForm();
        Scanner sc = new Scanner(System.in);
        
        int countoftickets =0,option;
        String PNRNumber ="",trainname="",departuretime="",departure="",pnrnumber="";
        String query = "";
        PreparedStatement pstmt;
        ResultSet rs;
        System.out.println();
        System.out.println("*** Show Tickets ***");
        System.out.println();
        System.out.print("Enter PNR No (like PNR000 ) : ");
        PNRNumber = sc.nextLine();
        query = "select * from bookingdetails where pnrnumber =(?)";
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, PNRNumber);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            pnrnumber = rs.getString("pnrnumber");
            countoftickets = rs.getInt("countoftickets");
            trainname = rs.getString("trainname");
            departuretime = rs.getString("departuretime");
            departure = rs.getString("departure");
        }
        if(countoftickets==0){
            System.out.println();
            System.out.println(">>> No ticket details found for this "+PNRNumber+" <<<");
            System.out.println();
        }
        else{
        System.out.println();
        System.out.println("|-------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|                                                  Ticket Details                                                   |");
        System.out.println("|-------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("|%-7s %-20s %-40s %-10s %-30s%n", "PNR No","No.of Tickets", "Train Name", "Time", "From" );
        System.out.println("|-------------------------------------------------------------------------------------------------------------------|"); 
        System.out.printf("|%-7s %-20d %-40s %-10s %-30s%n",pnrnumber, countoftickets, trainname, departuretime, departure);
        System.out.println("|-------------------------------------------------------------------------------------------------------------------|");
        System.out.println();  
        query = "select * from passengerdetails where pnrnumber = (?) ";
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, PNRNumber);
        rs = pstmt.executeQuery();
        System.out.println();
        System.out.println("|---------------------------------------------------------------|");
        System.out.println("|                       Passenger Details                       |");
        System.out.println("|---------------------------------------------------------------|");
        System.out.printf("|%-25s %-7s %-15s %-10s %n", "Name","Age", "Phone No", "Gender       |");
        System.out.println("|---------------------------------------------------------------|");
        while(rs.next()){
        String name = rs.getString("passengername");
        int age = rs.getInt("passengerage");
        Long phoneNumber = rs.getLong("phno");
        String gender = rs.getString("gender");
        System.out.printf("|%-25s %-7s %-15s %-10s %n",name, age, phoneNumber, gender);
        }
        System.out.println("|---------------------------------------------------------------|");
        System.out.println();
        System.out.println();
    }
        System.out.println("1. LogOut & Exit || 2.Ticket Booking || 3.Ticket Cancellation ");
        option = sc.nextInt();
        sc.nextLine();
        if (option == 1) {
            System.out.println();
            System.out.println(">>> Logout successfully <<<");
            System.out.println();
            System.out.println(">>> Thank You...Come Again!... <<<");
            System.out.println();
        }
        else if(option==2){
            reservationSystem.tickerBooking(con);
        }
        else {
            cancellationForm.ticketCancellation(con);
        }
        sc.close();
    }   
}
