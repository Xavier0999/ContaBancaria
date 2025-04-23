package br.org.fundatec.conta_bancaria.repository;

import br.org.fundatec.conta_bancaria.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BancoRepository  extends JpaRepository<Banco, Integer> {

   List<Banco> findBancoByNomeContains(String nome);
   Banco findBancoByCodigo(Integer codigo);
}
