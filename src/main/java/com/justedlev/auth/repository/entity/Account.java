package com.justedlev.auth.repository.entity;

import com.justedlev.auth.enumeration.AccountStatusCode;
import com.justedlev.auth.enumeration.Gender;
import com.justedlev.auth.enumeration.ModeType;
import com.justedlev.auth.repository.entity.base.BaseEntity;
import com.justedlev.auth.repository.entity.json.PhoneNumberInfo;
import com.justedlev.auth.repository.entity.json.Photo;
import com.justedlev.auth.util.DateTimeUtils;
import com.justedlev.auth.util.Generator;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "accounts")
//@NamedEntityGraph(name = "Account.user", attributeNodes = @NamedAttributeNode("user"))
public class Account extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "account_id", length = 36)
    private UUID id;
    @Column(name = "nick_name", nullable = false)
    private String nickname;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private Timestamp birthDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Email
    @Column(name = "email", nullable = false)
    private String email;
    @Type(type = "jsonb")
    @Column(name = "phone_number_info", columnDefinition = "jsonb")
    private PhoneNumberInfo phoneNumberInfo;
    @Type(type = "jsonb")
    @Column(name = "photo", columnDefinition = "jsonb")
    private Photo photo;
    @Builder.Default
    @Column(name = "activation_code", length = 32, nullable = false, unique = true)
    private String activationCode = Generator.generateActivationCode();
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private AccountStatusCode status = AccountStatusCode.UNCONFIRMED;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false)
    private ModeType mode = ModeType.OFFLINE;
    @Builder.Default
    @Column(name = "mode_at")
    private Timestamp modeAt = DateTimeUtils.nowTimestamp();
    @OneToOne(mappedBy = "account",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    @ToString.Exclude
    private UserEntity user;

    public void setMode(ModeType mode) {
        this.mode = mode;
        this.modeAt = DateTimeUtils.nowTimestamp();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
