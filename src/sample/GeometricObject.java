package sample;

import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Vector;

public class GeometricObject {
    private int copyCounter;
    private String id;
    private double pointX1, pointY1;
    private Color color;
    private double dashes;

    public GeometricObject(double pointX1, double pointY1, String id ){
        this.copyCounter = 0;
        this.pointX1 = pointX1;
        this.pointY1 = 500 - pointY1;
        this.id = id;
        this.color = Color.BLACK;
        this.dashes = 0;
    }

    public double getPointX1() {
        return this.pointX1;
    }

    public double getPointY1() {
        return this.pointY1;
    }

    public String getId() {
        return id;
    }

    public double getDashes() {
        return dashes;
    }

    public Color getColor() {
        return color;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPoints(double pointX1, double pointY1) {
        this.pointX1 = pointX1;
        this.pointY1 = pointY1;
    }

    public void setDashes(double dashes) {
        this.dashes = dashes;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void incCopyCounter(){
        this.copyCounter+=1;
    }

    public int getCopyCounter(){
        return this.copyCounter;
    }

}

class StrokeLine extends GeometricObject {
    private double pointX2, pointY2;

    public StrokeLine(double pointX1,double pointY1,double pointX2,double pointY2, String id ){
        super(pointX1,pointY1,id);
        this.pointX2 = pointX2;
        this.pointY2 = 500 - pointY2;
    }

    public StrokeLine(StrokeLine copyOf){
        this(copyOf.getPointX1(), 500-copyOf.getPointY1(), copyOf.pointX2, 500-copyOf.pointY2, copyOf.getId()+copyOf.getCopyCounter());
    }

    public double getPointX2(){
        return this.pointX2;
    }

    public double getPointY2(){
        return this.pointY2;
    }

    public void setPointL(double x2, double y2){
        this.pointX2 = x2;
        this.pointY2 = y2;
    }

    public String getFunc(double x, double y) {
        y = 500 - y;
        double A, B, C, leng, lengAC, lengBC;
        String ans = new String("");
        B = (this.pointX2 - this.getPointX1());
        A = (this.getPointY1() - this.pointY2);
        leng = Math.sqrt(A * A + B * B);
        C = this.getPointX1() * this.pointY2 - this.pointX2 * this.getPointY1();

        // проверка принадлежности точки к прямой near 80 80 b    line 10 10 70 70 b
        if ((A * x + B * y + C) == 0) {
            // first vector BC, second AC
            lengAC = Math.max(Math.sqrt((this.pointX2 - x) * (this.pointX2 - x) + (this.pointY2 - y) * (this.pointY2 - y)),
                    Math.sqrt((this.getPointX1() - x) * (this.getPointX1() - x) + (this.getPointY1() - y) * (this.getPointY1() - y)));

            // проверка точки на отрезке
            if (leng > lengAC) return ans = "x: " + x + " ; y: " + (500-y);
            lengBC = Math.sqrt((this.pointX2 - x) * (this.pointX2 - x) + (this.pointY2 - y) * (this.pointY2 - y));
            lengAC = Math.sqrt((this.getPointX1() - x) * (this.getPointX1() - x) + (this.getPointY1() - y) * (this.getPointY1() - y));

            if( lengAC > lengBC) {
                ans = "x: " + this.pointX2 + " ; y: " + (500 - this.pointY2);
            }else{
                ans = "x: " + this.getPointX1() + " ; y: " + (500 - this.getPointY1());
            }

        } else {
            double xP1, yP1, xP2, yP2;
            //yP1 = (C+A*xP1)/(-B);
            xP1 = (-A * C - y * B * A + x * B * B) / (B * B + A * A);
            yP1 = (C + A * xP1) / (-B);
            ans = "x: " + xP1 + " ; y: " + (500-yP1);
            // line 10 10 200 200 a
            lengBC = Math.sqrt((this.pointX2 - xP1) * (this.pointX2 - xP1) + (this.pointY2 - yP1) * (this.pointY2 - yP1));
            lengAC = Math.sqrt((this.getPointX1() - xP1) * (this.getPointX1() - xP1) + (this.getPointY1() - yP1) * (this.getPointY1() - yP1));
            System.out.println(ans);

            if ( leng > Math.max(lengAC, lengBC) ){
                System.out.println("inside");
                return ans;
            }

            if( lengAC > lengBC) {
                ans = "x: " + this.pointX2 + " ; y: " + (500 - this.pointY2);
            }else{
                ans = "x: " + this.getPointX1() + " ; y: " + (500 - this.getPointY1());
            }
        }

        return ans;
    }

    public String setCut(double x1, double y1, double x2, double y2){
        return null;
    }
}

class Circle extends GeometricObject{

    private double radius, realX, realY;

    public Circle(double pointX1, double pointY1, double radius, String id){
        super(pointX1, pointY1,id);
        realX = pointX1;
        realY = 500 - pointY1;
        this.radius = radius;
    }

    public Circle(Circle copy){
        this(copy.getPointX1(), 500 - copy.getPointY1(), copy.getRadius(), copy.getId()+copy.getCopyCounter());
    }

    public double getRadius(){
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setPoints(double x1, double y1, double radius){
        setPoints(x1, y1);
        this.radius = radius;
    }

    public String getFunc(double x, double y){
        y = 500 - y;
        double A,B,C,a,b;
        String ans = new String("");
        B = (x - this.realX);
        A = (this.realY - y);
        C = this.realX * y - x * this.realY;
        a = this.realX;
        b = this.realY;

        double nY1, nY2, n, k ,m;
        double nX1, nX2;

        if ( A == 0 ){
            return ans = "x: " + (a + this.radius) + " ; y: " + (500-b);
        }
        if ( B == 0){
            return ans = "x: " + a + " ; y: " + (500-b + this.radius);
        }


        n = (B*B)/(A*A)+1;
        k = ( 2*(C*B)/(A*A) + 2*(B*a)/A - 2*b);
        m = ( (C*C)/(A*A) + 2*C*a/A + a*a + b*b - this.getRadius()*this.getRadius() );
        double D = Math.sqrt(k*k - 4*n*m);
        nY1 = (-k + D)/(2*n);
        nY2 = (-k - D)/(2*n);

        nX1 = (C + B*nY1)/(-A);
        nX2 = (C + B*nY2)/(-A);

        if ( (nY1 - this.realY)*(nY1 - this.realY) + (nX1 - this.realX)*(nX1 - this.realX) >
                (nY2 - this.realY)*(nY2 - this.realY) + (nX2 - this.realX)*(nX2 - this.realX) ){
            ans = "x: " + Math.round(nX2) + " ; y: " + Math.round(500-nY2);
        } else ans = "x: " + Math.round(nX1) + " ; y: " +  Math.round(500-nY1);
        return ans;
    }
}

class BezierLine extends GeometricObject{
    private double pointX2, pointY2, pointX3, pointY3;

    public BezierLine(double pointX1,double pointY1,double pointX2,double pointY2,double pointX3, double pointY3 ,String id ){
        super(pointX1,pointY1,id);
        this.pointX2 = pointX2;
        this.pointY2 = 500 - pointY2;
        this.pointX3 = pointX3;
        this.pointY3 = 500 - pointY3;
    }

    public BezierLine(BezierLine c){
        this(c.getPointX1(), 500-c.getPointY1(), c.pointX2, 500-c.pointY2, c.pointX3, 500-c.pointY3, c.getId()+c.getCopyCounter());
    }

    public double getPointX2(){
        return this.pointX2;
    }

    public double getPointY2(){
        return this.pointY2;
    }

    public double getPointX3(){
        return this.pointX3;
    }

    public double getPointY3() {
        return pointY3;
    }

    public void setPointB2(double x2, double y2){
        this.pointX2 = x2;
        this.pointY2 = y2;
    }

    public void setPointB3(double x3, double y3){
        this.pointX3 = x3;
        this.pointY3 = y3;
    }

    public String getFunc(double x, double y){
        double xC, yC, prevL = 0, countL, xA=x, yA=y; // bez 10 10 100 100 190 10 a

        for( double i = 0; i <= 1; i+=0.005){
            xC = (1-i)*(1-i)*this.getPointX1() + 2*(1-i)*i*this.pointX2 + i*i*this.pointX3;
            yC = (1-i)*(1-i)*(500-this.getPointY1()) + 2*(1-i)*i*(500-this.pointY2) + i*i*(500-this.pointY3);
            countL = (xC-x)*(xC-x)+(yC - y)*(yC - y);
            if (prevL > countL || i == 0){
                prevL = countL;
                xA = xC;
                yA = yC;
            }
        }
        return "x: " + Math.round(xA) + " ; y: " +  Math.round(yA);
    }
}