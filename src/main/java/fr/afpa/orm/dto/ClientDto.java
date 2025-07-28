package fr.afpa.orm.dto;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;



/**
 * => implémenter un DTO (uniquement à partir de l'implémentation de la relation
 * "OneToMany")
 * 
 * Attention : il faudra peut être 1 DTO par classe métier ?
 * 
 * Plus d'informations sur la pattern DTO :
 * https://medium.com/@zubeyrdamar/java-spring-boot-handling-infinite-recursion-a95fe5a53c92
 */

public class ClientDto {
    private UUID id;
    private String first_name;
    private String last_name;
    private String email;
    private LocalDate birthdate;
    private List<AccountDto> accounts;

    // Constructeur vide obligatoire
    public ClientDto() {
    }

    // Getter et Setter
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<AccountDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDto> accounts) {
        this.accounts = accounts;
    }
}
