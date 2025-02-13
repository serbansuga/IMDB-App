import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Request {
    public enum RequestTypes{
        DELETE_ACCOUNT,
        ACTOR_ISSUE,
        MOVIE_ISSUE,
        OTHERS
    }

    RequestTypes requestType;
    LocalDate createdDate;
    String MovieTtile;
    String ActorName;
    String description;
    String username;
    String usernameSolve;

    public Request(RequestTypes requestType, LocalDate createdDate, String TitleOrName, String description, String username){
        this.requestType = requestType;
        this.createdDate = createdDate;
        if(requestType == RequestTypes.ACTOR_ISSUE){
            this.MovieTtile = null;
            this.ActorName = TitleOrName;
        }
        else if(requestType == RequestTypes.MOVIE_ISSUE){
            this.ActorName = null;
            this.MovieTtile = TitleOrName;
        }else{
            this.ActorName = null;
            this.MovieTtile = null;
        }
        this.description = description;
        this.username = username;

        //Pun usernameSolve-ul in functie de tipul de cerere
        if(requestType == RequestTypes.ACTOR_ISSUE || requestType == RequestTypes.MOVIE_ISSUE){
            Actor searchedActor = null;
            Production searchedProd = null;
            boolean ok = false;

            //Cazurile in care se face o cerere te tip movie_issue sau actor_issue pentru o prod/actor aflat in lista
            //de adaugati de admini.
            if(requestType == RequestTypes.MOVIE_ISSUE){
                for(Production prod : IMDB.getInstance().productions){
                    if(prod.title.equals(this.MovieTtile)){
                        searchedProd = prod;
                        break;
                    }
                }
                if(IMDB.getInstance().adminProds.contains(searchedProd)){
                    this.usernameSolve = "ADMIN";
                    ok = true;
                }
            }
            if(requestType == RequestTypes.ACTOR_ISSUE) {
                for (Actor actor : IMDB.getInstance().actors) {
                    if (actor.name.equals(this.ActorName)) {
                        searchedActor = actor;
                        break;
                    }
                }
                if (IMDB.getInstance().adminActors.contains(searchedActor)) {
                    this.usernameSolve = "ADMIN";
                    ok = true;
                }
            }
                //Daca nu s-a intrat pe niciun caz anterior, inseamna ca productia sau actorul nu provin de la un user contributor
            //care a fost sters, deci nu se afla sub administrarea adminilor
            if(!ok) {
                this.usernameSolve = getAdder(TitleOrName).username;
            }
        }else{
            this.usernameSolve = "ADMIN";
        }
    }

    public String toString(){
        String str = "";
        str += "Type: " + this.requestType + ", createdDate: " + this.createdDate + ", description: " + this.description + ", username: " + this.username;
        return str;
    }

    public Staff getAdder(String Name){
        for(User user : IMDB.getInstance().users){
            if(user.accountType != User.AccountTypes.REGULAR){
                Staff staffUser = (Staff)user;
                Iterator it = staffUser.addedItems.iterator();
                while(it.hasNext()){
                    Object obj = it.next();
                    if(obj instanceof Production){
                        if(((Production) obj).title.equals(Name)){
                            return staffUser;
                        }
                    }else if(obj instanceof Actor){
                        if(((Actor) obj).name.equals(Name)){
                            return staffUser;
                        }
                    }
                }
            }
        }
        return null;
    }
}