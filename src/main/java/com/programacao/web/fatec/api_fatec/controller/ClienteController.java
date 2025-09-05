package com.programacao.web.fatec.api_fatec.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacao.web.fatec.api_fatec.domain.cidade.CidadeRepository;
import com.programacao.web.fatec.api_fatec.domain.cliente.ClienteRepository;
import com.programacao.web.fatec.api_fatec.domain.cliente.dto.BuscaPorIdOuNomeDto;
import com.programacao.web.fatec.api_fatec.entities.Cidade;
import com.programacao.web.fatec.api_fatec.entities.Cliente;
import com.programacao.web.fatec.api_fatec.entities.Estado;

import jakarta.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @PostConstruct
    public void dadosIniciais(){
        // Criando 5 cidades de exemplo com diferentes estados
        Cidade saoPaulo = cidadeRepository.save(new Cidade(null, "São Paulo", Estado.SP));
        Cidade rioDeJaneiro = cidadeRepository.save(new Cidade(null, "Rio de Janeiro", Estado.RJ));
        Cidade beloHorizonte = cidadeRepository.save(new Cidade(null, "Belo Horizonte", Estado.MG));
        Cidade salvador = cidadeRepository.save(new Cidade(null, "Salvador", Estado.BA));
        Cidade fortaleza = cidadeRepository.save(new Cidade(null, "Fortaleza", Estado.CE));

        // Criando clientes associados às cidades
        // Observe como o relacionamento é estabelecido passando a cidade como parâmetro
        clienteRepository.save(new Cliente(null, "Danilo", "Av. Paulista, 1000", saoPaulo));
        clienteRepository.save(new Cliente(null, "Maria", "Rua Copacabana, 500", rioDeJaneiro));
        clienteRepository.save(new Cliente(null, "João", "Av. Afonso Pena, 123", beloHorizonte));
        clienteRepository.save(new Cliente(null, "Ana", "Rua Chile, 45", salvador));
        clienteRepository.save(new Cliente(null, "Pedro", "Av. Beira Mar, 789", fortaleza));
    }

    

    //* CRUD - Leitura de clientes
    @GetMapping("/listar")
    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }

    //* CRUD - Leitura de clientes por nome
    @GetMapping("/listar/{nome}")
    public List<Cliente> listarClientesPorNome(@PathVariable String nome){
        return clienteRepository.findByNome(nome);
    }

    //* CRUD - Criar Cliente
    @PostMapping("/criarCliente")
    public String criarCliente(@RequestBody Cliente cliente){
        try {
            clienteRepository.save(cliente);
        return "Cliente salvo";
        } catch (Exception e) {
            System.out.printf("Erro: %s", e);
            return "Erro ao salvar cliente";
        }
    }

    @GetMapping("/buscaPorIdOuNome/{search}")
    public List<Cliente> buscaPorIdOuNomeGenerico(@PathVariable String search) {
        Long id = null;
        try {
            id = Long.parseLong(search);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return clienteRepository.buscarPorIdOuNome(id, search);
    }

    @PostMapping("/buscarIdOuNome")
    public List<Cliente> buscarPorIdOuNome(@RequestBody BuscaPorIdOuNomeDto dto){
        return clienteRepository.buscarPorIdOuNome(dto.getId(), dto.getNome());
    }

    //* CRUD - Atualizar cliente
    @PutMapping("/atualizar")
    public String atualizarCliente(@RequestBody Cliente cliente){
        try {
            clienteRepository.save(cliente);
            return "Cliente atualizado com sucesso";
        } catch (Exception e) {
            System.out.printf("Erro: %s", e);
            return "Erro ao atualizar cliente";
        }
    }

    //* CRUD FORMA 2 - Atualizar cliente
    @PutMapping("/atualizar/{id}")
    public String alterarCliente(@PathVariable Long id, @RequestBody Cliente entity) {
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(id);
        if (!clienteEncontrado.isPresent()) {
            return String.format("Cliente não encontrado: {}", id);
        }
        
        entity.setId(id);
        clienteRepository.save(entity);
        return "Cliente alterado";
    }

    //* CRUD - Deletar por ID
    @DeleteMapping("/deletar/{id}")
    public String deletarCliente(@PathVariable Long id){
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(id);
        if (!clienteEncontrado.isPresent()) {
            return String.format("Cliente não encontrado: %d", id);
        }
        
        clienteRepository.deleteById(id);
        return "Cliente deletado";
    }

    @GetMapping("/buscarPorIdNome/{search}")
    public List<Cliente> buscarPorIdNomeGenerico(@PathVariable String search){
        Long id = null;
        try {
            id = Long.parseLong(search);
        } catch (Exception e) {
            
        }
        return clienteRepository.buscarPorIdOuNome(id, search);
    }
    
}
