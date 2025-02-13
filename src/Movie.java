public class Movie extends Production {
    String duration;
    Long releaseYear;

    public Movie(String title, String description, Double averageRating, String duration, Long releaseYear){
        super(title, description, averageRating);
        this.duration = duration;
        this.releaseYear = releaseYear;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Production) {
            Production movie = (Production) o;
            return this.title.compareTo(movie.title);
        }else{
            Actor actor = (Actor) o;
            return this.title.compareTo(actor.name);
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("Movie " + super.title + ", duration: " + this.duration + ", genres: " + this.genres);
    }
}


