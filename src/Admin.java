import java.util.ArrayList;
import java.util.List;

class Admin<T extends Comparable<T>> extends Staff {

    public Admin(Information information, AccountTypes type, String username, int experience) {
        super(information, type, username, experience);
    }

    public void addUser(User user){
        IMDB.getInstance().users.add(user);
    }

    public void delUser(User user){
        List<Request> requestForRemove = new ArrayList<>();

        for(Request request : IMDB.getInstance().requests){
            if(request.username.equals(user.username)){
                requestForRemove.add(request);
                if(request.usernameSolve.equals("ADMIN") == false) {
                    User solver = getUserFromUsername(request.usernameSolve);
                    ((Staff) solver).requests.remove(request);
                }else{
                    RequestHolder.requestList.remove(request);
                }
            }
        }
        IMDB.getInstance().requests.removeAll(requestForRemove);

        for(Production production : IMDB.getInstance().productions){
            for(Rating rating : production.ratings){
                if(rating.username.equals(user.username)){
                    production.ratings.remove(rating);
                    //O productie poate avea o singura recenzie de la un user
                    break;
                }
            }
        }

        if(user instanceof Contributor<?>){
            //Cand stergem un contributor, punem toate elementele adaugate de el in lista elementelor pt admini
            for(Object obj : ((Contributor<?>) user).addedItems){
                if(obj instanceof Production){
                    IMDB.getInstance().adminProds.add((Production) obj);
                }else if(obj instanceof Actor){
                    IMDB.getInstance().adminActors.add((Actor) obj);
                }
                for(User user2 : IMDB.getInstance().users){
                    if(user2.accountType == AccountTypes.ADMIN){
                        ((Admin)user2).addedItems.add(obj);
                    }
                }
            }
        }

        IMDB.getInstance().users.remove(user);
    }

    public User getUserFromUsername(String username){
        for(User user : IMDB.getInstance().users){
            if(user.username.equals(username)){
                return user;
            }
        }
        return null;
    }

    @Override
    public void addProductionSystem(Production p){
        super.addProductionSystem(p);
    }

    @Override
    public void addActorSystem(Actor a){
        super.addActorSystem(a);
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
    @Override
    public void removeProductionSystem(String name) {
        Production SearchedProd = null;

        for(Production p : IMDB.getInstance().productions){
            if(p.title.equals(name)){
                SearchedProd = p;
                break;
            }
        }
        boolean ok = false;

        //Un admin poate sterge o productie adaugata de oricine
        //Se sterge din lista de contributii a userului care a adaugat productia respectiva
        if(SearchedProd != null) {
            for(User user : IMDB.getInstance().users){
                if(ok == true){
                    break;
                }
                if(user instanceof Staff<?>){
                    for(Object item : ((Staff<?>) user).addedItems){
                        if(item == SearchedProd){
                            ((Staff<?>) user).addedItems.remove(SearchedProd);
                            ok = true;
                            break;
                        }
                    }
                }
            }
        }
        IMDB.getInstance().productions.remove(SearchedProd);
    }

    @Override
    public void removeActorSystem(String name) {
        Actor SearchedActor = null;

        for(Actor a : IMDB.getInstance().actors){
            if(a.name.equals(name)){
                SearchedActor = a;
            }
        }

        boolean ok = false;

        //Un admin poate sterge un actor adaugat de oricine
        //Se sterge din lista de contributii a userului care a adaugat actorul respectiv
        if(SearchedActor != null) {
            for(User user : IMDB.getInstance().users){
                if(ok == true){
                    break;
                }
                if(user instanceof Staff<?>){
                    for(Object item : ((Staff<?>) user).addedItems){
                        if(item == SearchedActor){
                            ((Staff<?>) user).addedItems.remove(SearchedActor);
                            ok = true;
                            break;
                        }
                    }
                }
            }
        }
            IMDB.getInstance().actors.remove(SearchedActor);
    }
}
