package com.nhnacademy.tdd.parkingsystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
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
        parkingSystem = new ParkingSystem(parkingLot,userRepository);
    }

    @DisplayName("주차장에 차가 들어온다.")
    @Test
    void comeIn() {
        String parkingLotCode = "A-1";
        String number = "99조9999";
        String userId = "semi";
        User driver = new User(userId);
        Car car = new Car(number, driver);

        when(parkingLot.enter(parkingLotCode, car)).thenReturn(driver);

        User result = parkingSystem.comeIn(car, parkingLotCode);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);

        verify(parkingLot).enter(parkingLotCode, car);
        verify(userRepository).insert(result);
    }

    @Test
    void comeIn_carIsNull_throwsIllegalArgumentException(){
        String parkingLotCode = "A-1";
        Car car = null;

        assertThatThrownBy(()->parkingSystem.comeIn(car, parkingLotCode))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("no car");

        verify(parkingLot,never()).enter(parkingLotCode, car);
    }

    @DisplayName("주차장에서 차가 나간다.")
    @Test
    void comeOut(){
        String carNumber = "99조9999";
        String userId = "semi";
        User driver = new User(userId);
        when(parkingLot.exit(carNumber)).thenReturn(driver);

        User result = parkingSystem.comeOut(carNumber);

        assertThat(result).isNotNull();
        assertThat(result.getUserId().equals(userId)).isTrue();

        verify(parkingLot).exit(carNumber);
        verify(userRepository).delete(driver);
    }

    @Test
    void comeOut_carNumberIsNull_throwsIllegalArgumentException(){
        String carNumber = null;

        assertThatIllegalArgumentException()
            .isThrownBy(()->parkingSystem.comeOut(carNumber))
            .withMessageContaining("carNumber is null");

        verify(parkingLot,never()).exit(carNumber);
    }
}