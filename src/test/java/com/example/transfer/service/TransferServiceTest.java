package com.example.transfer.service;

import com.example.transfer.model.Transfer;
import com.example.transfer.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    private TransferRepository repo;
    private TransferService service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(TransferRepository.class);
        service = new TransferService(repo);
    }

    @Test
    void quandoDiasZero_deveAplicarTaxa3Mais2_5Percento() {
        Transfer t = new Transfer();
        t.setAmount(BigDecimal.valueOf(1000));
        t.setTransferDate(LocalDate.now());

        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        Transfer saved = service.schedule(t);

        BigDecimal expectedFee = BigDecimal.valueOf(3.00)
                .add(BigDecimal.valueOf(1000).multiply(BigDecimal.valueOf(0.025)));
        assertThat(saved.getFee()).isEqualByComparingTo(expectedFee);
        verify(repo).save(saved);
    }

    @Test
    void quandoDiasEntre1e10_deveAplicarTaxa12Reais() {
        Transfer t = new Transfer();
        t.setAmount(BigDecimal.valueOf(500));
        t.setTransferDate(LocalDate.now().plusDays(5));

        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));
        Transfer saved = service.schedule(t);

        assertThat(saved.getFee()).isEqualByComparingTo(BigDecimal.valueOf(12.00));
    }

    @Test
    void quandoPrazoInvalido_lancaBadRequest() {
        Transfer t = new Transfer();
        t.setAmount(BigDecimal.valueOf(10));
        t.setTransferDate(LocalDate.now().plusDays(100));

        assertThrows(
            org.springframework.web.server.ResponseStatusException.class,
            () -> service.schedule(t)
        );
        verify(repo, never()).save(any());
    }
}
