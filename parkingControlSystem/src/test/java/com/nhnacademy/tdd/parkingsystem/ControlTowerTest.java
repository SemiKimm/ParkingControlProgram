package com.nhnacademy.tdd.parkingsystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.nhnacademy.tdd.parkingsystem.domain.Car;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ControlTowerTest {
    //SUT
    ControlTower controlTower;
    //DOC

    @BeforeEach
    void setUp() {
        controlTower = new ControlTower();
    }


    @AfterEach
    void tearDown() {
    }
}