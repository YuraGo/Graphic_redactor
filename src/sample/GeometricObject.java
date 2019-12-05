package sample;

import java.util.HashMap;
import java.util.Vector;

public class GeometricObject {

    private static HashMap<String, GeometricObject> allDraws = new HashMap<>();

    public static void addDraws(String name, GeometricObject draw){
        if ( allDraws.containsKey(name)) return;

        allDraws.put(name, draw);
    }

    public static void delDraw(String name){
        if ( !allDraws.containsKey(name)) return;

        allDraws.remove(name);
    }

    public static class StrokeLine {
        private String id;
        private double pointX1, pointX2, pointY1, pointY2;
        private Vector<Double> cordXY;

        public StrokeLine(double pointX1,double pointX2,double pointY1,double pointY2, String id ){
            this.pointX1 = pointX1;
            this.pointX2 = pointX2;
            this.pointY1 = pointY1;
            this.pointY2 = pointY2;
            this.id = id;
            //updateVector();
        }

        public StrokeLine(Vector<Double> cordXY, String id){
            this.cordXY = cordXY;
            this.id = id;
            updatePoint();
        }

        private void updateVector(){
            this.cordXY.clear();
            this.cordXY.add(0,this.pointX1);
            this.cordXY.add(1,this.pointY1);
            this.cordXY.add(2,this.pointX2);
            this.cordXY.add(3,this.pointY2);
        }

        private void updatePoint(){
            this.pointX1 = this.cordXY.get(0);
            this.pointY1 = this.cordXY.get(1);
            this.pointX2 = this.cordXY.get(2);
            this.pointY2 = this.cordXY.get(3);
        }

        public Vector getCord(){
            return this.cordXY;
        }

        public String getId(){
            return this.id;
        }

        public double getPointX1(){
            return this.pointX1;
        }

        public double getPointX2(){
            return this.pointX2;
        }

        public double getPointY1(){
            return this.pointY1;
        }

        public double getPointY2(){
            return this.pointY2;
        }

        public void setPoints(double x1, double y1, double x2, double y2, String id){
            this.pointX1 = x1;
            this.pointX2 = x2;
            this.pointY1 = y1;
            this.pointY2 = y2;
            this.id = id;
        }
    }

    public static class Circle {
        private String id;
        private double pointX1, pointY1;
        private Vector<Double> center;
        private double radius;

        private void updateVector(){
            this.center.clear();
            this.center.add(0,this.pointX1);
            this.center.add(1,this.pointY1);
        }

        private void updatePoint(){
            this.pointX1 = this.center.get(0);
            this.pointY1 = this.center.get(1);
        }

        public Circle(Vector<Double> center, String id, double radius){
            this.center = center;
            this.id = id;
            this.radius = radius;
            updatePoint();
        }

        public Circle(double pointX1, double pointY1, double radius, String id){
            this.pointX1 = pointX1;
            this.pointY1 = pointY1;
            this.radius = radius;
            this.id = id;
        }

        public Vector<Double> getCord(){
            return this.center;
        }

        public double getPointX1(){
            return this.pointX1;
        }

        public double getPointY1(){
            return this.pointY1;
        }

        public String getId(){
            return this.id;
        }

        public double getRadius(){
            return this.radius;
        }
    }

}
