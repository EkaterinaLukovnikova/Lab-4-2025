package functions.meta;

import functions.Function;

public class Mult implements Function {
    private Function f1;
    private Function f2;
    
    public Mult(Function f1, Function f2) {
        if (f1 == null || f2 == null){
            throw new IllegalArgumentException("Функции не могут быть нулевыми");
        }
        this.f1 = f1;
        this.f2 = f2;
    }
    
    @Override
    public double getLeftDomainBorder() {
        return Math.max(f1.getLeftDomainBorder(), f2.getLeftDomainBorder());
    }
    
    @Override
    public double getRightDomainBorder() {
        return Math.min(f1.getRightDomainBorder(), f2.getRightDomainBorder());
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