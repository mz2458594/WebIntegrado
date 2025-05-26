package com.example.domain.ecommerce;

import java.util.ArrayList;
import java.util.List;
import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.dto.RequestDTO.ItemsVentaDTO;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.entities.Venta;
import com.example.domain.ecommerce.models.entities.Persona;
import com.fasterxml.jackson.databind.JsonNode;
import com.example.domain.ecommerce.services.VentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RestTests {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private VentaService ventaService;
    
    @Autowired
    private ObjectMapper objectMapper;

    private int testProductId;
    private int testUserId;

    @BeforeEach
    public void setup() throws Exception {
        insertTestProduct();
    }

    private void insertTestProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setNombre("Producto Test");
        productDTO.setDescripcion("Descripción test");
        productDTO.setNombre_categoria("Test");
        productDTO.setPrecio("25.99");
        productDTO.setStock("100");
        productDTO.setImagen1("test.jpg");
        productDTO.setProveedor("Proveedor Test");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/createProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
                
        }

    @Test
    public void testCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setNombre("Nuevo Producto");
        productDTO.setDescripcion("Nueva descripción");
        productDTO.setNombre_categoria("Nueva Categoría");
        productDTO.setPrecio("19.99");
        productDTO.setStock("50");
        productDTO.setImagen1("nueva.jpg");
        productDTO.setProveedor("Nuevo Proveedor");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/createProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nuevo Producto"))
                .andExpect(jsonPath("$.descripcion").value("Nueva descripción"))
                .andExpect(jsonPath("$.precio").value("19.99"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/products/"))
                .andReturn().getResponse().getContentAsString();
        
        Producto[] productos = objectMapper.readValue(response, Producto[].class);
        int productId = productos[0].getIdProducto();

        
        ProductDTO updateDTO = new ProductDTO();
        updateDTO.setNombre("Producto Actualizado");
        updateDTO.setDescripcion("Descripción actualizada");
        updateDTO.setNombre_categoria("Categoría Actualizada");
        updateDTO.setPrecio("29.99");
        updateDTO.setStock("75");
        updateDTO.setImagen1("actualizada.jpg");
        updateDTO.setProveedor("Proveedor Actualizado");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/updateProduct/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("Producto actualizado correctamente"));
        }

    @Test
    public void testDeleteProduct() throws Exception {
        
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/products/"))
                .andReturn().getResponse().getContentAsString();
        
        Producto[] productos = objectMapper.readValue(response, Producto[].class);
        int productId = productos[0].getIdProducto();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/deleteProduct/" + productId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("Producto eliminado con éxito"));
        }


    @Test
    public void testCreateSale() throws Exception {
        RequestDTO.ItemsVentaDTO item = new RequestDTO.ItemsVentaDTO();
        Producto producto = new Producto();
        producto.setIdProducto(this.testProductId); 
        item.setProducto(producto);
        item.setCantidad(2);

        List<RequestDTO.ItemsVentaDTO> items = new ArrayList<>();
        items.add(item);

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId_usuario(this.testUserId); 
        requestDTO.setItem(items);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/sales/createSale")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
        }

        @Test
        public void testGetAllUsers() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").isArray());
        }

        @Test
        public void testCreateUser() throws Exception {
        long timestamp = System.currentTimeMillis();
        UserDTO userDTO = new UserDTO();
        userDTO.setNombre("Test");
        userDTO.setApellido("User");
        userDTO.setNum_documento(String.valueOf(timestamp)); 
        userDTO.setCelular("987654321");
        userDTO.setCalle("Av. Test 123");
        userDTO.setCiudad("Lima");
        userDTO.setDistrito("Miraflores");
        userDTO.setProvincia("Lima");
        userDTO.setCorreo("test" + timestamp + "@example.com");
        userDTO.setUsername("testuser" + timestamp);
        userDTO.setPassword("password123");
        userDTO.setRol("CLIENTE");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/createUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userDTO.getUsername()))
                .andExpect(jsonPath("$.persona.nombre").value(userDTO.getNombre()))
                .andExpect(jsonPath("$.persona.apellido").value(userDTO.getApellido()));
        }
/*
        @Test
        public void testCreateAndUpdateDirection() throws Exception {
            long timestamp = System.currentTimeMillis();
         
            UserDTO userDTO = new UserDTO();
            userDTO.setNombre("Direction");
            userDTO.setApellido("Test");
            userDTO.setNum_documento("76543210");
            userDTO.setCelular("987654323");
            userDTO.setCorreo("direction"+timestamp+"@example.com");
            userDTO.setUsername("directionuser"+timestamp);
            userDTO.setContraseña("directionpass");
            userDTO.setRol("CLIENTE");
            
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/createUser")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userDTO)))
                    .andReturn();
    
            JsonNode usuarioNode = objectMapper.readTree(result.getResponse().getContentAsString());
            int userId = usuarioNode.get("idUsuario").asInt();
    

            DireccionDTO direccionDTO = new DireccionDTO();
            direccionDTO.setCalle("Av. Nueva 123");
            direccionDTO.setCiudad("Lima");
            direccionDTO.setDistrito("San Isidro");
            direccionDTO.setProvincia("Lima");
    
            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/createDirection/" + userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(direccionDTO)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
    

            direccionDTO.setCalle("Av. Actualizada 456");
            
            mockMvc.perform(MockMvcRequestBuilders.put("/api/users/updateDirection/" + userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(direccionDTO)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
        }
    
        @Test
public void testUpdateUser() throws Exception {
    long timestamp = System.currentTimeMillis();
    UserDTO userDTO = new UserDTO();
    userDTO.setNombre("Update");
    userDTO.setApellido("Test");
    userDTO.setNum_documento(String.valueOf(timestamp));
    userDTO.setCelular("987654324");
    userDTO.setCorreo("update"+timestamp+"@example.com");
    userDTO.setUsername("updateuser"+timestamp);
    userDTO.setContraseña("updatepass");
    userDTO.setRol("CLIENTE");
    userDTO.setCalle("Av. Test");
    userDTO.setCiudad("Lima");
    userDTO.setDistrito("Miraflores");
    userDTO.setProvincia("Lima");
    
    MvcResult result = mockMvc.perform(post("/api/users/createUser")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO)))
            .andExpect(status().isOk())
            .andReturn();
    
    JsonNode usuarioNode = objectMapper.readTree(result.getResponse().getContentAsString());
    int userId = usuarioNode.get("idUsuario").asInt();
    
    UserDTO updateDTO = new UserDTO();
    updateDTO.setNombre("Updated");
    updateDTO.setApellido("User");
    updateDTO.setNum_documento(String.valueOf(timestamp));
    updateDTO.setCelular("987654325");
    updateDTO.setCorreo("updated"+timestamp+"@example.com");
    updateDTO.setUsername("updateduser"+timestamp);
    updateDTO.setContraseña("updatedpass");
    updateDTO.setRol("CLIENTE");
    updateDTO.setCalle("Av. Updated");
    updateDTO.setCiudad("Lima");
    updateDTO.setDistrito("San Isidro");
    updateDTO.setProvincia("Lima");
    
    mockMvc.perform(put("/api/users/updateUser/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDTO)))
            .andExpect(status().isOk());
}
    */
        @Test
        public void testUpdatePassword() throws Exception {
            long timestamp = System.currentTimeMillis();
            
            UserDTO userDTO = new UserDTO();
            userDTO.setNombre("Password");
            userDTO.setApellido("Test");
            userDTO.setNum_documento("54321098");
            userDTO.setCelular("987654326");
            userDTO.setCorreo("password"+timestamp+"@example.com");
            userDTO.setUsername("passworduser"+timestamp);
            userDTO.setPassword("oldpassword");
            userDTO.setRol("CLIENTE");
            
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/createUser")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userDTO)))
                    .andReturn();
    
            JsonNode usuarioNode = objectMapper.readTree(result.getResponse().getContentAsString());
            int userId = usuarioNode.get("idUsuario").asInt();
    
            Map<String, String> passwordMap = new HashMap<>();
            passwordMap.put("password", "newpassword");
    
            mockMvc.perform(MockMvcRequestBuilders.put("/api/users/updatePassword/" + userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(passwordMap)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
        }
    
        @Test
        public void testDeleteUser() throws Exception {
            long timestamp = System.currentTimeMillis();
            
            UserDTO userDTO = new UserDTO();
            userDTO.setNombre("Delete");
            userDTO.setApellido("Test");
            userDTO.setNum_documento("43210987");
            userDTO.setCelular("987654327");
            userDTO.setCorreo("delete"+timestamp+"@example.com");
            userDTO.setUsername("deleteuser"+timestamp);
            userDTO.setPassword("deletepass");
            userDTO.setRol("CLIENTE");
            
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/createUser")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userDTO)))
                    .andReturn();
    
            JsonNode usuarioNode = objectMapper.readTree(result.getResponse().getContentAsString());
            int userId = usuarioNode.get("idUsuario").asInt();
    
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/deleteUser/" + userId))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
        }
}
