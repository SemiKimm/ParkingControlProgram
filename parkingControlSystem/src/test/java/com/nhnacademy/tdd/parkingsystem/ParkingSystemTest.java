package com.nhnacademy.tdd.parkingsystem;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.tdd.parkingsystem.domain.Car;
import com.nhnacademy.tdd.parkingsystem.domain.Money;
import com.nhnacademy.tdd.parkingsystem.domain.Parkable;
import com.nhnacademy.tdd.parkingsystem.domain.User;
import com.nhnacademy.tdd.parkingsystem.domain.UserRepository;
import java.time.LocalTime;
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
        Money money = new Money(10_000L);
        User driver = new User(userId, money);
        Car car = new Car(number, driver);

        LocalTime startParkingTime = LocalTime.of(14,00,00);

        when(parkingLot.enter(parkingLotCode, car, startParkingTime)).thenReturn(driver);

        parkingSystem.comeIn(car, parkingLotCode, startParkingTime);

        verify(parkingLot).enter(parkingLotCode, car, startParkingTime);
        verify(userRepository).insert(driver);
    }

    @Test
    void comeIn_carIsNull_throwsIllegalArgumentException(){
        String parkingLotCode = "A-1";
        Car car = null;
        LocalTime startParkingTime = LocalTime.of(14,00,00);
        assertThatThrownBy(()->parkingSystem.comeIn(car, parkingLotCode, startParkingTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("no car");

        verify(parkingLot,never()).enter(parkingLotCode, car, startParkingTime);
    }

    @DisplayName("주차장에서 차가 나간다.")
    @Test
    void comeOut(){
        String carNumber = "99조9999";
        String userId = "semi";
        Money money = new Money(10_000L);
        User driver = new User(userId, money);
        LocalTime endParkingTime = LocalTime.of(14,30,0);

        when(parkingLot.exit(carNumber, endParkingTime)).thenReturn(driver);

        parkingSystem.comeOut(carNumber, endParkingTime);

        verify(parkingLot).exit(carNumber, endParkingTime);
        verify(userRepository).delete(driver);
    }

    @Test
    void comeOut_carNumberIsNull_throwsIllegalArgumentException(){
        String carNumber = null;
        LocalTime endParkingTime = LocalTime.of(14,30,0);
        assertThatIllegalArgumentException()
            .isThrownBy(()->parkingSystem.comeOut(carNumber, endParkingTime))
            .withMessageContaining("carNumber is null");

        verify(parkingLot,never()).exit(carNumber, endParkingTime);
    }
}