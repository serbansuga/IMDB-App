public class UserFactory{
    public static User factory(User.AccountTypes accountType, User.Information information, String username, int experience ){
        if(accountType == User.AccountTypes.REGULAR){
            return new Regular(information, accountType, username, experience);
        }
        if(accountType == User.AccountTypes.CONTRIBUTOR){
            return new Contributor(information, accountType, username, experience);
        }
        if(accountType == User.AccountTypes.ADMIN){
            return new Admin(information, accountType, username, experience);
        }
        return null;
    }
}
