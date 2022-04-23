package com.bo.cloudmusic.domain;

/**
 * 用户模型
 */
public class User extends BaseModel{
    /**
     * ⼿机号
     */
    private String phone;
    /**
     * 邮件
     */
    private String email;
    /**
     * ⽤户的密码,登录，注册向服务端传递
     */
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
