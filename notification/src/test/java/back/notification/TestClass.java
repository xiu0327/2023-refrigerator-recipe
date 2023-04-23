package back.notification;

import back.dto.OutIngredientDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Transactional
@SpringBootTest
public class TestClass {

    @Autowired
    EntityManager em;

    @Test
    void JPQL_TEST() {
        List<OutIngredientDTO> list = em.createQuery("select new back.dto.OutIngredientDTO(i.email, min(i.name) as name, count(i.id) as ingredient_count) from Ingredient i where i.expirationDate = :date group by i.email")
                .setParameter("date", LocalDate.now().plusDays(1))
                .getResultList();

        for (OutIngredientDTO outIngredientDTO : list) {
            System.out.println("outIngredientDTO = " + outIngredientDTO);
        }
    }
}
