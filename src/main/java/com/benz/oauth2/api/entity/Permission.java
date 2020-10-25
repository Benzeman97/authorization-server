package com.benz.oauth2.api.entity;

import com.benz.oauth2.api.db.EPermission;
import com.benz.oauth2.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PERMISSION",schema = Schema.SECURITY,uniqueConstraints = @UniqueConstraint(
        columnNames = "NAME"
))
@Getter
@Setter
public class Permission {

    @Id
    @Column(name="ID")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME")
    private EPermission name;
}
