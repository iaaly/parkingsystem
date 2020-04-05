package com.iaaly.parkingsystem.respository;

import com.iaaly.parkingsystem.entity.PKSlot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends CrudRepository<PKSlot, Long> {
}
