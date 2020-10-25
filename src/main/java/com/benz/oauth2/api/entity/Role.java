package com.benz.oauth2.api.entity;

import com.benz.oauth2.api.db.ERole;
import com.benz.oauth2.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="role",schema = Schema.SECURITY,uniqueConstraints =
@UniqueConstraint(columnNames = "name")
)
@Getter
@Setter
public class Role {

    @Id
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name="name")
    private ERole name;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="permission_role",
            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id",referencedColumnName = "id")})
    private Set<Permission> permissions;
}
