package com.ad.entity;


import org.springframework.util.DigestUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
//创建一个Entity Bean对象相当于新建一条记录，删除一个Entity Bean会同时从数据库中删除对应记录，修改一个Entity Bean时，容器会自动将Entity Bean的状态和数据库同步。
@Entity
// 此标记需要标注在类名前，不能标注在方法或属性前。name属性表示实体所对应表的名称，默认表名为实体的名称。
@Table(name = "admin_users")
public class AdminUser {
    //这是说Id是个自增主键,映射到你这个类中的String id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false, name = "username")
    private String userName;

    @Column(nullable = false, name = "sha_password")
    private String shaPassword;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_user_id")
    private Set<Role> roles;
    @Column
    private String name;
    @Column
    private String cardNo;
    @Column
    private String phone;
    @Column
    private String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();
    @Column(name = "updated_at", insertable = false)
    private Date updatedAt;

    public AdminUser() {
    }

    public AdminUser(String userName, String shaPassword) {
        this.userName = userName;
        this.shaPassword = shaPassword;
    }

    @PreUpdate
    protected void preUpdate() {
        this.setUpdatedAt(new Date());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShaPassword() {
        return shaPassword;
    }

    //为用户密码加密
    public void setShaPassword(String password) {
        this.shaPassword = DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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
}
