package cinema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for all the operations related to movie
 * e.g. addition, deletion, movie booking, display information, unique movie id generation etc
 * @author Raul Gomez,  Student ID 12837452, MSc Informatics
 *
 */
public class Movie implements Serializable{

	private static final long serialVersionUID = 8141050188382124407L;
	private static Scanner sc = new Scanner(System.in);
	private int movieId;
	private String title;
	private int releaseYear;
	private String runningTime;
	private String genre;
	private String yourRating;
	private String synopsis;
	private String director;
	private String stars;
	private String trailerUrl;
	private Cinema cinema;
	private Screen screen;
	private ArrayList<Movie> movies;
	private ArrayList<Movie> listMovie;
	private ArrayList<Screen> listScreen;
	private ArrayList<Booking> listBooking;
	private Movie mov;
	private final static double TICKETPRICE = 8.00;
	private static String[] slotArray = {"9am", "12pm", "3pm", "6pm", "9pm"};
	private int userNumber;
	private int screenNumber;
	private Booking book;
	private Screen scr;
	private String cardNum;
	private String cardType;
	private String cardExpDate;
	private int countTicket;


	/**
	 * no-arguments constructor
	 */
	public Movie () {

		movies = new ArrayList<Movie>();
		listMovie = new ArrayList<Movie>();
		listBooking = new ArrayList<Booking>();
		listScreen = new ArrayList<Screen>();
	}

	/**
	 * this method adds movie in the application and invokes 
	 * method to ensure that the same movie is not added in the application twice
	 * @param BookingSystem sys
	 * @return void
	 */
	public static boolean addMovie (BookingSystem sys) {	

		boolean allMoviesCreated = false; 
		int uniqueMovieId;
		String title;
		int releaseYear;
		String runningTime;
		String genre;
		String yourRating;
		String synopsis;
		String director;
		String stars;
		String trailerUrl;	
		do{
			allMoviesCreated = false;
			displayMovieInformation(sys);
			System.out.println("Enter following data ");
			System.out.println("Movie title");			
			title = inputValidateMovieTitle(sys);	
			System.out.println("Release Year");
			releaseYear = Utilities.hasInt();
			System.out.println("Running Time");		
			runningTime = Utilities.captureCompleteLine();
			System.out.println("Genre");
			genre = Utilities.captureCompleteLine();
			System.out.println("Your Rating (e.g. 8/10)");
			yourRating = Utilities.captureCompleteLine();
			System.out.println("Synopsis");
			synopsis = Utilities.captureCompleteLine();
			System.out.println("Director");
			director = Utilities.captureCompleteLine();
			System.out.println("Stars");
			stars = Utilities.captureCompleteLine();
			System.out.println("Trailer URL");
			trailerUrl = Utilities.captureCompleteLine();
			Movie mov = new Movie();
			uniqueMovieId = generateMovieId(sys);
			mov.setMovieId(uniqueMovieId);
			mov.setTitle(title);
			mov.setReleaseYear(releaseYear);
			mov.setRunningTime(runningTime);
			mov.setGenre(genre);
			mov.setYourRating(yourRating);
			mov.setSynopsis(synopsis);
			mov.setDirector(director);
			mov.setStars(stars);
			mov.setTrailerUrl(trailerUrl);		
			sys.getMovies().add(mov);		
			Utilities.save(sys);
			System.out.println("Movie  "+ title + " added successfully");
			String addNewMovie = "Would you like to add a new movie? Enter Y or N";
			String reply = Utilities.yesOrNoReply(addNewMovie);
			if(reply.equalsIgnoreCase("N")){
				allMoviesCreated = true;
			}
		}while(!allMoviesCreated);
		return true;
	}

	/**
	 * This method inputs and validate that the
	 * same movie is not added in the system again
	 * @param BookingSystem sys
	 * @return String movieTitle
	 */
	public static String inputValidateMovieTitle(BookingSystem sys) {

		boolean movieTitleExists = false;		
		String movieTitle;
		do{				
			movieTitleExists = false;			
			movieTitle = Utilities.captureCompleteLine();
			for(int i=0;i<sys.getMovies().size();i++){			
				if(movieTitle.equalsIgnoreCase(sys.getMovies().get(i).getTitle())){
					movieTitleExists = true;
					System.out.println("This Movie Title already exists. Please select a different one");
					break;
				}
			}
		}while(movieTitleExists);
		return movieTitle;
	}

	/**
	 * This method generated unique Id for every movie added in the system
	 * @param BookingSystem sys
	 * @return int
	 */
	public static int generateMovieId(BookingSystem sys){
		if (sys.getMovies().size()==0){
			return 1;
		}
		else {
			return(sys.getMovies().get(sys.getMovies().size()-1).getMovieId()+1);
		}
	}

	/**
	 * This method displays all the movie information available in the application
	 * @param BookingSystem sys
	 */
	public static void displayMovieInformation(BookingSystem sys){

		if(sys.getMovies()==null || sys.getMovies().size()==0){
			System.out.println("No movie information has yet been added ");
		}
		else{		
			System.out.println("Movie database ");
			System.out.println("-------------------------");
			for(int i=0;i<sys.getMovies().size();i++){
				System.out.println("\t Id "+sys.getMovies().get(i).getMovieId());
				System.out.println("\t Title "+sys.getMovies().get(i).getTitle());
				System.out.println("\t Release Year "+sys.getMovies().get(i).getReleaseYear());
				System.out.println("\t Running Time "+sys.getMovies().get(i).getRunningTime());
				System.out.println("\t Genre "+sys.getMovies().get(i).getGenre());
				System.out.println("\t Your Rating "+sys.getMovies().get(i).getYourRating());
				System.out.println("\t Synopsis "+sys.getMovies().get(i).getSynopsis());
				System.out.println("\t Director "+sys.getMovies().get(i).getDirector());
				System.out.println("\t Stars "+sys.getMovies().get(i).getStars());
				System.out.println("\t Trailer URL "+sys.getMovies().get(i).getTrailerUrl());	
				System.out.println("-------------------------");
			}
		}
	}

	/**
	 * This method inputs movie title and validates if the movie exists or not
	 * @param BookingSystem sys
	 * @return String movieSelection
	 */
	public static String captureMovieSelection(BookingSystem sys){

		String movieSelection;
		boolean matchedMovieTitles;

		do {
			matchedMovieTitles = false; 
			System.out.println("Please insert the name of the Movie of your choice");
			movieSelection = sc.nextLine();

			for(int i=0;i<sys.getMovies().size();i++){    		
				if(movieSelection.equals(sys.getMovies().get(i).getTitle())) {    			
					matchedMovieTitles = true;
				}  
			}  
			if(!matchedMovieTitles){
				System.out.println("Wrong movie selection. Try again");
			}

		} while(!matchedMovieTitles);
		return movieSelection;

	}

	/**
	 * This method takes the following parameters and invokes method to book movie
	 * and saves the booking details against the user information
	 * @param BookingSystem sys
	 * @param String login
	 * @param String pw
	 * @return void
	 */
	public static boolean bookMovie(BookingSystem sys, String login, String pw){

		Movie m = new Movie();
		for(int i=0;i<sys.getUsers().size();i++){
			if((login.equals(sys.getUsers().get(i).getLogin()))
					&& (pw.equals(sys.getUsers().get(i).getPassword()))){
				Person p = sys.getUsers().get(i);
				p = m.bookMovie(login, sys, p);
				sys.getUsers().set(i, p);
				Utilities.save(sys);
				System.out.println("Movie booking successful ");
			}
		}	
		return true;
	}

	/**
	 * This method does the movie booking, invoking methods to validate
	 * screen capacity and capturing payment details
	 * @param String login
	 * @param BookingSystem sys
	 * @param Person p
	 * @return Person p
	 */
	public Person bookMovie(String login, BookingSystem sys, Person p){

		mov = new Movie();
		book = new Booking();
		scr = new Screen();
		int bookingId;
		Schedule.displayMovieSchedule(sys, false, slotArray);
		Showing sh = Showing.inputValidateShowing(sys,p);
		int countTicket = scr.inputValidateCapacity(sh.getScreen().getCapacity());
		book.setNumber(countTicket);
		bookingId = book.generateBookingId(sys, p);
		book.setBookingId(bookingId);
		book.setShow(sh);		
		p.getListBooking().add(book);	
		p = p.addPaymentDetails(p);
		System.out.println("Payment details ");		
		double chargedAmount = countTicket*TICKETPRICE;
		System.out.printf("Thank you for your payment. The amount of GBP %8.2f", chargedAmount);
		System.out.println(" has been debited from your card");
		scr.updateScreenCapacity(sys, sh.getScreen().getScreenNumber(),countTicket,sh);
		System.out.println("Your booking Id is: "+bookingId+". Please keep it for reference in case you wish to cancel/update booking ");
		return p;
	}

	
	/**
	 * this method updates movie attributes like Title, Running Time, etc
	 * @param BookingSystem sys
	 */
	public static void updateMovieData (BookingSystem sys) {	

		int movId;
		String updateValue;
		boolean isMoviePresent = false;
		boolean isUpdateMore = false;
		movId = displayMovieNameId(sys);		

		do{
			for(int i=0;i<sys.getMovies().size();i++){
				if(movId == sys.getMovies().get(i).getMovieId()){
					isMoviePresent = true;
					System.out.println("What Information would you like to update?");
					System.out.println("\t Type T to update Title ");
					System.out.println("\t Type RY to update Release Year ");
					System.out.println("\t Type RT to update Running Time ");
					System.out.println("\t Type G to update Genre ");
					System.out.println("\t Type YR to update Your Rating ");
					System.out.println("\t Type SY to update Synopsis ");
					System.out.println("\t Type D to update Director ");
					System.out.println("\t Type ST to update Stars ");
					System.out.println("\t Type TU to update Trailer URL ");
					System.out.print("\t>>");
					updateValue = sc.next();

					if (updateValue.equalsIgnoreCase("T")){
						String newTitle;
						System.out.println("Current Title " + sys.getMovies().get(i).getTitle() );
						System.out.println("Please add a new Title");
						newTitle = inputValidateMovieTitle(sys);						
						sys.getMovies().get(i).setTitle(newTitle);
						System.out.println("Title has been updated to " + sys.getMovies().get(i).getTitle());
					}
					else if (updateValue.equalsIgnoreCase("RY")) {   
						int newReleaseYear;
						System.out.println("Current Release Year " + sys.getMovies().get(i).getReleaseYear() );
						System.out.println("Please add a new Release Year");
						newReleaseYear = sc.nextInt();
						sys.getMovies().get(i).setReleaseYear(newReleaseYear);
						System.out.println("Release Year has been updated to " + sys.getMovies().get(i).getReleaseYear());
					}
					else if (updateValue.equalsIgnoreCase("RT")) {   
						String newRunningTime;
						System.out.println("Current Running Time " + sys.getMovies().get(i).getRunningTime());
						System.out.println("Please add a new Release Year");
						newRunningTime = sc.next();
						sys.getMovies().get(i).setRunningTime(newRunningTime);
						System.out.println("Running Time has been updated to " + sys.getMovies().get(i).getRunningTime());
					}
					else if (updateValue.equalsIgnoreCase("G")) {   
						String newGenre;
						System.out.println("Current Genre " + sys.getMovies().get(i).getGenre() );
						System.out.println("Please add a new Genre");
						newGenre = sc.next();
						sys.getMovies().get(i).setGenre(newGenre);
						System.out.println("Genre has been updated to " + sys.getMovies().get(i).getGenre() );
					}
					else if (updateValue.equalsIgnoreCase("YR")) {   
						String newYourRating;
						System.out.println("Current Rating " + sys.getMovies().get(i).getYourRating() );
						System.out.println("Please add a new Rating");
						newYourRating = sc.next();
						sys.getMovies().get(i).setYourRating(newYourRating);
						System.out.println("Your Rating has been updated to " + sys.getMovies().get(i).getYourRating() );
					}
					else if (updateValue.equalsIgnoreCase("SY")) {   
						String newSynopsis;
						System.out.println("Current Synopsis " + sys.getMovies().get(i).getSynopsis() );
						System.out.println("Please add a new Synopsis");
						newSynopsis = sc.next();
						sys.getMovies().get(i).setSynopsis(newSynopsis);
						System.out.println("Synopsis has been updated to " + sys.getMovies().get(i).getSynopsis() );

					}
					else if (updateValue.equalsIgnoreCase("D")) {   
						String newDirector;
						System.out.println("Current Director " + sys.getMovies().get(i).getDirector() );
						System.out.println("Please add a new Director");
						newDirector = sc.next();
						sys.getMovies().get(i).setDirector(newDirector);
						System.out.println("Director has been updated to " + sys.getMovies().get(i).getDirector() );
					}
					else if (updateValue.equalsIgnoreCase("ST")) {   
						String newStars;
						System.out.println("Current Stars " + sys.getMovies().get(i).getStars() );
						System.out.println("Please add new Stars");
						newStars = sc.next();
						sys.getMovies().get(i).setStars(newStars);
						System.out.println("Stars have been updated to " + sys.getMovies().get(i).getStars() );
					}
					else if (updateValue.equalsIgnoreCase("TU")) {  
						System.out.println("Current URL " + sys.getMovies().get(i).getTrailerUrl() );
						String newTrailerUrl;
						System.out.println("Please add new Trailer URL");
						newTrailerUrl = sc.next();
						sys.getMovies().get(i).setTrailerUrl(newTrailerUrl);
						System.out.println("URL has been updated to " + sys.getMovies().get(i).getTrailerUrl() );
					}
					else {
						System.out.println("Invalid reply");					
					}

					Utilities.save(sys);
					String updateMoreDataQ = "Would you like to continue updating Movie Information? Enter Y or N";
					String updateMoreDataAns  = Utilities.yesOrNoReply(updateMoreDataQ);

					if(updateMoreDataAns.equalsIgnoreCase("Y")){
						isUpdateMore = true;
					}
					else{System.out.println("Update complete ");
					return; 
					}	
				}
			}
		}while(isUpdateMore);

		if(!isMoviePresent){
			String contQ = "Incorrect movie entered. Try Again (Y/N) ";
			String contA = Utilities.yesOrNoReply(contQ);			
			if(contA.equalsIgnoreCase("Y")){
				Movie.updateMovieData(sys);
			}
			else{
				System.out.println("returning");
				return;
			}
		}
	}
	
	
	/**
	 * This method deletes a movie from the application
	 * @param BookingSystem sys
	 */	
	public static boolean deleteMovie(BookingSystem  sys){

		int deletedMovieId;
		boolean doesMovieExist = false;
		boolean hasShowingsScheduled = false;

		if(sys.getMovies().size()!=0){	
			Movie.displayMovieId(sys);
				
			System.out.println("Enter the Movie Id to be deleted from above");   
			deletedMovieId = Utilities.hasInt();               

			for(int i=0;i<sys.getMovies().size(); i++){
				if (deletedMovieId == sys.getMovies().get(i).getMovieId()){
					doesMovieExist = true;
					for (int j=0;j<sys.getShowings().size(); j++){
						if(deletedMovieId == sys.getShowings().get(j).getMovie().getMovieId()){
							hasShowingsScheduled = true;	
						}
					}
					if(hasShowingsScheduled){
						System.out.println("Movie has showings scheduled and cannot be deleted");
					}
				}
			}

			if(!doesMovieExist){System.out.println("Movie selected does not exist");
			deleteMovie(sys);}
			if(!hasShowingsScheduled){
				for(int i=0;i<sys.getMovies().size(); i++){
					if (deletedMovieId == sys.getMovies().get(i).getMovieId()){

						String confirmDeletionQ = "Please confirm you want to delete this Movie. (Y/N)";
						String confirmDeletionA = Utilities.yesOrNoReply(confirmDeletionQ);						
						if(confirmDeletionA.equalsIgnoreCase("Y")){

							System.out.println("Movie "+ deletedMovieId + " deleted");
							sys.getMovies().remove(i);
							Utilities.save(sys);
						}
					}
				}
			}
		}
		return true;
	}

	
	/**
	 * This method displays movie name along with its Id, for user to select a specific id
	 * @param BookingSystem sys
	 * @return int mId
	 */
	public static int displayMovieNameId(BookingSystem sys){
		int mId;
		if(sys.getMovies().size()==0){
			System.out.println("No movies have yet been created");
			Utilities.returnOption(sys);
		}
		else{
			System.out.println("Movies and Ids");
			for(int i=0;i<sys.getMovies().size();i++){
				System.out.println("Movie name: "+sys.getMovies().get(i).getTitle()+ " Id: "+sys.getMovies().get(i).getMovieId());			
			}
		}
		System.out.print("Enter movie Id you would like to update ");
		mId = Utilities.hasInt();

		return mId;
	}

	/**
	 * This method displays Movie Id, title and running time, with no user input required
	 * and running time of all the movies in the system
	 * @param BookingSystem sys
	 * @return boolean
	 */
	public static boolean displayMovieId(BookingSystem sys){
		System.out.println("Movies ");
		for(int i=0;i<sys.getMovies().size();i++){
			System.out.println("\t Id "+sys.getMovies().get(i).getMovieId());
			System.out.println("\t Title "+sys.getMovies().get(i).getTitle());			
			System.out.println("\t Running Time "+sys.getMovies().get(i).getRunningTime());
		}
		return true;    	
	}

	/**
	 * This method inputs movie Id and validates if this movie exists or not
	 * @param BookingSystem sys
	 * @return Movie mov
	 */
	public static Movie inputDisplayMovieTitle(BookingSystem sys){
		Movie mov = null;
		boolean uniqueMovieIdFound;
		boolean isNumeric;
		String uniqueMovId;
		int uniqueMovieId;

		do{
			uniqueMovieIdFound = false;
			do{ 
				isNumeric = true;
				System.out.println("Enter the movie ID you want to schedule ");
				uniqueMovId = sc.next();
				if(!Utilities.isNumeric(uniqueMovId)){
					System.out.println("Input should be a number");
					isNumeric = false;
				}
			}while(!isNumeric);
			uniqueMovieId = Integer.parseInt(uniqueMovId);

			for(int i=0;i<sys.getMovies().size();i++){
				if(uniqueMovieId == sys.getMovies().get(i).getMovieId()){
					mov = sys.getMovies().get(i);
					uniqueMovieIdFound = true;
					System.out.println("You have selected movie "+ sys.getMovies().get(i).getTitle());
					break;
				} 
			} if(!uniqueMovieIdFound){
				System.out.println("Movie id not found, try again");}			
		}while (!uniqueMovieIdFound);	
		return mov;		
	}

	/**
	 * getters and setters
	 * @return
	 */

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getYourRating() {
		return yourRating;
	}

	public void setYourRating(String yourRating) {
		this.yourRating = yourRating;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	public String getTrailerUrl() {
		return trailerUrl;
	}

	public void setTrailerUrl(String trailerUrl) {
		this.trailerUrl = trailerUrl;
	}

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

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}

	public ArrayList<Movie> getListMovie() {
		return listMovie;
	}

	public void setListMovie(ArrayList<Movie> listMovie) {
		this.listMovie = listMovie;
	}

	public ArrayList<Screen> getListScreen() {
		return listScreen;
	}

	public void setListScreen(ArrayList<Screen> listScreen) {
		this.listScreen = listScreen;
	}

	public ArrayList<Booking> getListBooking() {
		return listBooking;
	}

	public void setListBooking(ArrayList<Booking> listBooking) {
		this.listBooking = listBooking;
	}

	public Movie getMov() {
		return mov;
	}

	public void setMov(Movie mov) {
		this.mov = mov;
	}

	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	public int getScreenNumber() {
		return screenNumber;
	}

	public void setScreenNumber(int screenNumber) {
		this.screenNumber = screenNumber;
	}

	public Booking getBook() {
		return book;
	}

	public void setBook(Booking book) {
		this.book = book;
	}

	public Screen getScr() {
		return scr;
	}

	public void setScr(Screen scr) {
		this.scr = scr;
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

	public int getCountTicket() {
		return countTicket;
	}

	public void setCountTicket(int countTicket) {
		this.countTicket = countTicket;
	}
}