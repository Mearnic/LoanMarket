package com.market.loan.bean;

public class BaseRequest {
    String gender;
    String marital;
    String education;
    String email;
    String name;
    String birthday;

    public BaseRequest(){}

    public BaseRequest(UserResult userResult) {
        this.gender = userResult.gender;
        this.marital = userResult.marital;
        this.education = userResult.education;
        this.email = userResult.email;
        this.name = userResult.name;
        this.birthday = userResult.birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String checked() {
        if (name == null || name.equals("")) {
            return "Name not be empty";
        }
        if (gender == null || gender.equals("")) {
            return "Gender not be empty";
        }
        if (marital == null || marital.equals("")) {
            return "Marital not be empty";
        }
        if (education == null || education.equals("")) {
            return "Education not be empty";
        }
        if (email == null || email.equals("")) {
            return "Email not be empty";
        }
        if (birthday == null || birthday.equals("")) {
            return "Birthday not be empty";
        }
        return null;
    }
}
