package net.DChore.DChoreApp;

public class User {

    private int id;
    private String username, email, gender, category;

    public User(int id, String username, String email, String gender, String category) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getCategory() {
        return category;
    }
}