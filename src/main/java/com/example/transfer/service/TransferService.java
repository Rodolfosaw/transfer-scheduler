package com.example.transfer.service;

import com.example.transfer.model.Transfer;
import com.example.transfer.repository.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class TransferService {
    private final TransferRepository repo;

    public TransferService(TransferRepository repo) {
        this.repo = repo;
    }

    public Transfer schedule(Transfer t) {
        LocalDate today = LocalDate.now();
        t.setSchedulingDate(today);
        long days = ChronoUnit.DAYS.between(today, t.getTransferDate());
        BigDecimal fee = calculateFee(days, t.getAmount());
        t.setFee(fee);
        return repo.save(t);
    }

    private BigDecimal calculateFee(long days, BigDecimal amount) {
        if (days < 0 || days > 50)
            throw new ResponseStatusException(BAD_REQUEST, "Prazo inválido ou taxa não aplicável");

        if (days == 0) {
            return BigDecimal.valueOf(3.00).add(amount.multiply(BigDecimal.valueOf(0.025)));
        }
        if (days >= 1 && days <= 10) {
            return BigDecimal.valueOf(12.00);
        }
        if (days <= 20) {
            return amount.multiply(BigDecimal.valueOf(0.082));
        }
        if (days <= 30) {
            return amount.multiply(BigDecimal.valueOf(0.069));
        }
        if (days <= 40) {
            return amount.multiply(BigDecimal.valueOf(0.047));
        }
        // days <=50
        return amount.multiply(BigDecimal.valueOf(0.017));
    }

    public java.util.List<Transfer> listAll() {
        return repo.findAll();
    }
}
