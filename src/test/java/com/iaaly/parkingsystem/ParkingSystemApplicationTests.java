package com.iaaly.parkingsystem;

import com.iaaly.parkingsystem.entity.*;
import com.iaaly.parkingsystem.respository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ParkingSystemApplicationTests {

	private final FloorRepository floorRepository;
	private final SlotTypeRepository slotTypeRepository;
	private final FloorSlotsRepository floorSlotsRepository;
	private final SlotRepository slotRepository;
	private final PricingPolicyAttributesRepository pricingPolicyAttributesRepository;
	private final BillRepository billRepository;
	private final TicketRepository ticketRepository;
	private final CarRepository carRepository;

	@Autowired
	ParkingSystemApplicationTests(FloorRepository floorRepository,
								  SlotTypeRepository slotTypeRepository,
								  FloorSlotsRepository floorSlotsRepository,
								  SlotRepository slotRepository,
								  PricingPolicyAttributesRepository pricingPolicyAttributesRepository,
								  BillRepository billRepository,
								  TicketRepository ticketRepository,
								  CarRepository carRepository) {
		this.floorRepository = floorRepository;
		this.slotTypeRepository = slotTypeRepository;
		this.floorSlotsRepository = floorSlotsRepository;
		this.slotRepository = slotRepository;
		this.pricingPolicyAttributesRepository = pricingPolicyAttributesRepository;
		this.billRepository = billRepository;
		this.ticketRepository = ticketRepository;
		this.carRepository = carRepository;
	}

	@Test
	void contextLoads() {
	}

	/**
	 * Sample Parking description:
	 * This is a demo Test function that will generate test data in our DB
	 * In this sample, we are defining a Parking with three floors: P1, P2, and P3
	 * Also we are defining three slot types:
	 * 		- Type A: Gasoline cars
	 * 		- Type B: 20KW electric cars
	 * 		- Type C: 50KW electric cars
	 * The following rules apply:
	 * 		- P1 accepts electric cars only (20 slots type B, 20 slots type C)
	 * 		- P2 & P3 accept only Gasoline Cars (40 slots type A each)
	 * For billing, three pricing policies all based on linear calcualtion as the following:
	 * 		- Type A: hourly rate of 1 EUR per hour
	 * 		- Type B: hourly rate of 2 EUR per hour + fixed rate of 3 EUR
	 * 		- Type C: hourly rate of 3 EUR per hour + fixed rate of 4 EUR
	 */
	@Test
	void initializeSampleParking() {

		// Clear previous data
		emptyDB();

		///////////////////////////////////////////////////////////////
		// We define three pricing policies, one for every slot type /
		/////////////////////////////////////////////////////////////

		PKPricingPolicyAttributes policyAttributesA = PKPricingPolicyAttributes.builder()
				.fixedRate(0)
				.hourlyRate(1)
				.build();
		policyAttributesA = pricingPolicyAttributesRepository.save(policyAttributesA);


		PKPricingPolicyAttributes policyAttributesB = PKPricingPolicyAttributes.builder()
				.fixedRate(3)
				.hourlyRate(2)
				.build();
		policyAttributesB = pricingPolicyAttributesRepository.save(policyAttributesB);


		PKPricingPolicyAttributes policyAttributesC = PKPricingPolicyAttributes.builder()
				.fixedRate(4)
				.hourlyRate(2)
				.build();
		policyAttributesC = pricingPolicyAttributesRepository.save(policyAttributesC);

		////////////////////////////////////////////////////////////
		// We define three type of car slots: 20KW, 50KW, and GAS /
		//////////////////////////////////////////////////////////
		PKSlotType gasType = PKSlotType.builder()
				.description("Gasoline based cars")
				.key("A")
				.pricingPolicyAttributes(policyAttributesA)
				.build();
		gasType = slotTypeRepository.save(gasType);

		PKSlotType elec20Type = PKSlotType.builder()
				.description("20KW electric cars")
				.key("B")
				.pricingPolicyAttributes(policyAttributesB)
				.build();
		elec20Type = slotTypeRepository.save(elec20Type);

		PKSlotType elec50Type = PKSlotType.builder()
				.description("50KW electric cars")
				.key("C")
				.pricingPolicyAttributes(policyAttributesC)
				.build();
		elec50Type = slotTypeRepository.save(elec50Type);

		// We have Parking of 3 floors P1, P2, P3
		// P1 is for electric cars only: 20KW and 50KW
		// P2 and P3 are for Gasoline cars only

		///////////////////////
		///////// P1 //////////
		///////////////////////

		PKFloor floor1 = PKFloor.builder()
				.name("P1")
				.build();
		floor1 = floorRepository.save(floor1);

		PKFloorSlots floor20KWSlots = PKFloorSlots.builder()
				.nbSlots(20)
				.floor(floor1)
				.slotType(elec20Type)
				.build();
		floor20KWSlots = floorSlotsRepository.save(floor20KWSlots);

		fillSlots(floor20KWSlots);

		PKFloorSlots floor50KWSlots = PKFloorSlots.builder()
				.nbSlots(20)
				.floor(floor1)
				.slotType(elec50Type)
				.build();
		floor50KWSlots = floorSlotsRepository.save(floor50KWSlots);

		fillSlots(floor50KWSlots);

		///////////////////////
		///////// P2 //////////
		///////////////////////

		PKFloor floor2 = PKFloor.builder()
				.name("P2")
				.build();
		floor2 = floorRepository.save(floor2);

		PKFloorSlots floorGasSlots = PKFloorSlots.builder()
				.nbSlots(40)
				.floor(floor2)
				.slotType(gasType)
				.build();
		floorGasSlots = floorSlotsRepository.save(floorGasSlots);

		fillSlots(floorGasSlots);

		///////////////////////
		///////// P3 //////////
		///////////////////////

		PKFloor floor3 = PKFloor.builder()
				.name("P3")
				.build();
		floor3 = floorRepository.save(floor3);


		PKFloorSlots floorGasSlots2 = PKFloorSlots.builder()
				.nbSlots(40)
				.floor(floor3)
				.slotType(gasType)
				.build();
		floorGasSlots2 = floorSlotsRepository.save(floorGasSlots2);

		fillSlots(floorGasSlots2);
	}

	private void emptyDB() {
		// The order is important here!
		ticketRepository.deleteAll();
		billRepository.deleteAll();
		slotRepository.deleteAll();
		carRepository.deleteAll();
		floorSlotsRepository.deleteAll();
		slotTypeRepository.deleteAll();
		pricingPolicyAttributesRepository.deleteAll();
		floorRepository.deleteAll();
	}

	void fillSlots(PKFloorSlots floorSlots) {

		for (int i = 1; i <= floorSlots.getNbSlots(); i++) {
			slotRepository.save(PKSlot.builder()
					.slotType(floorSlots.getSlotType())
					.floor(floorSlots.getFloor())
					.identifier(String.format("%s%s%03d",
							floorSlots.getFloor().getName(),
							floorSlots.getSlotType().getKey(),
							i))
					.build());
		}

	}

}
