package com.yushi.leke.fragment.exhibition.voteing.allproject;

public class Allprojectsinfo {
    private String title;
    private String video100Pic;
    private String address;
    private String industry;
    private String entrepreneur;
    private int votes;
    private int playCount;
    private String id;
    public Allprojectsinfo(){

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo100Pic() {
        return video100Pic;
    }

    public void setVideo100Pic(String video100Pic) {
        this.video100Pic = video100Pic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getEntrepreneur() {
        return entrepreneur;
    }

    public void setEntrepreneur(String entrepreneur) {
        this.entrepreneur = entrepreneur;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Allprojectsinfo(String logo) {
        this.video100Pic=logo;
    }
}
