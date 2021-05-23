package com.example.bankapi.repository;

import com.example.bankapi.dto.input.ClientDTO;
import com.example.bankapi.dto.input.CounterPartyDTO;
import com.example.bankapi.entity.Client;
import com.example.bankapi.exception.DuplicateCounterPartyException;
import com.example.bankapi.exception.NoSuchClientException;
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
class ClientRepositoryImplTest {
    @Autowired
    ClientRepository clientRepository;

    @Test
    void getAllByClientIdSuccess() {
        List<Client> counterPartiesExpected = new ArrayList<>(Arrays.asList(
                new Client(2, "Petrov Petr")
        ));
        List<Client> actualCounterParties = clientRepository.getAllByClientId(1);
        assertEquals(counterPartiesExpected, actualCounterParties);
    }

    @Test
    void getAllByClientIdEmpty() {
        List<Client> cardsExpected = Collections.emptyList();
        List<Client> cardsActual = clientRepository.getAllByClientId(2);
        assertEquals(cardsExpected, cardsActual);
    }

    @Test
    void createCounterPartySuccess() {
        CounterPartyDTO partyDTO = new CounterPartyDTO(1, 3);
        Client clientExpected = new Client(3, "Zhmishenko Valeriy");
        Client clientActual = clientRepository.create(partyDTO);
        assertEquals(clientExpected, clientActual);
    }

    @Test
    void createCounterPartyException() {
        CounterPartyDTO duplicateExDTO = new CounterPartyDTO(1, 2);
        assertThrows(DuplicateCounterPartyException.class, () -> clientRepository.create(duplicateExDTO));
        CounterPartyDTO clientExDTO = new CounterPartyDTO(1, 8);
        assertThrows(NoSuchClientException.class, () -> clientRepository.create(clientExDTO));
    }

    @Test
    void createClient() {
        ClientDTO dto = new ClientDTO("Zxc Asd");
        Client clientExpected = new Client(4, "Zxc Asd");
        Client clientActual = clientRepository.create(dto);
        assertEquals(clientExpected, clientActual);
    }
}