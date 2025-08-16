package com.example.orlobank.dto;

import java.util.List;

public class TransactionHistoryDTO {
    private List<TransactionDTO> from;
    private List<TransactionDTO> to;

    public TransactionHistoryDTO(List<TransactionDTO> from, List<TransactionDTO> to) {
        this.from = from;
        this.to = to;
    }

    public List<TransactionDTO> getFrom() { return from; }
    public List<TransactionDTO> getTo() { return to; }
}
