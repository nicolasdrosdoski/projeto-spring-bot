package com.programacao.web.fatec.api_fatec.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacao.web.fatec.api_fatec.domain.cliente.ClienteRepository;
import com.programacao.web.fatec.api_fatec.entities.Cliente;

import jakarta.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    
    private final List<Cliente> listaDeCliente = new ArrayList<>();

    @Autowired
    private ClienteRepository clienteRepository;

    // public ClienteController() {
    //     listaDeCliente.add(new Cliente(94L, "Nicolas", "Rua João Barth"));
    // }

    @PostConstruct
    public void dadosIniciais(){
        clienteRepository.save(new Cliente(null, "Nicolas", "Rua Barth 1"));
        clienteRepository.save(new Cliente(null, "Maria", "Rua Barth 2"));
        clienteRepository.save(new Cliente(null, "Eduarda", "Rua Barth 3"));
        clienteRepository.save(new Cliente(null, "Joao", "Rua Barth 4"));
    }

    

    //* CRUD - Leitura de clientes
    @GetMapping("/listar")
    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }

    //* CRUD - Leitura de clientes por nome
    @GetMapping("/{nome}")
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

    @DeleteMapping("/deletar/{id}")
    public String deletarCliente(@PathVariable Long id){
        try {
            clienteRepository.deleteById(id);
            return "Cliente deletado com sucesso";
        } catch (Exception e) {
            System.out.printf("Erro: %s", e);
            return "Erro ao deletar cliente";
        }
    }

    // @GetMapping("/cpf/{idade}")
    // public String clienteCpf(@PathVariable int idade){
    //     if(idade >= 18){
    //         return "Cliente maior de idade";
    //     } else if(idade < 0) {
    //         return "Ta morto!";
    //     } else{
    //         return "Cliente menor de idade";
    //     }
    // }


    // @GetMapping("/cnpj/{nome}")
    // public String clienteCnpj(@PathVariable String nome){
    //     return "Cliente CNPJ: " + nome;
    // }

    // @PostMapping("")
    // public Cliente createCliente(@RequestBody Cliente cliente){

    //     return cliente;
    // }

    // @PostMapping("/criarCliente")
    // public List<Cliente> criarCliente(@RequestBody Cliente cliente) {
    //     listaDeCliente.add(new Cliente(cliente.getId(), cliente.getNome(), cliente.getEndereco()));
    //     return listaDeCliente;
    // }

    @PutMapping("/atualizar/{id}")
    public String alterarCliente(@PathVariable Long id, @RequestBody Cliente entity) {
        for (Cliente cliente : listaDeCliente) {
            if (cliente.getId() == id) {
                cliente.setId(entity.getId());
                cliente.setNome(entity.getNome());
                return "Cliente alterado";
            }
        }
        return "Cliente não encontrado";
    }

    // @DeleteMapping("/deletar/{id}")
    // public String deletarCliente(@PathVariable Long id){
    //     for (Cliente cliente : listaDeCliente) {
    //         if(cliente.getId() == id){
    //             listaDeCliente.remove(cliente);
    //             return id + ": deletado!";

    //         }
    //     }
    //     return "ID " + id + " não encontrado";
    // }

    
    

    
}
