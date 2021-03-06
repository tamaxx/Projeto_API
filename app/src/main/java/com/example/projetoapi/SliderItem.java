package com.example.projetoapi;

public class SliderItem {
    private String id;
    private String poster;
    private String title;
    private String year;
    private String gender;
    private String director;
    private String cast;
    private String plot;

    SliderItem(String id, String title, String poster, String year, String gender, String director, String cast, String plot){
        this.id = id;
        this.poster = poster;
        this.title = title;
        this.year = year;
        this.gender = gender;
        this.director = director;
        this.cast = cast;
        this.plot = plot;
    }

    public String getPoster(){return poster;}
    public String getTitle(){return title;}
    public String getGender(){return gender;}
}

