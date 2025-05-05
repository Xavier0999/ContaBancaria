package br.org.fundatec.conta_bancaria.controller;

import br.org.fundatec.conta_bancaria.model.Agencia;
import br.org.fundatec.conta_bancaria.model.Banco;
import br.org.fundatec.conta_bancaria.model.Cliente;
import br.org.fundatec.conta_bancaria.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "cliente-api")
public class ClienteController {

    @Autowired
    private ClienteService service;


    @GetMapping(value = {"/find-id/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Cliente> buscarPorId(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarCliente(id));
    }

    @GetMapping(value = {"consulta-nome"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity <List<Cliente>> buscarPorNome(@RequestParam("nome") String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorNomeApp(nome));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> adicionar(@RequestBody @Valid Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(cliente));
    }

    @PutMapping(value = {"/{id}"}, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Cliente> editar (@PathVariable("id") Integer id, @RequestBody @Valid Cliente cliente){
        return ResponseEntity.status(HttpStatus.OK).body(service.editar(id,cliente));
    }

    @DeleteMapping(value = {"/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Agencia> remover (@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
