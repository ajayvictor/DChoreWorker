package net.DChoreWorker.DChoreWorkerApp;


public class BookedDataModel {


    String name;
    String email;
    String location;
    int mobile;
    double experience;
    String date;
    String time;

    public BookedDataModel(String name, String email, String location, int mobile, String date, String time) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.mobile=mobile;
        this.date=date;
        this.time=time;
    }


    public String getName() {
        return name;
    }


    public String getLocation() {
        return location;
    }


    public int getMobile() {
        return mobile;
    }


    public String getEmail() {
        return email;
    }


    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

}