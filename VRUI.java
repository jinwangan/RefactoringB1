import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VRUI {
	private static Scanner scanner = new Scanner(System.in) ;

	private List<ConcreteCustomer> customers = new ArrayList<ConcreteCustomer>() ;

	private List<Video> videos = new ArrayList<Video>() ;

	public static void main(String[] args) {
		VRUI ui = new VRUI() ;

		boolean quit = false ;
		while ( ! quit ) {
			int command = ui.showCommand() ;
			switch ( command ) {
				case 0: quit = true ; break ;
				case 1: ui.listCustomers() ; break ;
				case 2: ui.listVideos() ; break ;
				case 3: ui.registerCustomer() ; break ;
				case 4: ui.registerVideo() ; break ;
				case 5: ui.rentVideo() ; break ;
				case 6: ui.returnVideo() ; break ;
				case 7: ui.getCustomerReport() ; break;
				case 8: ui.clearRentals() ; break ;
				case -1: ui.init() ; break ;
				default: break ;
			}
		}
		System.out.println("Bye");
	}

	public void printEnterCustomerName() {
		System.out.println("Enter customer name: ") ;
	}
	
	public void clearRentals() {
		printEnterCustomerName();
		String customerName = scanner.next() ;

		ConcreteCustomer foundCustomer = findCustomer(customerName);

		// query
		foundCustomer.printCustomerInfo();
		for ( Rental rental: foundCustomer.getRentals() ) {
			System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
			System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
		}
		// modifier
		List<Rental> rentals = new ArrayList<Rental>() ;
		foundCustomer.setRentals(rentals);
	}



	public void returnVideo() {
		printEnterCustomerName();
		String customerName = scanner.next() ;

		ConcreteCustomer foundCustomer = findCustomer(customerName);
		if ( foundCustomer == null ) return ;

		System.out.println("Enter video title to return: ") ;
		String videoTitle = scanner.next() ;

		List<Rental> customerRentals = foundCustomer.getRentals() ;
		for ( Rental rental: customerRentals ) {
			if ( rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented() ) {
				rental.returnVideo();
				rental.getVideo().setRented(false);
				break ;
			}
		}
	}

	private ConcreteCustomer findCustomer(String customerName) {
		for ( ConcreteCustomer customer: customers ) {
			if ( customer.getName().equals(customerName)) {
				return customer ;
			}
		}
		return new NullCustomer(customerName);
	}

	private void init() {
		ConcreteCustomer james = new ConcreteCustomer("James") ;
		ConcreteCustomer brown = new ConcreteCustomer("Brown") ;
		customers.add(james) ;
		customers.add(brown) ;

		Video v1 = new Video("v1", Video.CD, Video.REGULAR, new Date()) ;
		Video v2 = new Video("v2", Video.DVD, Video.NEW_RELEASE, new Date()) ;
		videos.add(v1) ;
		videos.add(v2) ;

		Rental r1 = new Rental(v1) ;
		Rental r2 = new Rental(v2) ;

		james.addRental(r1) ;
		james.addRental(r2) ;
	}

	public void listVideos() {
		System.out.println("List of videos");

		for ( Video video: videos ) {
			System.out.println("Price code: " + video.getPriceCode() +"\tTitle: " + video.getTitle()) ;
		}
		System.out.println("End of list");
	}

	public void listCustomers() {
		System.out.println("List of customers");
		for ( ConcreteCustomer customer: customers ) {
			System.out.println("Name: " + customer.getName() +
					"\tRentals: " + customer.getRentals().size()) ;
			for ( Rental rental: customer.getRentals() ) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
			}
		}
		System.out.println("End of list");
	}

	public void getCustomerReport() {
		printEnterCustomerName();
		String customerName = scanner.next() ;

		ConcreteCustomer foundCustomer = findCustomer(customerName);

		if ( foundCustomer == null ) {
			System.out.println("No customer found") ;
		} else {
			String result = foundCustomer.getReport() ;
			System.out.println(result);
		}
	}

	public void rentVideo() {
		printEnterCustomerName();
		String customerName = scanner.next() ;

		ConcreteCustomer foundCustomer = findCustomer(customerName);

		if ( foundCustomer == null ) return ;

		System.out.println("Enter video title to rent: ") ;
		String videoTitle = scanner.next() ;

		Video foundVideo = null ;
		for ( Video video: videos ) {
			if ( video.getTitle().equals(videoTitle) && video.isRented() == false ) {
				foundVideo = video ;
				break ;
			}
		}

		if ( foundVideo == null ) return ;

		Rental rental = new Rental(foundVideo) ;
		foundVideo.setRented(true);

		// encapulate collection
		List<Rental> customerRentals = foundCustomer.getRentals() ;
		customerRentals.add(rental);
		foundCustomer.setRentals(customerRentals);
	}

	private void registerCustomer() {
		printEnterCustomerName();
		String name = scanner.next();
		ConcreteCustomer customer = new ConcreteCustomer(name) ;
		customers.add(customer) ;
	}

	private void registerVideo() {
		System.out.println("Enter video title to register: ") ;
		String title = scanner.next() ;

		System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):") ;
		int videoType = scanner.nextInt();

		System.out.println("Enter price code( 1 for Regular, 2 for New Release ):") ;
		int priceCode = scanner.nextInt();

		Date registeredDate = new Date();
		Video video = new Video(title, videoType, priceCode, registeredDate) ;
		videos.add(video) ;
	}

	public int showCommand() {
		System.out.println("\nSelect a command !");
		System.out.println("\t 0. Quit");
		System.out.println("\t 1. List customers");
		System.out.println("\t 2. List videos");
		System.out.println("\t 3. Register customer");
		System.out.println("\t 4. Register video");
		System.out.println("\t 5. Rent video");
		System.out.println("\t 6. Return video");
		System.out.println("\t 7. Show customer report");
		System.out.println("\t 8. Show customer and clear rentals");

		int command = scanner.nextInt() ;

		return command ;

	}
}
