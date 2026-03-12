package HotelManagement.HotelManagement.entity;

import HotelManagement.HotelManagement.entity.enums.UserRole;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    /**
     * Stored as a hashed password (BCrypt) in service layer.
     */
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    /**
     * Many Users can belong to one Location (where they are registered).
     * FK lives in users.location_id
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    /**
     * One-to-One relationship.
     * UserProfile holds the FK (user_profiles.user_id) and is the owning side.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private UserProfile userProfile;

    /**
     * One user can create many bookings.
     * FK lives in bookings.user_id
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();
}

