package net.DChore.DChoreApp;


public class BookedDataModel {


    String name;
    String job;
    String place;
    int age;
    int mobile;
    double experience;
    String date;
    String time;

    public BookedDataModel(String name, String job, int age, String place, int mobile, double experience, String date, String time) {
        this.name = name;
        this.job = job;
        this.age = age;
        this.place = place;
        this.mobile=mobile;
        this.experience=experience;
        this.date=date;
        this.time=time;
    }


    public String getName() {
        return name;
    }


    public String getJob() {
        return job;
    }


    public String getPlace() {
        return place;
    }


    public Double getExperience() {
        return experience;
    }


    public int getMobile() {
        return mobile;
    }


    public int getAge() {
        return age;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

}