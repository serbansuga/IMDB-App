import java.util.ArrayList;
import java.util.List;

 class RequestHolder{
    static List<Request> requestList = new ArrayList<>();

    public static void addRequest(Request request){
        requestList.add(request);
    }

    public void deleteRequest(Request request){
        this.requestList.remove(request);
    }
}
