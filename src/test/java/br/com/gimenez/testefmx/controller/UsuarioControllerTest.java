package br.com.gimenez.testefmx.controller;

import br.com.gimenez.testefmx.model.Usuario;
import br.com.gimenez.testefmx.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static br.com.gimenez.testefmx.Util.createUsuario;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Teste Usuario controller")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioController usuarioController;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void saveUsuario_whenSuccess() throws Exception {
        //Aqui seria um DTO
        Usuario usuario = createUsuario();

        Usuario usuarioSaved = createUsuario();
        usuarioSaved.setId(1L);

        when(usuarioService.save(any(Usuario.class))).thenReturn(usuarioSaved);

        mockMvc.perform(post("/usuarios")
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.email", Matchers.is(usuario.getEmail())));

        Mockito.verify(usuarioService, times(1)).save(any(Usuario.class));
    }
}