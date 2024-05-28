package SpringBootStarterProject.UserPackage.Repositories;

import SpringBootStarterProject.UserPackage.Models.MoneyCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyCodeRepository  extends JpaRepository<MoneyCode,Integer> {

    MoneyCode findMoneyCodeByCode(String code);
}
