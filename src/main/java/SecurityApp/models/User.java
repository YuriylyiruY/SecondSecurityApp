package SecurityApp.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;


@Entity
@Table(name = "Person")
public class User {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;

    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "age")
    private int age;

    @Column(name = "email")
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "last_name")
    @NotEmpty
    private String last_name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Person_Auth"
            , joinColumns = @JoinColumn(name = "for_person_id")
            , inverseJoinColumns = @JoinColumn(name = "for_auth_id")
    )
    private Set<Auth> auths = new HashSet<>();


    // Конструктор по умолчанию нужен для Spring
    public User() {
    }

    public User(int person_id, String name, int age, String email, String password, String last_name) {
        this.person_id = person_id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.last_name = last_name;
    }

    public Set<Auth> getAuths() {
        return auths;
    }


    public void setAuths(Auth auth) {
        auths.add(auth);

    }

    public @NotEmpty String getLast_name() {
        return last_name;
    }

    public void setLast_name(@NotEmpty String last_name) {
        this.last_name = last_name;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void deleteAuth(String s) {
        // System.out.println (cont.getRole ());
        auths.removeIf(cont -> Objects.equals(cont.getRole(), s));
    }


    public void getStringAuthFromForm(String s) {
        // rolesForTimeleaf.add(s);
        System.out.println(s);
        System.out.println(s);


    }


    @Min(value = 0, message = "Age should be greater than 0")
    public int getAge() {
        return age;
    }

    public void setAge(@Min(value = 0, message = "Age should be greater than 0") int age) {
        this.age = age;
    }

    public @NotEmpty(message = "Email should not be empty") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "Email should not be empty") @Email String email) {
        this.email = email;
    }

    public int getId() {
        return person_id;
    }

    public void setId(int person_id) {
        this.person_id = person_id;
    }


    public @NotEmpty(message = "Name should not be empty") @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Name should not be empty") @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters") String name) {
        this.name = name;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "person_id=" + person_id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", auths=" + auths +
                '}';
    }
}