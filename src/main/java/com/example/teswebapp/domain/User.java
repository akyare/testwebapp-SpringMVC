package com.example.teswebapp.domain;

import com.example.teswebapp.validation.PasswordMatches;
import com.example.teswebapp.validation.ValidPassword;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

//Spring security needs a table authorities with properties: username, authority
//Spring security needs a table users with properties: username, password, enabled
@Entity(name="User")
@Table(name="users")//Spring security needs a tables: authorities and users. Names of tables should be authorities and users.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@PasswordMatches
@DynamicUpdate
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    @NotBlank(message = "Name is mandatory")
    private String username;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user",cascade = CascadeType.ALL)
//    private List<Authority> authority;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name="user_authority",
            joinColumns = {@JoinColumn(name="USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="AUTH_ID", referencedColumnName = "ID")}
    )
    private List<Authority> authority;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<VerificationToken> verificationToken;

    //@NotBlank(message = "Name is mandatory")
    private String name;

    //@NotBlank(message = "Email is mandatory")
    //@Email(message = "Email should be valid")
    private String email;

    //@ValidPassword
    @Transient
    private String password;

    @Column(name = "password")
    private String encodedPassword;

    //@NotNull
    @Size(min = 1)
    @Transient
    private String confirmPassword;

    @Transient
    private String oldPassword;

    private Boolean isWriter;

    @Column(name = "enabled")
    private boolean enabled = false;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", encodedPassword='" + encodedPassword + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", isWriter=" + isWriter +
                ", enabled=" + enabled +
                '}';
    }
}
