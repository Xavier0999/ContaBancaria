package br.org.fundatec.conta_bancaria.controller;

import br.org.fundatec.conta_bancaria.model.Agencia;
import br.org.fundatec.conta_bancaria.model.Banco;
import br.org.fundatec.conta_bancaria.repository.BancoRepository;
import br.org.fundatec.conta_bancaria.service.AgenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/agencia-api")
public class AgenciaController {

    @Autowired
    private AgenciaService service;

    @Autowired
    BancoRepository bancoRepository;

    @GetMapping(value = {"/find-id/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Agencia> buscarPorId(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarAgenciaPorId(id));
    }
    @GetMapping(value = {"/find-numero/{numero}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Agencia> buscarPorCodigo(@PathVariable("numero") Integer numero){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorNumero(numero));
    }
    @GetMapping(value = {"/find-banco/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Agencia> buscarPorBanco(@PathVariable("id") Banco banco){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorBanco(banco));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Agencia> adicionar(@RequestBody @Valid Agencia agencia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(agencia));
    }

    @PutMapping(value = {"/{id}"}, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Agencia> editar (@PathVariable("id") Integer id, @RequestBody @Valid Agencia agencia){
        return ResponseEntity.status(HttpStatus.OK).body(service.editar(id,agencia));
    }

    @DeleteMapping(value = {"/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Agencia> remover (@PathVariable("id") Integer id){
        service.remover(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
