import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public abstract class Production implements Comparable{
    String title;
    List<String> directors;

    List<String> actors;


    public enum Genres{
        Action, Adventure, Comedy, Drama, Horror, SF,
        Fantasy, Romance, Mystery, Thriller, Crime, Biography, War
    }
    List<Genres> genres;

    List<Rating> ratings;

    Integer noRatings;

    String description;

    Double averageRating;

    public Production(String title, String description, Double averageRating){
        directors = new ArrayList<>();
        actors = new ArrayList<>();
        genres = new ArrayList<>();
        ratings = new ArrayList<>();
        this.title = title;
        this.description = description;
        this.averageRating = averageRating;
    }
    public abstract void displayInfo();
    public int compareTo(Object o){
        Production prod = (Production) o;
        return this.title.compareTo(prod.title);
    }

    public String toString(){
        String str = "";
        if(this instanceof Movie){
            str += "Movie name: ";
        }
        else{
            str += "Series name: ";
        }
        str += this.title + ", Rating: " + this.averageRating;
        return str;
    }

    public static class ProductionRatingsSort implements Comparator<Production>{

        @Override
        public int compare(Production p1, Production p2) {
            return Double.compare(p2.ratings.size(), p1.ratings.size());
        }
    }

    public static class ProductionYearSort implements Comparator<Production>{

        @Override
        public int compare(Production p1, Production p2) {
            Long r1 = null;
            Long r2 = null;
            if(p1 instanceof Movie){
                r1 = ((Movie) p1).releaseYear;
            }else if(p1 instanceof Series){
                r1 = ((Series) p1).releaseYear;
            }

            if(p2 instanceof Movie){
                r2 = ((Movie) p2).releaseYear;
            }else if(p2 instanceof Series){
                r2 = ((Series) p2).releaseYear;
            }
            return (int) (r2- r1);
        }
    }
}





