package ep.affordable_automation.rest;

import ep.affordable_automation.model.UsersDTO;
import ep.affordable_automation.service.UsersService;
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
@RequestMapping(value = "/api/userss", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersResource {

    private final UsersService usersService;

    public UsersResource(final UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUserss() {
        return ResponseEntity.ok(usersService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UsersDTO> getUsers(@PathVariable(name = "userId") final Integer userId) {
        return ResponseEntity.ok(usersService.get(userId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createUsers(@RequestBody @Valid final UsersDTO usersDTO) {
        return new ResponseEntity<>(usersService.create(usersDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUsers(@PathVariable(name = "userId") final Integer userId,
            @RequestBody @Valid final UsersDTO usersDTO) {
        usersService.update(userId, usersDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsers(@PathVariable(name = "userId") final Integer userId) {
        usersService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
