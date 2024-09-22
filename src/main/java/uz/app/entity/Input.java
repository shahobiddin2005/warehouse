package uz.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Input {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @CreationTimestamp
    private Timestamp date;
    @ManyToOne
    private User courier;
    @ManyToOne
    private Warehouse warehouse;
    private Double totalPrice;
    private Boolean hasPaid;
}

