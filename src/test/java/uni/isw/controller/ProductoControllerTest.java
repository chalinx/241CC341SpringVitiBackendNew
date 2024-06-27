package uni.isw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uni.isw.entity.Producto;
import uni.isw.service.ProductoService;
import uni.isw.Controller.ProductoController;

@WebMvcTest(controllers = ProductoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;   

    private Producto producto1, producto2;    
    
    @BeforeEach
    public void init() {
        producto1 = Producto.builder()                
                .nombre("Rose")
                .marca("Queirolo")
                .categoria("Vino seco")
                .precio(15.5)
                .cantidad(12).build();          
        
        producto2 = Producto.builder()                
                .nombre("Quebranta")
                .marca("Tabernero")
                .categoria("Vino semi-seco")
                .precio(29.9)
                .cantidad(24).build();          
    }

    @Test
    public void ProductoController_Insertar() throws Exception {                
        given(productoService.saveOrUpdate(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));
        
        ResultActions response = mockMvc.perform(post("/api/v1/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is(producto1.getNombre())));
    }   
    
    @Test
    public void ProductoController_Listar() throws Exception {
        List<Producto> productoList = new ArrayList<>();
        productoList.add(producto1);
        productoList.add(producto2);
        
        when(productoService.listar()).thenReturn(productoList);
        
        ResultActions response = mockMvc.perform(get("/api/v1/productos")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(productoList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
    }
}
