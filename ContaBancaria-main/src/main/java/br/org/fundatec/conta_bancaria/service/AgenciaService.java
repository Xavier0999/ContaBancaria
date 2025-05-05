package br.org.fundatec.conta_bancaria.service;


import br.org.fundatec.conta_bancaria.exception.RegistroNaoEncontradoException;
import br.org.fundatec.conta_bancaria.model.Agencia;
import br.org.fundatec.conta_bancaria.model.Banco;
import br.org.fundatec.conta_bancaria.repository.AgenciaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenciaService {

    @Autowired
    private AgenciaRepository agenciaRepository;

    public Agencia salvar(@Valid Agencia agencia) {
        return agenciaRepository.save(agencia);
    }

    public Agencia buscarAgenciaPorId(Integer id) {
        Optional<Agencia> busca = agenciaRepository.findById(id);
        return busca.orElseThrow(() -> new RegistroNaoEncontradoException("Agencia " + id + " nao encontrada"));
    }

    public void remover(Integer id) {
        Agencia agenciaBuscar = this.buscarAgenciaPorId(id);
        agenciaRepository.delete(agenciaBuscar);
    }

    public Agencia buscarPorNumero(Integer numero) {
        return agenciaRepository.findAgenciaByNumero(numero);
    }

    public Agencia buscarPorBanco(Banco banco) {
        return agenciaRepository.findAgenciaByBanco(banco);
    }

    public Agencia editar(Integer id, Agencia agencia) {

        Agencia agenciaBusca = this.buscarAgenciaPorId(id);

        agenciaBusca.setNome(agencia.getNome());
        agenciaBusca.setNumero(agencia.getNumero());
        agenciaBusca.setBanco(agencia.getBanco());

        return agenciaRepository.save(agenciaBusca);
    }

}


