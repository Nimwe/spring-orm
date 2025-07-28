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
import fr.afpa.orm.repositories.ClientRepository;

import fr.afpa.orm.dto.AccountDto;
import fr.afpa.orm.mappers.AccountMapper;

/**
 * => ajouter la/les annotations nécessaires pour faire de
 * "AccountRestController" un contrôleur de REST API
 */
@RestController
@RequestMapping("/accounts") // Pour ne pas mettre le parametre Account sur tous les Get et Post
public class AccountRestController {
    /*
     * => implémenter un constructeur
     * => injecter {@link AccountRepository} en dépendance par injection via le
     * constructeur
     * Plus d'informations ->
     * https://keyboardplaying.fr/blogue/2021/01/spring-injection-constructeur/
     */
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountRestController(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
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
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = StreamSupport
                .stream(accountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        return AccountMapper.toDtoList(accounts);
    }

    /**
     * => implémenter une méthode qui traite les requêtes GET avec un identifiant
     * "variable de chemin" et qui retourne les informations du compte associé
     * Plus d'informations sur les variables de chemin ->
     * https://www.baeldung.com/spring-pathvariable
     * 
     * => serait-il possible de renvoyer plutôt un "AccountDTO" dans la
     * "ResponseEntity" ?
     * la déclaration de la fonction deviendrait : public ResponseEntity<AccountDTO>
     * getOne(@PathVariable long id)
     * Pour se faire il faudra instancier un nouvel objet de DTO à partir de account
     * 
     * Pour plus d'informations sur le concept de DTO :
     * https://www.axopen.com/blog/2023/09/dto-definition-avantage/
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getOne(@PathVariable long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        return optionalAccount
                .map(account -> ResponseEntity.ok(AccountMapper.toDto(account)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * => implémenter une méthode qui traite les requêtes POST
     * Cette méthode doit recevoir les informations d'un compte en tant que "request
     * body", elle doit sauvegarder le compte en mémoire et retourner ses
     * informations (en json)
     * Tutoriel intéressant -> https://stackabuse.com/get-http-post-body-in-spring/
     * Le serveur devrai retourner un code http de succès (201 Created)
     * 
     * => serait-il possible de renvoyer plutôt un "AccountDTO" dans la
     * "ResponseEntity" ?
     **/
    @PostMapping
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto dto) {
        Account account = AccountMapper.toEntity(dto);

        if (dto.getClient() != null && dto.getClient().getId() != null) {
            clientRepository.findById(dto.getClient().getId()).ifPresent(account::setClient);
        }

        Account saved = accountRepository.save(account);
        return new ResponseEntity<>(AccountMapper.toDto(saved), HttpStatus.CREATED);
    }

    /**
     * => implémenter une méthode qui traite les requêtes PUT
     * 
     * Attention de bien ajouter les annotations qui conviennent
     * 
     * => serait-il possible de renvoyer plutôt un "AccountDTO" dans la
     * "ResponseEntity" ?
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> update(@PathVariable long id, @RequestBody AccountDto dto) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Account account = optionalAccount.get();
        account.setBalance(dto.getBalance());
        account.setCreationTime(dto.getCreationTime());
        account.setActive(dto.isActive());

        if (dto.getClient() != null && dto.getClient().getId() != null) {
            clientRepository.findById(dto.getClient().getId()).ifPresent(account::setClient);
        }

        Account updated = accountRepository.save(account);
        return ResponseEntity.ok(AccountMapper.toDto(updated));
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
    public ResponseEntity<Void> remove(@PathVariable long id) {
        if (!accountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        accountRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

}
