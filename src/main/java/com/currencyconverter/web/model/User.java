package com.currencyconverter.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name")
    @Length(min = 5, message = "*Your user name must have at least 5 characters")
    @NotEmpty(message = "*Please provide a user name")
    private String userName;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "first_name")
    @NotEmpty(message = "*Please provide your first name")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;

    @Column(name = "date_of_birth")
    @NotEmpty(message = "*Please choose Date of Birth")
    @DateTimeFormat( pattern="yyyy-MM-dd")
    private String dateOfBirth;

    @Column(name = "city")
    @NotEmpty(message = "*Please provide your city name")
    private String city;

    @Column(name = "address")
    @NotEmpty(message = "*Please provide your address")
    private String address;

    @Column(name = "stateOfResidence")
    @NotEmpty(message = "*Choose state")
    private String stateOfResidence;

    @Column(name = "country")
    @NotEmpty(message = "*Please provide your country")
    private String country;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles=roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address=address;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active=active;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city=city;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country=country;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth=dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName=userName;
    }

    public String getStateOfResidence() {
        return stateOfResidence;
    }

    public void setStateOfResidence(String stateOfResidence) {
        this.stateOfResidence=stateOfResidence;
    }
}
