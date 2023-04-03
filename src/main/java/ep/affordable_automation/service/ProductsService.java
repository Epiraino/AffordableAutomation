package ep.affordable_automation.service;

import ep.affordable_automation.domain.Products;
import ep.affordable_automation.model.ProductsDTO;
import ep.affordable_automation.repos.ProductsRepository;
import ep.affordable_automation.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsService(final ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<ProductsDTO> findAll() {
        final List<Products> productss = productsRepository.findAll(Sort.by("productId"));
        return productss.stream()
                .map((products) -> mapToDTO(products, new ProductsDTO()))
                .toList();
    }

    public ProductsDTO get(final Integer productId) {
        return productsRepository.findById(productId)
                .map(products -> mapToDTO(products, new ProductsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProductsDTO productsDTO) {
        final Products products = new Products();
        mapToEntity(productsDTO, products);
        return productsRepository.save(products).getProductId();
    }

    public void update(final Integer productId, final ProductsDTO productsDTO) {
        final Products products = productsRepository.findById(productId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productsDTO, products);
        productsRepository.save(products);
    }

    public void delete(final Integer productId) {
        productsRepository.deleteById(productId);
    }

    private ProductsDTO mapToDTO(final Products products, final ProductsDTO productsDTO) {
        productsDTO.setProductId(products.getProductId());
        productsDTO.setTitle(products.getTitle());
        productsDTO.setDescription(products.getDescription());
        productsDTO.setPrice(products.getPrice());
        return productsDTO;
    }

    private Products mapToEntity(final ProductsDTO productsDTO, final Products products) {
        products.setTitle(productsDTO.getTitle());
        products.setDescription(productsDTO.getDescription());
        products.setPrice(productsDTO.getPrice());
        return products;
    }

}
