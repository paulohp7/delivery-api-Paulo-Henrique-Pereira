package com.deliverytech.delivery.service;

import com.deliverytech.delivery.model.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrar(Cliente cliente) {
        // Validar email único
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + cliente.getEmail());
        }

        // Validações de negócio
        validarDadosCliente(cliente);

        // Deﬁnir como ativo por padrão
        cliente.setAtivo(true);

        return clienteRepository.save(cliente);
    }

    // Buscar cliente por ID
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    // Buscar cliente por email
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    // Listar todos os clientes ativos
    @Transactional(readOnly = true)
    public List<Cliente> listarAtivos() {
        return clienteRepository.findByAtivoTrue();
    }

    // Atualizar dados do cliente
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        Cliente cliente = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        // Veriﬁcar se email não está sendo usado por outro cliente
        if (!cliente.getEmail().equals(clienteAtualizado.getEmail())
                && clienteRepository.existsByEmail(clienteAtualizado.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + clienteAtualizado.getEmail());
        }
        // Atualizar campos
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setEmail(clienteAtualizado.getEmail());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setEndereco(clienteAtualizado.getEndereco());

        return clienteRepository.save(cliente);
    }

    // Inativar cliente
    public void inativar(Long id) {
        Cliente cliente = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        cliente.setAtivo(false);
        // cliente.inativar();
        clienteRepository.save(cliente);
    }

    // Buscar clientes por nome
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Validação dos dados do cliente
    private void validarDadosCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (cliente.getNome().length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
        }
    }
}
