import java.util.Iterator;

class Regular<T extends Comparable<T>> extends User<T> implements RequestsManager{
    public Regular(Information information, AccountTypes type, String username, int experience){
        super(information, type, username, experience);
    }
    public void createRequest(Request r){
        Observer observer;
        observer = getUserFromUsername(r.usernameSolve);
        String notification = "Aveti de rezolvat cererea " + r.requestType + " de la user-ul " + r.username + ", Description: " + r.description;
        if(r.usernameSolve != "ADMIN") {
            if(observer instanceof Staff){
                observer.update(notification);
            }
            ((Staff)observer).requests.add(r);
        }else {
            RequestHolder.addRequest(r);
            for(Observer Observer : IMDB.getInstance().observers){
                if(((User)Observer).accountType == AccountTypes.ADMIN){
                    Observer.update(notification);
                }
            }
        }
        IMDB.getInstance().requests.add(r);
    }

    public void deleteRequest(Request r){
        IMDB.getInstance().requests.remove(r);
        if(r.usernameSolve.equals("ADMIN")){
            RequestHolder.requestList.remove(r);
        }else {
            User solver = getUserFromUsername(r.usernameSolve);
            ((Staff) solver).requests.remove(r);
        }
    }

    public void addRating(String MovieName,Integer rating, String comment){
        Production SearchedProd = null;
        Rating SearchedRating = null;
        for(Production p : IMDB.getInstance().productions){
            if(p.title.equals(MovieName)){
                SearchedProd = p;
                break;
            }
        }

        //Verific daca a mai adaugat o recenzie pana acum utilizatorul
        if(SearchedProd != null){
            for(Rating r : SearchedProd.ratings){
                if(r.username.equals(super.username)){
                    SearchedRating = r;
                }else{
                    //O productie pe care a evaluat-o primeste o altă recenzie
                    Observer observer = getUserFromUsername(r.username);
                    String notification = "User-ul " + super.username + " a adaugat o recenzie pentru productia " + SearchedProd.title + " pe care si tu ai evaluat-o: " + "'" + comment + "'.";
                    if(observer instanceof Regular<?>){
                        observer.update(notification);
                    }
                }
            }
        }

        if(SearchedRating != null){
            SearchedProd.ratings.remove(SearchedRating);
        }else{
            super.strategy = new AddRatingExperience();
            super.updateExperience();
        }
        Rating new_rating = new Rating(super.username, rating, comment);

       // O productie pe care a adăugat-o în sistem primeste o recenzie.
        Staff Observer = getAdder(MovieName);
        String notification = "User-ul " + super.username + " a adaugat o recenzie pentru productia " + SearchedProd.title + " pe care tu ai adaugat-o: " + "'" + comment + "'.";
        Observer.update(notification);

        //Crestem experienta utilizatorului

        SearchedProd.ratings.add(new_rating);
       // SearchedProd.noRatings++;
    }

    public User getUserFromUsername(String username){
        for(User user : IMDB.getInstance().users){
            if(user.username.equals(username)){
                return user;
            }
        }
        return null;
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
                    }
                }
            }
        }
        return null;
    }
    
    //Sterge recenzia adaugata unei productii
    public void delRating(String MovieName){
        Production searchedProd = null;
        for(Production prod : IMDB.getInstance().productions){
            if(prod.title.equals(MovieName)){
                searchedProd = prod;
                break;
            }
        }
        
        for(Rating rating : searchedProd.ratings){
            if(rating.username.equals(this.username)){
                searchedProd.ratings.remove(rating);
                searchedProd.noRatings--;
                break;
            }
        }
    }


}

