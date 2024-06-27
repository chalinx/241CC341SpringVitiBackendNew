package uni.isw.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ProductoService productoService;

    @GetMapping("/productos")
    public List<Producto> listar() {
        return productoService.listar();
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoId(@PathVariable int id) {
        Producto producto = productoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el producto con el ID :" + id));
        return ResponseEntity.ok(producto);
    }

    @PostMapping("/productos")
    public Producto insertar(@RequestBody Producto prod) {
        return productoService.saveOrUpdate(prod);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable int id, @RequestBody Producto detallesProducto) {
        Producto productoExistente = productoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el producto con el ID :" + id));

        productoExistente.setNombre(detallesProducto.getNombre());
        productoExistente.setMarca(detallesProducto.getMarca());
        productoExistente.setCategoria(detallesProducto.getCategoria());
        productoExistente.setPrecio(detallesProducto.getPrecio());
        productoExistente.setCantidad(detallesProducto.getCantidad());

        Producto productoActualizado = productoService.saveOrUpdate(productoExistente);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminar(@PathVariable int id) {
        Producto producto = productoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el producto con el ID :" + id));

        productoService.deleteProducto(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
