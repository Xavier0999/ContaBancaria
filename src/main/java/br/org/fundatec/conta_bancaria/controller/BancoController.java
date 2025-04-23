package br.org.fundatec.conta_bancaria.controller;

import br.org.fundatec.conta_bancaria.model.Banco;
import br.org.fundatec.conta_bancaria.service.BancoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/banco-api")
public class BancoController {

    @Autowired
    private BancoService service;


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Banco>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listarTodos());
    }

    @GetMapping(value = {"/find-id/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Banco> buscarPorId(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarBanco(id));
    }
    @GetMapping(value = {"/find-codigo/{codigo}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Banco> buscarPorCodigo(@PathVariable("codigo") Integer codigo){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorCodigo(codigo));
    }

  @GetMapping(value = {"consulta-nome"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Banco>> buscarPorNome(@RequestParam("nome") String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorNomeApr(nome));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Banco> adicionar(@RequestBody @Valid Banco banco) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(banco));
  }

  @PutMapping(value = {"id"}, consumes = {MediaType.APPLICATION_JSON_VALUE},
  produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Banco> editar (@PathVariable("id") Integer id, @RequestBody @Valid Banco banco){
        return ResponseEntity.status(HttpStatus.OK).body(service.editar(id,banco));
  }

  @DeleteMapping(value = {"id"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Banco> remover (@PathVariable("id") Integer id){
        service.remover(id);
        return ResponseEntity.status(HttpStatus.OK).build();
  }

}
