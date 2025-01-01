package user;

public abstract class User {
    protected String userID;
    protected String username;

    // Constructor
    public User(String userID, String username) {
        this.userID = userID;
        this.username = username;
    }

    // Shared Methods
    public void login() {
        System.out.println(username + " (ID: " + userID + ") has logged in.");
    }

    public void logout() {
        System.out.println(username + " (ID: " + userID + ") has logged out.");
    }

    // Abstract Methods
    public abstract void performRole();
}

