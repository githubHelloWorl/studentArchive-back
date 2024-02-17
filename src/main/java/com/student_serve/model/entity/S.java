package com.student_serve.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.student_serve.model.vo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "s")
public class S {

  private long sid;
  private String sname;
  private String password;


  public long getSid() {
    return sid;
  }

  public void setSid(long sid) {
    this.sid = sid;
  }


  public String getSname() {
    return sname;
  }

  public void setSname(String sname) {
    this.sname = sname;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public User toUser(){
    User user = new User();
    user.setId(this.sid);
    user.setName(this.sname);
    user.setType("student");
    return user;
  }

}
