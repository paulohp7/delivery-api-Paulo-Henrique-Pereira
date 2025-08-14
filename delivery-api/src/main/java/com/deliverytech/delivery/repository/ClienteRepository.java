package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Buscar cliente por email
    Optional<Cliente> findByEmail(String email);

    // Verificar se email já existe
    boolean existsByEmail(String email);

    // Buscar clientes ativos
    List<Cliente> findByAtivoTrue();

    // Buscar clientes por nome
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    // Buscar cliente por telefone
    Optional<Cliente> findByTelefone(String telefone);

    // Query customizada - clientes com pedidos
    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.pedidos p WHERE c.ativo = true")
    List<Cliente> findClientesComPedidos();

    // Query na va - clientes por cidade
    @Query(
        value = "SELECT * FROM clientes WHERE endereco LIKE %:cidade% AND ativo = true",
        nativeQuery = true
    )
    List<Cliente> ﬁndByCidade(@Param("cidade") String cidade);

    // Contar clientes a vos
    @Query("SELECT COUNT(c) FROM Cliente c WHERE c.ativo = true")
    Long countClientesAtivos();
}
