package com.nhnacademy.tdd.parkingsystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.tdd.parkingsystem.domain.Car;
import com.nhnacademy.tdd.parkingsystem.domain.Parkable;
import com.nhnacademy.tdd.parkingsystem.domain.User;
import com.nhnacademy.tdd.parkingsystem.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingSystemTest {
    //SUT
    private ParkingSystem parkingSystem;
    //DOC
    private Parkable parkingLot;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        parkingLot = mock(Parkable.class);
        userRepository = mock(UserRepository.class);
        parkingSystem = new ParkingSystem(parkingLot, userRepository);
    }

    @DisplayName("주차장에 차가 들어온다. -> 들어온 차에는 운전자가 있다.")
    @Test
    void comeIn() {
        String number = "99조9999";
        String userId = "semi";
        User driver = new User(userId);
        Car car = new Car(number, driver);

        when(parkingLot.enter(car)).thenReturn(driver);

        User result = parkingSystem.comeIn(car);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);

        verify(parkingLot).enter(car);
    }

    @Test
    void comeIn_carIsNull_throwsIllegalArgumentException(){
        Car car = null;

        assertThatThrownBy(()->parkingSystem.comeIn(car))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("no car");

        verify(parkingLot,never()).enter(car);
    }
}