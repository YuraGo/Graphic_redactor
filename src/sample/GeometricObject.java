package sample;

import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Vector;

public class GeometricObject {

    private String id;
    private double pointX1, pointY1;
    private Color color;
    //private StrokeLineCap lineCap;
    private double dashes;

    public GeometricObject(double pointX1, double pointY1, String id ){
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


    //private static HashMap<String, GeometricObject> allDraws = new HashMap<>();



}

class StrokeLine extends GeometricObject{
    private double pointX2, pointY2;

    public StrokeLine(double pointX1,double pointY1,double pointX2,double pointY2, String id ){
        super(pointX1,pointY1,id);
        this.pointX2 = pointX2;
        this.pointY2 = 500 - pointY2;
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

}

class Circle extends GeometricObject{

    private double radius;

    public Circle(double pointX1, double pointY1, double radius, String id){
        super(pointX1,pointY1,id);
        this.radius = radius;
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
}