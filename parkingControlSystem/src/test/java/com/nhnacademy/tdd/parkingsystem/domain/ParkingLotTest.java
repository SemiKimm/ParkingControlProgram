package com.nhnacademy.tdd.parkingsystem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingLotTest {

    Parkable parkingLot;
    ParkingSpaceRepository parkingSpaces;
    Entrance entrance;

    @BeforeEach
    void setUp() {
        parkingSpaces = mock(ParkingSpaceRepository.class);
        entrance = mock(Entrance.class);
        parkingLot = new ParkingLot(parkingSpaces, entrance);
    }

    @DisplayName("주차장에 차가 들어오면 번호판을 인식하고 주차한다.")
    @Test
    void enter() {
        String parkingLotCode = "A-1";

        String number = "99조9999";
        String userId = "semi";
        User driver = new User(userId);
        Car car = new Car(number, driver);

        ParkingSpace parkingSpace = new ParkingSpace(parkingLotCode, car);

        when(entrance.scan(car)).thenReturn(number);
        when(parkingSpaces.reserveParkingSpace(parkingLotCode, car)).thenReturn(parkingSpace);

        User result = parkingLot.enter(parkingLotCode, car);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);

        verify(entrance).scan(car);
        verify(parkingSpaces).reserveParkingSpace(parkingLotCode,car);
        verify(parkingSpaces).parking(number, parkingSpace);
    }

    @Test
    void enter_carIsNull_throwsIllegalArgumentException() {
        String parkingLotCode = "A-1";
        Car car = null;

        assertThatThrownBy(() -> parkingLot.enter(parkingLotCode, car))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("no car");

        verify(entrance, never()).scan(car);
        verify(parkingSpaces,never()).reserveParkingSpace(parkingLotCode,car);
    }

    @Test
    void enter_parkingLotCodeIsNull_enter_carIsNull_throwsIllegalArgumentException(){
        String parkingLotCode = null;

        String number = "99조9999";
        String userId = "semi";
        User driver = new User(userId);
        Car car = new Car(number, driver);

        assertThatThrownBy(() -> parkingLot.enter(parkingLotCode, car))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("no parkingLotCode");

        verify(entrance, never()).scan(car);
        verify(parkingSpaces,never()).reserveParkingSpace(parkingLotCode,car);
    }
}