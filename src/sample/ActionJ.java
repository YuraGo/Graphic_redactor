package sample;

import javafx.beans.InvalidationListener;
import javafx.collections.*;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.StrokeLineCap;
import sample.GeometricObject.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class ActionJ {
    public static HashMap<String, GeometricObject.StrokeLine> listOfDrawsLines = new HashMap<>();
    public static HashMap<String, GeometricObject.Circle> listOfDrawsCircles =new HashMap<>();
    private static GeometricObject item = new GeometricObject();

    public static HashMap<String,String> test = new HashMap<>();
    public static boolean check = false;
//    public <Hbox> CreateAll(<Hbox> hbox) {
//
//        Button button1 = new Button("Add");
//        Button button2 = new Button("Remove");
//        HBox.setHgrow(button1, Priority.ALWAYS);
//        HBox.setHgrow(button2, Priority.ALWAYS);
//        button1.setMaxWidth(Double.MAX_VALUE);
//        button2.setMaxWidth(Double.MAX_VALUE);
//        hbox.getChildren().addAll(button1, button2);
//    }

    public static void setListOfDraws(ComboBox listOfDraws){

        listOfDraws.getItems().clear();

        for (Map.Entry<String,  StrokeLine> entry : listOfDrawsLines.entrySet()) {
            listOfDraws.getItems().add(entry.getKey());
        }
        for (Map.Entry<String,  Circle> entry : listOfDrawsCircles.entrySet()) {
            listOfDraws.getItems().add(entry.getKey());
        }
    }

    public String runCommand(GraphicsContext gc, ComboBox listOfDraws,String command){
        String check;
        String[] commandLine = command.split(" ");
        setListOfDraws(listOfDraws);

//        for(String word : commandLine){
//            System.out.println("com " +  word);
//        }
        switch (commandLine[0]) {
            case "circle":
                if ( commandLine.length != 5) return "Wrong command";
                check = createCircle(commandLine);
                if (check != null) return check;
                break;
            case "line":
                if ( commandLine.length != 6) return "Wrong command";
                check = createLine(commandLine);
                //drawShapes(gc);
                if (check != null) return check;
                break;
            case "del":
                check = delDraw(commandLine[1]);
                if (check != null) return check;
                break;
            case "clear":
                delAll();
                break;
            case "chg":
                check = changeDraw(commandLine);
                if ( check != null) return check;
            default:
                drawShapes(gc);
                return "Error";
        }
        drawShapes(gc);
        setListOfDraws(listOfDraws);
        return command;
    }

    public static void drawShapes(GraphicsContext gc) {
        gc.clearRect(0,0,800,500);
        gc.setFill(Color.BLACK);
        //gc.setLineCap(StrokeLineCap.BUTT);
        //gc.setLineDashOffset(10);
        //gc.setLineDashes(8);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(0,0,800,500);
        gc.strokeLine(0, 0, 100, 100);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);


        for (Map.Entry<String,  GeometricObject.StrokeLine> entry : listOfDrawsLines.entrySet()) {
            //System.out.println("value " + entry.getValue().getPointX1() +" "+ entry.getValue().getPointX2());
            gc.strokeLine(entry.getValue().getPointX1(), entry.getValue().getPointX2(), entry.getValue().getPointY1()
            , entry.getValue().getPointY2());
        }

        for (Map.Entry< String, GeometricObject.Circle> entry : listOfDrawsCircles.entrySet()){
            gc.strokeOval(entry.getValue().getPointX1(), entry.getValue().getPointY1(), entry.getValue().getRadius(),
                    entry.getValue().getRadius());
        }

    }

    private String createLine(String[] command){

        if ( listOfDrawsLines.containsKey(command[5]) ) return "Error";

        try{
            for(int i = 1; i < 5; i++){
                Double.parseDouble(command[i]);
            }
        }catch (NumberFormatException e) {
                return "Error in cordinate";
            }

        listOfDrawsLines.put(command[5], new GeometricObject.StrokeLine(
                Double.parseDouble(command[1]),
                Double.parseDouble(command[2]),
                Double.parseDouble(command[3]),
                Double.parseDouble(command[4]),
                command[5]));


        return null;
    }

    public String createCircle(String[] command){

        if ( listOfDrawsCircles.containsKey(command[4]) ) return "Error";

        try{
            for(int i = 1; i < 4; i++){
                Double.parseDouble(command[i]);
            }
        }catch (NumberFormatException e) {
            return "Error at input value";
        }

        listOfDrawsCircles.put(command[4], new GeometricObject.Circle(
                Double.parseDouble(command[1]) - Double.parseDouble(command[3])/2,
                Double.parseDouble(command[2]) - Double.parseDouble(command[3])/2,
                Double.parseDouble(command[3]),
                command[4]));

        return null;
    }

    public static String showParam(Object box){
        String result = "";

        if ( listOfDrawsLines.containsKey(box)) {
            result = "Object: line;  Name = " + listOfDrawsLines.get(box).getId()
                    + ";  X1 = " + listOfDrawsLines.get(box).getPointX1()
                    + ";  Y1 = " + listOfDrawsLines.get(box).getPointY1()
                    + ";  X2 = " + listOfDrawsLines.get(box).getPointX2()
                    + ";  Y2 = " + listOfDrawsLines.get(box).getPointY2();
        }

        if ( listOfDrawsCircles.containsKey(box)){
            result = "Object: line;  Name = " + listOfDrawsCircles.get(box).getId()
                    + ";  X = " + listOfDrawsCircles.get(box).getPointX1()
                    + ";  Y = " + listOfDrawsCircles.get(box).getPointY1()
                    + ";  radius = " + listOfDrawsCircles.get(box).getRadius();
        }

        return result;
    }

    private String delDraw(String name){

        listOfDrawsLines.remove(name);
        listOfDrawsCircles.remove(name);

        return null;
    }

    private void delAll (){
        listOfDrawsLines.clear();
        listOfDrawsCircles.clear();
    }

    private String changeDraw(String[] command){
        String ret = null;
        switch (command[1]){
            case "line":
                if ( command.length != 7) return "Wrong number of arguments";
                ret = changeLine(command);
                if ( ret != null) return ret;
                break;
            case "circle":

        }

        return ret;
    }

    private String changeLine(String[] com){
        if ( listOfDrawsLines.containsKey(com[6])){
            listOfDrawsLines.get(com[6]).setPoints( Double.parseDouble(com[2]),
                    Double.parseDouble(com[3]), Double.parseDouble(com[4]),Double.parseDouble(com[5]), com[6]);
        } else return "Wrong name";
        return null;
    }
    private String changeCircle(String[] com){
        return null;
    }
}
