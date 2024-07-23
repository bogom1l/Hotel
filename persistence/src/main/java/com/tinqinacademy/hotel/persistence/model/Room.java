package com.tinqinacademy.hotel.persistence.model;

import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "price", nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "room_number", nullable = false, unique = true, length = 4) // potential problem
    private String roomNumber;

    @Column(name = "bathroom_type")
    @Enumerated(EnumType.STRING)
    private BathroomType bathroomType;

    @ManyToMany
    private List<Bed> beds;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
