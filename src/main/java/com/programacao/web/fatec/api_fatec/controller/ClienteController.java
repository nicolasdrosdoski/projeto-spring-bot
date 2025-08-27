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
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    
    private final List<Cliente> listaDeCliente = new ArrayList<>();

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteController() {
        listaDeCliente.add(new Cliente(94L, "Nicolas", "Rua João Barth"));
        
    }

    @GetMapping("/cpf/{idade}")
    public String clienteCpf(@PathVariable int idade){
        if(idade >= 18){
            return "Cliente maior de idade";
        } else if(idade < 0) {
            return "Ta morto!";
        } else{
            return "Cliente menor de idade";
        }
    }

    @GetMapping("/listar")
    public List<Cliente> listarClientes() {
        return listaDeCliente;
    }
    

    @GetMapping("/cnpj/{nome}")
    public String clienteCnpj(@PathVariable String nome){
        return "Cliente CNPJ: " + nome;
    }

    @PostMapping("")
    public Cliente createCliente(@RequestBody Cliente cliente){

        return cliente;
    }

    @PostMapping("/criarCliente")
    public List<Cliente> criarCliente(@RequestBody Cliente cliente) {
        listaDeCliente.add(new Cliente(cliente.getId(), cliente.getNome(), cliente.getEndereco()));
        return listaDeCliente;
    }

    @DeleteMapping("/deletar/{id}")
    public String deletarCliente(@PathVariable Long id){
        for (Cliente cliente : listaDeCliente) {
            if(cliente.getId() == id){
                listaDeCliente.remove(cliente);
                return id + ": deletado!";
            }
        }
        return "ID " + id + " não encontrado";
    }
    
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

    
}
