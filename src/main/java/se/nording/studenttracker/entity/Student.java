package se.nording.studenttracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name="last_name")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email required")
    @Email(message = "Valid email is required")
    private String email;

    @Pattern(regexp="^\\+?[0-9\\s.()-]{7,20}$", message = "Invalid phone number")
    private String phone;

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
