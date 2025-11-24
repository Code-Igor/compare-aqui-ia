package br.edu.univille.poo.busque_aqui.repository;

import br.edu.univille.poo.busque_aqui.entity.SearchLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchLogRepository extends JpaRepository<SearchLog, Long> {
}