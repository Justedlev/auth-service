package com.justedlev.auth.repository.entity;

import com.justedlev.auth.enumeration.UserStatusCode;
import com.justedlev.auth.repository.entity.base.BaseEntity;
import com.justedlev.auth.repository.entity.json.LastHash;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
//@NamedEntityGraph(name = "UserEntity.account-roles",
//        attributeNodes = {
//                @NamedAttributeNode("roles"),
//                @NamedAttributeNode("account")
//        })
public class UserEntity extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id", length = 36)
    private UUID id;
    @Column(name = "username", nullable = false)
    private String username;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private UserStatusCode status = UserStatusCode.ACTUAL;
    @Column(name = "hash_code", nullable = false, columnDefinition = "text")
    private String hashCode;
    @Builder.Default
    @Type(type = "jsonb")
    @Column(name = "last_hashes", columnDefinition = "jsonb")
    private List<LastHash> lastHashes = new LinkedList<>();
    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ToString.Exclude
    private List<Role> roles;
    @OneToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "account_id")
    @ToString.Exclude
    private Account account;

    public void addLastHash(String hashCode) {
        Optional.ofNullable(hashCode)
                .filter(StringUtils::isNotBlank)
                .map(current -> LastHash.builder()
                        .hashCode(current)
                        .build())
                .ifPresent(this.lastHashes::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
