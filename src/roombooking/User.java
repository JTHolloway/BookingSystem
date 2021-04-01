/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roombooking;

/**
 *
 * @author James
 */
public class User {
    
    private int UserId;
    private String FirstName;
    private String Surname;
    private int Department;
    private String EmailAddress;
    private double Salary;
    private String Company;
    private String Password;

    public User(int UserId, String FirstName, String Surname, int Department, String EmailAddress, double Salary, String Company, String Password) {
        this.UserId = UserId;
        this.FirstName = FirstName;
        this.Surname = Surname;
        this.Department = Department;
        this.EmailAddress = EmailAddress;
        this.Salary = Salary;
        this.Company = Company;
        this.Password = Password;
    }

    public int getDepartment() {
        return Department;
    }

    public String getPassword() {
        return Password;
    }

    

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double Salary) {
        this.Salary = Salary;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String EmailAddress) {
        this.EmailAddress = EmailAddress;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    } 
    
}
