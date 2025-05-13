import java.util.*;

class Destination{

  float dest_rating;
  List<TravelPackage> travelPackage = new ArrayList<>();
  HashMap<String,List<String>> adjList = new HashMap<>();

  public Destination(float dest_rating, List<TravelPackage> travelPackage,HashMap<String,List<String>> adjList){
    this.dest_rating = dest_rating;
    this.travelPackage= travelPackage;
    this.adjList = adjList;
  }

}
