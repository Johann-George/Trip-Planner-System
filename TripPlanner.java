class TripPlanner{

  HashMap<String,Destination> map = new HashMap<>();
  
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
      System.out.println("Enter the destination price:");
      int dest_price= sc.nextInt();
      Destination dest = new Destination(dest_name,dest_rating,dest_price);
      map.put(dest_name,dest);
    }
     
  }
  
  public static void CustomerBooking(){

    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the Customer Name:");
    String CustName = sc.nextLine();
    System.out.println("Enter the Destionation Name:");
    String DestName = sc.nextLine();
    for(Map.Entry<String, Destination> entry: map.entrySet()){
      if(entry.getKey().equals(DestName)){
        Destination dest = entry.getValue();
      }
    }
    Booking book = new Booking(CustName,dest);
    System.out.println("Destination Booked successfully!");
    LinkedList<Booking> list = new LinkedList<>();
    list.add(book);

  } 

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

  public static void SearchDestinations() {
    
    for(Map.Entry<String, Destination> entry: map.entrySet()){
       if(entry.getKey().equals(DestName)){
         System.out.println("Destination found!");
         Destination dest = entry.getValue();
       }
    }   
    System.out.println("Destination Rating ",+dest.dest_rating);
    TripPlanner tp = new TripPlanner();
    tp.CustomerBooking();
  }

  public static void main(String[] args) {
    TripPlanner tp = new TripPlanner();
    int UserRole=tp.findRole();
    if(UserRole==1){
      tp.admin_func();
    }
    else{
      tp.user_func();
    }
  }

}
