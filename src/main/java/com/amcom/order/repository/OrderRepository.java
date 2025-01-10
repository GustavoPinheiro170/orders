package com.amcom.order.repository;
import com.amcom.order.entitys.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderTable, Long> {

}
