package net.DChoreWorker.DChoreWorkerApp;


public class BookedDataModel {


    String name;
    String email;
    String location;
    String mobile;
    String date;
    String time;
    String status;

    public BookedDataModel(String name, String email, String location, String mobile, String date, String time, String status) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.mobile=mobile;
        this.date=date;
        this.time=time;
        this.status=status;
    }


    public String getName() {
        return name;
    }


    public String getLocation() {
        return location;
    }


    public String getMobile() {
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

    public String getStatus() {
        return status;
    }


}