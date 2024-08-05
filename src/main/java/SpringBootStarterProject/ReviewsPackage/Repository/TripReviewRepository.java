package SpringBootStarterProject.ReviewsPackage.Repository;

import SpringBootStarterProject.ReviewsPackage.Models.HotelReview;
import SpringBootStarterProject.ReviewsPackage.Models.TripReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripReviewRepository extends JpaRepository<TripReview, Integer> {
    public Optional<List<TripReview>> findByTripId(Integer tripId);
    public Optional<TripReview> findByClientIdAndTripId(Integer clientId, Integer tripId);

}
