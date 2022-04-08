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
        Car car = new Car();

        //when(controlTower.manageComeIn(car)).thenReturn(true);
        assertThat(controlTower.manageComeIn(car)).isEqualTo(true);

        verify(controlTower).manageComeIn(car);
    }

    @AfterEach
    void tearDown() {
    }
}