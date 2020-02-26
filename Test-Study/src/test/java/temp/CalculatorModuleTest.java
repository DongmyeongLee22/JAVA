package temp;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalculatorModuleTest {

    @Test
    void calculate() throws Exception{
        final CalculatorModule sum = (a, b) -> a + b;
        final CalculatorModule subtract = (a, b) -> a - b;
        final CalculatorModule multiply = (a, b) -> a * b;
        final CalculatorModule divide = (a, b) -> {
            if (b == 0) throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
            return a / b;
        };

        final Calculator calculator = new Calculator();
        assertThat(calculator.calculate(1, 2, sum)).isEqualTo(3);
        assertThat(calculator.calculate(1, 2, subtract)).isEqualTo(-1);
        assertThat(calculator.calculate(1, 2, multiply)).isEqualTo(2);
        assertThat(calculator.calculate(1, 2, divide)).isEqualTo(0);

        assertThatThrownBy(() -> calculator.calculate(2, 0, divide))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0으로 나눌 수 없습니다.");

    }
}