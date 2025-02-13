import javax.imageio.metadata.IIOMetadataFormat;
import java.util.concurrent.ConcurrentMap;

class Contributor<T extends Comparable<T>> extends Staff implements RequestsManager {
    public Contributor(Information information, AccountTypes accountType, String username, int experience) {
        super(information, accountType, username, experience);
    }

    public void createRequest(Request r) {
        //Un user contributor nu are voie sa creeze o cerere pt un actor/prod adaugata de el in sistem
        if (r.requestType == Request.RequestTypes.MOVIE_ISSUE || r.requestType == Request.RequestTypes.ACTOR_ISSUE) {
            Object item = null;
            if (r.requestType == Request.RequestTypes.ACTOR_ISSUE) {
                Actor SearchedActor = null;
                for (Actor actor : IMDB.getInstance().actors) {
                    if (r.ActorName.equals(actor.name)) {
                        SearchedActor = actor;
                        break;
                    }
                }
                item = SearchedActor;
            }

            if (r.requestType == Request.RequestTypes.MOVIE_ISSUE) {
                Production SearchedProd = null;
                for (Production prod : IMDB.getInstance().productions) {
                    if (r.MovieTtile.equals(prod.title)) {
                        SearchedProd = prod;
                        break;
                    }
                }
                item = SearchedProd;
            }
            if(this.addedItems.contains(item) == false){
                IMDB.getInstance().requests.add(r);
            }else{

            }

            //Cazul in care productia/actorul a fost al unui user Contributor, devine in responsabilitatea adminilor
            //si cererea este pusa la lista de admini
//            if(super.AdminItems.contains(item)){
//                //RequestHolder.addRequest(r);
//            }

        } else {
            IMDB.getInstance().requests.add(r);
        }

       // IMDB.getInstance().requests.add(r);
        Observer observer = getUserFromUsername(r.usernameSolve);
        String notification = "Aveti de rezolvat cererea " + r.requestType + " de la user-ul " + r.username + ", Description: " + r.description;
        if(r.usernameSolve != "ADMIN") {
            ((Staff)observer).requests.add(r);
            observer.update(notification);
        }else {
            for(Observer user : IMDB.getInstance().observers){
                if(((User)user).accountType == AccountTypes.ADMIN){
                    user.update(notification);
                }
            }
            //Punem in lista requesturilor pentru admini
            RequestHolder.addRequest(r);
        }


    }

    public User getUserFromUsername(String username){
        for(User user : IMDB.getInstance().users){
            if(user.username.equals(username)){
                return user;
            }
        }
        return null;
    }
    public void deleteRequest(Request r){
        if(r.usernameSolve.equals("ADMIN")){
            RequestHolder.requestList.remove(r);
        }else {
            User solver = getUserFromUsername(r.usernameSolve);
            ((Staff) solver).requests.remove(r);
        }
        IMDB.getInstance().requests.remove(r);
    }

    public void removeProductionSystem(String name){
        Production SearchedProd = null;
        boolean ok = false;//se va folosi pt a verifica daca productia a fost adaugata de el in sistem
        for(Production p : IMDB.getInstance().productions){
            if(p.title.equals(name)){
                SearchedProd = p;
                break;
            }
        }

        if(SearchedProd != null) {
            for (Object item : super.addedItems) {
                if (item == SearchedProd) {
                    ok = true;
                    break;
                }
            }
        }


        if(ok){
            super.addedItems.remove(SearchedProd);
            IMDB.getInstance().productions.remove(SearchedProd);
            for(User user : IMDB.getInstance().users){
                if(user.favorites.contains(SearchedProd)){
                    user.favorites.remove(SearchedProd);
                }
            }
        }
    }

     public void removeActorSystem(String name){
        Actor SearchedActor = null;
        boolean ok = false;//se va folosi pt a verifica daca actorul a fost adaugat de el in sistem
        for(Actor a : IMDB.getInstance().actors){
            if(a.name.equals(name)){
                SearchedActor = a;
            }
        }

        if(SearchedActor != null) {
            for (Object item : super.addedItems) {
                if (item == SearchedActor) {
                    ok = true;
                    break;
                }
            }
        }

        //Cazul in care se sterge actorul pentru ca a fost adaugat de el
        if(ok){
            super.addedItems.remove(SearchedActor);
            IMDB.getInstance().actors.remove(SearchedActor);
            for(User user : IMDB.getInstance().users){
                if(user.favorites.contains(SearchedActor)){
                    user.favorites.remove(SearchedActor);
                }
            }
        }
    }
}