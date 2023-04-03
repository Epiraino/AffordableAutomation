package ep.affordable_automation.repos;

import ep.affordable_automation.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<Users, Integer> {
}
