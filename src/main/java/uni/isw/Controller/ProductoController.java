package uni.isw.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uni.isw.entity.Producto;
import uni.isw.excepciones.ResourceNotFoundException;
import uni.isw.service.ProductoService;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {
    
    @Autowired
    private ProductoService productoservice;
    
    @GetMapping("/productos")
    public List<Producto> listar(){
        return productoservice.listar();
    }
    
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoId(@PathVariable int id){
        Producto producto = productoservice.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe el producto con el ID :" + id));
        return ResponseEntity.ok(producto);
    }
    
    @PostMapping("/productos")
    public Producto insertar(@RequestBody Producto prod){
        return productoservice.saveOrUpdate(prod);
    }
    
    @PutMapping("/productos")
    public Producto actualizar(@RequestBody Producto prod){
        return productoservice.saveOrUpdate(prod);
    }
    
    /*@DeleteMapping("/productos")
    public void eliminar(@RequestBody Producto prod){
        productoservice.deleteProducto(id);
    }*/
}
