package cinema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * This class is the starting point in the application.
 * This is responsible for login functionality 
 * and accordingly control the flow for Manager or customer
 * @author Raul Gomez,  Student ID 12837452, MSc Informatics
 */
public class BookingSystem implements Serializable {

	private static final long serialVersionUID = 1175280499073663849L;
	private static final String FILENAME = "system.ser";
	private static final String EXIT = "x";
	enum ACCESS_LEVELS { MANAGER, CUSTOMER};
	private String name;
	private ArrayList<Cinema> cinemas;
	private ArrayList<Person> users;
	private ArrayList<Movie> movies;
	private ArrayList<Screen> scr;
	private ArrayList<Showing> showings;    
	private ArrayList<Booking> bookings;
	private static BookingSystem sys;
	private static Scanner sc = new Scanner(System.in);
	private Showing showing;

	/**
	 * Main method
	 * @param String[] args
	 */
	public static void main(String[] args) {
		new BookingSystem().run();
	}
	
	/**
	 * Constructor
	 */
	public BookingSystem() {
		this("default");
	}

	/**
	 * This method is responsible for exiting from the application
	 */
	public static void stop (){
		Utilities.save(sys);
		System.out.println("Exited");	
		return;
	}

	/**
	 * This method loads the value from System.ser file in BookingSystem object
	 * and invokes a method to prompt user for details
	 */
	void run() {
		sc = new Scanner(System.in);
		sys = Utilities.initialise();
		welcomeScreen();
	}

	/**
	 * One arg constructor
	 * @param s
	 */
	public BookingSystem(String s) {
		
		name = s;
		cinemas = new ArrayList<Cinema>();
		scr = new ArrayList<Screen>();
		movies = new ArrayList<Movie>();
		showings = new ArrayList<Showing>();
		bookings = new ArrayList<Booking>();
		users = new ArrayList<Person>();
		showings = new ArrayList<Showing>();
	}

	/**
	 * This method asks user if he is an existing customer in the system
	 * asks for login if he is an existing customer 
	 * and invokes method to add user in the system if he/she is not an existing user
	 */
	public static void welcomeScreen() {

		String quest1;
		String reply;
		quest1 = "Are you an existing user?, Type Y or N ";	
		reply = Utilities.yesOrNoReply(quest1);
		if(reply.equalsIgnoreCase("Y")){
			loginScreen(sys);
		}
		else if(reply.equalsIgnoreCase("N")) {
			System.out.println("Please register on our System");
			Person p = new Person();
			p.addCustomer(p,sys);
			System.out.println("Thank you for registering with us ");
			String q2;
			String a2;
			q2 = "Would you like to login? Enter Y/N";
			a2 = Utilities.yesOrNoReply(q2);
			if(a2.equalsIgnoreCase("Y")){
				loginScreen(sys);
			}
			if(a2.equalsIgnoreCase("N")){
				System.out.println("Exited");
				BookingSystem.stop();
			}			
		}
	}

	/**
	 * This method does the validation and login
	 * Displays options for managers and customer accordingly
	 */
	public static boolean loginScreen(BookingSystem sys) {

		String login;
		String pw;
		boolean userExists = false;
		System.out.print("Login: ");
		login = sc.next();
		System.out.print("Password: ");
		pw = sc.next();
		for(int i=0;i<sys.getUsers().size();i++){
			if(login.equals(sys.getUsers().get(i).getLogin()) && pw.equals(sys.getUsers().get(i).getPassword())){
				if(sys.getUsers().get(i).getAccessLevel().equals(Person.ACCESS_LEVELS.MANAGER)){
					userExists = true;
					System.out.println("You have logged in as the Manager");
					Person.performManagerActions(sys);
				}
				else if(sys.getUsers().get(i).getAccessLevel().equals(Person.ACCESS_LEVELS.CUSTOMER)){
					userExists = true;
					System.out.println("You have logged in as Customer " + login);
					Person.performCustomerActions(sys, login, pw);
				}					
			}	
		}

		if(!userExists){
			String retryQ;
			String retryAnsw;
			System.out.println("Incorrect credentials");
			retryQ = "Do you want to retry (Y/N) ";
			retryAnsw = Utilities.yesOrNoReply(retryQ);
			if(retryAnsw.equalsIgnoreCase("Y")){
				loginScreen(sys);
			}
			else{
				System.out.println("Exited");
				BookingSystem.stop();						
			}
		}
		return true;
	}

	/**
	 * getters and setters
	 * @return
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Person> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<Person> users) {
		this.users = users;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}

	public static String getFilename() {
		return FILENAME;
	}

	public ArrayList<Screen> getScr() {
		return scr;
	}

	public void setScr(ArrayList<Screen> scr) {
		this.scr = scr;
	}

	public ArrayList<Cinema> getCinemas() {
		return cinemas;
	}

	public void setCinemas(ArrayList<Cinema> cinemas) {
		this.cinemas = cinemas;
	}

	public ArrayList<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(ArrayList<Booking> bookings) {
		this.bookings = bookings;
	}

	public ArrayList<Showing> getShowings() {
		return showings;
	}

	public void setShowings(ArrayList<Showing> showings) {
		this.showings = showings;
	}

	public Showing getShowing() {
		return showing;
	}

	public void setShowing(Showing showing) {
		this.showing = showing;
	}
}
