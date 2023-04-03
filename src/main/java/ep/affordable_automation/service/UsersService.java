package ep.affordable_automation.service;

import ep.affordable_automation.domain.Products;
import ep.affordable_automation.domain.Users;
import ep.affordable_automation.model.UsersDTO;
import ep.affordable_automation.repos.ProductsRepository;
import ep.affordable_automation.repos.UsersRepository;
import ep.affordable_automation.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final ProductsRepository productsRepository;

    public UsersService(final UsersRepository usersRepository,
            final ProductsRepository productsRepository) {
        this.usersRepository = usersRepository;
        this.productsRepository = productsRepository;
    }

    public List<UsersDTO> findAll() {
        final List<Users> userss = usersRepository.findAll(Sort.by("userId"));
        return userss.stream()
                .map((users) -> mapToDTO(users, new UsersDTO()))
                .toList();
    }

    public UsersDTO get(final Integer userId) {
        return usersRepository.findById(userId)
                .map(users -> mapToDTO(users, new UsersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UsersDTO usersDTO) {
        final Users users = new Users();
        mapToEntity(usersDTO, users);
        return usersRepository.save(users).getUserId();
    }

    public void update(final Integer userId, final UsersDTO usersDTO) {
        final Users users = usersRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usersDTO, users);
        usersRepository.save(users);
    }

    public void delete(final Integer userId) {
        usersRepository.deleteById(userId);
    }

    private UsersDTO mapToDTO(final Users users, final UsersDTO usersDTO) {
        usersDTO.setUserId(users.getUserId());
        usersDTO.setEmail(users.getEmail());
        usersDTO.setPassword(users.getPassword());
        usersDTO.setFirstName(users.getFirstName());
        usersDTO.setLastName(users.getLastName());
        usersDTO.setUserProductProducts(users.getUserProductProducts() == null ? null : users.getUserProductProducts().stream()
                .map(products -> products.getProductId())
                .toList());
        return usersDTO;
    }

    private Users mapToEntity(final UsersDTO usersDTO, final Users users) {
        users.setEmail(usersDTO.getEmail());
        users.setPassword(usersDTO.getPassword());
        users.setFirstName(usersDTO.getFirstName());
        users.setLastName(usersDTO.getLastName());
        final List<Products> userProductProducts = productsRepository.findAllById(
                usersDTO.getUserProductProducts() == null ? Collections.emptyList() : usersDTO.getUserProductProducts());
        if (userProductProducts.size() != (usersDTO.getUserProductProducts() == null ? 0 : usersDTO.getUserProductProducts().size())) {
            throw new NotFoundException("one of userProductProducts not found");
        }
        users.setUserProductProducts(userProductProducts.stream().collect(Collectors.toSet()));
        return users;
    }

}
