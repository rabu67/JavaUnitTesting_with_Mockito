package com.rabu.java.unit.testing.JavaUnitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
	
	Calculator calc;
	
	@BeforeEach
	void setUp() {
		calc = new Calculator();
	}
	
	@Test
	public void multiplyTest() {
		assertEquals(20, calc.multiply(10, 2));
	}
	
}
