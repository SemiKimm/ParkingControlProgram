package com.nhnacademy.tdd.parkingsystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.tdd.parkingsystem.domain.Car;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ControlTowerTest {
    //SUT
    ControlTower controlTower;

    @BeforeEach
    void setUp() {
        controlTower = new ControlTower();
    }

    @DisplayName("controlTower에서 차량의 들어옴을 관리한다.")
    @Test
    void manage_carComesIn() {
        String carNumber = "99조 9999";
        Car car = new Car(carNumber);
        assertThat(controlTower.manageComeIn(car)).isEqualTo(true);
    }

    @DisplayName("차가 들어오면 번호판을 스캔한다. - 스캔해서 얻은 번호와 차량의 번호가 동일하다.")
    @Test
    void manage_scanCarNumber(){
        String carNumber = "99조 9999";
        Car car = new Car(carNumber);

        assertThat(controlTower.scanCarNumber(car.getNumber()).equals(carNumber)).isTrue();
    }



    @AfterEach
    void tearDown() {
    }
}