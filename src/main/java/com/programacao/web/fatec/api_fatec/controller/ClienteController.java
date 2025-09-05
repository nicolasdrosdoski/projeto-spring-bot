package com.programacao.web.fatec.api_fatec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacao.web.fatec.api_fatec.domain.cidade.CidadeRepository;
import com.programacao.web.fatec.api_fatec.domain.cidade.ClienteService;
import com.programacao.web.fatec.api_fatec.domain.cliente.ClienteRepository;
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

    @Autowired
    private ClienteService clienteService;

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

    

    //* Leitura de clientes
    @GetMapping("/listar")
    public List<Cliente> listarClientes(){
        return clienteService.listarClientes();
    }

    //* Buscar por Nome ou ID
    @GetMapping("/buscaPorIdOuNome/{search}")
    public List<Cliente> buscaPorIdOuNomeGenerico(@PathVariable String search) {
        return clienteService.buscaPorIdOuNomeGenerico(search);
    }

    //* CRUD - Leitura de clientes por nome
    @GetMapping("/listar/{nome}")
    public List<Cliente> listarClientesPorNome(@PathVariable String nome){
        return clienteService.listarClientesPorNome(nome);
    }

    //* CRUD - Buscar por texto
    @GetMapping("/buscarPorTexto/")
    public List<Cliente> buscarPorTexto(@PathVariable String texto) {
        return clienteService.buscarPorTexto(texto);
    }

    //* CRUD - Criar Cliente
    @PostMapping("")
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteService.createCliente(cliente);
    }

    //* CRUD - Deletar cliente
    @DeleteMapping("/{id}")
    public String deletarCliente(@PathVariable Long id) {
        return clienteService.deletarCliente(id);
    }

    //* CRUD - Atualizar cliente
    @PutMapping("/{id}")
    public String alterarCliente(@PathVariable Long id, @RequestBody Cliente entity) {
        return clienteService.alterarCliente(id, entity);
    }
    
}
