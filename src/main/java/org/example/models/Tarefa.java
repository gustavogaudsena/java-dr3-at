package org.example.models;

import java.util.Date;
import java.util.UUID;

public class Tarefa {
    private UUID id;
    private String titulo;
    private String descricao;
    private boolean concluida;
    private Date dataCriacao;

    public Tarefa() {
    }

    public Tarefa(String titulo, String descricao) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = false;
        this.dataCriacao = new Date();
    }

    public void initialize() {
        if (this.id == null) this.id = UUID.randomUUID();
        if (this.dataCriacao == null) this.dataCriacao = new Date();
    }


    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }


}
