package com.iaaly.parkingsystem.respository;

import com.iaaly.parkingsystem.entity.PKBill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends CrudRepository<PKBill, Long> {
}
