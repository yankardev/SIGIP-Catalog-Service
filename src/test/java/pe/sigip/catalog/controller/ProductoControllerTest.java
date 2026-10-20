package pe.sigip.catalog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pe.sigip.catalog.repository.ProductoRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoRepository repository;

    @Test
    void healthDevuelveServicioActivo() throws Exception {
        mockMvc.perform(get("/api/catalogo/productos/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").value("activo"));
    }

    @Test
    void crearRechazaNombreVacio() throws Exception {
        mockMvc.perform(post("/api/catalogo/productos")
                .contentType("application/json")
                .content("""
                    {
                      "nombre": "",
                      "categoria": "Tarjetas",
                      "descripcion": "Prueba",
                      "precioBase": 20.00,
                      "disponible": true
                    }
                    """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Datos inválidos"))
            .andExpect(jsonPath("$.mensaje").exists());
    }

    @Test
    void crearRechazaPrecioNegativo() throws Exception {
        mockMvc.perform(post("/api/catalogo/productos")
                .contentType("application/json")
                .content("""
                    {
                      "nombre": "Producto de prueba",
                      "categoria": "Tarjetas",
                      "descripcion": "Prueba",
                      "precioBase": -1.00,
                      "disponible": true
                    }
                    """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Datos inválidos"));
    }
}
