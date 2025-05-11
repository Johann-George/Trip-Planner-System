class Destination{

  int dest_rating;
  int dest_price;
  List<TravelPackage> package = new ArrayList<>();

  public Destination(int dest_rating, int dest_price, List<TravelPackage> package){
    this.dest_rating = dest_rating;
    this.dest_price = dest_price;
    this.package = package;
  }

}
