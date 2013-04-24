package cinema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * This class is a starting point for invocation of all the operations
 * related to customer and Manager 
 * e.g. login validation, customer addition, display and input user information etc 
 * @author Raul Gomez,  Student ID 12837452, MSc Informatics
 *
 */
public class Person implements Serializable {
	private static final long serialVersionUID = 8124483907758853663L;
	public int userNumber;
	public String login;
	public String fName;
	public String lName;
	public String password;
	public String confirmedPassword;
	public ArrayList<Movie> listMovie;
	public ArrayList<Booking>listBooking;
	public ArrayList<Screen> listScreen;
	public ArrayList<Showing> listShowings;
	public enum ACCESS_LEVELS { MANAGER, CUSTOMER}; 
	private ArrayList<Booking> bookings;
	private Movie mov;
	private Screen scr;
	private Booking book;
	private String cardNum;
	private String cardType;
	private String cardExpDate;
	private Person mgr;
	private Person cust;
	private ArrayList<Person> users;
	private static Scanner sc;
	private static final String EXIT = "x";
	Person.ACCESS_LEVELS accessLevel; 
	private static String[] slotArray = {"9am", "12pm", "3pm", "6pm", "9pm"};

	/**
	 * no arg constructor
	 */
	public Person (){

		users = new ArrayList<Person>();
		scr = new Screen();
		book = new Booking();
		listMovie = new ArrayList<Movie>();
		listBooking = new ArrayList<Booking>();
		listScreen = new ArrayList<Screen>();
		listShowings = new ArrayList<Showing>();
		sc = new Scanner(System.in);
	}


	/**
	 * six arg constructor
	 * 
	 * @param firstName
	 * @param lastName
	 * @param login
	 * @param password
	 * @param accessLevel
	 * @param userNumber
	 */
	public Person(String firstName, String lastName, String login, String password, 
			Person.ACCESS_LEVELS accessLevel, int userNumber){

		this.fName = firstName;
		this.lName = lastName;		
		this.login = login;
		this.password = password;
		this.accessLevel = accessLevel;
		this.userNumber = userNumber;
	}

	/**
	 * This method ensures that the logins created are unique
	 * @param BookingSystem sys
	 * @return boolean userExists
	 */
	public boolean checkUniqueLogin(BookingSystem sys) {

		boolean userExist;
		do{
			userExist = false;
			System.out.println("Please create a Login");
			login = sc.next();
			for(int i=0;i<sys.getUsers().size();i++){    
				if(login.equals(sys.getUsers().get(i).getLogin())){
					userExist = true;
					System.out.println("This Login already exists. Please select a different one");
					break;
				}
			}
		}while(userExist);
		return true;
	}

	/**
	 * This method adds customer in the system
	 * @param Person p
	 * @param BookingSystem sys
	 * @return boolean
	 */
	public boolean addCustomer(Person p, BookingSystem sys){

		Person sysRet = inputCustomerDetails(p ,sys);
		sys.getUsers().add(sysRet);
		Utilities.save(sys);
		System.out.println("Customer added. Login: " + sysRet.getLogin() + " User Number: "+sysRet.getUserNumber() + 
		". Please make a note of these details as you will need them to access our system.");
		return true;
	}

	/**
	 * This method inputs user details for adding user in the application
	 * @param Person p
	 * @param BookingSystem sys
	 * @return Person p
	 */
	private Person inputCustomerDetails(Person p, BookingSystem sys){

		Movie m = new Movie();
		checkUniqueLogin(sys);
		p.setLogin(login);
		String password = inputValidatePasswordLength();
		p.setPassword(password);
		String confirmedPassword = validateConfirmPassword(password);
		p.setPassword(confirmedPassword);
		System.out.println("Enter first name ");
		fName = sc.next();
		p.setfName(fName);
		System.out.println("Enter last name ");
		lName = sc.next();
		p.setlName(lName);
		int userNumber = generateUserNumber(sys);
		p.setUserNumber(userNumber);
		p.accessLevel = ACCESS_LEVELS.CUSTOMER;
		return p;	
	}

	/**
	 * This method displays all the information of user
	 * @param BookingSystem sys
	 * @param String login
	 */
	public static void displayUserInformation(BookingSystem sys, String login){

		boolean isLoginFound = false;
		String retryQ;
		String retryA;

		for(int i=0;i<sys.getUsers().size();i++){
			if(login.equals(sys.getUsers().get(i).getLogin())){
				isLoginFound = true;
				System.out.println("User Details as follows ");
				System.out.println("User Number: "+sys.getUsers().get(i).getUserNumber());
				System.out.println("User Id: "+sys.getUsers().get(i).getLogin());
				System.out.print("Name: "+sys.getUsers().get(i).getfName()+ " ");
				System.out.println(sys.getUsers().get(i).getlName());
				System.out.println("-------------------------------------");					
				Booking.displayBookingsInformation(sys, i);
			}
		} 

		if(!isLoginFound){
			retryQ = "User not found, would you like to try again? Y/N ";
			retryA = Utilities.yesOrNoReply(retryQ);			
			if(retryA.equalsIgnoreCase("Y")){
				displayUserInformation(sys,login);
			}
			else{
				return;
			}
		}
	}

	/**
	 * This method does validation on confirm password
	 * @param String password
	 * @return String confirmedPassword
	 */
	public String validateConfirmPassword(String password){

		boolean matchingPasswords = true;
		do{  
			System.out.println("Confirm Password");
			confirmedPassword = sc.next();	
			matchingPasswords = true;
			if(!password.equals(confirmedPassword)) {
				matchingPasswords = false;
				System.out.println("Password does not match.");				
			}			
		}
		while(!matchingPasswords);
		return confirmedPassword;
	}

	
	/**
	 * This method validates length of the password
	 * @return String password
	 */
	public String inputValidatePasswordLength() {

		boolean passwordIncorrectLength;
		int minPasswordLength = 5;
		do{	
			passwordIncorrectLength = false;
			System.out.println("Enter password ");
			password = sc.next();
			if(password.length()<=minPasswordLength) {
				passwordIncorrectLength = true;
				System.out.println("Minimum number of characters in a password is six. ");
			}	
		}
		while(passwordIncorrectLength);	

		return password;
	}

	/**
	 * This method generates unique user number 
	 * whenever user is added in the application
	 * @param BookingSystem sys
	 * @return int
	 */
	public static int generateUserNumber(BookingSystem sys){

		if(sys.getUsers().size() ==0){
			return 1;
		}
		else{
			return(sys.getUsers().get(sys.getUsers().size()-1).getUserNumber()+1);
		}
	}


	/**
	 * This method invokes method for prompting user for payment details
	 * @param Person p
	 * @return Person p
	 */
	public Person addPaymentDetails(Person p) {

		cardType = addCardType();
		p.setCardType(cardType);
		cardNum = addCardNumber();
		p.setCardNum(cardNum);
		cardExpDate = addCardExpDate();
		p.setCardExpDate(cardExpDate);
		return p;

	}

	/**
	 * This method inputs and validates user's card type while booking movie
	 * @return String cardType
	 */
	public static String addCardType() {

		boolean validCardType;
		String MC;
		String VS;
		String cardType;
		do {
			validCardType = true;
			System.out.println("Enter Card Type (VS or MC) ");
			cardType =  sc.next();
			if(!(cardType.equalsIgnoreCase("VS")|| cardType.equalsIgnoreCase("MC"))) {
				System.out.println("Incorrect card type. Card should be of type VS or MC");
				validCardType = false;				
			}
		}while(!validCardType);
		return cardType;		
	}

	/**
	 * This method inputs and validates user's card number while booking movie
	 * @return String cardnumber
	 */
	public static String addCardNumber() {

		boolean validCardLength = false;
		int acceptedCardLength = 16;
		String cardNumber;
		do {
			validCardLength = true;
			System.out.println("Enter Card Number");
			cardNumber = sc.next();
			if(!Utilities.isNumeric(cardNumber) || cardNumber.length()<acceptedCardLength) {
				validCardLength = false;
				System.out.println("Card number length should contain 16 digits");
			}
		} while (!validCardLength);
		return cardNumber;
	}

	/**
	 * This method inputs and validates card expiry date while booking movie
	 * @return string cardExpDate
	 */
	public static String addCardExpDate(){
		boolean cardExpiryCorrect;
		String cardExpDate;

		do {
			cardExpiryCorrect = true;
			System.out.println("Enter Card expiry Date (mm/yyyy) ");
			cardExpDate =  sc.next();
			String[] expDateSplitter = cardExpDate.split("/");

			if(expDateSplitter.length>=2){
				if(Utilities.isNumeric(expDateSplitter[0]) && Utilities.isNumeric(expDateSplitter[1])){
					if(!Person.checkCardValidity(Integer.parseInt(expDateSplitter[0]),
							Integer.parseInt(expDateSplitter[1]))){
						System.out.println("Card Expired");
						cardExpiryCorrect = false;
					}
				}
				else{
					System.out.println("Please enter date numerics format mm/yyyy");
					cardExpiryCorrect = false;
				}
			}
			else{
				System.out.println("Incorrect date format ");
				cardExpiryCorrect = false;
			}			
		}while(!cardExpiryCorrect);
		return cardExpDate;
	}

	/**
	 * This method validates card validity
	 * @param int month
	 * @param int year
	 * @return boolean
	 */
	public static boolean checkCardValidity(int month, int year){

		final int MAXMONTHS=12;
		Calendar cal = Calendar.getInstance();
		Calendar currentcal = Calendar.getInstance();
		int cardExpMonth = currentcal.get(Calendar.MONTH);
		int cardExpiryDay=0;
		switch (cardExpMonth) {
		case 1: cardExpMonth = Calendar.JANUARY; cardExpiryDay = 31;             
		break;
		case 2: cardExpMonth = Calendar.FEBRUARY;  cardExpiryDay = 28;            
		break; 
		case 3: cardExpMonth = Calendar.MARCH;   cardExpiryDay = 31;            
		break;
		case 4: cardExpMonth = Calendar.APRIL;  cardExpiryDay = 30;            
		break; 
		case 5: cardExpMonth = Calendar.MAY; cardExpiryDay = 31;               
		break;
		case 6: cardExpMonth = Calendar.JUNE;  cardExpiryDay = 30;             
		break; 
		case 7: cardExpMonth = Calendar.JULY; cardExpiryDay = 31;  
		break;
		case 8: cardExpMonth = Calendar.AUGUST;  cardExpiryDay = 31;             
		break; 
		case 9: cardExpMonth = Calendar.SEPTEMBER;  cardExpiryDay = 30;             
		break;
		case 10: cardExpMonth = Calendar.OCTOBER; cardExpiryDay = 31;              
		break; 
		case 11: cardExpMonth = Calendar.NOVEMBER;  cardExpiryDay = 30;             
		break;
		case 12: cardExpMonth = Calendar.DECEMBER;  cardExpiryDay = 31;             
		}	

		if(month>MAXMONTHS){
			return false;
		}
		else{
			cal.set(year, month, cardExpiryDay);
			currentcal.set(currentcal.get(Calendar.YEAR),
					currentcal.get(Calendar.MONTH), currentcal.get(Calendar.DAY_OF_MONTH));
		}
		if(cal.after(currentcal) || cal.equals(currentcal)){
			return true;
		}
		else
			return false;
	}


	/**
	 * This method displays users information and gets input for a login to be selected
	 * @param BookingSystem sys
	 * @return String login
	 */
	public static String displayUserLogin(BookingSystem sys){

		String login;
		boolean isLoginFound = false;
		System.out.println("Customer Names, Logins and User Numbers");
		System.out.println("--------------------------");
		for(int i=0;i<sys.getUsers().size();i++){			
			System.out.println("Name: "+sys.getUsers().get(i).getfName()+ " " +sys.getUsers().get(i).getlName());
			System.out.println("Login: "+sys.getUsers().get(i).getLogin());
			System.out.println("User Number: "+sys.getUsers().get(i).getUserNumber());
			System.out.println("--------------------------");				
		}
		do{ 
			isLoginFound = false;
			System.out.println("Select a Login ");
			System.out.print("\t>>");
			login = sc.next();
			for(int j=0;j<sys.getUsers().size();j++){
				if(login.equalsIgnoreCase(sys.getUsers().get(j).getLogin())){
					isLoginFound = true;

				}
			}
			if(!isLoginFound){
				System.out.println("--------------------------");
				System.out.println("Invalid selection");
			}

		}while(!isLoginFound);
		return login;		

	}

	/**
	 * This method displays menu of all the operation a manager can perform
	 * @return String reply
	 */
	public static String displayManagerOperationMenu(){

		String reply;
		System.out.println("\tMenu");
		System.out.println();
		System.out.println("\t - Manager's Access");
		System.out.println("\t mo1 - Create a screen"); 
		System.out.println("\t mo2 - Update screen definition");
		System.out.println("\t mo3 - Delete a Screen");
		System.out.println("\t mo4 - Create a movie ");
		System.out.println("\t mo5 - Update a movie ");
		System.out.println("\t mo6 - Delete a movie ");
		System.out.println("\t mo7 - Create weekly schedule");
		System.out.println("\t mo8 - Delete a showing");	
		System.out.println("\t mo9 - Add a Movie Booking ");
		System.out.println("\t mo10 - Update booking ");
		System.out.println("\t mo11 - Cancel booking ");
		System.out.println("\t mo12 - Register User");
		System.out.println("\t mo13 - Update a User Account");
		System.out.println("\t Return to Manager's main menu - Enter mm");
		System.out.println("\t Exit - Enter x");
		System.out.print("\t>> ");
		reply = sc.next();
		return reply;
	}

	/**
	 * This method displays menu of all the operations a customer can perform on a booking
	 * @return String reply
	 */
	public static String displayCustomerOperationMenu(){

		String reply;
		System.out.println("\tMenu");
		System.out.println();
		System.out.println("\t - Customer's Access \n");
		System.out.println("\t co1 - Add a Movie booking ");
		System.out.println("\t co2 - Update booking ");
		System.out.println("\t co3 - Cancel booking ");
		System.out.println("\t rtn - Return to main menu");
		System.out.println("\t Exit - Enter x");
		System.out.print("\t>> ");
		reply = sc.next();
		return reply;
	}

	/**
	 * This method is starting point to invoke all the operation 
	 * to be performed by manager depending on manager's input 
	 * @param BookingSystem sys
	 */
	public static void processCinemaManager(BookingSystem sys) {

		String reply;
		reply = Person.displayManagerOperationMenu();
				
		if(reply.equalsIgnoreCase("mo1")){
			Screen.addScreen(sys);
			Utilities.returnOption(sys);
		}		
		else if(reply.equalsIgnoreCase("mo2")){
			Screen.updateScreen(sys);
			Utilities.returnOption(sys);
		}
		else if(reply.equalsIgnoreCase("mo3")){
			Screen.deleteScreen(sys); 
			Utilities.returnOption(sys);
		}		
		else if(reply.equalsIgnoreCase("mo4")){
			Movie.addMovie(sys);
			Utilities.returnOption(sys);
		}
		else if(reply.equalsIgnoreCase("mo5")){
			Movie.updateMovieData(sys);
			Utilities.returnOption(sys);
		}
		
		else if (reply.equalsIgnoreCase("mo6")){
			Movie.deleteMovie(sys);
			Utilities.returnOption(sys);
		}		
		
		else if(reply.equalsIgnoreCase("mo7")){
			boolean allShowingsCreated = false; 
			do{
				allShowingsCreated = false;			
				sys =  Showing.addShowing(sys);			
				String addNewShowingQ = "Would you like to add a new Showing ? Enter Y or N";
				String addNewShowingA = Utilities.yesOrNoReply(addNewShowingQ);
				if(addNewShowingA.equalsIgnoreCase("N")){
					allShowingsCreated = true;
				}
			}while(!allShowingsCreated);
			Utilities.save(sys);
			Utilities.returnOption(sys);
		}
		else if(reply.equalsIgnoreCase("mo8")){
			Showing.deleteShowing(sys);
			Utilities.returnOption(sys);
		}
		else if(reply.equalsIgnoreCase("mo9")){
			String login;
			String pw;
			login = Person.displayUserLogin(sys);
			pw = Utilities.retrieveCustPw(sys, login);
			Movie.bookMovie(sys,login, pw);
			Utilities.returnOption(sys);
		}
		else if(reply.equalsIgnoreCase("mo10")){

			String login;
			String pw;
			login = Person.displayUserLogin(sys);
			Person.displayUserInformation(sys, login);			
			Booking m = new Booking();
			int bookingId;
			System.out.println("Please select booking Id from the above ");
			bookingId = Utilities.hasInt();
			pw = Utilities.retrieveCustPw(sys, login);
			m.updateMovieBooking(sys, bookingId, login, pw);
			Utilities.returnOption(sys);
		}
		else if(reply.equalsIgnoreCase("mo11")){
			String login;
			String pw = null;
			login = Person.displayUserLogin(sys);
			pw=Utilities.retrieveCustPw(sys, login);
			int bookId = Booking.displayBookingData(sys, login, pw);
			if(bookId!=0){
				Booking.cancelBooking(sys, login, pw, bookId);}
			Utilities.returnOption(sys);
		}
		else if(reply.equalsIgnoreCase("mo12")){
			Person p = new Person();
			p.addCustomer(p, sys)	;
			Utilities.returnOption(sys);
		}		
		else if (reply.equalsIgnoreCase("mo13")){
			Person p = new Person();
			String log = Person.displayUserLogin(sys);
			String pw = Utilities.retrieveCustPw(sys, log);
			p.updateCustomerDetails(sys, log, pw);
			Utilities.returnOption(sys);
		}		
		
		else if(reply.equalsIgnoreCase("mm")){
			Person.performManagerActions(sys);
		}
		else if (reply.equalsIgnoreCase(EXIT))
			return;
		else{
			System.out.println("You have entered incorrect option (Enter from above options)");
			processCinemaManager(sys);
		}

		Utilities.save(sys);
	}


	/**
	 * This method displays the customer's main menu and is a starting point to invoke all the operations 
	 * to be performed by customer
	 * @param BookingSystem sys
	 * @param String login
	 * @param String pw
	 */
	public static void performCustomerActions(BookingSystem sys, String login, String pw) {

		sc = new Scanner(System.in);
		String str=null;
		String makeBooking = null;
		String q1 = null;
		String q2 = null;
		String answ1 = null;
		System.out.println("\tMain Menu \n");
		System.out.println("\t Type m to display Display Movie Information ");
		System.out.println("\t Type s to view Movie Schedule");
		System.out.println("\t Type b for the Bookings menu ");
		System.out.println("\t Type p for Personal Information ");
		System.out.println("\t Type x to exit ");
		System.out.print("\t>>");
		str = sc.next();
		if (str.equalsIgnoreCase("m")){
			Movie.displayMovieInformation(sys);
			q1 = "Would you like to view movie schedule, enter Y/N";
			answ1 = Utilities.yesOrNoReply(q1);
			if(answ1.equalsIgnoreCase("Y")){
				Schedule.displayMovieSchedule(sys, false, slotArray);
				q2 = "Would you like to make a booking, enter Y or N";			
				makeBooking = Utilities.yesOrNoReply(q2);	
				if(makeBooking.equalsIgnoreCase("Y")){				
					Movie.bookMovie(sys, login, pw);
				}
			}
			performCustomerActions(sys, login, pw);
		}
		else if(str.equalsIgnoreCase("s")){
			String q;
			String a;
			Schedule.displayMovieSchedule(sys, false, slotArray);
			q = "Would you like to make a booking? Enter Y/N";
			a = Utilities.yesOrNoReply(q);
			if(a.equalsIgnoreCase("Y")){
				Movie.bookMovie(sys, login, pw);
			}	
			performCustomerActions(sys, login, pw);
		}
		else if (str.equalsIgnoreCase("b")){
			System.out.println("\t Booking menu ");	
			String custResponse = displayCustomerOperationMenu();
			if(custResponse.equals("co1")){
				Movie.bookMovie(sys, login, pw);
				performCustomerActions(sys, login, pw);
			}
			else if(custResponse.equalsIgnoreCase("co2")) {
				Booking m = new Booking();
				int bookingId;

				bookingId = Booking.displayBookingData(sys, login, pw);
				if(bookingId!=0){

					m.updateMovieBooking(sys, bookingId, login, pw);}

				performCustomerActions(sys, login, pw);
			}
			else if(custResponse.equalsIgnoreCase("co3")){
				int bookId = Booking.displayBookingData(sys, login, pw);
				if(bookId!=0){
					Booking.cancelBooking(sys,login,pw, bookId);}

				else{System.out.println("No bookings eligible for cancellation");}
				performCustomerActions(sys, login, pw);
			}

			else if(custResponse.equalsIgnoreCase("rtn")){

				performCustomerActions(sys, login, pw);
			}
			else if(custResponse.equalsIgnoreCase("x")){
				BookingSystem.stop();
			}


			else{System.out.println("Incorrect reply " + custResponse);

			performCustomerActions(sys, login, pw);
			}
		}
		else if (str.equals("p")){		
			String q = null;
			String answ = null;
			displayUserInformation(sys, login);
			q = "Would you like to update your personal information? Y/N ";
			answ = Utilities.yesOrNoReply(q);
			if(answ.equalsIgnoreCase("Y")){					
				Person p = new Person();			
				p.updateCustomerDetails(sys, login, pw);			
			}
			performCustomerActions(sys, login, pw);
		}	
		else if (str.equals(EXIT)){
			BookingSystem.stop();
		}
		else {
			System.out.println("invalid reply [" + str + "]");
			performCustomerActions(sys, login, pw);}
	}


	/**
	 * This method displays manager's main operation menu
	 * @param BookingSystem sys
	 */
	public static void performManagerActions(BookingSystem sys) {

		String str="";
		sc = new Scanner(System.in);
		System.out.println("\tMain Menu \n");
		System.out.println("\t Type p to display All Cinema Information ");
		System.out.println("\t Type c to display Manager's operations on Cinema ");
		System.out.println("\t Type x to exit ");
		System.out.print("\t>>");
		str = sc.next();

		if (str.equals("p")){
			Cinema.displayCinemaInformation(sys,true);
			Utilities.returnOption(sys);
		}
		else if (str.equals("c")){
			processCinemaManager(sys);
			Utilities.returnOption(sys);
		}
		else if (str.equals(EXIT)){
			BookingSystem.stop();
		}
		else{
			System.out.println("invalid reply [" + str + "]");
			Utilities.returnOption(sys);
		}		
	}

	/**
	 * This method updates customer details like first name, last name etc
	 * @param BookingSystem sys
	 * @param String login
	 * @param String pw
	 */
	public void updateCustomerDetails (BookingSystem sys, String login, String pw) {	
		String updateValue;
		boolean isUserPresent = false;
		for(int i=0;i<sys.getUsers().size();i++){
			if(login.equalsIgnoreCase(sys.getUsers().get(i).getLogin())&& 
					pw.equalsIgnoreCase(sys.getUsers().get(i).getPassword())){
				isUserPresent = true;
				System.out.println("What Information would you like to update?");
				System.out.println("\t Type FN to update First Name ");
				System.out.println("\t Type LN to update Release Last Name ");
				System.out.println("\t Type P to update Password ");
				System.out.println("\t Type RTN to return to main menu");
				System.out.print("\t>>");										
				updateValue = sc.next();

				if (updateValue.equalsIgnoreCase("FN")){
					String newFirstNam;
					System.out.println("Current value " + sys.getUsers().get(i).getfName());
					System.out.println("Please add a new First Name");
					newFirstNam = Utilities.captureCompleteLine();
					sys.getUsers().get(i).setfName(newFirstNam);
					System.out.println("First Name has been updated to " + sys.getUsers().get(i).getfName());
					updateCustomerDetails(sys, login, pw);
				}
				else if (updateValue.equalsIgnoreCase("LN")){
					String newLastNam;
					System.out.println("Current value " + sys.getUsers().get(i).getlName());
					System.out.println("Please add a new Last Name");
					newLastNam = Utilities.captureCompleteLine();
					sys.getUsers().get(i).setlName(newLastNam);
					System.out.println("Last Name has been updated to " + sys.getUsers().get(i).getlName());
					updateCustomerDetails(sys, login, pw);
				}
				else if (updateValue.equalsIgnoreCase("P")) {   
					String newPw;
					String newCnfPw;
					System.out.println("Current Value " + sys.getUsers().get(i).getPassword());	
					newPw = inputValidatePasswordLength();						
					newCnfPw = validateConfirmPassword(newPw);
					sys.getUsers().get(i).setPassword(newCnfPw);
					System.out.println("Password has been updated to " + sys.getUsers().get(i).getPassword());
					updateCustomerDetails(sys, login, newCnfPw);
				}	
				else if (updateValue.equalsIgnoreCase("Rtn")){
					System.out.println("Returning to main menu ");
					return;
				}

				else {
					System.out.println("Invalid reply");
					updateCustomerDetails(sys, login, pw);					
				}
			}
		}

		if(!isUserPresent){
			String q = "Incorrect Login entered. Try again (Y/N)";
			String cont = Utilities.yesOrNoReply(q);
			if(cont.equalsIgnoreCase("Y")){				
				updateCustomerDetails(sys, login, pw);
			}
			else{
				System.out.println("Returning");
				return;
			}
		}
	}
	
	/**
	 * getters and setters
	 * @return
	 */

	public String getLogin(){
		return login;
	}

	public String getPassword(){
		return password;
	}

	public Person.ACCESS_LEVELS getAccessLevel(){
		return accessLevel;
	}

	public String getfName() {
		return fName;
	}


	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}


	public ArrayList<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(ArrayList<Booking> bookings) {
		this.bookings = bookings;
	}

	public Movie getMov() {
		return mov;
	}

	public void setMov(Movie mov) {
		this.mov = mov;
	}


	public Screen getScr() {
		return scr;
	}

	public void setScr(Screen scr) {
		this.scr = scr;
	}

	public Booking getBook() {
		return book;
	}

	public void setBook(Booking book) {
		this.book = book;
	}


	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardExpDate() {
		return cardExpDate;
	}

	public void setCardExpDate(String cardExpDate) {
		this.cardExpDate = cardExpDate;
	}

	public Person getMgr() {
		return mgr;
	}

	public void setMgr(Person mgr) {
		this.mgr = mgr;
	}

	public Person getCust() {
		return cust;
	}


	public void setCust(Person cust) {
		this.cust = cust;
	}

	public ArrayList<Person> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<Person> users) {
		this.users = users;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccessLevel(Person.ACCESS_LEVELS accessLevel) {
		this.accessLevel = accessLevel;
	}

	public ArrayList<Movie> getListMovie() {
		return listMovie;
	}

	public void setListMovie(ArrayList<Movie> listMovie) {
		this.listMovie = listMovie;
	}

	public ArrayList<Booking> getListBooking() {
		return listBooking;
	}

	public void setListBooking(ArrayList<Booking> listBooking) {
		this.listBooking = listBooking;
	}

	public ArrayList<Showing> getListShowings() {
		return listShowings;
	}

	public void setListShowings(ArrayList<Showing> listShowings) {
		this.listShowings = listShowings;
	}

	public ArrayList<Screen> getListScreen() {
		return listScreen;
	}

	public void setListScreen(ArrayList<Screen> listScreen) {
		this.listScreen = listScreen;
	}

	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}
}