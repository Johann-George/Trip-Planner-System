import java.util.*;

class TripPlanner{

  HashMap<String,Destination> map = new HashMap<>();
  LinkedList<Booking> list = new LinkedList<>();
  TripPlanner tp = new TripPlanner();
  
  public static int findRole(){

    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the role of the User:");
    int UserRole=sc.nextInt();
    return UserRole;

  }

  public static void add_dest_info(){

    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the number of destionations:");
    dest_no= sc.nextInt();
    //Enter the destination information
    for (int i = 0; i < dest_no; i++) {
      System.out.println("Enter the destionation name:");
      String dest_name = sc.nextLine();
      System.out.println("Enter the destination rating:");
      int dest_rating = sc.nextInt();
      System.out.println("Enter the number of travel packages:");
      int travel_package_no = sc.nextInt();
      //Adding travel package information
      for(int j=0;j < travel_package_no;j++){
        System.out.println("Enter the name of the travel package:");
        String travel_package_name= sc.nextLine();
        System.out.println("Enter the price of the travel package:");
        int travel_package_price = sc.nextInt();
        System.out.println("Enter the rating of the travel package:");
        int travel_package_rating = sc.nextInt();
      }
      TravelPackage travelPack = new TravelPackage(travel_package_name,travel_package_price,travel_package_rating);
      Destination dest = new Destination(dest_name,dest_rating,travelPack);
      map.put(dest_name,dest);
    }
     
  }

  //Sorts travel packages by rating
  public static void SortTravelPackages(Destination dest) {
    List<TravelPackage> sortedPackage = new ArrayList<>(dest.package.values());
    sortedPackage.sort((a,b->Integer.compare(b.rating,a.rating)));
    System.out.println("Destination Name:"+dest.name);
    for(TravelPackage tp: sortedPackage){
      System.out.println("Package Name:"+tp.name+"\nPackage rating:"+tp.rating+"\nPackage Price:"+tp.price);
    }
  }

  //Calculates shortest travel routes between cities
  public static int ShortestTravelRoute() {
    
    

  }

  public static void AllPossiblePaths() {
    
  }
  
  //Stores customer bookings using linked list
  public static void CustomerBooking(){

    Scanner sc = new Scanner(System.in);
    Destination dest;
    System.out.println("Enter the Customer Name:");
    String CustName = sc.nextLine();
    System.out.println("Enter the Destionation Name:");
    String DestName = sc.nextLine();
    for(Map.Entry<String, Destination> entry: map.entrySet()){
      if(entry.getKey().equals(DestName)){
        dest = entry.getValue();
      }
    }
    System.out.println("Select the travel package you want:");
    tp.SortTravelPackages(dest);
    System.out.println("Enter the travel package name:");
    String travel_package_name = sc.nextLine();
    for(int i=0;i<dest.package.length;i++){
      if(dest.package.name.equals(travel_package_name)){
        Booking book = new Booking(CustName,travel_package_name);
        System.out.println("Destination Booked successfully!");
      }
    }
    list.add(book);

  } 

  //Recommends best-rated destinations
  public static void OrderDestionationsByRating() {

    HashMap<String,Integer> OrderMap = new HashMap<>();    
    for(Map.Entry<String,Destination> entry: map.entrySet()){
      OrderMap.put(entry.getKey(),entry.getValue().dest_rating);
    }
    PriorityQueue<Map<String,Integer>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());
    for(Map.Entry<String,Integer> entry : OrderMap.entrySet()){
      queue.add(entry);
      if(queue.size()>3){
        queue.poll();
      }
    } 
    
  }

  //Provides quick search by rating
  public static void SearchDestinations() {
    
    for(Map.Entry<String, Destination> entry: map.entrySet()){
       if(entry.getKey().equals(DestName)){
         System.out.println("Destination found!");
         Destination dest = entry.getValue();
       }
    }   
    System.out.println("Destination Rating ",+dest.dest_rating);
    for(int i=0;i<dest.package.length;i++){
      System.out.println("Travel Package name:"+dest.package.name);
      System.out.println("Travel Package price:"+dest.package.price);
      System.out.println("Travel Package rating:"+dest.package.rating);
      System.out.println("===========================================");
    }
  }

  public static void main(String[] args) {
    int UserRole=tp.findRole();
    Scanner sc = new Scanner(System.in);
    //Two roles are provided: Admin and Customer
    int quit=0;
    while(!quit){
      if(UserRole==1){
        //Admin has the privileage of adding the destination information
        tp.add_dest_info();
      }
      else{
        //Customer can search, book etc
        System.out.println("Recommendation of Destinations:");
        tp.OrderDestionationsByRating();
        System.out.println("1. Search\n 2. Book");
        System.out.println("Enter the operation that you want to perform:");
        int option = sc.nextInt(); 
        if(option == 1){
          tp.SearchDestinations();
        }
        else if(option == 2){
          tp.CustomerBooking();
        }
        else{
          System.out.println("Incorrect option. Please try again!");
        }
      }
      System.out.println("Do you want to quit(0/1):");
      quit=sc.nextInt();
    }
    
  }

}
