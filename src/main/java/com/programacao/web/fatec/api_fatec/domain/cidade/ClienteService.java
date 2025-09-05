package com.programacao.web.fatec.api_fatec.domain.cidade;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.programacao.web.fatec.api_fatec.domain.cliente.ClienteRepository;
import com.programacao.web.fatec.api_fatec.entities.Cidade;
import com.programacao.web.fatec.api_fatec.entities.Cliente;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    //* LISTAR TODOS OS CLIENTES
    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }

    //* BUSCAR CLIENTE POR ID OU NOME
    public List<Cliente> buscaPorIdOuNomeGenerico(String search) {
        Long id = null;
        try {
            id = Long.parseLong(search);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return clienteRepository.buscarPorIdOuNome(id, search);
    }

    //* LISTAR CLIENTES POR NOME
    public List<Cliente> listarClientesPorNome(String nome){
        return clienteRepository.findByNome(nome);
    }

    //* CRUD - Buscar por texto
    public List<Cliente> buscarPorTexto(String texto) {
        // Tenta converter o texto para Long para buscar por ID
        Long idLong = null;
        try {
            idLong = Long.parseLong(texto);
        } catch (NumberFormatException e) {
            // Se não for possível converter para Long, deixa idLong como null
            System.out.println("Texto não pode ser convertido para Long: " + texto);
        }

        // Chama o método do repositório com o texto e o possível ID
        return clienteRepository.buscarPorIdOuNomeComCidade(texto, idLong);
    }

    //* CRIAR CLIENTE
    public Cliente createCliente(Cliente cliente) {
        cliente.setId(null);

        // Verifica se o cliente tem uma cidade associada
        if (cliente.getCidade() != null && cliente.getCidade().getId() != null) {
            // Busca a cidade pelo ID
            Optional<Cidade> cidadeOpt = cidadeRepository.findById(cliente.getCidade().getId());

            // Se a cidade existir, associa ao cliente
            if (cidadeOpt.isPresent()) {
                cliente.setCidade(cidadeOpt.get());
            } else {
                // Se a cidade não existir, remove a associação
                cliente.setCidade(null);
            }
        }

        Cliente clienteCreated = clienteRepository.save(cliente);
        return clienteCreated;
    }

    //* DELETAR CLIENTE
    public String deletarCliente(Long id) {
        //1º exemplo 
        //clienteRepository.deleteById(id);

        //2 exemplo
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(id);
        if (clienteEncontrado.isPresent()) {
            clienteRepository.deleteById(id);
            return "Cliente Deletado";
        }

        return "NÃO ENCONTRADO ID:"+id;
    }

    //* ATUALIZAR CLIENTE
    public String alterarCliente(Long id, Cliente entity) {
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(id);
        if (!clienteEncontrado.isPresent()) {
            return String.format("NÃO ENCONTRADO ID: %s", id);
        }

        entity.setId(id);

        // Verifica se o cliente tem uma cidade associada
        if (entity.getCidade() != null && entity.getCidade().getId() != null) {
            // Busca a cidade pelo ID
            Optional<Cidade> cidadeOpt = cidadeRepository.findById(entity.getCidade().getId());

            // Se a cidade existir, associa ao cliente
            if (cidadeOpt.isPresent()) {
                entity.setCidade(cidadeOpt.get());
            } else {
                // Se a cidade não existir, remove a associação
                entity.setCidade(null);
            }
        }

        clienteRepository.save(entity);
        return "Cliente Atualizado com sucesso";
    }

}
