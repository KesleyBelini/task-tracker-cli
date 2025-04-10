package com.tasktrackercli.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Task {
    private final Integer id;
    private String descricao;
    private StatusTaskEnum status;
    private final LocalDateTime criadaEm;
    private LocalDateTime atualizadaEm;
    public static DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Task(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
        this.status = StatusTaskEnum.PENDENTE;
        this.criadaEm = LocalDateTime.now();
        this.atualizadaEm = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public StatusTaskEnum getStatus() {
        return status;
    }

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

    public LocalDateTime getAtualizadaEm() {
        return atualizadaEm;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
        this.atualizadaEm = LocalDateTime.now();
    }

    public void setStatus(StatusTaskEnum status) {
        this.status = status;
        this.atualizadaEm = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("""
                Task {
                  id=%d,
                  descrição='%s',
                  status='%s',
                  criadaEm=%s,
                  atualizadaEm=%s
                }""", id, descricao, status, criadaEm.format(FORMATADOR), atualizadaEm.format(FORMATADOR));
    }
}