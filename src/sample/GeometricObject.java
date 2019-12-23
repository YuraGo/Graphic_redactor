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

    public void setCut(double x1, double y1, double x2, double y2, int choose){
        y1 = 500 - y1;
        y2 = 500 - y2;
//        double A, B, C, A1, B1, C1, leng, lengAC, lengBC;
//        String ans = new String("");
//        B = (this.pointX2 - this.getPointX1());
//        A = (this.getPointY1() - this.pointY2);
//        leng = Math.sqrt(A * A + B * B);
//        C = this.getPointX1() * this.pointY2 - this.pointX2 * this.getPointY1();
//
//        A1 = (x2 - x1);
//        B1 = (y1 - y2);
//        C = x1*y2 - x2*y1;
        double x1L, x2L, y1L, y2L , x1P, x2P, y1P, y2P ,a, b1, b2, k1,k2;
        x1L = this.getPointX1();
        y1L = this.getPointY1();
        x2L = this.pointX2;
        y2L = this.pointY2;
        if ( x1L > x2L ){
            a = x1L;
            x1L = x2L;
            x2L = a;
            a = y1L;
            y1L = y2L;
            y2L = a;
        }

        if ( x1 > x2 ){
            a = x1;
            x1 = x2;
            x2 = a;
            a = y1;
            y1 = y2;
            y2 = a;
        }

        if ( y1L == y2L){
            k1 = 0;
        } else {
            if ( x1L != x2L ){
                k1 = (y2L - y1L) / (x2L - x1L);
            } else{
                x1P = x1L;
                k1 = -2;
                return;
            }
        }

        if ( y1 == y2){
            k2 = 0;
        } else{
            if( x1 != x2){
                k2 = (y2 - y1)/(x2 - x1);
            } else{
                x1P = x1;
                k2 = -2;
                return;
            }
        }


        if ( k1 == k2) return;

        b1 = y1L - k1*x1L;
        b2 = y1 - k2*x1;

        x1P = (b2 - b1)/(k1 - k2);
        y1P = k1*x1P + b1;

        if ( (x1L <= x2 && x2 <= x2L) || (x1L <= x1 && x1 <= x2L)){
            if ( choose == 1) {
                this.setPoints(x1P, y1P);
            }
            else{
                this.setPointL(x1P, y1P);
            }
        }
    }
}

class Circle extends GeometricObject{

    private double radius, realX, realY, startAngle, arcExtent;

    public Circle(double pointX1, double pointY1, double radius, String id){
        super(pointX1, pointY1,id);
        realX = pointX1;
        realY = 500 - pointY1;
        this.radius = radius;
        this.startAngle = 0;
        this.arcExtent = 360;
    }

    public Circle(Circle copy){
        this(copy.getPointX1(), 500 - copy.getPointY1(), copy.getRadius(), copy.getId()+copy.getCopyCounter());
    }

    public double getRadius(){
        return this.radius;
    }

    public double getArcExtent() {
        return arcExtent;
    }

    public double getStartAngle() {
        return startAngle;
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

    public void setCut(double x1, double y1, double x2, double y2, int choose){
        //y1 = y1;
        //y2 = y2;
        if (y1 == y2) return;
        double A,B,C,a,b;
        a = this.getPointX1();
        b = 500 - this.getPointY1();
        A = (x2 - x1);
        B = Math.abs(y2 - y1);

        double nY1, nY2, n, k ,m;
        double nX1, nX2, t;

        t = A/B;
        C = (x1 - y1*(A/B) - a );

        n = t*t + 1;
        k = 2*(t*C - b);
        m = C*C + b*b - this.getRadius()*this.getRadius();
        double tmp = Math.sqrt(k*k - 4*m*n);
        nY1 = ((-k) + tmp)/(2*n);
        nY2 = ((-k) - tmp)/(2*n);
        nX1 = ((nY1-y1)*A/B) + x1;
        nX2 = ((nY2-y1)*A/B) + x1;

        double angle1, angle2, A1, A2, B1, B2;
        A1 = 0;
        B1 = a + this.radius - a;
        A2 = nY1 - b;
        B2 = a - nX1;
        // a=x b=y circle
        //angle = Math.cos( (A1*A2 + B1*B2)/( Math.sqrt(A1*A1+B1*B1) + Math.sqrt(A2*A2+B2*B2) )); // a*pi/180
        angle1 =  (B1*B2 + A1*A2 )/( Math.sqrt(A1*A1+B1*B1) * Math.sqrt(A2*A2+B2*B2) );
        A1 = 0;
        B1 = a + this.radius;
        A2 = nY2 - b;
        B2 = a - nX2;
        angle2 =  (B1*B2+A1*A2)/( Math.sqrt(A1*A1+B1*B1) * Math.sqrt(A2*A2+B2*B2) );
        //angle2 = Math.abs(Math.toDegrees(angle2));
        angle1 = (Math.toDegrees(angle1));
        angle2 = (Math.toDegrees(angle2));
        if( angle1 < 0){
            angle1 = Math.abs(angle1);
            angle1+=180;
        }
        if( angle2 < 0){
            angle2 = Math.abs(angle2);
            angle2+=180;
        }
        if (choose == 1){
//            this.startAngle = Math.abs(angle1);
//            this.arcExtent  = Math.abs(angle1) + Math.abs(angle2);
            this.startAngle = angle1;
            this.arcExtent  = angle2 - angle1; // line 110 50 30 180 s
        } else {  //   circle 100 100 50 a      cut 10 10 200 200 a    line 100 170 170 80 s  cut 100 170 170 80 a circle 100 100 50 a
            this.startAngle =  angle2;
            this.arcExtent  = 360 - (angle2 - angle1);
        }
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