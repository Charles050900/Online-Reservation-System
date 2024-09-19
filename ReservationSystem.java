import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReservationSystem extends CancellationForm {

    LogInForm logInForm = new LogInForm();
    CancellationForm cancellationForm = new CancellationForm();
    TicketDetails ticketDetails = new TicketDetails();
    Scanner sc = new Scanner(System.in);
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    @SuppressWarnings("unused")
    public void tickerBooking(Connection con) throws Exception {
        PreparedStatement pstmt; 
        String query;
        String newpnr ="";
        String oldpnr ="PNR100";
        ResultSet rs = null;

        int option;
        String name = "",gender = "";
        int age = 0,ticketcount;
        long phoneNumber = 0;
        String classType,from,to,dateInput;
        Date date;

        System.out.println();
        System.out.println("*** Ticket Booking ***");
        System.out.println();
        System.out.printf("%-35s","From : ");
        from = sc.nextLine();
        System.out.printf("%-35s","To : ");
        to = sc.nextLine();
        System.out.printf("%-35s","Enter class type : ");
        classType = sc.nextLine();
        System.out.printf("%-35s","Date(dd-mm-yyyy) : ");
        dateInput = sc.nextLine();
        System.out.printf("%-35s","Enter No.of Passengers : ");
        ticketcount = sc.nextInt();
        sc.nextLine();
        System.out.println(">>> Enter Passenger's Details <<<");
        Statement st = con.createStatement() ;
        rs = st.executeQuery("select pnrnumber from bookingdetails order by pnrnumber desc limit 1 ");
        while ((rs.next())) {
            oldpnr= rs.getString("pnrnumber");
        }
        rs.close(); 
        String numericPartStr = oldpnr.substring(3);
        int numericPart = Integer.parseInt(numericPartStr) + 1;    
        newpnr = "PNR" + String.format("%03d", numericPart);
        for(int i=1;i<=ticketcount;i++){
            System.out.println();
            System.out.printf("%-35s","Enter your name : ");
            name = sc.nextLine();
            System.out.printf("%-35s","Enter your age : ");
            age = sc.nextInt();
            sc.nextLine();
            System.out.printf("%-35s","Enter your gender : ");
            gender = sc.nextLine();
            System.out.printf("%-35s","Enter your 10 digit Phone Number : ");
            phoneNumber = sc.nextLong();
            sc.nextLine();
            st.execute("use Online_Reservation");
            query = "insert into passengerdetails (pnrnumber,passengername,passengerage,phno,gender) values (?,?,?,?,?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, newpnr);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.setLong(4, phoneNumber);
            pstmt.setString(5, gender);
            pstmt.executeUpdate();
         }
        try {
            sdf.parse(dateInput);
        } catch (Exception e) {
            System.out.println("Enter date in Correct Format...!");
        }
        switch (from) {
            case "Trichy":
                from = "Tiruchchirappalli Junction";
                break;
            case "Chennai":
                from = "Chennai Egmore";
                break;
            case "Madurai":
                from = "Madurai Junction";
                break;  
        }
        System.out.println(">>> CHOOSE THE TRAIN <<<");
        System.out.println();   
        rs = st.executeQuery("select * from traindetails where departure='"+from+"'");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("                                                        Train Details                                                         ");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-7s %-40s %-10s %-30s %-20s %-10s%n", "No", "Train Name", "Time", "From", "To", "Train Status");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
 
        while (rs.next()) {
            int trainno =rs.getInt("trainno");
            String trainname =rs.getString("trainname");
            String departuretime =rs.getString("departuretime");
            String departure =rs.getString("departure");
            String arrival =rs.getString("arrival");
            String trainstatus =rs.getString("trainstatus");
            System.out.printf("%-7d %-40s %-10s %-30s %-20s %-10s%n",trainno, trainname, departuretime, departure, arrival, trainstatus);
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
        rs.close();
        System.out.print("Enter the Train Number : ");
        int selectedTrainNo = sc.nextInt();
        
        String selectedtrainname = null;
        String selecteddeparturetime = null;
        String selecteddeparture = null;
        String selectedArrival = null;
        rs = st.executeQuery("select * from traindetails where trainno="+selectedTrainNo);
        while(rs.next()){
        selectedtrainname = rs.getString("trainname");
        selecteddeparturetime = rs.getString("departuretime");
        selecteddeparture = rs.getString("departure");
        selectedArrival = rs.getString("arrival");
        }
        rs.close();
        String insertQuery = "INSERT INTO bookingdetails (pnrnumber, countoftickets, trainname, departuretime, departure, classtype) VALUES (?, ?, ?, ?, ?,?)";
        pstmt = con.prepareStatement(insertQuery);
        pstmt.setString(1, newpnr);
        pstmt.setInt(2, ticketcount);
        pstmt.setString(3, selectedtrainname);
        pstmt.setString(4, selecteddeparturetime);
        pstmt.setString(5, selecteddeparture);
        pstmt.setString(6, classType);
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println();
            System.out.println(">>> Booking successful...! <<<");
        } else {
            System.out.println("Booking failed.");
        }
        System.out.println();
        System.out.println("|------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|                                                                        Ticket Details                                                                            |");
        System.out.println("|------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("|%-7s %-10s %-40s %-10s %-30s %-30s %-15s %-15s%n", "PNR No","Train No", "Train Name", "Time", "From", "To", "No.of Tickets", "Class Type   |");
        System.out.println("|------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        
        System.out.printf("|%-7s %-10d %-40s %-10s %-30s %-30s %-15d %-15s%n",newpnr, selectedTrainNo, selectedtrainname, selecteddeparturetime, selecteddeparture, selectedArrival, ticketcount,classType);
        System.out.println("|------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println();
        System.out.println();
        query = "select * from passengerdetails where pnrnumber = (?) ";
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, newpnr);
        rs = pstmt.executeQuery();
        System.out.println();
        System.out.println("|---------------------------------------------------------------|");
        System.out.println("|                       Passenger Details                       |");
        System.out.println("|---------------------------------------------------------------|");
        System.out.printf("|%-25s %-7s %-15s %-10s %n", "Name","Age", "Phone No", "Gender       |");
        System.out.println("|---------------------------------------------------------------|");
        while(rs.next()){
        name = rs.getString("passengername");
        age = rs.getInt("passengerage");
        phoneNumber = rs.getLong("phno");
        gender = rs.getString("gender");
        System.out.printf("|%-25s %-7s %-15s %-10s %n",name, age, phoneNumber, gender);
        }
        System.out.println("|---------------------------------------------------------------|");
        System.out.println();
        System.out.println();
        System.out.println("1. LogOut & Exit || 2.Ticket Booking || 3.Ticket Cancellation || 4. Show Tickets ");
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
            tickerBooking(con);
        }
        else if(option==3){
            cancellationForm.ticketCancellation(con);
        }
        else{
            ticketDetails.showTickets(con);
        }
    }
}
