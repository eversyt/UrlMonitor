package com.example.monitor;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime data;
    private String host;
    private Integer response;

    public State() {
    }

    public State(LocalDateTime data, String host, Integer response) {
        this.data = data;
        this.host = host;
        this.response = response;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(final LocalDateTime data) {
        this.data = data;
    }

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(final Integer response) {
        this.response = response;
    }
}
