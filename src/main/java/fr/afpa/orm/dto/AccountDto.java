package fr.afpa.orm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import fr.afpa.orm.entities.User;

public record AccountDto(Long id, LocalDateTime creationTime, BigDecimal balance, User owner) {
}
