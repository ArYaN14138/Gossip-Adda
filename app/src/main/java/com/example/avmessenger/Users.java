package com.example.avmessenger;

public class Users {

    private final Object profilepic;
    String Profilepic,email,userName,password,userid,lastmessage,status;

    public  Users(String id, String name, String email, String password, String cpassword, String imageURI, String status)
    {
        this.userid = id;
        this.userName = name;
        this.email = email;
        this.password = password;
        this.profilepic = Profilepic;
        this.status = status;
    }


    //press contol+return and go to getter and setter and select all the operations.

    public String getProfilepic() {
        return Profilepic;
    }

    public void setProfilepic(String profilepic) {
        Profilepic = profilepic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

