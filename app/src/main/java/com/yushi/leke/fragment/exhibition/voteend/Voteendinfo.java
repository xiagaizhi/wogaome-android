package com.yushi.leke.fragment.exhibition.voteend;

public class Voteendinfo {
    private String title;
    private String logo;
    private String address;
    private String industry;
    private String entrepreneur;
    private int votes;
    private int playCount;
    private String introduction;
    private String id;
    public Voteendinfo(){

    }
    public Voteendinfo(String title, String logo, String address, String industry, String entrepreneur, int votes, int playCount, String introduction, String id){
        this.title = title;
        this.logo=logo;
        this.address=address;
        this.industry=industry;
        this.entrepreneur=entrepreneur;
        this.votes=votes;
        this.playCount=playCount;
        this.introduction=introduction;
        this.id=id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setId(String id) {
        this.id = id;
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


    public void setTitle(String title) {

        this.title = title;
    }

    public String getId() {

        return id;
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
    public String getTitle() {

        return title;
    }
}

