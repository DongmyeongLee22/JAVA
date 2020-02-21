package youtube;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Stranger on 2020/02/22
 */
public class FpCalculatorServiceTest {

    @Test
    public void calculation() {
        Calculation sum = (a, b) -> a + b;
        Calculation sub = (a, b) -> a - b;
        Calculation mul = (a, b) -> a * b;
        Calculation div = (a, b) -> a / b;

        FpCalculatorService calculatorService = new FpCalculatorService();
        assertEquals(3, calculatorService.calculation(sum, 1, 2));
        assertEquals(1, calculatorService.calculation(sub, 3, 2));
        assertEquals(30, calculatorService.calculation(mul, 10, 3));
        assertEquals(10, calculatorService.calculation(div,20, 2));
    }
}