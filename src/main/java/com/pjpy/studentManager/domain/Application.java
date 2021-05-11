package com.pjpy.studentManager.domain;

public class Application {
    public static int LEAVE_STATUS_WAIT = 0;//等待审核
    public static int LEAVE_STATUS_AGREE = 1;//同意
    public static int LEAVE_STATUS_DISAGREE = -1;//不同意
    private int id;
    private String sn;
    private String applicant;
    private String award;
    private String material;
    private int status = LEAVE_STATUS_WAIT;//申请状态
    private double grade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
