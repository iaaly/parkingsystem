package com.iaaly.parkingsystem.respository;

import com.iaaly.parkingsystem.entity.PKSlot;
import com.iaaly.parkingsystem.entity.PKSlotType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SlotRepository extends CrudRepository<PKSlot, Long> {
    Optional<PKSlot> findFirstByOccupiedAndSlotType_KeyOrderByFloorAsc(boolean occupied,
                                                                   String slotTypeKey);
}
