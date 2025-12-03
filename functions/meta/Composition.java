package functions.meta;

import functions.Function;

public class Composition implements Function {
    private Function f1;
    private Function f2;
    
    public Composition(Function f1, Function f2) {
        if (f1 == null || f2 == null){
            throw new IllegalArgumentException("Функции не могут быть нулевыми");
        }
        this.f1 = f1;
        this.f2 = f2;
    }
    
    @Override
    public double getLeftDomainBorder() {
        return f1.getLeftDomainBorder();
    }
    
    @Override
    public double getRightDomainBorder() {
        return f1.getRightDomainBorder();
    }
    
    @Override
    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()){
            return Double.NaN;
        }
        else{
            return f2.getFunctionValue(f1.getFunctionValue(x));
        }
        
    }
}