package com.example.konko.account;

import com.example.konko.User.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "account_table",uniqueConstraints = {
        @UniqueConstraint(name = "account_number_unique_constraint", columnNames = "account_Number")
})
public class Account implements Serializable {

    public Account(String accountName, String accountNumber, LocalDateTime createdAt, boolean active, Double balance, AccountType accountType, User userId) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.createdAt = createdAt;
        this.active = active;
        this.balance = balance;
        this.accountType = accountType;
        this.userId = userId;
    }

    @Id
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String accountName;
    @Column(
            name = "account_number",
            nullable = false
    )
    private String accountNumber;
    @Column(
            nullable = false
    )
    private LocalDateTime createdAt;
    @Column(
            nullable = false
    )
    private boolean active;
    private Double balance;
    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false
    )
    private AccountType accountType;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REFRESH},
            optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User userId;

}
