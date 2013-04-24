package cinema;
import java.io.Serializable;
 
/**
 * This class is responsible for displaying information related to movie schedule
 * @author Raul Gomez,  Student ID 12837452, MSc Informatics
 *
 */
public class Schedule implements Serializable {
	
	private static final long serialVersionUID = 7300796292261743853L;
	private static String DAY1;
	private static String DAY2;
	private static String DAY3;
	private static String DAY4;
	private static String DAY5;
	private static String DAY6;
	private static String DAY7;

	/**
	 * This method displays movie schedule information like date, slot, screen number, movie, movie running time etc
	 * @param BookingSystem sys
	 * @param int i
	 * @param String[] slotArray
	 */
	public static void displaySchedule(BookingSystem sys, int i, String[] slotArray){
		for(int j=0;j<sys.getScr().size();j++){
			if(sys.getScr().get(j).getScreenNumber() == sys.getShowings().get(i).getScreen().getScreenNumber()){
				for(int l=0;l<sys.getMovies().size();l++){
					if(sys.getMovies().get(l).getTitle()==sys.getShowings().get(i).getMovie().getTitle()){
						for(int k=0;k<slotArray.length;k++){
							if(sys.getShowings().get(i).getScreen().getScheduledDateTime().getSlot().equals(slotArray[k])){
								System.out.println("Date: "+sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate());
								System.out.println(" Slot: "+sys.getShowings().get(i).getScreen().getScheduledDateTime().getSlot());
								System.out.println("Screen Number :"+sys.getShowings().get(i).getScreen().getScreenNumber());
								System.out.println("Movie: "+sys.getShowings().get(i).getMovie().getTitle());
								System.out.println("Movie Running Time: "+sys.getShowings().get(i).getMovie().getRunningTime());
								System.out.println("Movie Rating: "+sys.getShowings().get(i).getMovie().getYourRating());
								System.out.println("Showing Id: "+sys.getShowings().get(i).getUniqueShowingId());
								System.out.println("------------------------------------");
							}
						}
					}
				}
			}
		}
	}
	
	/*
	public static boolean displayMovieSchedule(BookingSystem sys, boolean scheduleDisplayParameter, String[] slotArray){
		if(sys!=null && sys.getShowing()!=null){
			DAY1 = sys.getShowing().getStartDateRange();
			DAY2 = Utilities.dayIncrementer(DAY1);
			DAY3 = Utilities.dayIncrementer(DAY2);
			DAY4 = Utilities.dayIncrementer(DAY3);
			DAY5 = Utilities.dayIncrementer(DAY4);
			DAY6 = Utilities.dayIncrementer(DAY5);
			DAY7 = sys.getShowing().getEndDateRange();
			for(int i=0;i<sys.getShowings().size();i++){
			
				if((Utilities.isDateInFuture(DAY1) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY1)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
				else if((Utilities.isDateInFuture(DAY2) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY2)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
				else if((Utilities.isDateInFuture(DAY3) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY3)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
				else if((Utilities.isDateInFuture(DAY4) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY4)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
				else if((Utilities.isDateInFuture(DAY5) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY5)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
				else if((Utilities.isDateInFuture(DAY6) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY6)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
				else if((Utilities.isDateInFuture(DAY7) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY7)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
			}
		}
		else {
			System.out.println("No movies have yet been scheduled ");
		}
		return true;
	}  
}
	
	*/
	
	/**
	 * This method displays complete movie schedule
	 * @param BookingSystem sys
	 * @param boolean scheduleDisplayParameter
	 * @param String[] slotArray
	 */	
	public static boolean displayMovieSchedule(BookingSystem sys, boolean scheduleDisplayParameter, String[] slotArray){
		if(sys!=null && sys.getShowing()!=null){
			DAY1 = sys.getShowing().getStartDateRange();
			DAY2 = Utilities.dayIncrementer(DAY1);
			DAY3 = Utilities.dayIncrementer(DAY2);
			DAY4 = Utilities.dayIncrementer(DAY3);
			DAY5 = Utilities.dayIncrementer(DAY4);
			DAY6 = Utilities.dayIncrementer(DAY5);
			DAY7 = sys.getShowing().getEndDateRange();
			for(int i=0;i<sys.getShowings().size();i++){
				if((Utilities.isDateInFuture(DAY1) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY1)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
				else if((Utilities.isDateInFuture(DAY2) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY2)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
				else if((Utilities.isDateInFuture(DAY3) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY3)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
				else if((Utilities.isDateInFuture(DAY4) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY4)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
				else if((Utilities.isDateInFuture(DAY5) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY5)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
				else if((Utilities.isDateInFuture(DAY6) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY6)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
				else if((Utilities.isDateInFuture(DAY7) || scheduleDisplayParameter) && sys.getShowings().get(i).getScreen().getScheduledDateTime().getShowingDate().equals(DAY7)){
					Schedule.displaySchedule(sys, i, slotArray);
				}
			}
		}
		else {
			System.out.println("No movies have yet been scheduled ");
		}
		return true;
	}  
}