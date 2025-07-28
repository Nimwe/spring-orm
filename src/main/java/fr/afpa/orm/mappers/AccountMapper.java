package fr.afpa.orm.mappers;

import java.util.stream.Collectors;
import java.util.List;

import fr.afpa.orm.dto.AccountDto;
import fr.afpa.orm.dto.ClientDto;
import fr.afpa.orm.entities.Account;

public class AccountMapper {

    public static List<AccountDto> toDtoList(List<Account> accounts) {
        return accounts.stream()
                .map(AccountMapper::toDto)
                .collect(Collectors.toList());
    }

    public static AccountDto toDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setCreationTime(account.getCreationTime());
        dto.setBalance(account.getBalance());
        dto.setActive(account.isActive());

        if (account.getClient() != null) {
            ClientDto clientDto = new ClientDto();
            clientDto.setId(account.getClient().getId());
            clientDto.setFirst_name(account.getClient().getFirstName());
            clientDto.setLast_name(account.getClient().getLastName());
            clientDto.setEmail(account.getClient().getEmail());
            clientDto.setBirthdate(account.getClient().getBirthdate());

            dto.setClient(clientDto); // Passage au clientDto
        }

        return dto;
    }

    public static Account toEntity(AccountDto dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setCreationTime(dto.getCreationTime());
        account.setBalance(dto.getBalance());
        account.setActive(dto.isActive());

        return account;
    }
}
