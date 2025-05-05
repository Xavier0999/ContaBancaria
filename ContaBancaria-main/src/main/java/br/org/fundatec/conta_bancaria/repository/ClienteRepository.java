package br.org.fundatec.conta_bancaria.repository;

import br.org.fundatec.conta_bancaria.model.Agencia;
import br.org.fundatec.conta_bancaria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findClienteByNomeContains(String nome);
}
