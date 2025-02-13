import java.lang.ref.PhantomReference;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class User<T extends Comparable<T>> implements Observer{
    Information information;

    public static class Information {
        final Credentials credentials;
        final String name, country, gender;
        final int age;
        final LocalDate birthDate;

        public Information(InformationBuilder builder) {
            this.credentials = builder.credentials;
            this.name = builder.name;
            this.country = builder.country;
            this.gender = builder.gender;
            this.age = builder.age;
            this.birthDate = builder.birthDate;
        }

        //Folosesc sablonul de proiectare Builder pentru a putea instantia pe parcurs anumite campuri din Information
        public static class InformationBuilder {
            final Credentials credentials;
            final String name;
            String country, gender;
            int age;
            LocalDate birthDate;

            public InformationBuilder(Credentials credentials, String name) {
                this.credentials = credentials;
                this.name = name;
            }

            public InformationBuilder country(String country) {
                this.country = country;
                return this;
            }

            public InformationBuilder gender(String gender) {
                this.gender = gender;
                return this;
            }

            public InformationBuilder age(int age) {
                this.age = age;
                return this;
            }

            public InformationBuilder birthDate(LocalDate birthDate) {
                this.birthDate = birthDate;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
        }
    }
   public enum AccountTypes{
        REGULAR,
        CONTRIBUTOR,
        ADMIN
    }

    AccountTypes accountType;
    String username;
    int experience;
    List<String> notifications;
    SortedSet<T> favorites;

    ExperienceStrategy strategy;

    public User(Information information, AccountTypes accountType, String username, int experience){
        this.information = information;
        this.accountType = accountType;
        this.username = username;
        this.experience = experience;
        this.favorites = new TreeSet<>();
        this.notifications = new ArrayList<>();
    }

    public void addFavoriteProduction(String ProdName){
        for(Production prod : IMDB.getInstance().productions){
            if(prod.title.equals(ProdName)){
                if(this.favorites.contains(prod) == false) {
                    this.favorites.add((T) prod);
                }
                break;
            }
        }
    }

    public void addFavoriteActor(String ActorName){
        for(Actor actor : IMDB.getInstance().actors){
            if(actor.name.equals(ActorName)){
                this.favorites.add((T)actor);
                break;
            }
        }
    }

    public void delFavoriteProduction(Production favorite){
        if(favorites.contains(favorite)){
            favorites.remove(favorite);
        }
    }

    public void delFavoriteActor(Actor favorite){
        if(favorites.contains(favorite)){
            favorites.remove(favorite);
        }
    }

    public void updateExperience(){
        if(this.accountType != AccountTypes.ADMIN) {
            this.experience += strategy.calculateExperience();
        }
    }

    @Override
    public void update(String notification) {
        this.notifications.add(notification);
    }

    public String toString(){
        return "User " + this.information.name + " of type " + this.accountType;
    }

}


