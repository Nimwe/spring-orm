package fr.afpa.orm.dto;

import java.time.LocalDateTime;

/**
 * => implémenter un DTO (uniquement à partir de l'implémentation de la relation
 * "OneToMany")
 * 
 * Attention : il faudra peut être 1 DTO par classe métier ?
 * 
 * Plus d'informations sur la pattern DTO :
 * https://medium.com/@zubeyrdamar/java-spring-boot-handling-infinite-recursion-a95fe5a53c92
 */

public class AccountDto {
    private Long id;
    private LocalDateTime creationTime;
    private Long balance;
    private boolean active;

    // Constructeur vide obligatoire
    public AccountDto() {
    }

    // Getter et Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
