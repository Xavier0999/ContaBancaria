package br.org.fundatec.conta_bancaria.service;


import br.org.fundatec.conta_bancaria.exception.RegistroNaoEncontradoException;
import br.org.fundatec.conta_bancaria.model.Banco;
import br.org.fundatec.conta_bancaria.repository.BancoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    public Banco salvar(@Valid Banco banco){
        return bancoRepository.save(banco);
    }

    public Banco buscarBanco(Integer id){
        Optional<Banco> busca = bancoRepository.findById(id);
        return busca.orElseThrow(()-> new RegistroNaoEncontradoException("Banco" + id + "não encontrado"));
    }

    public void remover(Integer id){
        Banco bancoBusca = this.buscarBanco(id);
        bancoRepository.delete(bancoBusca);
    }

    public List<Banco> listarTodos(){
        return StreamSupport.stream(bancoRepository.findAll().spliterator(), false).toList();
    }

    public List<Banco> buscarPorNomeApr(String nome){
        return  bancoRepository.findBancoByNomeContains(nome);
    }

    public Banco buscarPorCodigo(Integer codigo){
        return bancoRepository.findBancoByCodigo(codigo);
    }

    public Banco editar(Integer id, Banco banco){

        Banco bancoBuscaa = this.buscarBanco(id);

        bancoBuscaa.setNome(banco.getNome());
        bancoBuscaa.setCodigo(banco.getCodigo());
        bancoBuscaa.setCnpj(banco.getCnpj());
        return bancoRepository.save(bancoBuscaa);
    }

}
