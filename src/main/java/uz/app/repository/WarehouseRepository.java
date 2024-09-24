package uz.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.entity.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, String> {

}
