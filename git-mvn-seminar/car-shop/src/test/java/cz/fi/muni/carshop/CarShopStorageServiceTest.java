package cz.fi.muni.carshop;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import org.junit.rules.ExpectedException;
import org.junit.Rule;

import cz.fi.muni.carshop.entities.Car;
import cz.fi.muni.carshop.enums.CarTypes;
import cz.fi.muni.carshop.exceptions.RequestedCarNotFoundException;
import cz.fi.muni.carshop.services.CarShopStorageService;
import cz.fi.muni.carshop.services.CarShopStorageServiceImpl;
import static org.junit.Assert.assertFalse;

public class CarShopStorageServiceTest {

	private CarShopStorageService service = new CarShopStorageServiceImpl();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test()
	public void testPriceCantBeNegative() {
		// JUnit 4.11
		//thrown.expect(IllegalArgumentException.class);
		// JUnit 4.12
		thrown.reportMissingExceptionWithMessage("We expect exception on negative price").expect(IllegalArgumentException.class);

		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, -1));
	}

	@Test
	public void testGetCar() {
		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 899000));

		assertTrue(service.isCarAvailable(Color.BLACK, CarTypes.AUDI).isPresent());
	}

	@Test
	public void testCarShopStorage_containsTypeForExistingCar() {
		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 899000));
		Map<CarTypes, List<Car>> cars = CarShopStorage.getInstancce().getCars();

		assertThat(cars, hasKey(CarTypes.AUDI));
	}

	// expected to fail with JUnit < 4.11
	@Test
	public void testGetCheaperCars_returnsCorrectResult() {
		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 899000));
		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 889000));
		service.addCarToStorage(new Car(Color.WHITE, CarTypes.AUDI, 2016, 859000));
		service.addCarToStorage(new Car(Color.BLUE, CarTypes.AUDI, 2016, 909000));

		assertThat(service.getCheaperCarsOfSameTypeAndYear(new Car(Color.BLACK, CarTypes.AUDI, 2016, 900000)),
				hasSize(3));

	}
        
        @Test()
	public void sellNonexistingCar() throws RequestedCarNotFoundException {
                thrown.reportMissingExceptionWithMessage("Requested car does not exist").expect(RequestedCarNotFoundException.class);
                
		service.sellCar(new Car(Color.BLACK, CarTypes.AUDI, 2016, 100));
	}
        
        @Test
	public void sellExistingCar() throws RequestedCarNotFoundException {
            service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 899000));
            Map<CarTypes, List<Car>> allCars = CarShopStorage.getInstancce().getCars();
            Car createdCar = allCars.get(CarTypes.AUDI).iterator().next();
            
            service.sellCar(createdCar);
            assertFalse(service.isCarAvailable(Color.BLACK, CarTypes.AUDI).isPresent());
	}

}
