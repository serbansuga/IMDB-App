import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

abstract class Staff<T extends Comparable<T>> extends User implements StaffInterface {
    List<Request> requests;
    SortedSet<T> addedItems;

    public Staff(Information information, AccountTypes accountType, String username, int experience){
        super(information, accountType, username, experience);
        requests = new ArrayList<>();
        addedItems = new TreeSet<>();
    }

    public void addProductionSystem(Production p){
        IMDB.getInstance().productions.add(p);
        addedItems.add((T) p);
        super.strategy = new AddProdActorExperience();
        super.updateExperience();
    }

    public  void addActorSystem(Actor a){
        IMDB.getInstance().actors.add(a);
        addedItems.add((T) a);
        super.strategy = new AddProdActorExperience();
        super.updateExperience();
    }

    abstract public void removeProductionSystem(String name);
    abstract public void removeActorSystem(String name);
    public void updateProduction(Production p){
        int i;
        boolean ok = false;
        Production SearchedProd = null;

        for(i = 0; i < IMDB.getInstance().productions.size(); i++){
            if(IMDB.getInstance().productions.get(i).title.equals(p.title)){
                SearchedProd = IMDB.getInstance().productions.get(i);
                break;
            }
        }

        for (Object item : this.addedItems) {
            if (item == SearchedProd) {
                ok = true;
                this.addedItems.remove(item);
                break;
            }
        }

        if(ok) {
            IMDB.getInstance().productions.set(i, p);
            this.addedItems.add((T)p);
        }
    }
    public void updateActor(Actor a){
        int i;
        boolean ok = false;
        Actor SearchedActor = null;

        for(i = 0; i < IMDB.getInstance().actors.size(); i++){
            if(IMDB.getInstance().actors.get(i).name.equals(a.name)){
                SearchedActor = IMDB.getInstance().actors.get(i);
                break;
            }
        }

        for (Object item : this.addedItems) {
            if (item == SearchedActor) {
                ok = true;
                this.addedItems.remove(item);
                break;
            }
        }

        if(ok) {
            IMDB.getInstance().actors.set(i, a);
            this.addedItems.add((T) a);
        }
    }

    //Metoda pentru rezolvarea cererilor
}
