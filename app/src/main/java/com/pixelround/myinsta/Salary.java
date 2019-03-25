package com.pixelround.myinsta;

public class Salary {
    private String name, jobType;
    private Integer menSalaryL, menSalaryH, womenSalaryL, womenSalaryH;

    public Salary() {
    }

    public Salary(String name, String jobType, Integer menSalaryL, Integer menSalaryH, Integer womenSalaryL, Integer womenSalaryH) {
        this.name = name;
        this.jobType = jobType;
        this.menSalaryL = menSalaryL;
        this.menSalaryH = menSalaryH;
        this.womenSalaryL = womenSalaryL;
        this.womenSalaryH = womenSalaryH;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Integer getMenSalaryL() {
        return menSalaryL;
    }

    public void setMenSalaryL(Integer menSalaryL) {
        this.menSalaryL = menSalaryL;
    }

    public Integer getMenSalaryH() {
        return menSalaryH;
    }

    public void setMenSalaryH(Integer menSalaryH) {
        this.menSalaryH = menSalaryH;
    }

    public Integer getWomenSalaryL() {
        return womenSalaryL;
    }

    public void setWomanSalaryL(Integer womanSalaryL) {
        this.womenSalaryL = womanSalaryL;
    }

    public Integer getWomenSalaryH() {
        return womenSalaryH;
    }

    public void setWomanSalaryH(Integer womanSalaryH) {
        this.womenSalaryH = womanSalaryH;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "name='" + name + '\'' +
                ", jobType='" + jobType + '\'' +
                ", menSalaryL=" + menSalaryL +
                ", menSalaryH=" + menSalaryH +
                ", womanSalaryL=" + womenSalaryL +
                ", womanSalaryH=" + womenSalaryH +
                '}';
    }
}
