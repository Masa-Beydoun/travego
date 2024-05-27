package SpringBootStarterProject.HotelsPackage.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomServices {
    @Id
    @SequenceGenerator(
            name = "RoomServiceType_id",
            sequenceName ="RoomServiceType_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "RoomServiceType_id"

    )
    private Integer id;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Room> roomList;


}
