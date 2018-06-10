package model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "test", catalog = "")
public class UserEntity {
    private int id;
    private String nickname;
    private String password;
    private String firstName;
    private String lastName;
    private Collection<model.BlogEntity> blogsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nickname", nullable = false, length = 45)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(password, that.password) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nickname, password, firstName, lastName);
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<model.BlogEntity> getBlogsById() {
        return blogsById;
    }

    public void setBlogsById(Collection<model.BlogEntity> blogsById) {
        this.blogsById = blogsById;
    }
}
