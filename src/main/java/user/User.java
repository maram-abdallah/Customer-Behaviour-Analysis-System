package user;

public abstract class User {
    protected String userID;
    protected String username;

    public User(String userID, String username) {
        this.userID = userID;
        this.username = username;
    }

    // Abstract methods for login and logout
    public abstract void login();

    public abstract void logout();

    // Getter methods
    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }
}
