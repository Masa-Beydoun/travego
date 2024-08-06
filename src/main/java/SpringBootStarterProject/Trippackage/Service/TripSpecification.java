package SpringBootStarterProject.Trippackage.Service;

import SpringBootStarterProject.Trippackage.Models.Trip;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TripSpecification  implements Specification<Trip> {

    private final String searchTerm;

    public TripSpecification(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public Predicate toPredicate(Root<Trip> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (char c : this.searchTerm.toCharArray()) {
            predicates.add(criteriaBuilder.like(root.get("TripCategory"),"%" +c+"%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }
}
