package cinema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for all the actions related to screen creation, display, update, deletion,
 * validation of screen capacity when making a booking.
 * @author Raul Gomez,  Student ID 12837452, MSc Informatics
 */

public class Screen implements Serializable{

	private static final long serialVersionUID = -6753053656212683770L;
	private int screenNumber;
	private int capacity;
	private Movie mov;
	private Cinema cinema;
	private ArrayList<Screen> screens;
	private static Scanner sc;
	enum SCREEN_LAYOUT {LEFT, RIGHT, CENTRE};
	private String layout; 
	private DateTime scheduledDateTime;

	/**
	 * four arg constructor
	 * @param cinema
	 * @param screenNumber
	 * @param capacity
	 * @param layout
	 */
	public Screen (Cinema cinema, int screenNumber, int capacity, Screen.SCREEN_LAYOUT layout) {

		this.cinema = cinema;
		this.screenNumber = screenNumber;
		this.capacity = capacity;
	}

	/**
	 * no-arg constructor
	 */
	public Screen () {

		screens = new ArrayList<Screen>();
		sc = new Scanner(System.in);
	}

	/**
	 * This method inputs and validate the screen number if it exists or not
	 * @param BookingSystem sys
	 * @return int screenNumber
	 */
	public int inputCheckScreenNumber(BookingSystem sys){

		boolean validscreenNumber;
		do{
			validscreenNumber = false;
			Screen.displayScreenNumber(sys);
			screenNumber = Utilities.hasInt();  
			for(int i=0;i<sys.getScr().size();i++){
				if(screenNumber == sys.getScr().get(i).getScreenNumber()){
					validscreenNumber = true;
					break;
				}
			}
			if(!validscreenNumber){
				System.out.println("Screen Number incorrect. Try again.");
			}
		}while(!validscreenNumber);
		return screenNumber;

	}

	/**
	 * This method validates if the seats are available while booking a movie
	 * @param int showScreenCapacity
	 * @return int countTicket
	 */
	public int inputValidateCapacity(int showScreenCapacity){

		boolean isSeatAvaialable;
		int countTicket;
		do{
			isSeatAvaialable = false;
			System.out.println("Enter number of tickets ");
			countTicket =  Utilities.hasInt();
			if(countTicket!=0){
				if(showScreenCapacity>=countTicket){
					isSeatAvaialable = true;
					return countTicket;
				}
				else{
					System.out.println("Only "+showScreenCapacity+" space available in this showing");

				}

			}
			else{
				System.out.println("Ticket count can not be 0. Try Again");
			}
		}while(!isSeatAvaialable);
		return countTicket;
	}

	/**
	 * This method deletes screen from the application
	 * @param BookingSystem sys
	 */
	public static boolean deleteScreen(BookingSystem  sys){

		int deletedScreenId;
		boolean doesScreenExist = false;
		boolean hasShowingsScheduled = false;

		if(sys.getScr().size()!=0){		
			Screen.displayScreenNumber(sys);		
			System.out.println("Enter the screen Id to be deleted from above");   
			deletedScreenId = Utilities.hasInt();               

			for(int i=0;i<sys.getScr().size(); i++){
				if (deletedScreenId == sys.getScr().get(i).getScreenNumber()){
					doesScreenExist = true;
					for (int j=0;j<sys.getShowings().size(); j++){
						if(deletedScreenId == sys.getShowings().get(j).getScreen().getScreenNumber()){
							hasShowingsScheduled = true;
						}
					}
					if(hasShowingsScheduled){
						System.out.println("Screen has showings scheduled and cannot be deleted");
					}
				}
			}

			if(!doesScreenExist){System.out.println("screen selected does not exist");
			deleteScreen(sys);}
			if(!hasShowingsScheduled){
				for(int i=0;i<sys.getScr().size(); i++){
					if (deletedScreenId == sys.getScr().get(i).getScreenNumber()){

						String confirmDeletionQ = "Please confirm you want to delete this screen. (Y/N)";
						String confirmDeletionA = Utilities.yesOrNoReply(confirmDeletionQ);						
						if(confirmDeletionA.equalsIgnoreCase("Y")){

							System.out.println("Screen "+ deletedScreenId+ " deleted");
							sys.getScr().remove(i);
							Utilities.save(sys);
						}
					}
				}

			}
		}
		return true;
	}


	/**
	 * This method update attribute of screen
	 * @param BookingSystem sys
	 * @return boolean
	 */
	public static boolean updateScreen(BookingSystem sys){

		int screenNumber;
		if(displayScreenSpecifications(sys)==true){
			screenNumber = inputDisplayScreenNumber(sys);
			inputUpdateScreenData(sys, screenNumber);		
			return true;
		}
		else {return false;}
	}

	/**
	 * This method updates screen capacity when movie booking is successful
	 * @param BookingSystem sys
	 * @param int screenmuber
	 * @param int countTicket
	 * @param Showing sh
	 */
	public void updateScreenCapacity(BookingSystem sys, int screenmuber, int countTicket, Showing sh){

		boolean validScreen = false;
		int updateCapacity;
		for(int i=0;i<sys.getScr().size();i++){
			if(screenmuber == sys.getScr().get(i).getScreenNumber()){
				validScreen = true;
				updateCapacity = sh.getScreen().getCapacity()-countTicket;
				sh.getScreen().setCapacity(updateCapacity);
				Utilities.save(sys);
				break;
			}
		}
	}

	/**
	 * This method updates screen capacity whenever cancellation of booking happens
	 * @param BookingSystem sys
	 * @param int screenmuber
	 * @param int countTicket
	 * @param Showing sh
	 */
	public static void cancellationUpdateScreenCapacity(BookingSystem sys, int screenmuber, int countTicket, Showing sh){

		boolean validScreen = false;
		int updateCapacity;
		for(int i=0;i<sys.getScr().size();i++){
			if(screenmuber == sys.getScr().get(i).getScreenNumber()){
				validScreen = true;
				updateCapacity = sh.getScreen().getCapacity()+countTicket;
				sh.getScreen().setCapacity(updateCapacity);
				Utilities.save(sys);
				break;
			}
		}
	}

	/**
	 * This method adds screen to the application
	 * @param BookingSystem sys
	 * @return boolean
	 */
	public static boolean addScreen(BookingSystem sys){

		boolean allScreensCreated = false; 
		String screenLayout;
		String addNewScreen;
		String reply;
		int screenNumber;
		do{ 
			allScreensCreated = false;
			displayNumberOfScreens(sys);	
			screenNumber = inputValidateScreenNumber(sys);		
			System.out.println("Add the capacity of the screen");
			int capacity = Utilities.hasInt();
			Screen scr = new Screen();
			scr.setScreenNumber(screenNumber);
			scr.setCinema(sys.getCinemas().get(0));
			scr.setCapacity(capacity);
			screenLayout = inputValidateScreenLayout();
			scr.setLayout(screenLayout);
			sys.getScr().add(scr);
			Utilities.save(sys);
			System.out.println("Screen  "+ screenNumber + " added successfully");
			addNewScreen = "Would you like to add a new screen? Enter Y or N";
			reply = Utilities.yesOrNoReply(addNewScreen);
			if(reply.equalsIgnoreCase("N")){
				allScreensCreated = true;
				System.out.println("Screen(s) added successfully ");}
		}while(!allScreensCreated);

		return true;
	}

	/**
	 * This method inputs and validates screen attribute i.e. Layout
	 * @return String screenLayout
	 */
	public static String inputValidateScreenLayout() {

		boolean isScreenLayoutCorrect = false;
		String screenLayout ;
		do{
			System.out.println("Enter layout: (LEFT, RIGHT or CENTRE) ");
			screenLayout = sc.next();
			for (SCREEN_LAYOUT sl : SCREEN_LAYOUT.values()) {
				if (sl.name().equalsIgnoreCase(screenLayout)){
					isScreenLayoutCorrect = true;
					return screenLayout;
				}
			}
			if(!isScreenLayoutCorrect){
				System.out.println("Invalid layout. Try again");
			}
		}while(!isScreenLayoutCorrect);
		return screenLayout;
	}

	/**
	 * This method displays screen attributes like number, capacity and layout
	 * @param BookingSystem sys
	 * @return boolean
	 */
	public static boolean displayScreenSpecifications(BookingSystem sys){

		if(sys.getScr()==null || sys.getScr().size()==0){
			System.out.println("No Sceens have yet been created");			
			return false;
		}
		else{

			System.out.println("Screen specifications ");
			for(int i=0;i<sys.getScr().size();i++){
				System.out.println("\t Screen Number "+sys.getScr().get(i).getScreenNumber());
				System.out.println("\t Capacity "+sys.getScr().get(i).getCapacity());
				System.out.println("\t Layout "+sys.getScr().get(i).getScreenLayout());
				System.out.println("-----------------------");
			}
			return true;    
		}	
	}

	/**
	 * This method inputs and displays screen number
	 * @param BookingSystem sys
	 * @return int screenNumber
	 */
	public static int inputDisplayScreenNumber(BookingSystem sys){

		sc = new Scanner(System.in);
		boolean screenNumberFound;
		boolean isNumeric = true;
		String screenNum;
		int screenNumber;	
		do{ 
			screenNumberFound = false;	
			do {
				isNumeric = true;
				System.out.println("Type screen id selection");
				screenNum = sc.next();
				if(!Utilities.isNumeric(screenNum)){
					System.out.println("Input should be a number");
					isNumeric = false;
				}		
			}while(!isNumeric);
			screenNumber = Integer.parseInt(screenNum);

			for(int i=0;i<sys.getScr().size();i++){
				if(screenNumber == sys.getScr().get(i).getScreenNumber()){
					screenNumberFound = true;
					System.out.println("You have selected the screen "+ sys.getScr().get(i).getScreenNumber());
					break;
				}				
			}			
			if(!screenNumberFound){System.out.println("Screen number incorrect, try again");					
			}
		}while (!screenNumberFound);	

		return screenNumber;		
	}

	/**
	 * This method inputs and validate screen number
	 * @param BookingSystem sys
	 * @return int screennumber
	 */
	public static int inputValidateScreenNumber(BookingSystem sys) {

		boolean screenNumberExists;		
		int screenNumber;
		do{	
			screenNumberExists = false;			
			screenNumber = Utilities.hasInt();
			for(int i=0;i<sys.getScr().size();i++){			
				if(screenNumber == sys.getScr().get(i).getScreenNumber()){
					screenNumberExists = true;
					System.out.println("This Screen Number already exists. Please add a different Number");
					break;
				}
			}
		}while(screenNumberExists);
		return screenNumber;
	}

	/**
	 * This method updates screen attributes like capacity, layout and screen number
	 * @param BookingSystem sys
	 * @param int screenNumber
	 */
	public static void inputUpdateScreenData(BookingSystem sys, int screenNumber) {

		boolean isUpdateComplete = false;
		boolean isScreenPresent = false;
		String reply;

		do {
			isUpdateComplete = false;
			for(int i=0;i<sys.getScr().size();i++){
				if(screenNumber == sys.getScr().get(i).getScreenNumber()) {
					isScreenPresent = true;							
					System.out.println("What Information would you like to update?");
					System.out.println("\t Type C to update Capacity ");
					System.out.println("\t Type L to update Layout ");
					System.out.println("\t Type SN to update Screen Number ");
					System.out.println("\t Type X when all Screen information has been updated ");
					System.out.print("\t>>");
					reply = sc.next();	

					if (reply.equalsIgnoreCase("C")){
						int newCapacity;
						System.out.println("Please add a new Capacity");
						newCapacity = Utilities.hasInt();
						sys.getScr().get(i).setCapacity(newCapacity);
						System.out.println("Capacity has been updated. New value is " + newCapacity);
					}
					else if (reply.equalsIgnoreCase("L")) {   
						String newLayout;
						System.out.println("Please add a new Layout");
						newLayout = inputValidateScreenLayout();
						sys.getScr().get(i).setLayout(newLayout); 
						System.out.println("Layout has been updated. New value is " + newLayout);
					}
					else if (reply.equalsIgnoreCase("SN")) {   
						boolean screenAlreadyExists=false;
						do{
							screenAlreadyExists = false;					
							int newScreenNumber;
							System.out.println("Please add a new Screen Number");
							newScreenNumber = Utilities.hasInt();
							for(int j=0;j<sys.getScr().size();j++){
								if(newScreenNumber == sys.getScr().get(j).getScreenNumber()) { 
									System.out.println("The screen number selected already exists, please select a new number"); 
									screenAlreadyExists = true;
								}
							}
							if(screenAlreadyExists==false) {							
								sys.getScr().get(i).setScreenNumber(newScreenNumber);			
								System.out.println("Screen Number has been updated. New value is " + newScreenNumber);
							}	
						}while (screenAlreadyExists);
					}
					else if (reply.equals("X")){
						isUpdateComplete = true;
					}
					else {
						System.out.println("invalid reply [" + reply + "]");
					}
					Utilities.save(sys);
					String updateMoreDataQ = "Would you like to continue updating Screen Information? Enter Y or N";
					String updateMoreDataA = Utilities.yesOrNoReply(updateMoreDataQ);							

					if(updateMoreDataA.equalsIgnoreCase("Y")) {							
						isUpdateComplete = false;							
					}
					else if(updateMoreDataA.equalsIgnoreCase("N")){
						System.out.println("All Screen Information has now been updated ");
						return;							
					}
				}
			} if(!isScreenPresent) {inputUpdateScreenData(sys, screenNumber);}
		}while(!isUpdateComplete);
	}

	/**
	 * This method display the screen number for total number of screens in the application and prompts user to add a screen
	 * if none exist
	 * @param BookingSystem sys
	 */
	public static void displayNumberOfScreens(BookingSystem sys){	

		sc = new Scanner(System.in);
		if(sys!=null){
			if(sys.getScr()!= null){				
				if (sys.getScr().size()==0) { 
					System.out.println("No screens have been created.");
					System.out.println("Add a new Screen Number");
				}				
				else {
					System.out.println("The existing screens are ");
					for(int i=0; i<sys.getScr().size();i++){ 
						if(sys.getScr().get(i)!=null && sys.getScr().get(i).getScreenNumber()!=0){
							System.out.println("Screen number "  + sys.getScr().get(i).getScreenNumber());	

						}
						else {
							System.out.println("Value of Screens is null");
						}
					}	System.out.println("Add a new Screen Number");				
				} 
			}
			else{
				System.out.println("The value of Screens " + sys.getScr());				
			}
		}
		else{
			System.out.println("The value of Sys is " + sys);
		}
	}


	/**
	 * This method displays the screen number for total number of screens already existing in the application
	 * @param BookingSystem sys
	 */
	public static void displayScreenNumber(BookingSystem sys){

		System.out.println("Existing Screen No: ");
		for(int i=0;i<sys.getScr().size();i++){
			System.out.println(sys.getScr().get(i).getScreenNumber());
		}
	}

	/**
	 * getters and setters
	 * @return
	 */

	public int getScreenNumber() {
		return screenNumber;
	}

	public void setScreenNumber(int screenNumber) {
		this.screenNumber = screenNumber;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public ArrayList<Screen> getScr() {
		return screens;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public void setScr(ArrayList<Screen> screens) {
		this.screens = screens;
	}
	public String getScreenLayout(){
		return layout;
	}

	public Movie getMov() {
		return mov;
	}

	public void setMov(Movie mov) {
		this.mov = mov;
	}

	public DateTime getScheduledDateTime() {
		return scheduledDateTime;
	}

	public void setScheduledDateTime(DateTime scheduledDateTime) {
		this.scheduledDateTime = scheduledDateTime;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}