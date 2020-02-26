package temp;

public interface CalculatorModule {
    int calculate(final int a, final int b);

}

class Calculator {
    public int calculate(final int a, final int b, final CalculatorModule module) {
        return module.calculate(a, b);
    }
}
