package uni.isw.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.isw.entity.Producto;
import uni.isw.repo.ProductoRepo;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepo productorepo;
    
    public List<Producto> listar(){
        return productorepo.findAll();
    }
    
    public Optional<Producto> findById(int id) {
        return productorepo.findById(id);
    }
    
    public Producto saveOrUpdate(Producto prod){
        return productorepo.save(prod);
    }
    
    //Borra por el producto por su ID
    public void deleteProducto(int id){
        productorepo.deleteById(id);
    }
}
