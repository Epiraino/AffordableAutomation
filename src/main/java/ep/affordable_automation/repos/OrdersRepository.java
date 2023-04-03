package ep.affordable_automation.repos;

import ep.affordable_automation.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}
