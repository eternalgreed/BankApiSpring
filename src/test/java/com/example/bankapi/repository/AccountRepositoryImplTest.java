package com.example.bankapi.repository;


import com.example.bankapi.dto.input.AccountDTO;
import com.example.bankapi.dto.input.MoneyDTO;
import com.example.bankapi.entity.Account;
import com.example.bankapi.exception.NoSuchAccountException;
import com.example.bankapi.exception.NoSuchClientException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AccountRepositoryImplTest {

    @Autowired
    AccountRepositoryImpl accountRepository;

    @Test
    void getById() {
        Account actualAcc = new Account(1, "14883228111122225555", 3000.0, 1);
        Account expectedAcc = accountRepository.getById(actualAcc.getId());
        assertEquals(expectedAcc, actualAcc);
    }

    @Test
    void getByIdThrowsNoSuchAccExc() {
        assertThrows(NoSuchAccountException.class, () -> accountRepository.getById(6));
    }

    @Test
    void updateById() {
        Account actualAcc = new Account(1, "14883228111122225555", 5000.0, 1);
        Account expectedAcc = accountRepository.updateById(actualAcc.getId(), new MoneyDTO(2000.0));
        assertEquals(expectedAcc, actualAcc);
    }

    @Test
    void updateByIdNoSuchAccExc() {
        assertThrows(NoSuchAccountException.class, () ->
                accountRepository.updateById(5,
                        new MoneyDTO(2000.0)));
    }

    @Test
    void createSuccess() {
        AccountDTO dto = new AccountDTO(1);
        Account expected = new Account(4, "1238712368172331", 0.0, 1);
        Account actual = accountRepository.create(dto);
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "number");
    }

    @Test
    void createException() {
        AccountDTO dto = new AccountDTO(5);
        Account expected = new Account(4, "1238712368172331", 0.0, 1);
        assertThrows(NoSuchClientException.class, () -> accountRepository.create(dto));
        // assertThatThrownBy(() -> accountRepository.create(dto)).isInstanceOf(NoSuchClientException.class);

    }
}