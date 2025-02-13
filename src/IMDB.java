import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IMDB implements Subject{
    private static IMDB instance = null;
    List<Actor> actors;
    List<User> users;
    List<Request> requests;

    List<Production> productions;
    List<Production> recommendations;
    List<Production> recommendations1;

    List<Production> prodFavorites;

    List<Actor> actorFavorites;
    List<Production> filtered;

    List<Production> addedProductions;

    List<Actor> addedActors;
    List<Request> requestsDelete;

    List<Request> requestsSolve;
    User connectedUser;

    List<Production> adminProds;

    List<Actor> adminActors;

    List<Observer> observers;
    LoginPage loginPage;
    public IMDB(){
        actors = new ArrayList<>();
        productions = new ArrayList<>();
        requests = new ArrayList<>();
        users = new ArrayList<>();
        recommendations = new ArrayList<>();
        recommendations1 = new ArrayList<>();
        filtered = new ArrayList<>();
        prodFavorites = new ArrayList<>();
        actorFavorites = new ArrayList<>();
        requestsDelete =  new ArrayList<>();
        requestsSolve = new ArrayList<>();
        addedProductions = new ArrayList<>();
        addedActors = new ArrayList<>();
        adminActors = new ArrayList<>();
        adminProds = new ArrayList<>();
        observers = new ArrayList<>();
    }
    public void run() {
        JSONParser jsonParser = new JSONParser();

        try {
            File file = new File("production.json");
            FileReader reader = new FileReader(file);

            Object obj = jsonParser.parse(reader);
            JSONArray productions1 = (JSONArray) obj;

            for(Object ob : productions1){
                JSONObject Jobject = (JSONObject)ob;
                String type = (String) Jobject.get("type");
                if(type.equals("Movie")){
                    String duration = (String) Jobject.get("duration");
                    Long releaseYear = (Long) Jobject.get("releaseYear");


                    String title = (String)Jobject.get("title");
                    List<String> directors = (List<String>) Jobject.get("directors");
                    List<String> actors = (List<String>) Jobject.get("actors");
                    List<String> texts = (List<String>) Jobject.get("genres");
                    List<Production.Genres> genres = new ArrayList<>();
                    for(int i = 0; i < texts.size(); i++){
                    switch (texts.get(i)) {
                        case "Action":
                            genres.add(i, Production.Genres.Action);
                            break;
                        case "Adventure":
                            genres.add(i, Production.Genres.Adventure);
                            break;
                        case "Comedy":
                            genres.add(i, Production.Genres.Comedy);
                            break;
                        case "Drama":
                            genres.add(i, Production.Genres.Drama);
                            break;
                        case "Horror":
                            genres.add(i, Production.Genres.Horror);
                            break;
                        case "SF":
                            genres.add(i, Production.Genres.SF);
                            break;
                        case "Fantasy":
                            genres.add(i, Production.Genres.Fantasy);
                            break;
                        case "Romance":
                            genres.add(i, Production.Genres.Romance);
                            break;
                        case "Mystery":
                            genres.add(i, Production.Genres.Mystery);
                            break;
                        case "Thriller":
                            genres.add(i, Production.Genres.Thriller);
                            break;
                        case "Crime":
                            genres.add(i, Production.Genres.Crime);
                            break;
                        case "Biography":
                            genres.add(i, Production.Genres.Biography);
                            break;
                        case "War":
                            genres.add(i, Production.Genres.War);
                            break;
                        default:
                            genres.add(i, null);
                            break;
                    }
                    }

                    JSONArray ratingsArray = (JSONArray) Jobject.get("ratings");
                    List<Rating> ratings = new ArrayList<>();

                    for(Object ratingObj : ratingsArray){
                        JSONObject ratingJSON = (JSONObject) ratingObj;
                        String username = (String) ratingJSON.get("username");
                        Integer rating = ((Long)ratingJSON.get("rating")).intValue();
                        String comment = (String)ratingJSON.get("comment");

                        Rating rating2 = new Rating(username, rating, comment);
                        ratings.add(rating2);
                    }

                    String description = (String)Jobject.get("plot");

                    Number averageRating1 = (Number) Jobject.get("averageRating");
                    Double averageRating = averageRating1.doubleValue();
                    Production new_movie = new Movie(title, description, averageRating, duration, releaseYear);
                    new_movie.directors = directors;
                    new_movie.actors = actors;
                    new_movie.genres = genres;
                    new_movie.ratings = ratings;
                    new_movie.noRatings = ratings.size();

                    productions.add(new_movie);
                }
                else{
                    Long releaseYear = (Long) Jobject.get("releaseYear");
                    Long numSeasons = (Long) Jobject.get("numSeasons");
                    Map<String, List<Episode>> seasons = (Map<String, List<Episode>>) Jobject.get("seasons");


                    String title = (String)Jobject.get("title");
                    List<String> directors = (List<String>) Jobject.get("directors");
                    List<String> actors = (List<String>) Jobject.get("actors");
                    List<String> texts = (List<String>) Jobject.get("genres");
                    List<Production.Genres> genres = new ArrayList<>();
                    for(int i = 0; i < texts.size(); i++){
                        switch (texts.get(i)) {
                            case "Action":
                                genres.add(i, Production.Genres.Action);
                                break;
                            case "Adventure":
                                genres.add(i, Production.Genres.Adventure);
                                break;
                            case "Comedy":
                                genres.add(i, Production.Genres.Comedy);
                                break;
                            case "Drama":
                                genres.add(i, Production.Genres.Drama);
                                break;
                            case "Horror":
                                genres.add(i, Production.Genres.Horror);
                                break;
                            case "SF":
                                genres.add(i, Production.Genres.SF);
                                break;
                            case "Fantasy":
                                genres.add(i, Production.Genres.Fantasy);
                                break;
                            case "Romance":
                                genres.add(i, Production.Genres.Romance);
                                break;
                            case "Mystery":
                                genres.add(i, Production.Genres.Mystery);
                                break;
                            case "Thriller":
                                genres.add(i, Production.Genres.Thriller);
                                break;
                            case "Crime":
                                genres.add(i, Production.Genres.Crime);
                                break;
                            case "Biography":
                                genres.add(i, Production.Genres.Biography);
                                break;
                            case "War":
                                genres.add(i, Production.Genres.War);
                                break;
                            default:
                                genres.add(i, null);
                                break;
                        }
                    }


                    JSONArray ratingsArray = (JSONArray) Jobject.get("ratings");
                    List<Rating> ratings = new ArrayList<>();

                    for(Object ratingObj : ratingsArray){
                        JSONObject ratingJSON = (JSONObject) ratingObj;
                        String username = (String) ratingJSON.get("username");
                        Integer rating = ((Long)ratingJSON.get("rating")).intValue();
                        String comment = (String)ratingJSON.get("comment");

                        Rating rating2 = new Rating(username, rating, comment);
                        ratings.add(rating2);
                    }

                    String description = (String)Jobject.get("plot");

                    Number averageRating1 = (Number) Jobject.get("averageRating");
                    Double averageRating = averageRating1.doubleValue();

                    Production new_series = new Series(title, description, averageRating, releaseYear, numSeasons);
                    new_series.directors = directors;
                    new_series.actors = actors;
                    new_series.genres = genres;
                    new_series.ratings = ratings;
                    new_series.noRatings = ratings.size();
                    productions.add(new_series);
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File file = new File("actors.json");
            FileReader reader = new FileReader(file);

            Object obj = jsonParser.parse(reader);
            JSONArray actorss = (JSONArray) obj;

            for(Object ob : actorss) {
                JSONObject Jobject = (JSONObject) ob;
                String name = (String) Jobject.get("name");
                String biography = (String) Jobject.get("biography");
                Actor new_actor = new Actor(name, biography);

                JSONArray PerformancesArray = (JSONArray) Jobject.get("performances");
                for(Object perfObj : PerformancesArray){
                    JSONObject performance = (JSONObject) perfObj;
                    String title = (String) performance.get("title");
                    String type = (String) performance.get("type");
                    new_actor.addPerformance(title, type);
                }

                actors.add(new_actor);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            File file = new File("accounts.json");
            FileReader reader = new FileReader(file);

            Object obj = jsonParser.parse(reader);
            JSONArray userss = (JSONArray) obj;

            for(Object ob : userss){
                JSONObject Jobject = (JSONObject) ob;

                String username = (String)Jobject.get("username");

                Object experienceObject = (Object)Jobject.get("experience");
                int experience;
                if(experienceObject instanceof Long){
                    experience = ((Long) experienceObject).intValue();
                }else if(experienceObject instanceof String){
                    experience = Integer.parseInt((String) experienceObject);
                }else{
                    //cand e null;
                    //cazul cand e admin, are experienta maxima
                    experience = 10000;
                }

                JSONObject InformationObj = (JSONObject) Jobject.get("information");
                JSONObject CredentialsObj = (JSONObject)InformationObj.get("credentials");
                String email = (String)CredentialsObj.get("email");
                String password = (String)CredentialsObj.get("password");

                String name = (String)InformationObj.get("name");
                String country = (String)InformationObj.get("country");
                String gender = (String)InformationObj.get("gender");
                Long age1 = (Long)InformationObj.get("age");
                int age = age1.intValue();

                String date = (String) InformationObj.get("birthDate");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate birthDate = LocalDate.parse(date, formatter);

                Credentials credentials = new Credentials(email, password);
                User.Information information = new User.Information.InformationBuilder(credentials, name)
                        .country(country)
                        .gender(gender)
                        .age(age)
                        .birthDate(birthDate)
                        .build();

                String userType = (String)Jobject.get("userType");
                User.AccountTypes accountType;
                if(userType.equals("Regular")){
                    accountType = User.AccountTypes.REGULAR;
                } else if(userType.equals("Contributor")){
                    accountType = User.AccountTypes.CONTRIBUTOR;
                } else{
                    accountType = User.AccountTypes.ADMIN;
                }
                User new_user = UserFactory.factory(accountType, information, username, experience);

                if(Jobject.containsKey("favoriteProductions")) {
                    JSONArray favoriteProd = (JSONArray) Jobject.get("favoriteProductions");
                    for (Object prod : favoriteProd) {
                        String ProdName = (String) prod;
                        //System.out.println(ProdName);
                        new_user.addFavoriteProduction(ProdName);
                    }
                }

                if(Jobject.containsKey("favoriteActors")){
                    JSONArray favoriteActors = (JSONArray)Jobject.get("favoriteActors");
                    for(Object act : favoriteActors){
                        String ActorName = (String) act;
                        new_user.addFavoriteActor(ActorName);
                    }
                }

                if(accountType != User.AccountTypes.REGULAR){
                    Staff staffUser = (Staff) new_user;

                    if(Jobject.containsKey("productionsContribution")) {
                        JSONArray addedProductions = (JSONArray) Jobject.get("productionsContribution");
                        for (Object prod : addedProductions) {
                            String ProdName = (String) prod;
                            for (Production prodd : productions) {
                                if (prodd.title.equals(ProdName)) {
                                    staffUser.addedItems.add(prodd);
                                    break;
                                }
                            }
                        }
                    }

                    if(Jobject.containsKey("actorsContribution")) {
                        JSONArray addedActors = (JSONArray) Jobject.get("actorsContribution");
                        for (Object actor : addedActors) {
                            String ActorName = (String) actor;
                            for (Actor actorr : actors) {
                                if (actorr.name.equals(ActorName)) {
                                    staffUser.addedItems.add(actorr);
                                    break;
                                }
                            }
                        }
                    }
                }

                this.users.add(new_user);
                this.addObserver(new_user);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try{
            File file = new File("requests.json");
            FileReader reader = new FileReader(file);

            Object obj = jsonParser.parse(reader);
            JSONArray requestss = (JSONArray) obj;

            for(Object ob : requestss){
                JSONObject request = (JSONObject)ob;

                String type = (String)request.get("type");
                Request.RequestTypes requestType;
                if(type.equals("DELETE_ACCOUNT")){
                    requestType = Request.RequestTypes.DELETE_ACCOUNT;
                }else if(type.equals("OTHERS")){
                    requestType = Request.RequestTypes.OTHERS;
                }else if(type.equals("MOVIE_ISSUE")){
                    requestType = Request.RequestTypes.MOVIE_ISSUE;
                }else
                    requestType = Request.RequestTypes.ACTOR_ISSUE;

                String date = (String) request.get("createdDate");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDate dateTime = LocalDate.parse(date, formatter);

                String username = (String) request.get("username");
                String TitleOrName;
                if(requestType == Request.RequestTypes.ACTOR_ISSUE){
                    TitleOrName = (String) request.get("actorName");
                }
                else  if(requestType == Request.RequestTypes.MOVIE_ISSUE){
                    TitleOrName = (String) request.get("movieTitle");
                }else{
                    TitleOrName = null;
                }

                String usernamesolve = (String) request.get("to");
                String description = (String) request.get("description");
                boolean ok = true;
                Request new_request = new Request(requestType, dateTime, TitleOrName, description, username);
                User user = getUserByUsername(username);
                if(user.accountType == User.AccountTypes.CONTRIBUTOR){

                    Contributor contributor = (Contributor) user;
                    contributor.createRequest(new_request);
                }else{
                    Regular regular = (Regular) user;
                    regular.createRequest(new_request);
                }
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        addActorsFromProductions();
       loginPage = new LoginPage("Login");
    }

    public static IMDB getInstance(){
        if(instance == null){
            instance = new IMDB();
        }
        return instance;
    }
    public User getUserByUsername(String username){
        for(User user : IMDB.getInstance().users){
            if(user.username.equals(username)){
                return user;
            }
        }
        return null;
    }

    public void addRatingNotifications(){
        int i, j;
        for(Production prod : IMDB.getInstance().productions){
            for(i = 0; i < prod.ratings.size(); i++){
                User user = getUserByUsername(prod.ratings.get(i).username);
                for(j = i + 1; j < prod.ratings.size(); j++){
                    String notification = "User-ul " + prod.ratings.get(j).username + " a adaugat o recenzie pentru productia " + prod.title + " pe care si tu ai evaluat-o.";
                    user.notifications.add(notification);
                }
            }
        }
    }

    public void addAdderNotifications(){
        for(User user : IMDB.getInstance().users){
            if(user.accountType != User.AccountTypes.REGULAR){
                for(Object obj : ((Staff<?>) user).addedItems){
                    if(obj instanceof Production){
                        for(Rating rating : ((Production) obj).ratings){
                            if(!rating.username.equals(user.username)){
                                String notification = "User-ul " + rating.username + " a adaugat o recenzie pentru productia " + ((Production) obj).title + " pe care tu ai adaugat-o.";
                                user.notifications.add(notification);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean ContainsActor(String actorName){
        for(Actor actor : IMDB.getInstance().actors){
            if(actor.name.equals(actorName)) {
                return true;
            }
        }
        return false;
    }

    public void addActorsFromProductions(){
        int i;
        for(Production prod : IMDB.getInstance().productions){
            for(i = 0; i < prod.actors.size(); i++){
                if(!ContainsActor(prod.actors.get(i))){
                    Actor new_actor = new Actor(prod.actors.get(i), "");
                    if(prod instanceof Movie){
                        new_actor.addPerformance(prod.title, "Movie");
                    }else{
                        new_actor.addPerformance(prod.title, "Series");
                    }
                    IMDB.getInstance().actors.add(new_actor);

                    for(User user : IMDB.getInstance().users){
                        if(user.accountType == User.AccountTypes.ADMIN){
                            ((Admin)user).addedItems.add(new_actor);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public static void main(String[] args){
        IMDB.getInstance().run();
    }
}