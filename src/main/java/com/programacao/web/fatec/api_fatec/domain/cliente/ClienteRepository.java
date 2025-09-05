package com.programacao.web.fatec.api_fatec.domain.cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.programacao.web.fatec.api_fatec.entities.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    @Query("select c from Cliente c where c.cidade.id = :cidadeId")
    List<Cliente> findByNome(String nome);

    @Query("""
        SELECT c FROM Cliente c
        WHERE (:id IS NULL OR c.id = :id) OR LOWER(c.nome) LIKE LOWER (concat('%', :nome, '%')) 
            """)
    List<Cliente> buscarPorIdOuNome(@Param("id") Long id, @Param("nome") String nome);
}