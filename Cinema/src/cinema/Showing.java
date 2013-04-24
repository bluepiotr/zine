package cinema;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * This class is responsible for all the operations related to showings in the cinema 
 * e.g. add showings, delete showings, validation etc
 * @author Raul Gomez,  Student ID 12837452, MSc Informatics
 *
 */
public class Showing implements Serializable{

	private static final long serialVersionUID = 8141050188382124407L;
	private Cinema cinema;
	private Screen screen;
	private Movie movie;
	private Date slot;
	private static Scanner sc = new Scanner(System.in);
	private String startDateRange;
	private String endDateRange;
	private int UniqueShowingId;
	private static String[] slotArray = {"9am", "12pm", "3pm", "6pm", "9pm"};

	/**
	 * one arg constructor
	 * @param slot
	 */
	public Showing(Date slot){
		this.slot = slot;
	}    

	/**
	 * no-arg constructor
	 */
	public Showing(){
		sc = new Scanner(System.in); 
	}

	/**
	 * four arg constructor
	 * @param cinema
	 * @param screen
	 * @param movie
	 * @param slot
	 */
	public Showing(Cinema cinema, Screen screen, Movie movie, Date slot){

		this.cinema = cinema;
		this.screen = screen;
		this.movie = movie;
		SimpleDateFormat ft = new SimpleDateFormat ("E dd MMMM yy, hh:mm a");
		this.slot = slot;
		ft.format(this.slot);

	}

	/**
	 * This method adds showing to the application and invokes methods for different validations 
	 * @param BookingSystem sys
	 * @return
	 */
	public static BookingSystem addShowing(BookingSystem sys){

		Showing showing = new Showing();
		Screen scr = new Screen();
		Showing sh = new Showing();
		String scheduledSlot;
		String scheduledDate;
		int screenNumber;
		DateTime d = new DateTime();  
		System.out.println("Enter screen number ");
		screenNumber = scr.inputCheckScreenNumber(sys);
		scr.setScreenNumber(screenNumber);
		for(int i=0;i<sys.getScr().size();i++){
			if(sys.getScr().get(i).getScreenNumber()==screenNumber){
				scr.setCapacity(sys.getScr().get(i).getCapacity());
			}
		}
		sys = DateTime.isRangeDatePresent(sys);
		scheduledDate = DateTime.selectValidateDate(sys);
		scheduledSlot = DateTime.inputValidateScheduledSlot(sys);
		if(isSlotAvailable(sys,scheduledDate,screenNumber,scheduledSlot)) {	
			System.out.println(" Slot is available ");
			Movie.displayMovieId(sys);
			Movie mov = Movie.inputDisplayMovieTitle(sys);			
			d.setShowingDate(scheduledDate);
			d.setSlot(scheduledSlot);			
			scr.setScheduledDateTime(d);
			sh.setScreen(scr);			
			sh.setMovie(mov);
			sh.setUniqueShowingId(generateUniqueShowingId(sys));
			sys.getShowings().add(sh);	
			for (int i=0;i>sys.getShowings().size(); i++){
				System.out.println("showing id " + sys.getShowings().get(i).getUniqueShowingId());
				System.out.println("Movie " + sys.getShowings().get(i).getMovie());
			}
			System.out.println("Showing id  " + + sh.UniqueShowingId + " has been added on screen " + screenNumber 
					+ " on " + scheduledDate + " at " + scheduledSlot);		
		}
		else{
			System.out.println("Slot " + scheduledSlot + " on screen " + screenNumber + " on " + scheduledDate + " is unavailable.");
			addShowing(sys);
		}
		return sys;
	}

	/**
	 * This method validates if the showing id is present in the application or not
	 * @param BookingSystem sys
	 * @param Person p
	 * @return Showing
	 */
	public static Showing inputValidateShowing(BookingSystem sys, Person p){

		int sid;
		boolean isValidShowingId=false;
		do{
			System.out.println("Enter the showing Id from above");
			sid = Utilities.hasInt();
			for(int i=0;i<sys.getShowings().size();i++){
				if(sid==sys.getShowings().get(i).getUniqueShowingId()){
					isValidShowingId = true;
					p.getListShowings().add(sys.getShowings().get(i));
					return sys.getShowings().get(i);
				}
			}
			if(!isValidShowingId){
				System.out.println(" Showing id is invalid. Try Again");
			}
		}while(!isValidShowingId);
		return null;
	}

	/**
	 * This method deletes showing from the application
	 * @param BookingSystem sys
	 */
	public static boolean deleteShowing(BookingSystem sys){

		sc = new Scanner(System.in);
		boolean isShowingPresent = false;
		int deletedShowingId;
		boolean isBookingOnShowing = false;
		
		do{
			Schedule.displayMovieSchedule(sys, true, slotArray);   
			System.out.println("Enter the showing Id to be deleted from above");
			deletedShowingId = Utilities.hasInt();
			for(int j=0;j<sys.getUsers().size();j++){
				if(sys.getUsers().get(j).getListBooking()!=null){
					for(int k=0;k<sys.getUsers().get(j).getListBooking().size();k++){
						if(sys.getUsers().get(j).getListBooking().get(k).getShow().getUniqueShowingId()==deletedShowingId){
							isBookingOnShowing = true;
							break;
						}
					}
				}

			}
			if(!isBookingOnShowing){
				for(int i=0;i<sys.getShowings().size();i++){
					if(deletedShowingId == sys.getShowings().get(i).getUniqueShowingId()){
						isShowingPresent = true;
						System.out.println("Showing Id "+sys.getShowings().get(i).getUniqueShowingId()+ " removed successfully.");
						sys.getShowings().remove(i);
						Utilities.save(sys);
					}
				}
			}
			else{
				System.out.println("Showing "+deletedShowingId+" can not be deleted as there are booking(s) on this showing");
			}
			String contQ = "Do you want to remove more showings? (Y/N) ";
			String contA = Utilities.yesOrNoReply(contQ); 
			if(contA.equalsIgnoreCase("y")){
				deleteShowing(sys);
			}
			
			if(!isShowingPresent){
				System.out.println("Showing Id Incorrect. Try again");
			}
		}while(!isShowingPresent);	
		return true;

	}

	/**
	 * This method generates unique showing id when a showing is created
	 * @param BookingSystem sys
	 * @return int
	 */
	public static int generateUniqueShowingId(BookingSystem sys){    
		if (sys.getShowings().size()==0){
			return 1;
		}
		else {
			return (sys.getShowings().get(sys.getShowings().size()-1).getUniqueShowingId()+1);   		
		}
	}

	/**
	 * This method checks whether slot is available on a 
	 * particular day when manager wants to add a showing
	 * @param BookingSystem sys
	 * @param String schDate
	 * @param int screenNumber
	 * @param String schSlot
	 * @return
	 */
	public static boolean isSlotAvailable(BookingSystem sys, String schDate, int screenNumber, String schSlot){

		if(sys.getShowings().size()==0){
			System.out.println("No showings have yet been added ");
			return true;
		}
		else{
			for(int i=0;i<sys.getShowings().size();i++){
				if(sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(schDate)){
					if(sys.getShowings().get(i).getScreen().getScreenNumber()==screenNumber){
						if(sys.getShowings().get(i).getScreen().getScheduledDateTime().getSlot().equals(schSlot))
							return false;
					}
				}
			}
		}		
		return true;
	}


	/**
	 * getters and setters
	 * @return
	 */
	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Date getSlot() {
		return slot;
	}

	public void setSlot(Date slot) {
		this.slot = slot;
	}

	public String getStartDateRange() {
		return startDateRange;
	}

	public void setStartDateRange(String startDateRange) {
		this.startDateRange = startDateRange;
	}

	public String getEndDateRange() {
		return endDateRange;
	}

	public void setEndDateRange(String endDateRange) {
		this.endDateRange = endDateRange;
	}

	public int getUniqueShowingId() {
		return UniqueShowingId;
	}

	public void setUniqueShowingId(int uniqueShowingId) {
		UniqueShowingId = uniqueShowingId;
	}

}