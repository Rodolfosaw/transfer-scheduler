package com.example.transfer.controller;

import com.example.transfer.model.Transfer;
import com.example.transfer.service.TransferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
@CrossOrigin(origins = "*")
public class TransferController {
    private final TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping
    public Transfer create(@RequestBody Transfer t) {
        return service.schedule(t);
    }

    @GetMapping
    public List<Transfer> all() {
        return service.listAll();
    }
}
