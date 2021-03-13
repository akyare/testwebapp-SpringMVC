package com.example.teswebapp.domain;

import com.example.teswebapp.validation.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


//Spring security needs a table authorities with properties: username, authority
//Spring security needs a table users with properties: username, password, enabled
@Entity(name="Authority")
@Table(name="authorities")//Spring security needs a tables: authorities and users. Names of tables should be authorities and users.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "username", insertable=false, updatable=false)
//    private User user;

    @OneToMany(mappedBy = "authority")
    private List<User> users;

    @NotBlank(message = "Authority is mandatory")
    private String authority;
}
