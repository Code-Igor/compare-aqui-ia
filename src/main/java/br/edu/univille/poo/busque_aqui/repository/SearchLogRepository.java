package br.edu.univille.poo.busque_aqui.repository;

import br.edu.univille.poo.busque_aqui.entity.SearchLog;
import org.springframework.data.jpa.repository.JpaRepository;

// Não precisa de implementação, o Spring Data JPA cuida disso
public interface SearchLogRepository extends JpaRepository<SearchLog, Long> {
}