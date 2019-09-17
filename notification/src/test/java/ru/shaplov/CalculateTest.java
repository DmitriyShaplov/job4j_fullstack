package ru.shaplov;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class CalculateTest {
    @Test
    public void whenOnePlusOneThenTwo() {
        assertThat(
                new Calculate().add(1, 1),
                is(2)
        );
    }
}