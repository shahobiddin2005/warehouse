package uz.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OutputProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private Product product;
    private Integer quantity;
    private Double price;
    @ManyToOne
    private Input input;
    @ManyToOne
    private Measurment measurment;
    private Integer measurmentNum;
}
