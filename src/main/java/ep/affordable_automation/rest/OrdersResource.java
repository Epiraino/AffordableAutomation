package ep.affordable_automation.rest;

import ep.affordable_automation.model.OrdersDTO;
import ep.affordable_automation.service.OrdersService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/orderss", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdersResource {

    private final OrdersService ordersService;

    public OrdersResource(final OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    public ResponseEntity<List<OrdersDTO>> getAllOrderss() {
        return ResponseEntity.ok(ordersService.findAll());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersDTO> getOrders(
            @PathVariable(name = "orderId") final Integer orderId) {
        return ResponseEntity.ok(ordersService.get(orderId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createOrders(@RequestBody @Valid final OrdersDTO ordersDTO) {
        return new ResponseEntity<>(ordersService.create(ordersDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Void> updateOrders(@PathVariable(name = "orderId") final Integer orderId,
            @RequestBody @Valid final OrdersDTO ordersDTO) {
        ordersService.update(orderId, ordersDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{orderId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOrders(
            @PathVariable(name = "orderId") final Integer orderId) {
        ordersService.delete(orderId);
        return ResponseEntity.noContent().build();
    }

}
