package com.yushi.leke.fragment.exhibition.voteing;

public class Voteinginfo {
    private String title;
    private String video100Pic;
    private String address;
    private String industry;
    private String entrepreneur;
    private int votes;
    private int playCount;
    private String introduction;
    private String id;
    private String aliVideoId;

    public String getAliVideoId() {
        return aliVideoId;
    }

    public void setAliVideoId(String aliVideoId) {
        this.aliVideoId = aliVideoId;
    }

    public Voteinginfo(){

    }
    public Voteinginfo(String title, String video100Pic, String address, String industry, String entrepreneur, int votes, int playCount, String introduction, String id){
        this.title = title;
        this.video100Pic=video100Pic;
        this.address=address;
        this.industry=industry;
        this.entrepreneur=entrepreneur;
        this.votes=votes;
        this.playCount=playCount;
        this.introduction = introduction;
        this.id=id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIntroduction(String introduction) {

        this.introduction = introduction;
    }

    public void setPlayCount(int playCount) {

        this.playCount = playCount;
    }

    public void setVotes(int votes) {

        this.votes = votes;
    }

    public void setEntrepreneur(String entrepreneur) {

        this.entrepreneur = entrepreneur;
    }

    public void setIndustry(String industry) {

        this.industry = industry;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public void setVideo100Pic(String video100Pic) {

        this.video100Pic = video100Pic;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getId() {

        return id;
    }

    public String getIntroduction() {

        return introduction;
    }

    public int getPlayCount() {

        return playCount;
    }

    public int getVotes() {

        return votes;
    }

    public String getEntrepreneur() {

        return entrepreneur;
    }

    public String getIndustry() {

        return industry;
    }

    public String getAddress() {

        return address;
    }

    public String getVideo100Pic() {

        return video100Pic;
    }

    public String getTitle() {

        return title;
    }
}

