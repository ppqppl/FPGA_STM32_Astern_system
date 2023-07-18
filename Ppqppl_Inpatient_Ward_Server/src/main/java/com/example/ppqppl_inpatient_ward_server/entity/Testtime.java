package com.example.ppqppl_inpatient_ward_server.entity;

import javax.persistence.*;

@Entity
@Table(name="testtime")
public class Testtime {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    public int code;
    public String id;
    public String time;
    public int  num;
    public String distance;
    public String score;

    public Testtime() {
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
