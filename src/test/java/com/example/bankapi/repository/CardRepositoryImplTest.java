package com.example.bankapi.repository;

import com.example.bankapi.entity.Card;
import com.example.bankapi.exception.NoSuchAccountException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CardRepositoryImplTest {
    @Autowired
    CardRepositoryImpl cardRepository;

    @Test
    public void getAllByAccountId() {
        List<Card> cardsExpected = new ArrayList<>(Arrays.asList(
                new Card(1, "1488322845651234", 1, false),
                new Card(2, "3228148845671234", 1, false)));
        List<Card> cardsActual = cardRepository.getAllByAccountId(1);
        assertEquals(cardsExpected, cardsActual);
    }

    @Test
    public void getAllByAccountIdEmptyList() {
        List<Card> cardsExpected = Collections.emptyList();
        List<Card> cardsActual = cardRepository.getAllByAccountId(6);
        assertEquals(cardsExpected, cardsActual);
    }


    @Test
    public void createByAccountIdSuccess() {
        Card byAccountId = cardRepository.createByAccountId(1);
        int idActual = byAccountId.getId();
        assertEquals(3, idActual);
    }

    @Test
    public void crateByAccountIdNoSuchAccExc() {
        assertThrows(NoSuchAccountException.class, () -> cardRepository.createByAccountId(6));
    }

}