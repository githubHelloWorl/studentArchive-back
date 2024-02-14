package com.student_serve.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sct")
public class Sct {

  private long sctid;
  private long sid;
  private long cid;
  private long tid;
  private double grade;
  private String term;


  public long getSctid() {
    return sctid;
  }

  public void setSctid(long sctid) {
    this.sctid = sctid;
  }


  public long getSid() {
    return sid;
  }

  public void setSid(long sid) {
    this.sid = sid;
  }


  public long getCid() {
    return cid;
  }

  public void setCid(long cid) {
    this.cid = cid;
  }


  public long getTid() {
    return tid;
  }

  public void setTid(long tid) {
    this.tid = tid;
  }


  public double getGrade() {
    return grade;
  }

  public void setGrade(double grade) {
    this.grade = grade;
  }


  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

}
