package cinema;

import java.io.Serializable;
import java.util.Date;
import java.util.Scanner;

/**
 * This class is responsible for operations related date and time in the application
 * e.g. creating, checking  and validating date range etc
 * @author Raul Gomez,  Student ID 12837452, MSc Informatics
 *
 */
public class DateTime implements Serializable{

	private static final long serialVersionUID = 8141050188382124407L;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int min;
	private String showingDate;
	private String slot;
	private static String slot1 = "9am";
	private static String slot2 = "12pm";
	private static String slot3 = "3pm";
	private static String slot4 = "6pm";
	private static String slot5 = "9pm";


	/**
	 * six arg constructor
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param min
	 * @param slot
	 */
	public DateTime (int year, int month, int day, int hour, int min, String slot) {

		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.min = min;
		this.slot = slot;
	}

	/**
	 * no-arg constructor
	 */
	public DateTime() {}

	/**
	 * This method validates whether there is a range of dates present so that 
	 * showings can be created 
	 * @param BookingSystem sys
	 * @return BookingSystem sys
	 */
	public static BookingSystem isRangeDatePresent(BookingSystem sys){

		Scanner sc = new Scanner(System.in);
		String question = null;
		String reply = null;
		boolean isNewRangeNeeded = false;
		if(sys==null || sys.getShowing() ==null || (sys.getShowing().getStartDateRange()== null 
				|| sys.getShowing().getEndDateRange()== null)){
			System.out.println("Start Date or End Date missing ");
			sys = createDateRange(sys);
		}
		else{ 
			System.out.print("Existing Schedule Date Ranges are: ");			
			System.out.println(" Start " + sys.getShowing().getStartDateRange() + " End " + sys.getShowing().getEndDateRange());
		}

		do{
			isNewRangeNeeded = false;
			question = "Would you like to create a new weekly schedule Y/N (Note: Selecting this option WILL ERASE existing schedule)";
			reply = Utilities.yesOrNoReply(question);
			if(reply.equalsIgnoreCase("Y")){
				isNewRangeNeeded = true;
				sys = createDateRange(sys);
			}
			else if(reply.equalsIgnoreCase("N")){
				return sys;
			}		
			else { System.out.println("Invalid reply ");

			}		
		} while(!isNewRangeNeeded);		
		return sys;
	}

	/**
	 * This method inputs the date on which manager wants to add a showing
	 * and invokes method to do validation on its format
	 * Also, it invokes method to validate that the showing is within the date range
	 * @param BookingSystem sys
	 * @return String showingDate
	 */
	public static String selectValidateDate(BookingSystem sys){
		int dateTimeStartRangeDay; 
		int dateTimeStartRangeMonth; 
		int dateTimeStartRangeYear;		
		int dateTimeEndRangeDay; 
		int dateTimeEndRangeMonth; 
		int dateTimeEndRangeYear;
		String showingDate=null;
		Scanner sc = new Scanner(System.in);
		DateTime dateTime = new DateTime();
		boolean isDateCorrect;
		int dateTimeShowingDay; 
		int dateTimeShowingMonth; 
		int dateTimeShowingYear;	
		boolean isValidDate;

		do {
			isDateCorrect = true;
			isValidDate = false;
			do{
				System.out.println("Please add a showing date (format dd/MM/yyyy)");
				showingDate = sc.next();
				if(Utilities.isValidDate(showingDate)){
					isValidDate = true;
				}
				else{
					isValidDate = false;
					System.out.println("Date is in an invalid format");
				}
			}while(!isValidDate);
			String[] showingDateSplitter = showingDate.split("/");
			dateTimeShowingDay = Integer.parseInt(showingDateSplitter[0]);
			dateTimeShowingMonth = Integer.parseInt(showingDateSplitter[1]);
			dateTimeShowingYear = Integer.parseInt(showingDateSplitter[2]);		

			String[] startRangeDateSplitter = sys.getShowing().getStartDateRange().split("/"); 
			dateTimeStartRangeDay = Integer.parseInt(startRangeDateSplitter[0]);
			dateTimeStartRangeMonth = Integer.parseInt(startRangeDateSplitter[1]);
			dateTimeStartRangeYear = Integer.parseInt(startRangeDateSplitter[2]);	

			String[] endRangeDateSplitter = sys.getShowing().getEndDateRange().split("/");
			dateTimeEndRangeDay = Integer.parseInt(endRangeDateSplitter[0]);
			dateTimeEndRangeMonth = Integer.parseInt(endRangeDateSplitter[1]);
			dateTimeEndRangeYear = Integer.parseInt(endRangeDateSplitter[2]);	


			if(!dateTime.checkDateValidity(dateTimeShowingDay,dateTimeShowingMonth, 
					dateTimeShowingYear,dateTimeStartRangeDay, dateTimeStartRangeMonth,dateTimeStartRangeYear, 
					dateTimeEndRangeDay, dateTimeEndRangeMonth, dateTimeEndRangeYear)){
				isDateCorrect = false;
			}
			if(!isDateCorrect){
				System.out.println("Date entered is not in the range. Try again.");
			}
		} while(!isDateCorrect);

		return showingDate;
	}

	/**
	 * This method does the validation whether the date on which manager wants to
	 * add a showing is between the starting date and end date of the weekly schedule
	 * @param int date
	 * @param int month
	 * @param int year
	 * @param int date1
	 * @param int month1
	 * @param int year1
	 * @param int date2
	 * @param int month2
	 * @param int year2
	 * @return boolean
	 */
	public boolean checkDateValidity(int date, int month, int year, int date1, int month1, int year1, 
			int date2, int month2, int year2){
		
		Date actualDate = new Date(year, month-1, date);
		Date startDate = new Date(year1, month1-1, date1);
		Date endDate = new Date(year2, month2-1, date2);
		if(actualDate.compareTo(startDate)<0 || actualDate.compareTo(endDate) >0){
			System.out.println("Date is outside the range");
			return false;
		}
		return true;
	}

	/**
	 * This method validates the slot on which the manager wants to add
	 * a showing is valid or not
	 * @param BookingSystem sys
	 * @return String reply
	 */
	public static String inputValidateScheduledSlot(BookingSystem sys){
		String reply;
		boolean isSlotValid=false;
		Scanner sc = new Scanner(System.in);
		do{
			System.out.println("Please select a slot (9am, 12pm, 3pm, 6pm, or 9pm) ");
			reply = sc.next();
			if(reply.equalsIgnoreCase(slot1) ||reply.equalsIgnoreCase(slot2)||reply.equalsIgnoreCase(slot3) 
					||reply.equalsIgnoreCase(slot4)||reply.equalsIgnoreCase(slot5)){
				isSlotValid = true;}
			else{
				System.out.println("Slot is invalid, try again");
			}
		}	
		while(!isSlotValid);
		System.out.println("You have selected the " + reply + " slot");
		return reply;
	}

	/**
	 * This method creates the date range for a weekly schedule.
	 * @param BookingSystem sys
	 * @return BookingSystem sys
	 */
	public static BookingSystem createDateRange (BookingSystem sys){
		Showing sh = new Showing();
		Scanner sc = new Scanner(System.in);
		String startRangeDate;
		String endRangeDate;
		System.out.println("Please add a starting date range for the weekly schedule (dd/MM/yyyy)");
		startRangeDate = sc.next();
		sh.setStartDateRange(startRangeDate);
		System.out.println("Please add an end date range for the weekly schedule (dd/MM/yyyy)");
		endRangeDate = sc.next();
		sh.setEndDateRange(endRangeDate);
		sys.setShowing(sh);  	
		Utilities.save(sys);		
		return sys;
	}

	/**
	 * getters and setters
	 * @return
	 */
	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getShowingDate() {
		return showingDate;
	}

	public void setShowingDate(String showingDate) {
		this.showingDate = showingDate;
	}

}