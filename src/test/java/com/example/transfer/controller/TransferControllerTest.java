package com.example.transfer.controller;

import com.example.transfer.model.Transfer;
import com.example.transfer.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransferController.class)
class TransferControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransferService service;

    @Autowired
    private ObjectMapper mapper;

    private Transfer exemplo;

    @BeforeEach
    void setUp() {
        exemplo = new Transfer();
        exemplo.setId(1L);
        exemplo.setOriginAccount("A");
        exemplo.setDestinationAccount("B");
        exemplo.setAmount(BigDecimal.valueOf(200));
        exemplo.setFee(BigDecimal.valueOf(12));
        exemplo.setSchedulingDate(LocalDate.now());
        exemplo.setTransferDate(LocalDate.now().plusDays(5));
    }

    @Test
    void listarDevolveListaVazia() throws Exception {
        when(service.listAll()).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/transfers"))
           .andExpect(status().isOk())
           .andExpect(content().json("[]"));
    }

    @Test
    void criarDevolveObjetoAgendado() throws Exception {
        when(service.schedule(exemplo)).thenReturn(exemplo);

        mvc.perform(post("/api/transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(exemplo))
        )
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1))
           .andExpect(jsonPath("$.originAccount").value("A"))
           .andExpect(jsonPath("$.fee").value(12.0));
    }
}
