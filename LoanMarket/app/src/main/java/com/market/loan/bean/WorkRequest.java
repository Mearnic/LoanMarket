package com.market.loan.bean;

public class WorkRequest {
    String employment_type;
    String monthly_salary;
    String monthly_family_salary;

    public WorkRequest() {


    }

    public WorkRequest(UserResult userResult) {

        this.employment_type = userResult.employmentType;
        this.monthly_salary = userResult.monthlySalary;
        this.monthly_family_salary = userResult.monthlyFamilySalary;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public void setEmployment_type(String employment_type) {
        this.employment_type = employment_type;
    }

    public String getMonthly_salary() {
        return monthly_salary;
    }

    public void setMonthly_salary(String monthly_salary) {
        this.monthly_salary = monthly_salary;
    }

    public String getMonthly_family_salary() {
        return monthly_family_salary;
    }

    public void setMonthly_family_salary(String monthly_family_salary) {
        this.monthly_family_salary = monthly_family_salary;
    }

    public String checked() {
        if (employment_type == null || employment_type.equals("")) {
            return "Employment type not be empty";
        }
        if (monthly_salary == null || monthly_salary.equals("")) {
            return "Monthly salary not be empty";
        }
        if (monthly_family_salary == null || monthly_family_salary.equals("")) {
            return "Monthly family salary not be empty";
        }
        return null;
    }
}
