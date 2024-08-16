package SpringBootStarterProject.favouritePackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    public Optional<Favorite> findByClientIdAndFavouriteIdAndFavoriteType(Integer clientId, Integer favouriteId, FavoriteType favouriteType);
    public Optional<List<Favorite>> findByClientId(Integer clientId);


}
