package HotelManagement.HotelManagement.entity;

import HotelManagement.HotelManagement.entity.enums.RoomStatus;
import HotelManagement.HotelManagement.entity.enums.RoomType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoomType roomType;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoomStatus status;

    /**
     * Many Rooms can belong to one Location.
     * FK lives in rooms.location_id
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    /**
     * One Room can have many Bookings.
     * FK lives in bookings.room_id
     */
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

}
