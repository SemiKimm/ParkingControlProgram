package com.nhnacademy.tdd.parkingsystem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntranceTest {

    Entrance entrance;

    @BeforeEach
    void setUp() {
        entrance = new Entrance();
    }

    @DisplayName("차 번호판을 인식한 결과는 차의 번호와 동일하다.")
    @Test
    void scan() {
        String number = "99조 9999";
        String userId = "semi";
        User driver = new User(userId);
        Car car = new Car(number,driver);

        String result = entrance.scan(car);
        assertThat(result).isNotNull();
        assertThat(result.equals(number)).isTrue();
    }

    @Test
    void scan_carIsNull_throwsIllegalArgumentException(){
        Car car = null;

        assertThatThrownBy(()->entrance.scan(car))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("no car");
    }
}