package cinema;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contained various utilities methods which are commonly used across all the classes
 * in the application
 * @author Raul Gomez,  Student ID 12837452, MSc Informatics
 *
 */
public class Utilities {

	private static Scanner sc = new Scanner(System.in);
	private static final String EXIT = "x";
	private static final String FILENAME = "system.ser";
	private static String timeSlotOne = "SlotOne(9am)";
	private static String timeSlotTwo = "SlotTwo(12pm)";
	private static String timeSlotThree = "SlotThree(3pm)";
	private static String timeSlotFour = "SlotFour(6pm)";
	private static String timeSlotFive = "SlotFive(9pm)";

	/**
	 * This method returns the manager to the appropriate menu
	 * @param BookingSystem sys
	 */
	public static void returnOption(BookingSystem sys){
		
		String returnOption="";
		System.out.println("--------------------------------------------");
		System.out.println("Return to manager's operation menu: Enter mo");
		System.out.println("Return to manager's main menu: Enter mm");
		System.out.println("Exit: Enter x");
		System.out.println("--------------------------------------------");
		returnOption = sc.next();
		if(returnOption.equalsIgnoreCase("mo")){
			Person.processCinemaManager(sys);
		}
		else if(returnOption.equalsIgnoreCase("mm")){
			Person.performManagerActions(sys);
		}
		else if (returnOption.equalsIgnoreCase(EXIT)){
			BookingSystem.stop();			
		}
		else{
			System.out.println("invalid reply [" + returnOption + "]");
			Utilities.returnOption(sys);
		}
	}

	/**
	 * This method checks if the date is past
	 * @param String bookingShowingDate
	 * @return boolean
	 */
	public static boolean isBookingDatePast(String bookingShowingDate){
		
		boolean isBookingDateInPast = false;
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		String[] dateSplitter = bookingShowingDate.split("/");
		calendar1.set(Integer.parseInt(dateSplitter[2]), Integer.parseInt(dateSplitter[1]), Integer.parseInt(dateSplitter[0]));
		calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH)+1, calendar2.get(Calendar.DATE));
		if(calendar2.after(calendar1)) { 
			isBookingDateInPast = true;
		}
		else{
			return isBookingDateInPast;
		}
		return isBookingDateInPast;
	}

	/**
	 * This method captures the user input with spaces in between
	 * @return String
	 */
	public static String captureCompleteLine(){
		
		Scanner sc = new Scanner(System.in);
		String completeLine=null;
		sc.useDelimiter("\n");
		if(sc.hasNextInt()){
			completeLine = (String.valueOf(sc.nextInt()));
		}
		else if(sc.hasNext()){
			completeLine =  sc.next();
		}
		return completeLine;
	}

	/**
	 * This method intialises the BookingSystem object from the System.ser file
	 * @return BookingSystem sys
	 */
	public static BookingSystem initialise() {
		
		FileInputStream fis = null;
		ObjectInputStream in = null;
		BookingSystem sys = null;
		try {
			fis = new FileInputStream(FILENAME);
			in = new ObjectInputStream(fis);
			sys = (BookingSystem) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return sys;
	}

	/**
	 * This method saves everything from BookingSystem object onto System.ser object
	 * @param BookingSystem sys
	 */
	public static void save(BookingSystem sys) {
		
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(FILENAME);
			out = new ObjectOutputStream(fos);
			out.writeObject(sys);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * this method will increment day by one
	 * @param String startDate
	 * @return String
	 */
	public static String dayIncrementer(String startDate) {

		String dt = startDate; 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dt));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(Calendar.DATE, 1); 
		dt = sdf.format(c.getTime()); 
		return dt;
	}

	/**
	 * This method invokes method to check if the slot is valid or not
	 * @return String
	 */
	public static String inputValidateScheduleSlot(){
		boolean isSlotValid = false;
		String slot;
		do{
			System.out.println("Enter time slot - SlotOne(9am), SlotTwo(12pm), SlotThree(3pm), SlotFour(6pm), SlotFive(9pm)");
			slot = sc.next();
			if(validateTimeSlot(slot)){
				isSlotValid = true;
			}
			if(!isSlotValid){
				System.out.println("Invalid time slot. Try Again ");
			}
		}while(!isSlotValid);
		return slot;
	}

	/**
	 * This method checks if slot is valid
	 * @param String slot
	 * @return boolean
	 */
	public static boolean validateTimeSlot(String slot){
		if(slot.equals(timeSlotOne) || slot.equals(timeSlotTwo) 
				|| slot.equals(timeSlotThree) || slot.equals(timeSlotFour) 
				|| slot.equals(timeSlotFive)){
			return true;
		}
		return false;
	}

	/**
	 * This method checks if the String entered is numeric.
	 * @param String number
	 * @return boolean
	 */
	public static boolean isNumeric(String number){  
		boolean isValid = false;  
		String expression = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";  
		CharSequence inputStr = number;  
		Pattern pattern = Pattern.compile(expression);  
		Matcher matcher = pattern.matcher(inputStr);  
		if(matcher.matches()){  
			isValid = true;  
		}  
		return isValid;  
	}                           

	/**
	 * This method checks if the date is of valid format
	 * @param String text
	 * @return boolean
	 */
	public static boolean isValidDate(String text) {
		if (text == null || !text.matches("^(0[1-9]|[12][0-9]|3[01])[- /]?[0-1][1-9][- / ]?(18|19|20|21)\\d{2,4}"))
			return false;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		try {
			df.parse(text);
			return true;
		} catch (ParseException ex) {
			ex.printStackTrace();			
			return false;
		}
	}

	/**
	 * This method checks the user input
	 * @param String question
	 * @return String
	 */
	public static String yesOrNoReply(String question){
		String yesOrNo;
		System.out.println(question);
		System.out.print("\t>> ");
		while(!(sc.hasNext("Y")||sc.hasNext("y")||sc.hasNext("N")||sc.hasNext("n"))){
			System.out.println("Invalid Reply ");
			System.out.println(question);
			System.out.print("\t>> ");
			sc.next();
		}	
		yesOrNo = sc.next();
		return yesOrNo;		
	}

	/**
	 * This method checks the input to be a number
	 * @return
	 */
	public static int hasInt(){
		int input;
		while(!sc.hasNextInt()){
			System.out.println("Input must be a number ");
			sc.next();
		}	
		input = sc.nextInt();
		return input;	
	}

	/**
	 * This method retrieves customer password
	 * @param BookingSystem sys
	 * @param String login
	 * @return String
	 */
	public static String retrieveCustPw(BookingSystem sys, String login){
		String pw = null;
		boolean isPasswordFound = false;
		for(int i=0;i<sys.getUsers().size();i++){
			if(login.equals(sys.getUsers().get(i).getLogin())){
				isPasswordFound = true;
				pw = sys.getUsers().get(i).getPassword();
			}
		}
		if(!isPasswordFound){
			System.out.println("User and password not found");
			Utilities.returnOption(sys);}
		return pw;		
	}	

	/**
	 * This method checks if the date is in the future
	 * @param String date
	 * @return boolean
	 */
	public static boolean isDateInFuture(String date){
		boolean isDateInFuture = false;
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		String[] dateSplitter = date.split("/");
		calendar1.set(Integer.parseInt(dateSplitter[2]), Integer.parseInt(dateSplitter[1]), Integer.parseInt(dateSplitter[0]));
		calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH)+1, calendar2.get(Calendar.DATE));
		if(calendar1.after(calendar2) || calendar1.equals(calendar2)){
			isDateInFuture = true;
		}
		else{
			return isDateInFuture;
		}
		return isDateInFuture;
	}	
}