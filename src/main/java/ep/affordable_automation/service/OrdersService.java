package ep.affordable_automation.service;

import ep.affordable_automation.domain.Orders;
import ep.affordable_automation.domain.Products;
import ep.affordable_automation.domain.Users;
import ep.affordable_automation.model.OrdersDTO;
import ep.affordable_automation.repos.OrdersRepository;
import ep.affordable_automation.repos.ProductsRepository;
import ep.affordable_automation.repos.UsersRepository;
import ep.affordable_automation.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final ProductsRepository productsRepository;

    public OrdersService(final OrdersRepository ordersRepository,
            final UsersRepository usersRepository, final ProductsRepository productsRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.productsRepository = productsRepository;
    }

    public List<OrdersDTO> findAll() {
        final List<Orders> orderss = ordersRepository.findAll(Sort.by("orderId"));
        return orderss.stream()
                .map((orders) -> mapToDTO(orders, new OrdersDTO()))
                .toList();
    }

    public OrdersDTO get(final Integer orderId) {
        return ordersRepository.findById(orderId)
                .map(orders -> mapToDTO(orders, new OrdersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final OrdersDTO ordersDTO) {
        final Orders orders = new Orders();
        mapToEntity(ordersDTO, orders);
        return ordersRepository.save(orders).getOrderId();
    }

    public void update(final Integer orderId, final OrdersDTO ordersDTO) {
        final Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(ordersDTO, orders);
        ordersRepository.save(orders);
    }

    public void delete(final Integer orderId) {
        ordersRepository.deleteById(orderId);
    }

    private OrdersDTO mapToDTO(final Orders orders, final OrdersDTO ordersDTO) {
        ordersDTO.setOrderId(orders.getOrderId());
        ordersDTO.setStatus(orders.getStatus());
        ordersDTO.setTransactionId(orders.getTransactionId());
        ordersDTO.setUser(orders.getUser() == null ? null : orders.getUser().getUserId());
        ordersDTO.setProduct(orders.getProduct() == null ? null : orders.getProduct().getProductId());
        return ordersDTO;
    }

    private Orders mapToEntity(final OrdersDTO ordersDTO, final Orders orders) {
        orders.setStatus(ordersDTO.getStatus());
        orders.setTransactionId(ordersDTO.getTransactionId());
        final Users user = ordersDTO.getUser() == null ? null : usersRepository.findById(ordersDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        orders.setUser(user);
        final Products product = ordersDTO.getProduct() == null ? null : productsRepository.findById(ordersDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        orders.setProduct(product);
        return orders;
    }

}
