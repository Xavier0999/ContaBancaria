package br.org.fundatec.conta_bancaria.controller;


import br.org.fundatec.conta_bancaria.ContaBancariaApplication;
import br.org.fundatec.conta_bancaria.exception.RegistroNaoEncontradoException;
import br.org.fundatec.conta_bancaria.exception.handler.ErroResponse;
import br.org.fundatec.conta_bancaria.model.Banco;
import br.org.fundatec.conta_bancaria.service.BancoService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.http.HttpStatus;

import static org.mockito.ArgumentMatchers.eq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest(classes = ContaBancariaApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BancoControllerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BancoService service;

    private static final Integer ID_INSERIR = 1;

    private Banco build(Integer id, Integer codigo,String nome, Integer cnpj) {
        return new Banco(id, codigo, nome, cnpj);
    }

    @Test
    void testaAdicaoBanco() throws Exception{
        Banco banco = build(ID_INSERIR, 11, "Banrisul", 12345678);

        doAnswer(invocation -> {
            return banco;
        }).when(service).salvar(eq(banco));

        MvcResult mvcResult = mockMvc.perform(post("/banco-api").contentType("application/json")
                .content(MAPPER.writeValueAsString(banco))).andExpect(status().is(HttpStatus.CREATED.value())).andReturn();

        Banco retorno = parseResponse(mvcResult, Banco.class);

        verify(service, times(1)).salvar(eq(banco));

        assertThat("Não retornou a ID Correto", retorno.getId(), equalTo(ID_INSERIR));
    }

    @Test
    void testaAdicaoIdErrado() throws Exception{
        Banco banco = build(null, 11, "Banrisul", 12345678);

       MvcResult mvcResult = mockMvc.perform(post("/banco-api").contentType("application/json")
               .content(MAPPER.writeValueAsString(banco))).andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();

        ErroResponse retorno = parseResponse(mvcResult, ErroResponse.class);

        assertThat("Não retornou a calidaçao correta", retorno.getMensagem(), containsString("Banco id não encontrado"));
    }

    @Test
    void testaAdicaoCodigoNulo() throws Exception{
        Banco banco = build(1, null, "Banrisul", 12345678);

        MvcResult mvcResult = mockMvc.perform(post("/banco-api").contentType("application/json")
                .content(MAPPER.writeValueAsString(banco))).andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();

        ErroResponse retorno = parseResponse(mvcResult, ErroResponse.class);

        assertThat("Não retornou a calidaçao correta", retorno.getMensagem(), containsString("codigo - O codigo nao pode ser nulo ou invalido"));
    }
    @Test
    void testaAdicaoNomeNulo() throws Exception{
        Banco banco = build(1, 10, null, 12345678);

        MvcResult mvcResult = mockMvc.perform(post("/banco-api").contentType("application/json")
                .content(MAPPER.writeValueAsString(banco))).andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();

        ErroResponse retorno = parseResponse(mvcResult, ErroResponse.class);

        assertThat("Não retornou a calidaçao correta", retorno.getMensagem(), containsString("nome - O nome nao pode ser nulo ou invalido"));
    }

    @Test
    void testaAdicaoCnpjNulo() throws Exception{
        Banco banco = build(1, 10, "Banrisul", null);

        MvcResult mvcResult = mockMvc.perform(post("/banco-api").contentType("application/json")
                .content(MAPPER.writeValueAsString(banco))).andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();

        ErroResponse retorno = parseResponse(mvcResult, ErroResponse.class);

        assertThat("Não retornou a calidaçao correta", retorno.getMensagem(), containsString("cnpj - O cnpj nao pode ser nulo ou invalido"));
    }

    @Test
    void testaLista() throws Exception {
        List<Banco> lista = new ArrayList<>();
        lista.add(build(1, 10, "Banrisul", 127631786));

        doAnswer(invocation -> lista).when(service).listarTodos();

        MvcResult mvcResult = mockMvc.perform(get("/banco-api"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        verify(service, times(1)).listarTodos();


        assertThat("Não retornou id correto", lista.get(0).getId(), is(1));
    }
@Test
    void testaBuscaPorId() throws Exception {
        Banco banco = build(2, null, null, null);

        doAnswer(invocation -> banco).when(service).buscarBanco(eq(banco.getId()));

        MvcResult mvcResult = mockMvc.perform(get("/banco-api/find-id/2"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        Banco retorno = parseResponse(mvcResult, Banco.class);

        verify(service, times(1)).buscarBanco(eq(banco.getId()));

        assertThat("Não retornou o ID Correto", retorno, equalTo(banco));
    }

    @Test
    void testaBuscaIdNaoEncontrado() throws Exception{

        doAnswer( invocation -> {
            throw new RegistroNaoEncontradoException("Id: 1 nao encontrado");
        }).when(service).buscarBanco(eq(1));

        MvcResult mvcResult = mockMvc.perform(get("/banco-api/find-id/1"))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();

        ErroResponse retorno = parseResponse(mvcResult, ErroResponse.class);

        verify(service, times(1)).buscarBanco(eq(1));
        assertThat("Nao retornou a mensagem correta", retorno.getMensagem(), equalTo("Id: 1 nao encontrado"));

    }

    @Test
    void testaBuscaCodigoNaoEncontrado() throws Exception{

        doAnswer( invocation -> {
            throw new RegistroNaoEncontradoException("Codigo: 10 nao encontrado");
        }).when(service).buscarPorCodigo(eq(10));

        MvcResult mvcResult = mockMvc.perform(get("/banco-api/find-codigo/10"))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();

        ErroResponse retorno = parseResponse(mvcResult, ErroResponse.class);

        verify(service, times(1)).buscarPorCodigo(eq(10));
        assertThat("Nao retornou a mensagem correta", retorno.getMensagem(), equalTo("Codigo: 10 nao encontrado"));

    }

  @Test
  void testaEdicao() throws Exception{
      Banco banco = build(1, 10, "Banrisul", 12345678);

      doAnswer(invocation -> {
          return banco;
      }).when(service).editar(eq(banco.getId()), eq(banco));

      MvcResult mvcResult =
              mockMvc.perform(put("/banco-api/1").contentType("application/json")
                              .content(MAPPER.writeValueAsString(build(1, 10, "Banrisul", 12345678))))
                      .andExpect(status().is(HttpStatus.OK.value())).andReturn();

      Banco retorno = parseResponse(mvcResult, Banco.class);

      verify(service, times(1)).editar(eq(banco.getId()), eq(banco));
      assertThat("Não retornou o ID Correto", retorno.getId(), equalTo(1));
      assertThat("Não fez a aleteracao", retorno.getCnpj(), equalTo(12345678));
  }

    @Test
    void testaEdicaoNaoEncontrada() throws Exception{
        Banco banco = build(1, 10, "Banrisul", 12345678);

        doAnswer(invocation -> {
            throw new RegistroNaoEncontradoException("Id: 1 nao encontrado");
        }).when(service).editar(eq(banco.getId()), eq(banco));

        MvcResult mvcResult =
                mockMvc.perform(put("/banco-api/1").contentType("application/json")
                                .content(MAPPER.writeValueAsString(build(1, 10, "Banrisul", 12345678))))
                        .andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();
        
        verify(service, times(1)).editar(eq(banco.getId()), eq(banco));
        ErroResponse retorno = parseResponse(mvcResult, ErroResponse.class);
        assertThat("Não retornou a mensagem correta", retorno.getMensagem(), equalTo("Id: 1 nao encontrado"));
    }

    private static <T> List<T> parseResponseList(MvcResult mockHttpServletResponse, Class<T> clazz) {
        try {
            String contentAsString = mockHttpServletResponse.getResponse().getContentAsString();
            return MAPPER.readValue(contentAsString, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T parseResponse(MvcResult mockHttpServletResponse, Class<T> clazz) {
        try {
            String contentAsString = mockHttpServletResponse.getResponse().getContentAsString();
            return MAPPER.readValue(contentAsString, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
