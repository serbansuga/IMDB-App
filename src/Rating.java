import java.util.ArrayList;
import java.util.List;

public class Rating implements Comparable<Rating>,Subject{
    String username;
    Integer rating;
    String comment;

    List<Observer> observers = new ArrayList<>();
    public Rating(String username, Integer rating, String comment){
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }

    public String toString(){
        return "Username: " + this.username + ", Rating: " + this.rating + ", Comment: " + this.comment;
    }

    //Functie care imi da experienta unui user, stiind username-ul lui
    public int getExeperience(){
        for(User user : IMDB.getInstance().users){
            if(user.username.equals(this.username)){
                return user.experience;
            }
        }
        return 0;
    }

    //Ratingurile trebuie comparate dupa experienta userului care le-a oferit

    public int compareTo(Rating o) {
       // Rating rating = (Rating) o;
        return o.getExeperience() - this.getExeperience();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}