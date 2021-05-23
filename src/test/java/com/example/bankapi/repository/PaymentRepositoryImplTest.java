package com.example.bankapi.repository;

import com.example.bankapi.dto.input.PaymentDTO;
import com.example.bankapi.entity.Payment;
import com.example.bankapi.exception.NoSuchAccountException;
import com.example.bankapi.exception.PaymentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class PaymentRepositoryImplTest {

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    void updateByIdSuccess() {
        Payment paymentExpected = new Payment(1, 1000.0, true, 1, 2);
        Payment paymentActual = paymentRepository.updateById(1);
        assertEquals(paymentExpected, paymentActual);
    }

    @Test
    void updateByIdException() {
        assertThrows(PaymentException.class, () -> paymentRepository.updateById(3));
        assertThrows(PaymentException.class, () -> paymentRepository.updateById(2));
    }

    @Test
    void createSuccess() {
        Payment paymentActual = new Payment(4, 322.0, false, 2, 3);
        PaymentDTO dto = new PaymentDTO(4, 322.0, 2, 3);
        Payment paymentExpected = paymentRepository.create(dto);
        assertEquals(paymentExpected, paymentActual);
    }

    @Test
    void createException() {
        PaymentDTO dto = new PaymentDTO(4, 322.0, 2, 7);
        assertThrows(NoSuchAccountException.class, () -> paymentRepository.create(dto));
    }

    @Test
    void findByIdSuccess() {
        Payment paymentExpected = new Payment(1, 1000.0, false, 1, 2);
        Payment paymentActual = paymentRepository.findById(1);
        assertEquals(paymentExpected, paymentActual);
    }

    @Test
    void findByIdException() {
        assertThrows(PaymentException.class, () -> paymentRepository.updateById(8));
    }
}