package com.iaaly.parkingsystem.respository;

import com.iaaly.parkingsystem.entity.PKPricingPolicyAttributes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingPolicyAttributesRepository extends CrudRepository<PKPricingPolicyAttributes, Long> {
}
