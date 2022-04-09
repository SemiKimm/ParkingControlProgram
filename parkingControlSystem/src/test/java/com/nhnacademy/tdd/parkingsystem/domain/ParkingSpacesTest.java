package com.nhnacademy.tdd.parkingsystem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingSpacesTest {
    ParkingSpaceRepository parkingSpaceRepository;

    @BeforeEach
    void setUp() {
        parkingSpaceRepository = new ParkingSpaces();
    }

    @DisplayName("주차장에 차가 주차될때 repository 에는 차번호와 차량이 주차된 주차공간이 저장된다.")
    @Test
    void parkingAndFindByCarNumber() {
        String code = "A-1";
        String number = "99조9999";
        String userId = "semi";
        User driver = new User(userId);
        Car car = new Car(number, driver);
        ParkingSpace parkingSpace = new ParkingSpace(code, car);
        parkingSpaceRepository.parking(number, parkingSpace);

        ParkingSpace result = parkingSpaceRepository.findByCarNumber(number);

        assertThat(result).usingRecursiveComparison().isEqualTo(parkingSpace);
    }

    @Test
    void parking_carNumberIsNull_throwIllegalArgumentException() {
        String carNumber = null;
        ParkingSpace parkingSpace = new ParkingSpace("A-1", new Car("99조9999", new User("semi")));

        assertThatThrownBy(() -> parkingSpaceRepository.parking(carNumber, parkingSpace))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("carNumber is null");
    }

    @Test
    void parking_parkingSpaceIsNull_throwIllegalArgumentException() {
        String carNumber = "99조9999";
        ParkingSpace parkingSpace = null;

        assertThatThrownBy(() -> parkingSpaceRepository.parking(carNumber, parkingSpace))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("parkingSpace is null");
    }

    @Test
    void findByCode_carNumberIsNull_throwIllegalArgumentException() {
        String carNumber = null;

        assertThatIllegalArgumentException()
            .isThrownBy(() -> parkingSpaceRepository.findByCarNumber(carNumber))
            .withMessageContaining("carNumber is null");
    }

    @DisplayName("주차 공간 확보")
    @Test
    void reserveParkingSpace() {
        String parkingLotCode = "A-1";

        String number = "99조9999";
        String userId = "semi";
        User driver = new User(userId);
        Car car = new Car(number, driver);

        ParkingSpace result = parkingSpaceRepository.reserveParkingSpace(parkingLotCode, car);

        assertThat(result).isNotNull();
        assertThat(result.getCode()).isEqualTo(parkingLotCode);
        assertThat(result.getCar()).isEqualTo(car);
    }

    @Test
    void reserveParkingSpace_parkingLotCodeIsNull_throwIllegalArgumentException() {
        String parkingLotCode = null;

        String number = "99조9999";
        String userId = "semi";
        User driver = new User(userId);
        Car car = new Car(number, driver);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> parkingSpaceRepository.reserveParkingSpace(parkingLotCode, car))
            .withMessageContaining("parkingLotCode is null");
    }

    @Test
    void reserveParkingSpace_carIsNull_throwIllegalArgumentException() {
        String parkingLotCode = "A-1";
        Car car = null;

        assertThatIllegalArgumentException()
            .isThrownBy(() -> parkingSpaceRepository.reserveParkingSpace(parkingLotCode, car))
            .withMessageContaining("car is null");
    }
}