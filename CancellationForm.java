import java.sql.*;
import java.util.*;


public class CancellationForm extends TicketDetails{

    public void ticketCancellation(Connection con) throws Exception{

        ReservationSystem reservationSystem = new ReservationSystem();
        TicketDetails ticketDetails = new TicketDetails();

        Scanner sc = new Scanner(System.in);

        int cancelledTickets=0;
        String PNRNumber ="";
        String query = "";
        PreparedStatement pstmt;
        ResultSet rs;
        System.out.println();
        System.out.println("*** Ticket Cancellation ***");
        System.out.println();
        System.out.print("Enter PNR No (like PNR000 ) : ");
        PNRNumber = sc.nextLine();
        query = "select * from bookingdetails where pnrnumber =(?)";
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, PNRNumber);
        rs = pstmt.executeQuery();
        String pnrNumber ="-";
        int countoftickets =0;
        String trainname = "-";
        String departuretime = "-";
        String departure = "-";
        while (rs.next()) {
            pnrNumber = rs.getString("pnrnumber");
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
        System.out.printf("|%-7s %-20d %-40s %-10s %-30s%n",pnrNumber, countoftickets, trainname, departuretime, departure);
        System.out.println("|-------------------------------------------------------------------------------------------------------------------|");
        System.out.println();
        rs.close();
        System.out.println("*** Ticket Cancellation Confirmation ***");
        System.out.println();
        System.out.print(" How many tickets do you want to cancel :  ");
        cancelledTickets = sc.nextInt();
        sc.nextLine();
        if(cancelledTickets==countoftickets){
            query = "delete from bookingdetails where pnrnumber =(?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, pnrNumber);
            pstmt.execute();
            query = "delete from passengerdetails where pnrnumber =(?) limit 1";
            pstmt=con.prepareStatement(query);
            pstmt.setString(1, PNRNumber);
            pstmt.executeUpdate();
            System.out.println();
            System.out.println(">>> Tickets Cancelled <<<");
            System.out.println();
        }
        else{
            query = "update bookingdetails set countoftickets = (?) where pnrnumber =(?)";
            pstmt=con.prepareStatement(query);
            pstmt.setInt(1, countoftickets-cancelledTickets);
            pstmt.setString(2, pnrNumber);
            pstmt.executeUpdate();
            for(int i=1;i<=cancelledTickets;i++){
                query = "delete from passengerdetails where pnrnumber =(?) limit 1";
                pstmt=con.prepareStatement(query);
                pstmt.setString(1, PNRNumber);
                pstmt.executeUpdate();
            }
            query = "select * from bookingdetails where pnrnumber =(?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, PNRNumber);
            rs = pstmt.executeQuery();
            pnrNumber ="-";
            countoftickets =0;
            trainname = "-";
            departuretime = "-";
            departure = "-";
            while (rs.next()) {
                pnrNumber = rs.getString("pnrnumber");
                countoftickets = rs.getInt("countoftickets");
                trainname = rs.getString("trainname");
                departuretime = rs.getString("departuretime");
                departure = rs.getString("departure");
            }
            System.out.println(">>> Tickets Cancelled <<<");
            System.out.println();
            System.out.println("|-------------------------------------------------------------------------------------------------------------------|");
            System.out.println("|                                                  Ticket Details                                                   |");
            System.out.println("|-------------------------------------------------------------------------------------------------------------------|");
            System.out.printf("|%-7s %-20s %-40s %-10s %-30s%n", "PNR No","No.of Tickets", "Train Name", "Time", "From" );
            System.out.println("|-------------------------------------------------------------------------------------------------------------------|");  
            System.out.printf("|%-7s %-20d %-40s %-10s %-30s%n",pnrNumber, countoftickets, trainname, departuretime, departure);
            System.out.println("|-------------------------------------------------------------------------------------------------------------------|");
            System.out.println();
            }
        }
        System.out.println();
        System.out.println("1. Ticket Bookin || 2. Show Tickets || 3. LogOut & Exit ");
        System.out.println();
        int option = sc.nextInt();
        if(option==1){
            reservationSystem.tickerBooking(con);
        }
        else if(option==2){
            ticketDetails.showTickets(con);
        }
        else{
            System.out.println();
            System.out.println(">>> Logout successfully <<<");
            System.out.println();
            System.out.println(">>> Thank You...Come Again!... <<<");
            System.out.println();
        }
        sc.close();
    }   
}
