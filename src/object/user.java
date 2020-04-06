package object;

public class user {
    int userId;
    String userName;
    String password;
    int permission;
    
    public user(){

    }

    public user(String userName,String password,int permission){
        this.userName = userName;
        this.password = password;
        this.permission = permission;
    }

    public user(int userId,String userName,String password,int permission){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
    }

    public int getUserId () {
        return userId;
    }

    public void setUserId (int userId) {
        this.userId = userId;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public int getPermission () {
        return permission;
    }

    public void setPermission (int permission) {
        this.permission = permission;
    }
}
