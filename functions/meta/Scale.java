package functions.meta;

import functions.Function;

public class Scale implements Function {
    private Function f;
    private double scaleX;
    private double scaleY;
    
    public Scale(Function f, double scaleX, double scaleY) {
        if (f == null){
            throw new IllegalArgumentException("Функция не могут быть нулём");
        }
        this.f = f;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
    
    @Override
    public double getLeftDomainBorder() {
        if (scaleX > 0){
            return f.getLeftDomainBorder() * scaleX;
        } else if (scaleX < 0){
            return f.getRightDomainBorder() * scaleX;
        } else {
            return Double.NaN;
        }
        
    }
    
    @Override
    public double getRightDomainBorder() {
        if (scaleX > 0){
            return f.getRightDomainBorder() * scaleX;
        } else if (scaleX < 0){
            return f.getLeftDomainBorder() * scaleX;
        } else {
            return Double.NaN;
        }
    }
    
    @Override
    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()){
            return Double.NaN;
        }
        else{
            return f.getFunctionValue(x * scaleX) * scaleY;
        }
        
    }
}