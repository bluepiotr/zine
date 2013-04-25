package cinema;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Scanner;

/**
 * This class is responsible for all the actions related to movie booking of user
 * For eg. display bookings of the user, generate booking ID, cancel bookings, etc
 * @author Raul Gomez,  Student ID 12837452, MSc Informatics
 */
public class Booking implements Serializable {  
	private static final long serialVersionUID = -7266881288297174414L;
	Showing show;
	int number; 
	int bookingId;
	private Person p;
	private static Scanner sc = new Scanner(System.in);
	private final static double TICKETPRICE = 8.00;

	/**
	 * This method generates Unique booking Id once the movie booking is complete
	 * @param BookingSystem sys
	 * @param Person p
	 * @return int bookingIdCount
	 */
	public int generateBookingId(BookingSystem sys, Person p){
		int bookingIdCount=0;
		boolean isAnyBookingAvailable=false;
		for(int i=0;i<sys.getUsers().size();i++){
			if(sys.getUsers().get(i).getListBooking()!=null && sys.getUsers().get(i).getListBooking().size()!=0){
				isAnyBookingAvailable = true;
				bookingIdCount = bookingIdCount+sys.getUsers().get(i).getListBooking().size()+1;
			}
		}
		if(!isAnyBookingAvailable){
			bookingIdCount = 1;
		}
		return bookingIdCount;    
	}

	/**
	 * This method displays only one booking of any user
	 * @param BookingSystem sys
	 * @param int i
	 * @param int bookId
	 * @return void
	 */
	public static void displayOneBooking(BookingSystem sys, int i, int bookId){
		double bookingAmount;
		boolean isBookingFound = false;
		System.out.println("Booking id " + bookId + " details ");
		for(int j=0;j<sys.getUsers().get(i).getListBooking().size();j++){

			if(bookId==sys.getUsers().get(i).getListBooking().get(j).getBookingId()){
				isBookingFound = true;
				System.out.println("----");
				System.out.println("Movie Title: "+sys.getUsers().get(i).getListBooking().get(j).getShow().getMovie().getTitle());
				System.out.println("Day: "+sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().getScheduledDateTime().getShowingDate());
				System.out.println("Time: "+sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().getScheduledDateTime().getSlot());
				System.out.println("Screen Number: "+sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().getScreenNumber());
				System.out.println("No of Tickets: "+sys.getUsers().get(i).getListBooking().get(j).getNumber());				
				bookingAmount = sys.getUsers().get(i).getListBooking().get(j).getNumber()*TICKETPRICE;
				System.out.printf("Total Amount (@8GBP/ticket) GBP%.2f", bookingAmount);
				System.out.println();				
				System.out.println("-----------------------------");}
		}
		if(sys.getUsers().get(i).getListBooking().size()==0||!isBookingFound){
			System.out.println("Booking not found ");
		}	
	}	

	/**
	 * This method takes the following input and invoke method to display Booking information
	 * of any user
	 * @param BookingSystem sys
	 * @param String login
	 * @param String pw
	 * @return int bookingId
	 */
	public static int displayBookingData(BookingSystem sys, String login, String pw){
		boolean isUserPresent = false;
		int bookingId=0;
		do{
			for(int i=0;i<sys.getUsers().size();i++){
				if(login.equals(sys.getUsers().get(i).getLogin()) && pw.equals(sys.getUsers().get(i).getPassword())){
					isUserPresent = true;
					System.out.println(login+ " Booking data ");
					System.out.println("-----------------------");
					displayBookingsInformation(sys, i);
					if(sys.getUsers().get(i).getListBooking().size()!=0){
						System.out.println("Select a booking id");
						bookingId = Utilities.hasInt();
						bookingId = validateBookingSelection(sys, i, bookingId, login, pw);
					}	
				}
			}

			if(!isUserPresent){
				System.out.println("User not present");	
			}
		}while(!isUserPresent);
		return bookingId;		
	}

	/**
	 * This method displays all the bookings of a particular user
	 * @param BookingSystem sys
	 * @param int i
	 * @return void
	 */
	public static void displayBookingsInformation(BookingSystem sys, int i){
		double bookingAmount;
		System.out.println("Movie booking details ");
		for(int j=0;j<sys.getUsers().get(i).getListBooking().size();j++){
			System.out.println("Booking Id: "+sys.getUsers().get(i).getListBooking().get(j).getBookingId());
			System.out.println("----");
			System.out.println("Movie Title: "+sys.getUsers().get(i).getListBooking().get(j).getShow().getMovie().getTitle());
			System.out.println("Day: "+sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().getScheduledDateTime().getShowingDate());
			System.out.println("Time: "+sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().getScheduledDateTime().getSlot());
			System.out.println("Screen Number: "+sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().getScreenNumber());
			System.out.println("No of Tickets: "+sys.getUsers().get(i).getListBooking().get(j).getNumber());				
			bookingAmount = sys.getUsers().get(i).getListBooking().get(j).getNumber()*TICKETPRICE;
			System.out.printf("Total Amount (@8GBP/ticket) GBP%.2f", bookingAmount);
			System.out.println();				
			System.out.println("-----------------------------");
		}
		if(sys.getUsers().get(i).getListBooking().size()==0){
			System.out.println("No bookings exist ");
		}	
	}	

	/**
	 * This method validates whether bookingId exists or not
	 * @param BookingSystem sys
	 * @param int i
	 * @param int bookingId
	 * @param String login
	 * @param String pw
	 * @return int bkgId
	 */
	public static int validateBookingSelection(BookingSystem sys, int i, int bookingId, String login, String pw){
		int bkgId=0;
		boolean isBookingIdFound = false;
		for(int j=0;j<sys.getUsers().get(i).getListBooking().size();j++){
			if(bookingId==sys.getUsers().get(i).getListBooking().get(j).getBookingId()){
				isBookingIdFound = true;
				bkgId = bookingId;				
			}		

		}
		if(!isBookingIdFound){
			System.out.println("Booking Id not found");
			bkgId = Booking.displayBookingData(sys, login, pw);

		}
		return bkgId;
	}

	/**
	 * This method takes the following input and invokes method for cancellation of a booking 
	 * and calculating cancellation charges
	 * @param BookingSystem sys
	 * @param String login
	 * @param String pw
	 * @param int bookId
	 * @return void
	 */
	public static void cancelBooking(BookingSystem sys, String login, String pw, int bookId){
		boolean isUserPresent = false;
		boolean isCancelBookingIdFound=false;
		String q;
		String a;
		do{
			for(int i=0;i<sys.getUsers().size();i++){
				if(login.equals(sys.getUsers().get(i).getLogin()) && pw.equals(sys.getUsers().get(i).getPassword())){
					isUserPresent = true;
					System.out.println(login+ " Booking data ");
					System.out.println("-----------------------");
					displayOneBooking(sys, i, bookId);
					for(int j=0;j<sys.getUsers().get(i).getListBooking().size();j++){						
						if(bookId==sys.getUsers().get(i).getListBooking().get(j).getBookingId()){
							isCancelBookingIdFound = true;
							q = "Please confirm you wish to cancel this booking. (Enter Y/N)";
							a = Utilities.yesOrNoReply(q);
							if(a.equalsIgnoreCase("Y")){
								cancelPlusChargeCalculation(sys, i, bookId);}
							else{
								System.out.println("Booking not cancelled ");
								return;}
						}
					}
					if(!isCancelBookingIdFound){
						System.out.println("Incorrect booking id selected ");
						System.out.println("-----------------------");
						Booking.cancelBooking(sys, login, pw, bookId);}
				}
			}
			if(!isUserPresent){
				System.out.println("User not present");	
			}
		}while(!isUserPresent);
	}

	/**
	 * This method invokes method for cancellation charge calculation and then cancels booking(s) 
	 * @param BookingSystem sys
	 * @param int i
	 * @param int cancelBookingId
	 * @return void
	 */
	public static void cancelPlusChargeCalculation(BookingSystem sys, int i, int cancelBookingId){
		String date = null;
		String slot = null;
		for(int j=0;j<sys.getUsers().get(i).getListBooking().size();j++){
			if(cancelBookingId == sys.getUsers().get(i).getListBooking().get(j).getBookingId()){

				if(!Utilities.isBookingDatePast(sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().
						getScheduledDateTime().getShowingDate())){

					date = sys.getUsers().get(i).getListShowings().get(j).getScreen().getScheduledDateTime().getShowingDate();
					slot = sys.getUsers().get(i).getListShowings().get(j).getScreen().getScheduledDateTime().getSlot();
					String deductionMessage = calculateChargeDeduction(sys, i, j, date, slot);
					System.out.println(deductionMessage);
					Screen.cancellationUpdateScreenCapacity(sys, sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().getScreenNumber(), 
							sys.getUsers().get(i).getListBooking().get(j).getNumber(), sys.getUsers().get(i).getListBooking().get(j).getShow());
					System.out.println("Booking Id "+ cancelBookingId+ " cancelled and screen capacity updated to"+
							sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().getCapacity());
					sys.getUsers().get(i).getListBooking().remove(j);
					Utilities.save(sys);
				}

				else{
					System.out.println("Booking cannot be cancelled as the date is past ");
				}
			}
		}
	}

	/**
	 * This methods does the charge deduction depending on the current time and show time
	 * @param BookingSystem sys
	 * @param int i
	 * @param int j
	 * @param String date
	 * @param String slot
	 * @return String cancellationMessage
	 */
	public static String calculateChargeDeduction(BookingSystem sys, int i, int j, String date,String slot){
		int TICKETPRICE = 8;
		String cancellationMessage;
		int cancellationCharge;
		int refundedAmount;
		int bookingAmount;
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		String[] dateSplitter = date.split("/");
		calendar1.set(Integer.parseInt(dateSplitter[2]), Integer.parseInt(dateSplitter[1]), Integer.parseInt(dateSplitter[0]));
		calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH)+1, calendar2.get(Calendar.DATE));
		long milliseconds1 = calendar1.getTimeInMillis();
		long milliseconds2 = calendar2.getTimeInMillis();
		long diff = milliseconds1 - milliseconds2;
		long diffHours = diff / (60 * 60 * 1000);
		bookingAmount = sys.getUsers().get(i).getListBooking().get(j).getNumber()*TICKETPRICE;

		if(diffHours>72){
			refundedAmount = bookingAmount; 
			cancellationMessage = "No Cancellation Charges Incurred. Amount Refunded is GBP" + refundedAmount;

		}
		else if(diffHours>48 && diffHours<=72){
			cancellationCharge = bookingAmount/2;	
			refundedAmount = bookingAmount - cancellationCharge;
			cancellationMessage = "Cancellation charge is GBP" + cancellationCharge + " Refunded Amount GBP" + refundedAmount;
		}
		else if(diffHours>24 && diffHours<=48){
			cancellationCharge = bookingAmount/4;	
			refundedAmount = bookingAmount - cancellationCharge;
			cancellationMessage = "Cancellation charge is GBP"+ cancellationCharge + " Refunded Amount GBP" + refundedAmount;
		}
		else{
			cancellationCharge = bookingAmount;
			cancellationMessage = "100% charges incurred GBP "+cancellationCharge;
		}

		return cancellationMessage;
	}

	/**
	 * This method displays all the booking information of users
	 * @param BookingSystem sys
	 * @return void
	 */
	public static void displayAllBookings(BookingSystem sys){

		for(int i=0;i<sys.getUsers().size();i++){
			System.out.println("Login: "+sys.getUsers().get(i).getLogin());
			System.out.println("First Name: "+sys.getUsers().get(i).getfName());
			System.out.println("Last Name "+sys.getUsers().get(i).getlName());
			System.out.println("bookings " + sys.getUsers().get(i).getListBooking());
			System.out.println("============");
		}

	}

	/**
	 * This method updates an already existing booking.
	 * user can only increase the number of tickets
	 * @param BookingSystem sys
	 * @param int bookingId
	 * @param String login
	 * @param String pw
	 */
	public void updateMovieBooking(BookingSystem sys, int bookingId, String login, String pw){

		Screen scr = new Screen();
		int updatedTicketCount;
		int additionalTickets;
		int currentTicketCount;
		int showScreenCapacity;
		boolean isMovieUpdated = false;
		final int TICKETPRICE = 8;

		for(int i=0;i<sys.getUsers().size();i++){
			if(login.equalsIgnoreCase(sys.getUsers().get(i).getLogin())&& 
					pw.equalsIgnoreCase(sys.getUsers().get(i).getPassword())){
				Person p = sys.getUsers().get(i);
				for(int j=0;j<sys.getUsers().get(i).getListBooking().size();j++){
					if(bookingId==sys.getUsers().get(i).getListBooking().get(j).getBookingId()){
						if(!Utilities.isBookingDatePast(sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().
								getScheduledDateTime().getShowingDate())){
							showScreenCapacity = sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().getCapacity();
							currentTicketCount = sys.getUsers().get(i).getListBooking().get(j).getNumber();
							do{ 
								System.out.println("Please add the additional tickets required ");						
								additionalTickets = scr.inputValidateCapacity(showScreenCapacity);
								updatedTicketCount = currentTicketCount + additionalTickets;
								if(currentTicketCount>=updatedTicketCount){
									System.out.println("Current ticket count is "+currentTicketCount+". " +
											"You can only increase the count of ticket. Try again");
								}
								else{
									isMovieUpdated= true;
									sys.getUsers().get(i).getListBooking().get(j).setNumber(updatedTicketCount);
									int deltaTicketCount = showScreenCapacity - (additionalTickets);
									sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().setCapacity(deltaTicketCount);
									System.out.println("You have selected "+ additionalTickets + " additional tickets to be added to your booking");
									System.out.println("Payment ");
									p.addPaymentDetails(p);
									double chargedAmount = additionalTickets*TICKETPRICE;
									System.out.printf("Thank you for your payment. The amount of GBP%.2f", chargedAmount);
									System.out.println(" has been debited from your card");			
									Utilities.save(sys);
									System.out.print("Booking id "+sys.getUsers().get(i).getListBooking().get(j).getBookingId() + " updated ");
									System.out.println("Booking details: ");
									System.out.println("Movie Title: "+sys.getUsers().get(i).getListBooking().get(j).getShow().getMovie().getTitle());								
									System.out.println("Day: "+sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().getScheduledDateTime().getShowingDate());
									System.out.println("Time: "+sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().getScheduledDateTime().getSlot());
									System.out.println("Screen Number: "+sys.getUsers().get(i).getListBooking().get(j).getShow().getScreen().getScreenNumber());
									System.out.println("No of Tickets: "+sys.getUsers().get(i).getListBooking().get(j).getNumber());
									double updatedBookingAmount = sys.getUsers().get(i).getListBooking().get(j).getNumber()*TICKETPRICE;
									System.out.printf("Updated Booking Amount (@8GBP/ticket) GBP%.2f", updatedBookingAmount);
									System.out.println();
								}
							}while(!isMovieUpdated);
						}
						else{
							System.out.println("Booking cannot be amended as the date is past ");
						}
					}
				}	
			}
		}
	}

	/**
	 * Getters and setters
	 * @return
	 */
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public Showing getShow() {
		return show;
	}
	public void setShow(Showing show) {
		this.show = show;
	}


}
