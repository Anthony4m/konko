package com.example.konko.transactions;

import com.example.konko.User.User;
import com.example.konko.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Transaction implements Serializable {
    public Transaction(Account account, Double amount, String description, Catergory catergory, String recipient, LocalDateTime createdAt, User userId) {
        this.account = account;
        this.amount = amount;
        this.description = description;
        this.catergory = catergory;
        this.recipient = recipient;
        this.createdAt = createdAt;
        this.userId = userId;
    }
    @Id
    @SequenceGenerator(
    name = "transaction_sequence",
    sequenceName = "transaction_sequence",
    allocationSize = 1
            )
    @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "transaction_sequence"
            )
    private Long id;
    @Column(
            nullable = false
    )
    private Double amount;
    @Column(
            nullable = false
    )
    private String description;
    @Column(
            nullable = false
    )
    @Enumerated(value = EnumType.STRING)
    private Catergory catergory;
    @Column(
            nullable = false
    )
    private String recipient;
    @Column(
            nullable = false
    )
    private LocalDateTime createdAt;
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
    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REFRESH},
            optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "account_id",
            referencedColumnName = "id"
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Account account;
}
