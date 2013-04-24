package cinema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for all the information related
 * to the cinema
 * @author Raul Gomez,  Student ID 12837452, MSc Informatics
 *
 */
public class Cinema implements Serializable {

	private static final long serialVersionUID = -8724642137730004101L;
	private String name;
	private int numberOfScreens;
	private ArrayList<Cinema> cinemas;
	private static final String EXIT = "x";
	private static Scanner sc = new Scanner(System.in);
	private static String[] slotArray = {"9am", "12pm", "3pm", "6pm", "9pm"};
	private Screen[] screens;

	/**
	 * One arg constructor
	 * @param name
	 */
	public Cinema(String name){
		this.name = name;
		screens = new Screen[numberOfScreens];
	}

	/**
	 * no-arg constructor
	 */
	public Cinema () {
		cinemas = new ArrayList<Cinema>();
	}

	/**
	 * This method displays all the information related to the cinema
	 * @param BookingSystem sys
	 * @param boolean scheduleDisplayParameter
	 * @return void
	 */
	public static void displayCinemaInformation(BookingSystem sys, boolean scheduleDisplayParameter){

		System.out.println(" All Information ");
		System.out.println("Cinema  Details " );
		for(int i=0;i<sys.getCinemas().size();i++){
			System.out.println("\t  Name: "+ sys.getCinemas().get(i).getName());
			System.out.println("\t  Number of Screens: "+ sys.getScr().size());		
		}
		Screen.displayScreenSpecifications(sys);
		System.out.println("==========================");		
		System.out.println("User details " );
		for(int i=0;i<sys.getUsers().size();i++){
			System.out.println("\t Login "+sys.getUsers().get(i).getLogin());
			System.out.println("\t Password "+sys.getUsers().get(i).getPassword());
			System.out.println("\t Access Level "+sys.getUsers().get(i).getAccessLevel());
			System.out.println("\t User Number "+sys.getUsers().get(i).getUserNumber());
			System.out.println("==========================");	
		}
		Movie.displayMovieInformation(sys);
		System.out.println("==========================");			
		System.out.println("Movie Schedule ");
		System.out.println("------------------------------------");
		Schedule.displayMovieSchedule(sys, scheduleDisplayParameter, slotArray);
		System.out.println("==========================");

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

	public int getNumberOfScreens() {
		return numberOfScreens;
	}

	public void setNumberOfScreens(int numberOfScreens) {
		this.numberOfScreens = numberOfScreens;
	}

	public Screen[] getScreens() {
		return screens;
	}

	public void setScreens(Screen[] screens) {
		this.screens = screens;
	}
}
