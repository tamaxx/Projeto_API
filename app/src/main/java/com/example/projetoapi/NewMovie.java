package com.example.projetoapi;

public class NewMovie {
    private int newID_F;
    private String newTitle;
    private String newPoster;
    private String newPlot;
    private String newCast;
    private int newYear;
    private String newProducer;
    private String newDuration;
    private int newID_D;

    public NewMovie(int id_f, String title, String poster, String plot, String cast, int year, String producer, String duration, int id_d){
        this.newID_F = id_f;
        this.newTitle = title;
        this.newPoster = poster;
        this.newPlot = plot;
        this.newCast = cast;
        this.newYear = year;
        this.newProducer = producer;
        this.newDuration = duration;
        this.newID_D = id_d;
    }

    public int getNewID_F(){
        return newID_F;
    }
    public String getNewTitle(){
        return newTitle;
    }
    public String getNewPoster(){
        return newPoster;
    }
    public String getNewPlot(){
        return newPlot;
    }
    public String getNewCast(){
        return newCast;
    }
    public int getNewYear(){
        return newYear;
    }
    public String getNewProducer(){
        return newProducer;
    }
    public String getNewDuration(){
        return newDuration;
    }
    public int getNewID_D(){
        return newID_D;
    }
}
