package HotelManagement.HotelManagement.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(nullable = false, length = 100)
    private String sector;

    @Column(nullable = false, length = 100)
    private String cell;

    @Column(nullable = false, length = 100)
    private String village;

    /**
     * Many Locations belong to One Province.
     * This is the owning side: it contains the FK column "province_id".
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    /**
     * One Location can have many Users.
     * The FK lives in the "users" table (users.location_id).
     */
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    /**
     * One Location can have many Rooms.
     * The FK lives in the "rooms" table (rooms.location_id).
     */
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();
}

