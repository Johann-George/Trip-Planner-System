import java.util.*;

class TripPlanner{

  HashMap<String,Destination> map = new HashMap<>();
  LinkedList<Booking> list = new LinkedList<>();
  
  public static int findRole(){

    Scanner sc = new Scanner(System.in);
    System.out.println("1.Admin\n2.Customer\n");
    System.out.println("Enter the role of the User:");
    int UserRole=sc.nextInt();
    return UserRole;

  }

  public void add_dest_info(){

    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the number of destinations:");
    int dest_no= sc.nextInt();
    System.out.println("Enter the current location:");
    String CustLocation = sc.nextLine();
    //Enter the destination information
    for (int i = 0; i < dest_no; i++) {
      List<TravelPackage> travelPackage = new ArrayList<>();
      sc.nextLine();
      System.out.println("Enter the destionation name:");
      String dest_name = sc.nextLine();
      System.out.println("Enter the destination rating:");
      float dest_rating = sc.nextFloat();
      System.out.println("Enter the number of Travel Packages:");
      int travel_travelPackage_no = sc.nextInt();
      //Adding travel travelPackage information
      String travel_travelPackage_name;
      int travel_travelPackage_price;
      float travel_travelPackage_rating;
      for(int j=0;j < travel_travelPackage_no;j++){
        sc.nextLine();
        System.out.println("Enter the name of the Travel Package:");
        travel_travelPackage_name= sc.nextLine();
        System.out.println("Enter the price of the Travel Package:");
        travel_travelPackage_price = sc.nextInt();
        System.out.println("Enter the rating of the Travel Package:");
        travel_travelPackage_rating = sc.nextFloat();
        TravelPackage travelPack = new TravelPackage(travel_travelPackage_name,travel_travelPackage_price,travel_travelPackage_rating);
        travelPackage.add(travelPack);
        System.out.println("===========================================");
      }
      int cont=1;
      //Adding the paths between intermediate cities
      System.out.println("Enter the paths of the cities in between the destination:");
      HashMap<String,List<String>> adjList = new HashMap<>();
      while(cont!=0){
        sc.nextLine();
        System.out.println("Enter the source location:");
        String src = sc.nextLine();
        System.out.println("Enter the destination location:");
        String dest = sc.nextLine();
        adjList.putIfAbsent(src,new ArrayList<>());
        adjList.putIfAbsent(dest,new ArrayList<>());
        adjList.get(src).add(dest);
        adjList.get(dest).add(src);
        System.out.println("Do you want to continue(0/1):");
        cont = sc.nextInt();
      } 
      Destination dest = new Destination(dest_rating,travelPackage,adjList);
      map.put(dest_name,dest);
      System.out.println("============================================");
    }
     
  }

  //Sorts travel travelPackages by rating
  public static void SortTravelPackages(Destination dest) {
    List<TravelPackage> sortedPackage = new ArrayList<>(dest.travelPackage);
    sortedPackage.sort((a,b)->Float.compare(b.rating,a.rating));
    System.out.println("Sorting travel packages by rating");
    for(TravelPackage tp: sortedPackage){
      System.out.println("Package Name:"+tp.name+"\nPackage rating:"+tp.rating+"\nPackage Price:"+tp.price);
      System.out.println("=================================");
    }
  }

  //Calculates shortest travel routes between cities
  public static void ShortestTravelRoute(Destination dest, String start, String end) {
    
    //implemented BFS to find the shortest path
    Queue<String> queue = new LinkedList<>();
    Map<String,String> parent = new HashMap<>();
    Set<String> visited = new HashSet<>();

    queue.add(start);
    visited.add(start);
    parent.put(start,null);

    while(!queue.isEmpty()){
      String current = queue.poll();
      if(current.equals(end)){
        break;
      }

      for(String neighbour:dest.adjList.get(current)){

        if(!visited.contains(neighbour)){
          queue.add(neighbour);
          parent.put(neighbour,current);
          visited.add(neighbour);
        }
        
      }
    }
    List<String> path = new ArrayList<>();
    for(String i=end;i!=null;i=parent.get(i)){
      path.add(i);
    }
    Collections.reverse(path);
    System.out.println("Shortest Path:"+path);

  }

  public void printAllPaths(Destination dest,String start, String end){

    Set<String> visited = new HashSet<>();
    List<String> path = new ArrayList<>();
    dfs(start,end,visited,path,dest);

  }

  public static void dfs(String current, String end, Set<String> visited, List<String> path, Destination dest) {
    
    visited.add(current);
    path.add(current);

    if(current.equals(end)){
      System.out.println(" All Possible Paths:"+path);
    }
    else{
      for(String neighbour:dest.adjList.getOrDefault(current, new ArrayList<>())){
        if(!visited.contains(neighbour)){
          dfs(neighbour,end,visited,path,dest);
        }
      }
    }

    path.remove(path.size()-1);
    visited.remove(current);

  }
  
  //Stores customer bookings using linked list
  public void CustomerBooking(){

    Scanner sc = new Scanner(System.in);
    Destination dest = null;
    Booking book = null; 
    TripPlanner tp = new TripPlanner();
    System.out.println("Enter the Customer Name:");
    String CustName = sc.nextLine();
    System.out.println("Enter the Destination Name:");
    String DestName = sc.nextLine();
    for(Map.Entry<String, Destination> entry: map.entrySet()){
      if(entry.getKey().equals(DestName)){
        dest = entry.getValue();
      }
    }
    System.out.println("Select the Travel Package you want:");
    tp.SortTravelPackages(dest);
    System.out.println("Enter the Travel Package name:");
    String travel_travelPackage_name = sc.nextLine();
    for(TravelPackage travelPack: dest.travelPackage){
      if(travelPack.name.equals(travel_travelPackage_name)){
        book = new Booking(CustName,travelPack);
        System.out.println("Destination Booked successfully!");
      }
    }
    list.add(book);

  } 

  //Recommends best-rated destinations
  public void OrderDestinationsByRating(){

    HashMap<String,Float> OrderMap = new HashMap<>();    
    for(Map.Entry<String,Destination> entry: map.entrySet()){
      OrderMap.put(entry.getKey(),entry.getValue().dest_rating);
    }
    PriorityQueue<Map.Entry<String,Float>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());
    for(Map.Entry<String,Float> entry : OrderMap.entrySet()){
      queue.add(entry);
      if(queue.size()>3){
        queue.poll();
      }
    } 
    int i=1;
    for(Map.Entry<String,Float> entry: queue){
      System.out.println(i+".Destination:"+entry.getKey()+" Rating:"+entry.getValue());
      i++;
    }
    
  }

  //Provides quick search by rating
  public void SearchDestinations() {
    
    Destination dest = null;
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the destination you want to search:");
    String DestName = sc.nextLine();
    for(Map.Entry<String, Destination> entry: map.entrySet()){
       if(entry.getKey().equals(DestName)){
         System.out.println("Destination found!");
         dest = entry.getValue();
       }
    }   
    if(dest==null){
      System.out.println("Destination not found!");
    }
    System.out.println("Destination Rating "+dest.dest_rating);
    for(TravelPackage tp : dest.travelPackage){
      System.out.println("Travel Package name:"+tp.name);
      System.out.println("Travel Package price:"+tp.price);
      System.out.println("Travel Package rating:"+tp.rating);
      System.out.println("===========================================");
    }
    TripPlanner tp = new TripPlanner();
    System.out.println("Enter you current location:");
    String start = sc.nextLine();
    tp.ShortestTravelRoute(dest,start,DestName);
    tp.printAllPaths(dest,start,DestName);
  }

  public static void main(String[] args) {
    TripPlanner tp = new TripPlanner();
    Scanner sc = new Scanner(System.in);
    //Two roles are provided: Admin and Customer
    int cont=1;
    while(cont!=0){
      int UserRole=tp.findRole();
      if(UserRole==1){
        //Admin has the privileage of adding the destination information
        tp.add_dest_info();
      }
      else if(UserRole==2){
        //Customer can search, book etc
        System.out.println("Recommendation of Destinations:");
        tp.OrderDestinationsByRating();
        System.out.println("1. Search\n2. Book");
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
      else{
        System.out.println("Incorrect option. Please try again!");
      }
      System.out.println("Do you want to contiue the program:(0/1):");
      cont = sc.nextInt();
    }
    
  }

}
