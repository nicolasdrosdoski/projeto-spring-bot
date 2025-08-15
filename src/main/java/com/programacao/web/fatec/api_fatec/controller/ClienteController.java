package com.programacao.web.fatec.api_fatec.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

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

    @GetMapping("/cnpj/{nome}")
    public String clienteCnpj(@PathVariable String nome){
        return "Cliente CNPJ: " + nome;
    }
}
