package com.example.bankapi.repository;


import com.example.bankapi.entity.Account;
import com.example.bankapi.exception.NoSuchAccountException;
import com.example.bankapi.model.MoneyModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AccountRepositoryImplTest {

    @Autowired
    AccountRepositoryImpl accountRepository;

    @Test
    public void getById() {
        Account actualAcc = new Account(1, "14883228111122225555", 3000.0, 1);
        Account expectedAcc = accountRepository.getById(actualAcc.getId());
        assertEquals(expectedAcc, actualAcc);
    }

    @Test
    public void getByIdThrowsNoSuchAccExc() {
        Assertions.assertThrows(NoSuchAccountException.class, () -> accountRepository.getById(6));
    }

    @Test
    public void updateById() {
        Account actualAcc = new Account(1, "14883228111122225555", 5000.0, 1);
        Account expectedAcc = accountRepository.updateById(actualAcc.getId(), new MoneyModel(2000.0));
        assertEquals(expectedAcc, actualAcc);
    }
}