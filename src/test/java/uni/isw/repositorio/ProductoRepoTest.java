package uni.isw.repositorio;

import java.util.List;
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
    
    @Test
    public void ProductoRepo_Listar(){
         Producto producto1=Producto.builder()
                 .nombre("Rose")
                 .marca("Queirolo")
                 .categoria("Vino seco")
                 .precio(15.5)
                 .cantidad(12).build();
         
         Producto producto2=Producto.builder()
                 .nombre("Quebranta")
                 .marca("Tabernero")
                 .categoria("Vino semi-seco")
                 .precio(29.9)
                 .cantidad(24).build();
         
         productoRepo.save(producto1);
         productoRepo.save(producto2);
         
         List<Producto> productoList = (List<Producto>)productoRepo.findAll();
         Assertions.assertThat(productoList).isNotNull();
         Assertions.assertThat(productoList.size()).isEqualTo(2);
    }
    
    @Test
    public void ProductoRepo_Insert(){
         Producto producto1=Producto.builder()
                 .nombre("Rose")
                 .marca("Queirolo")
                 .categoria("Vino seco")
                 .precio(15.5)
                 .cantidad(12).build();
                  
         Producto newProducto=productoRepo.save(producto1);
                  
         Assertions.assertThat(newProducto).isNotNull();
         Assertions.assertThat(newProducto.getId()).isGreaterThan(0);
    }
    
    /*public ProductoRepoTest() {
    }
    
    @BeforeEach
    public void setUp() {
    }*/
    
}
