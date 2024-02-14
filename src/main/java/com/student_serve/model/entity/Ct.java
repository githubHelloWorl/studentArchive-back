package com.student_serve.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ct")
public class Ct {

  private long ctid;
  private long cid;
  private long tid;
  private String term;


  public long getCtid() {
    return ctid;
  }

  public void setCtid(long ctid) {
    this.ctid = ctid;
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


  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

}
