package com.healthy.healthy.repository;

import com.healthy.healthy.model.Alimenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentiRepository extends JpaRepository<Alimenti, Integer> {

}
