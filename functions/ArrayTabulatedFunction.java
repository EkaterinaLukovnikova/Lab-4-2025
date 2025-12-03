package functions;

import java.io.Serializable;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable{
    private FunctionPoint[] points;
    private int pointsCount;
    private final double EPSILON_DOUBLE = 1e-10;

    public ArrayTabulatedFunction(FunctionPoint[] points) {
    if (points == null || points.length < 2) {
        throw new IllegalArgumentException("Должно быть не менее 2 точек");
    }
    
    for (int i = 1; i < points.length; i++) {
        if (points[i].getX() <= points[i-1].getX()) {
            throw new IllegalArgumentException("Точки не упорядочены по X");
        }
    }
    
    this.pointsCount = points.length;
    this.points = new FunctionPoint[pointsCount + 10];
    
    for (int i = 0; i < pointsCount; i++) {
        this.points[i] = new FunctionPoint(points[i]);
    }
}

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (pointsCount < 3){
            throw new IllegalArgumentException("Количество точек должно быть не менее 3");
        }

        if (leftX>=rightX){
            throw new IllegalArgumentException("Правая граница должна быть больше левой");
        }

        this.pointsCount = pointsCount;
        this.points = new FunctionPoint[pointsCount+5];

        double h = (rightX-leftX)/(pointsCount-1);//h - шаг точек
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i*h;
            points[i] = new FunctionPoint(x,0.0);
        }
    
    
    
    }   

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values){
        if (values == null){
            throw new IllegalArgumentException("Массив не может быть пустым");
        }

        if (values.length < 2){
            throw new IllegalArgumentException("Массив должен содержать не менее 2");
        }

        if (leftX>=rightX){
            throw new IllegalArgumentException("Правая граница должна быть больше левой");
        }

        this.pointsCount = values.length;
        this.points = new FunctionPoint[pointsCount+5];

         double h = (rightX-leftX)/(pointsCount-1);//h - шаг точек
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i*h;
            points[i] = new FunctionPoint(x,values[i]);
        }

    }

    
    @Override
    public double getLeftDomainBorder(){
        return points[0].getX();
    }

    @Override
    public double getRightDomainBorder(){
        return points[pointsCount-1].getX();
    }

    @Override
    public double getFunctionValue(double x){
        double M = 0;
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }

        for (int i = 0; i < pointsCount - 1; i++) {
            double x_1 = points[i].getX();
            double x_2 = points[i + 1].getX();
            
            if (x > x_1 && x < x_2) {
                double y1 = points[i].getY();
                double y2 = points[i + 1].getY();
                M = y1 + (y2 - y1) * (x - x_1) / (x_2 - x_1);
                return M;
            }

            if(Math.abs(x - points[i].getX()) < EPSILON_DOUBLE){
                M = points[i].getY();
            }

        }
        
        return M;
    }

    @Override
    public int getPointsCount(){
        return pointsCount;
    }

    @Override
    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new IndexOutOfBoundsException("Индекс выходит за границы");
        }
        return new FunctionPoint(points[index]);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) {
        if (index < 0 || index >= pointsCount) {
            throw new IndexOutOfBoundsException("Индекс выходит за границы");
        }

        if (point.getX() <= points[index - 1].getX()) {
            return; 
        }
        if (point.getX() >= points[index + 1].getX()) {
            return; 
        }

        points[index] = new FunctionPoint(point);
    }

    @Override
    public double getPointX(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new IndexOutOfBoundsException("Индекс выходит за границы");
        }
        return points[index].getX();
    }

    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException{
    
        if (index < 0 || index >= pointsCount) {
            throw new IndexOutOfBoundsException("Индекс выходит за границы");
        }


        if (x <= points[index - 1].getX()) {
            throw new InappropriateFunctionPointException();
        }
        if (x >= points[index + 1].getX()) {
            throw new InappropriateFunctionPointException();
        }

        points[index].setX(x);
    }

    @Override
    public double getPointY(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new IndexOutOfBoundsException("Индекс выходит за границы");
        }
        return points[index].getY();
    }

    @Override
    public void setPointY(int index, double y) {
        if (index < 0 || index >= pointsCount) {
            throw new IndexOutOfBoundsException("Индекс выходит за границы");
        }
        points[index].setY(y);
    }

    @Override
    public void deletePoint(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new IndexOutOfBoundsException("Индекс выходит за границы");
        }
        if (pointsCount < 3) {
            throw new IllegalStateException("Невозможно удалить - нужно 3 точки");
        }

        System.arraycopy(points, index + 1, points, index, pointsCount - index - 1);
        pointsCount--;
        points[pointsCount]=null;
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException{
        int insertIndex = 0;
        while (insertIndex < pointsCount && points[insertIndex].getX() < point.getX()) {
            insertIndex++;
        }

        
        if (insertIndex < pointsCount && Math.abs(point.getX() - points[insertIndex].getX()) < EPSILON_DOUBLE) {
            throw new InappropriateFunctionPointException(); 
        }

        
        if (pointsCount == points.length) {
            FunctionPoint[] newPoints = new FunctionPoint[points.length * 2];
            System.arraycopy(points, 0, newPoints, 0, pointsCount);
            points = newPoints;
        }

        
        System.arraycopy(points, insertIndex, points, insertIndex + 1, pointsCount - insertIndex);
        
        
        points[insertIndex] = new FunctionPoint(point);
        pointsCount++;
    }
}