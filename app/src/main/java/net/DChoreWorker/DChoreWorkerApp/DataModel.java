package net.DChoreWorker.DChoreWorkerApp;


public class DataModel {


    String name;
    String job;
    String place;
    int age;
    String mobile;
    double experience;

    public DataModel(String name, String job, int age, String place, String mobile, double experience) {
        this.name = name;
        this.job = job;
        this.age = age;
        this.place = place;
        this.mobile=mobile;
        this.experience=experience;
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


    public String getMobile() {
        return mobile;
    }


    public int getAge() {
        return age;
    }
}