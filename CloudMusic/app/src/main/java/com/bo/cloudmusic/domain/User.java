package com.bo.cloudmusic.domain;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * 用户模型
 */
public class User extends BaseMultiItemEntity{

    /**
     * 省
     */
    private String province;
    /**
     * 省编码
     */
    @SerializedName("province_code")
    private String provinceCode;
    /**
     * 市
     */
    private String city;
    /**
     * 市编码
     */
    @SerializedName("city_code")
    private String cityCode;
    /**
     * 区
     */
    private String area;
    /**
     * 区编码
     *
     * SerializedName是GSON框架的功能
     * 所以如果使⽤其他JSON框架可能不⽀持
     * 更多的功能这⾥就不讲解了
     *
     * 作⽤是指定序列化和反序列化时字段
     * 也就说说在JSON中该字段为area_code
     * 当然也可以不使⽤这个功能
     * 字段就定义为area_code
     * 只是在Java中推荐使⽤驼峰命名法
     *
     */
    @SerializedName("area_code")
    private String areaCode;

    /**
     * 头像
     */
    private String avatar;
    /**
     * ⽣⽇
     *
     * 格式为yyyy-MM-dd
     */
    private String birthday;

    /**
     * 描述
     */
    private String description;

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
    /**
     * 验证码
     *
     * ⽤于找回密码时向服务端传递验证码
     */
    private String code;
    /**
     * 关注我的⼈（粉丝）
     */
    @SerializedName("followers_count")
    private long followersCount = 0;
    /**
     * 我关注的⼈（好友）
     */
    @SerializedName("followings_count")
    private long followingsCount = 0;
    /**
     * 性别
     *
     * 0：保密，10：男，20：⼥
     */
    private int gender = 0;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * QQ第三⽅登录Id(服务端加密了的)
     */
    private String qq_id;
    /**
     * 微博第三⽅登录Id(服务端加密了的)
     */
    private String weibo_id;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(long followersCount) {
        this.followersCount = followersCount;
    }

    public long getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(long followingsCount) {
        this.followingsCount = followingsCount;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getQq_id() {
        return qq_id;
    }

    public void setQq_id(String qq_id) {
        this.qq_id = qq_id;
    }

    public String getWeibo_id() {
        return weibo_id;
    }

    public void setWeibo_id(String weibo_id) {
        this.weibo_id = weibo_id;
    }

    public String getDescriptionFormat(){
        if(TextUtils.isEmpty(description)){
            return "这个人很懒，没有填写个人介绍!";
        }
        return description;
    }
}
