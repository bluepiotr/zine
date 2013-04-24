package cinema;

import java.util.ArrayList;

/**
 * This class is the starting point for this application
 * This is responsible for providing default credentials for manager when the application loads
 * IMPORTANT NOTE
 * All data has been created manually on the application and saved onto System.ser, so PreLoad.java
 * should not be run again, to prevent System.ser being overwritten with below data.
 * 
 * @author Raul Gomez,  Student ID 12837452, MSc Informatics
 *
 */
public class PreLoad {

	/**
	 * This is a main method which puts information for cinema
	 * and manager's credentials in System.ser file
	 * @param String[] args
	 */
	public static void main(String[] args) {

		BookingSystem sys= new BookingSystem("My Booking System");
		ArrayList<Cinema> cinema = new ArrayList<Cinema>();
		ArrayList<Person> users = new ArrayList<Person>();
		Person mgr;
		
		Cinema cn = new Cinema("O2 Cinemoi");
		cinema.add(cn);
		sys.setCinemas(cinema);
		mgr = new Person("Peter", "Cinemoi", "manager1", "manager123", Person.ACCESS_LEVELS.MANAGER, 0);
		users.add(mgr);
		sys.setUsers(users);
		Utilities.save(sys);

		for(int i=0; i<sys.getCinemas().size(); i++) {
			System.out.print("\t Cinema Name " + sys.getCinemas().get(i).getName());
			System.out.println("\t Number of Screens " + sys.getCinemas().get(i).getNumberOfScreens());
		}

		for(int i=0; i<sys.getUsers().size(); i++) {
			System.out.print("\t User ID " + sys.getUsers().get(i).getLogin());
			System.out.print("\t Password " + sys.getUsers().get(i).getPassword());
			System.out.print("\t Access Level " + sys.getUsers().get(i).getAccessLevel());
			System.out.println("\t User unique ID " + sys.getUsers().get(i).getUserNumber());
		}
	}
}