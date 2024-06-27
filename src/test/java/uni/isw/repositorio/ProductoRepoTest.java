package uni.isw.repositorio;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uni.isw.entity.Producto;
import uni.isw.repo.ProductoRepo;

@DataJpaTest
@AutoConfigureTestDatabase(connection=EmbeddedDatabaseConnection.H2)
public class ProductoRepoTest {
    
    @Autowired
    private ProductoRepo productoRepo;
    
     @BeforeEach
    public void setUp() {
        Producto producto1 = Producto.builder()
                .nombre("Rose")
                .marca("Queirolo")
                .categoria("Vino seco")
                .precio(15.5)
                .cantidad(12).build();
        
        Producto producto2 = Producto.builder()
                .nombre("Quebranta")
                .marca("Tabernero")
                .categoria("Vino semi-seco")
                .precio(29.9)
                .cantidad(24).build();
        
        productoRepo.save(producto1);
        productoRepo.save(producto2);
    }
    
    @Test
    public void ProductoRepo_Listar(){
        List<Producto> productoList = (List<Producto>)productoRepo.findAll();
        Assertions.assertThat(productoList).isNotNull();
        Assertions.assertThat(productoList.size()).isEqualTo(2);
    }
    
    @Test
    public void ProductoRepo_InsertarProducto(){
         Producto producto1=Producto.builder()
                 .nombre("Merlot")
                 .marca("San Pedro")
                 .categoria("Vino tinto")
                 .precio(45.0)
                 .cantidad(10).build();
                  
         Producto newProducto=productoRepo.save(producto1);
                  
         Assertions.assertThat(newProducto).isNotNull();
         Assertions.assertThat(newProducto.getId()).isGreaterThan(0);
    }
    
    //Devuelve el primer producto de la lista
    @Test
    public void ProductoRepo_EncontrarPorId(){
        List<Producto> productos = productoRepo.findAll();
        Integer id = productos.get(0).getId();
        
        Optional<Producto> newProducto = productoRepo.findById(id);
        assertEquals(newProducto.get().getId(),id);
    }
    
    //Testeamos la operacion Actualizar Producto
    @Test
    public void ProductoRepo_ActualizarProducto() {
        Integer id = productoRepo.findAll().get(0).getId();
        Producto producto = productoRepo.findById(id).get();
        
        producto.setNombre("Updated Rose");
        producto.setPrecio(20.0);
        
        Producto updatedProducto = productoRepo.save(producto);
        
        Assertions.assertThat(updatedProducto.getNombre()).isEqualTo("Updated Rose");
        Assertions.assertThat(updatedProducto.getPrecio()).isEqualTo(20.0);
    }
    
    //Se elimina el primer elemento de la lista productos
    @Test
    public void ProductoRepo_Eliminar(){
        List<Producto> productos = productoRepo.findAll();
        Integer id = productos.get(0).getId();
        
        boolean esExistenteAntesDeEliminar = productoRepo.findById(id).isPresent();
        productoRepo.deleteById(id);
        boolean noExisteDespuesDeEliminar = productoRepo.findById(id).isPresent();
        
        assertTrue(esExistenteAntesDeEliminar);
        assertFalse(noExisteDespuesDeEliminar);
    }    
}
