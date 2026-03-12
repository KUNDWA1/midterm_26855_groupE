package HotelManagement.HotelManagement.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String provinceCode;

    @Column(nullable = false, unique = true, length = 100)
    private String provinceName;

    /**
     * One Province can have many Locations.
     * The foreign key lives in the "locations" table (locations.province_id).
     */
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Location> locations = new ArrayList<>();
}

