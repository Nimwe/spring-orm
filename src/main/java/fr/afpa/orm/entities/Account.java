package fr.afpa.orm.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Classe représentant le compte bancaire d'un utilisateur
 * 
 * TODO faire de cette classe une entité
 * Plus d'informations sur les entité JPA -> https://www.baeldung.com/jpa-entities
 * Attention de bien choisir les types en fonction de ceux du script SQL.
 */
@Entity
@Table(name="account")
public class Account {
    /**
     * Identifiant unique du compte
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    /**
     * Date de création du compte
     */
    @Column(name = "creationtime")
    private LocalDateTime creationTime;

    /**
     * Montant disponible
     */
    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * Association de type @ManyToOne : plusieurs comptes différents peuvent être associés à la même personne
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    /*
     * TODO implémenter un constructeur vide --> obligatoire pour l'utilisation d'un ORM
     */
    public Account() {
        // Constructeur vide pour permettre à Spring d'instancier les objets.
    }

    /*
     * TODO implémenter les getters et les setters
     */

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
