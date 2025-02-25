package com.example1ipc2.app.model;

import java.sql.Date;

/**
 *
 * @author elvis
 */
public class UserModel {

    private Integer id;
    private String name;
    private String email;
    private String address;
    private String dpi;
    private String password;
    private Integer roleId;
    private String state;
    private Date createAt;

    public UserModel() {
    }

    public UserModel(String name, String email, String address, String dpi, String password, Integer roleId) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.dpi = dpi;
        this.password = password;
        this.roleId = roleId;
    }

    public UserModel(Integer id, String name, String email, String address, String dpi, String password, Integer roleId, String state, Date createAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.dpi = dpi;
        this.password = password;
        this.roleId = roleId;
        this.state = state;
        this.createAt = createAt;
    }
    
    

    @Override
    public String toString() {
        return "UserModel{" + "id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + ", dpi=" + dpi + ", password=" + password + ", roleId=" + roleId + ", state=" + state + ", createAt=" + createAt + '}';
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
    
}
