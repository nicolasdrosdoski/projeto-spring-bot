package com.programacao.web.fatec.api_fatec.domain.cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.programacao.web.fatec.api_fatec.entities.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    List<Cliente> findByNome(String nome);

    @Query("""
        select c from Cliente c
        where (:id is null or c.id = :id) or lower(c.nome) like lower (concat('%', :nome, '%')) 
            """)
    List<Cliente> buscarPorIdOuNome(@Param("id") Long id, @Param("nome") String nome);
}