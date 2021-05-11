package com.pjpy.studentManager.domain;


public class Score {

    private int id;
    private String sn;
    private String course;
    private double grade;
    private double credit;
    private double point;

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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", sn='" + sn + '\'' +
                ", course='" + course + '\'' +
                ", grade=" + grade +
                ", credit=" + credit +
                ", point=" + point +
                '}';
    }
}
