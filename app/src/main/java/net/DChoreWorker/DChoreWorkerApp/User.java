package net.DChoreWorker.DChoreWorkerApp;

public class User {

    private int id;
    private String username, name, email, gender, category, location;

    public User(String name, String username, String email, String gender, String category, String location) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.category = category;
        this.location = location;
    }

    public String getName() {
        return name;
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

    public String getLocation() {
        return location;
    }
}