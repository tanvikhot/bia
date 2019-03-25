package com.pixelround.myinsta;

public class Companies {
    private String name;
    private String logo;
    private Double equalityScore;
    private Double friendlinessScore;
    private Double familyScore;

    public Companies() {
    }

    public Companies(String name, String logo, Double equalityScore, Double friendlinessScore, Double familyScore) {
        this.name = name;
        this.logo = logo;
        this.equalityScore = equalityScore;
        this.friendlinessScore = friendlinessScore;
        this.familyScore = familyScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Double getEqualityScore() {
        return equalityScore;
    }

    public void setEqualityScore(Double equalityScore) {
        this.equalityScore = equalityScore;
    }

    public Double getFriendlinessScore() {
        return friendlinessScore;
    }

    public void setFriendlinessScore(Double friendlinessScore) {
        this.friendlinessScore = friendlinessScore;
    }

    public Double getFamilyScore() {
        return familyScore;
    }

    public void setFamilyScore(Double familyScore) {
        this.familyScore = familyScore;
    }
}
