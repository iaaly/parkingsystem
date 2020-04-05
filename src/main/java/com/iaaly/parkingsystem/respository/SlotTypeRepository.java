package com.iaaly.parkingsystem.respository;

import com.iaaly.parkingsystem.entity.PKSlotType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotTypeRepository extends CrudRepository<PKSlotType, Long> {
}
