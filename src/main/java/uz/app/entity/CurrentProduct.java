package uz.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CurrentProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Double price;
    private Integer quantity;
    @ManyToOne
    private InputProduct inputProduct;
}
