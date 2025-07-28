package fr.afpa.orm.web.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.afpa.orm.entities.Account;
import fr.afpa.orm.repositories.AccountRepository;
import jakarta.servlet.http.HttpServletResponse;

/**
 * => ajouter la/les annotations nécessaires pour faire de
 * "AccountRestController" un contrôleur de REST API
 */
@RestController
@RequestMapping("/accounts") // Pour ne pas mettre le parametre Account sur tous les Get et Post
public class AccountRestController {
    private final AccountRepository accountRepository;

    /*
     * => implémenter un constructeur
     * => injecter {@link AccountRepository} en dépendance par injection via le
     * constructeur
     * Plus d'informations ->
     * https://keyboardplaying.fr/blogue/2021/01/spring-injection-constructeur/
     */
    public AccountRestController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * => implémenter une méthode qui traite les requêtes GET et qui renvoie une
     * liste de comptes
     * Attention, il manque peut être une annotation :)
     * 
     * => récupération des compte provenant d'un repository
     * 
     * => renvoyer les objets de la classe "Account"
     * return null;
     * }
     * 
     */

    @GetMapping
    public List<Account> getAllAccounts() {
        return StreamSupport
                .stream(accountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * => implémenter une méthode qui traite les requêtes GET avec un identifiant
     * "variable de chemin" et qui retourne les informations du compte associé
     * Plus d'informations sur les variables de chemin ->
     * https://www.baeldung.com/spring-pathvariable
     * 
     * TODO serait-il possible de renvoyer plutôt un "AccountDTO" dans la "ResponseEntity" ?
     * la déclaration de la fonction deviendrait : public ResponseEntity<AccountDTO> getOne(@PathVariable long id)
     * Pour se faire il faudra instancier un nouvel objet de DTO à partir de account
     * 
     * Pour plus d'informations sur le concept de DTO : https://www.axopen.com/blog/2023/09/dto-definition-avantage/
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getOne(@PathVariable long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * => implémenter une méthode qui traite les requêtes POST
     * Cette méthode doit recevoir les informations d'un compte en tant que "request
     * body", elle doit sauvegarder le compte en mémoire et retourner ses
     * informations (en json)
     * Tutoriel intéressant -> https://stackabuse.com/get-http-post-body-in-spring/
     * Le serveur devrai retourner un code http de succès (201 Created)
     * 
    * TODO serait-il possible de renvoyer plutôt un "AccountDTO" dans la "ResponseEntity" ?
     **/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    /**
     * => implémenter une méthode qui traite les requêtes PUT
     * 
     * Attention de bien ajouter les annotations qui conviennent
     * 
     * TODO serait-il possible de renvoyer plutôt un "AccountDTO" dans la "ResponseEntity" ?
     */
    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable long id, @RequestBody Account updateAccount) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            // Mise à jour des champs
            account.setBalance(updateAccount.getBalance());
            account.setActive(updateAccount.isActive());
            account.setCreationTime(updateAccount.getCreationTime());
            account.setClient(updateAccount.getClient());

            return ResponseEntity.ok(accountRepository.save(account));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * => implémenter une méthode qui traite les requêtes DELETE
     * L'identifiant du compte devra être passé en "variable de chemin" (ou "path
     * variable")
     * Dans le cas d'un suppression effectuée avec succès, le serveur doit retourner
     * un status http 204 (No content)
     * 
     * Il est possible de modifier la réponse du serveur en utilisant la méthode
     * "setStatus" de la classe HttpServletResponse pour configurer le message de
     * réponse du serveur
     */
    @DeleteMapping("/{id}")
    public void remove(@PathVariable long id, HttpServletResponse response) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); // Erreur 204
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Erreur 404
        }
    }

}
