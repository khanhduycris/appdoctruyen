package edu.fpt.apptruyentranh.data;

public class Users {
    private String email, user, passWord;

    public Users(String email, String user, String passWord) {
        this.email = email;
        this.user = user;
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
