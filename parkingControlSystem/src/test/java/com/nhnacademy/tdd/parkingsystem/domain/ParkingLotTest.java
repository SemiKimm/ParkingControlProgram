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

    @DisplayName("주차장에 차가 들어오면 운전자가 반환된다.")
    @Test
    void enter() {
        String number = "99조9999";
        String userId = "semi";
        User driver = new User(userId);
        Car car = new Car(number, driver);

        when(entrance.scan(car)).thenReturn(number);

        User result = parkingLot.enter(car);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);

        verify(entrance).scan(car);
    }

    @Test
    void enter_carIsNull_throwsIllegalArgumentException(){
        Car car = null;

        assertThatThrownBy(()->parkingLot.enter(car))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("no car");

        verify(entrance,never()).scan(car);
    }
}