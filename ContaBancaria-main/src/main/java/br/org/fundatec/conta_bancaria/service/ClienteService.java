package br.org.fundatec.conta_bancaria.service;

import br.org.fundatec.conta_bancaria.exception.RegistroNaoEncontradoException;
import br.org.fundatec.conta_bancaria.model.Banco;
import br.org.fundatec.conta_bancaria.model.Cliente;
import br.org.fundatec.conta_bancaria.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente salvar(@Valid Cliente cliente){
        return repository.save(cliente);
    }

    public Cliente buscarCliente(Integer id){
        Optional<Cliente> busca = repository.findById(id);
        return busca.orElseThrow(()-> new RegistroNaoEncontradoException("Cliente" + id + "não encontrado"));
    }
public void delete(Integer id){
        Cliente achar = this.buscarCliente(id);
        repository.delete(achar);
}
public List<Cliente> buscarPorNomeApp(String nome){
     return   repository.findClienteByNomeContains(nome);
}
    public Cliente editar(Integer id, Cliente cliente){

        Cliente buscarClientee = this.buscarCliente(id);

        buscarClientee.setNome(cliente.getNome());
        buscarClientee.setCpf(cliente.getCpf());
        return repository.save(buscarClientee);
    }
}
