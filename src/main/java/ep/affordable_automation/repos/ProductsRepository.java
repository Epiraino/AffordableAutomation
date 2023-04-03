package ep.affordable_automation.repos;

import ep.affordable_automation.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductsRepository extends JpaRepository<Products, Integer> {
}
