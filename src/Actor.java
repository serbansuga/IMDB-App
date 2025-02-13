import java.util.*;
public class Actor implements Comparable{


    class Pair {
        String title;
        String type;
        public Pair(String title, String type){
            this.title = title;
            this.type = type;
        }

        public String toString(){
            return type + " " + title;
        }
    }
     String name;
     List<Pair> performances;
     String biography;

    public Actor(String name, String biography){
        this.name = name;
        this.biography = biography;
        this.performances = new ArrayList<>();
    }

    public void addPerformance(String title, String type){
        Pair newPair = new Pair(title, type);
        performances.add(newPair);
    }

    @Override
    public int compareTo(Object o) {
        //Fac 2 cazuri pentru comparare, pentru ca retin in acelasi SortedSet si productiile si actorii
        //favoriti ai unui user.
        if (o instanceof Actor) {
            Actor actor = (Actor) o;
            return this.name.compareTo(actor.name);
        }
        else {
            Production prod = (Production) o;
            return this.name.compareTo(prod.title);
        }
    }

    public String toString(){
        return "Actor " + this.name;
    }
}
