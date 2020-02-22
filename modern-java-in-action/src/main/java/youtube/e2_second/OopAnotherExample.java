package youtube.e2_second;

/**
 * Created by Stranger on 2020/02/22
 */

/**
 * OOP로 하면 이렇게 복잡한 계산기가 함수를 이용하며 매우 간단하게 구현이 가능하다.
 * - FpCalculatorServiceTest 참고
 */
public class OopAnotherExample {
}

interface Calculation {
    int calculate(int a, int b);
}

class Addition implements Calculation {
    @Override
    public int calculate(int a, int b) {
        return a + b;
    }
}

class Subtraction implements Calculation {
    @Override
    public int calculate(int a, int b) {
        return a - b;
    }
}

class Multiplication implements Calculation {
    @Override
    public int calculate(int a, int b) {
        return a * b;
    }
}

class Division implements Calculation {
    @Override
    public int calculate(int a, int b) {
        return a / b;
    }
}

class CalculatorService {
    private final Calculation calculation;

    public CalculatorService(Calculation calculation) {
        this.calculation = calculation;
    }

    public int calculate(int num1, int num2) {
        return calculation.calculate(num1, num2);
    }
}

class FpCalculatorService{
    public int calculation(Calculation calculation, int num1, int num2){
        return calculation.calculate(num1, num2);
    }
}