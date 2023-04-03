package ep.affordable_automation.rest;

import ep.affordable_automation.model.ProductsDTO;
import ep.affordable_automation.service.ProductsService;
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
@RequestMapping(value = "/api/productss", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsResource {

    private final ProductsService productsService;

    public ProductsResource(final ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<List<ProductsDTO>> getAllProductss() {
        return ResponseEntity.ok(productsService.findAll());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductsDTO> getProducts(
            @PathVariable(name = "productId") final Integer productId) {
        return ResponseEntity.ok(productsService.get(productId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createProducts(
            @RequestBody @Valid final ProductsDTO productsDTO) {
        return new ResponseEntity<>(productsService.create(productsDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProducts(
            @PathVariable(name = "productId") final Integer productId,
            @RequestBody @Valid final ProductsDTO productsDTO) {
        productsService.update(productId, productsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProducts(
            @PathVariable(name = "productId") final Integer productId) {
        productsService.delete(productId);
        return ResponseEntity.noContent().build();
    }

}
