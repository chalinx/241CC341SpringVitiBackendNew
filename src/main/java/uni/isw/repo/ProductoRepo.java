package uni.isw.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.isw.entity.Producto;

@Repository
public interface ProductoRepo extends JpaRepository<Producto, Integer>{
    
}
