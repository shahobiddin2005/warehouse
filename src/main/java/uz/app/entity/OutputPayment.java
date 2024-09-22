package uz.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OutputPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Timestamp date;
    private Double amount;
    @ManyToOne
    private Input input;
    private Boolean paid;
}
