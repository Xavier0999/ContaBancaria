package br.org.fundatec.conta_bancaria.repository;

import br.org.fundatec.conta_bancaria.model.Agencia;
import br.org.fundatec.conta_bancaria.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Integer> {

    Agencia findAgenciaByBanco(Banco banco);
    Agencia findAgenciaByNumero(Integer numero);
}
