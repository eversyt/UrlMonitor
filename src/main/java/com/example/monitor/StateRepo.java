package com.example.monitor;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface StateRepo extends CrudRepository<State, Long> {
   public List<State> findAllByOrderByDataDesc();
}
