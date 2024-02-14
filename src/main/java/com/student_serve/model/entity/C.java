package com.student_serve.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "c")
public class C {

  private long cid;
  private String cname;
  private long ccredit;


  public long getCid() {
    return cid;
  }

  public void setCid(long cid) {
    this.cid = cid;
  }


  public String getCname() {
    return cname;
  }

  public void setCname(String cname) {
    this.cname = cname;
  }


  public long getCcredit() {
    return ccredit;
  }

  public void setCcredit(long ccredit) {
    this.ccredit = ccredit;
  }

}
