import java.util.List;
import java.util.Map;

public class Series extends Production{
    Long releaseYear, numSeasons;
    private Map<String, List<Episode>> seasons;

    public Series(String title, String description, Double averageRating, Long releaseYear, Long numSeasons){
        super(title, description, averageRating);
        this.releaseYear = releaseYear;
        this.numSeasons = numSeasons;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Production) {
            Production series = (Production) o;
            return this.title.compareTo(series.title);
        }else{
            Actor actor = (Actor) o;
            return this.title.compareTo(actor.name);
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("Name of the serie: " + super.title + ", ReleaseYear: " + this.releaseYear + ", Num of seasons: " + this.numSeasons + ", Seasons: " + this.seasons);
    }
}