package fr.afpa.orm.repositories;

import org.springframework.data.repository.CrudRepository;


import fr.afpa.orm.entities.Account;

/**
 * => implémenter un "repository" (similaire à un DAO) permettant d'interagir avec les données de la BDD
 * Tutoriel -> https://www.geeksforgeeks.org/spring-boot-crudrepository-with-example/
 */
public interface AccountRepository extends CrudRepository<Account, Long> {

}
