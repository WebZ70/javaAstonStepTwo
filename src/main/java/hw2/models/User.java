package hw2.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime created_at;
    private String name;
    private String email;
    private Integer age;

    public User(){this.created_at = LocalDateTime.now();}
    public User(String name, String email, Integer age) {
        this.created_at = LocalDateTime.now();
        this.name = name;
        this.email = email;
        this.age = age;
    }

    //TODO Оптимизировать геттеры и сеттеры
    public int getId() {return id;}

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    //TODO END

    public String toString() {
        return String.format("%-5d %-15s %-20s %-30s %-5d", id, created_at, name, email, age);
    }
}
