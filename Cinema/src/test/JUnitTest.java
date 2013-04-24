package test;

import junit.framework.TestCase;
import cinema.BookingSystem;
import cinema.Movie;
import cinema.Person;
import cinema.Schedule;
import cinema.Screen;
import cinema.Showing;
import cinema.Utilities;

public class JUnitTest extends TestCase {
	
	/**
	 * JUnit positive scenario to add a screen 
	 */
	public void testAddScreen(){
		BookingSystem sys  = Utilities.initialise();
		assertEquals(true, Screen.addScreen(sys));
	}
	
	/**
	 * JUnit positive scenario to add movie
	 */
	public void testAddMovie(){
		BookingSystem sys  = Utilities.initialise();
		assertEquals(true, Movie.addMovie(sys));
	}
	
	/**
	 * JUnit positive scenario to add showing
	 */
	public void testAddShowing(){
		BookingSystem sys  = Utilities.initialise();
		sys = Showing.addShowing(sys);
		Utilities.save(sys);
		assertEquals(true, sys.getShowings().size()>0);
	}
	
	/**
	 * JUnit positive scenario to add customer
	 */
	public void testAddCustomer(){
		BookingSystem sys  = Utilities.initialise();
		Person p = new Person();
		assertEquals(true, p.addCustomer(p, sys));
	}
	
	/**
	 * JUnit positive scenario to book movie
	 */
	public void testBookMovie(){
		BookingSystem sys  = Utilities.initialise();
		Person p = new Person();
		assertEquals(true, Movie.bookMovie(sys,"customer1", "customer1"));
	}
	
	
	/**
	 * JUnit positive scenario to display movie schedule information 
	 */
	public void testDisplayMovieSchedule(){
		String[] slotArray = {"9am", "12pm", "3pm", "6pm", "9pm"};
		BookingSystem sys  = Utilities.initialise();
		assertEquals(true, Schedule.displayMovieSchedule(sys, true, slotArray));
	}
	
	/**
	 * JUnit positive scenario to validate login 
	 */
	public void testLoginScreen(){
		BookingSystem sys  = Utilities.initialise();
		assertEquals(true, BookingSystem.loginScreen(sys));	
		Utilities.save(sys);
	}	
}

