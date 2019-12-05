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
    public static HashMap<String, StrokeLine> listOfDrawsLines = new HashMap<>();
    public static HashMap<String, Circle> listOfDrawsCircles =new HashMap<>();
    //private static GeometricObject item = new GeometricObject();

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
                gcDrawCircles(gc, commandLine[4]);
                break;
            case "line":
                if ( commandLine.length != 6) return "Wrong command";
                check = createLine(commandLine);
                gcDrawLines(gc, commandLine[5]);
                if (check != null) return check;
                break;
            case "del":
                check = delDraw(commandLine[1], gc);
                if (check != null) return check; // draw error
                break;
            case "clear":
                delAll();
                drawShapes(gc);
                break;
            case "color":
                if ( commandLine.length != 3) return "Wrong command";
                check = changeDrawColor(commandLine[1], commandLine[2], gc);
                if ( check != null) return check;
                break;
            case "dash":
                if ( commandLine.length != 3) return "Wrong command";
                check = changeDrawDash(commandLine[2], Double.parseDouble(commandLine[1]), gc);
                if ( check != null) return check;
                break;
            default:
                drawShapes(gc);
                return "Error";
        }
        //drawShapes(gc);
        setListOfDraws(listOfDraws);
        return command;
    }

    public static void gcDrawLines(GraphicsContext gc, String id){
        gc.setStroke(listOfDrawsLines.get(id).getColor());
        gc.setLineDashes(listOfDrawsLines.get(id).getDashes());
        gc.strokeLine(listOfDrawsLines.get(id).getPointX1(), listOfDrawsLines.get(id).getPointY1(),
                listOfDrawsLines.get(id).getPointX2(), listOfDrawsLines.get(id).getPointY2());

    }

    public static void gcDrawCircles(GraphicsContext gc, String id){
        gc.setStroke(listOfDrawsCircles.get(id).getColor());
        gc.setLineDashes(listOfDrawsCircles.get(id).getDashes());
        gc.strokeOval(listOfDrawsCircles.get(id).getPointX1(), listOfDrawsCircles.get(id).getPointY1(),
                listOfDrawsCircles.get(id).getRadius(), listOfDrawsCircles.get(id).getRadius() );
    }

    public static void drawShapes(GraphicsContext gc) {
        gc.clearRect(0,0,800,500);
        //gc.setFill(Color.BLACK);
        //gc.setLineCap(StrokeLineCap.BUTT);
        //gc.setLineDashOffset(10);
        gc.setLineDashes(8);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(0,0,800,500);
        gc.strokeOval(50,50 , 50,50);
        //gc.strokeLine(0, 0, 100, 100);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);

    }

    private String createLine(String[] command){

        if ( listOfDrawsLines.containsKey(command[5]) ) return "Error, line already exist";

        try{
            for(int i = 1; i < 5; i++){
                Double.parseDouble(command[i]);
            }
        }catch (NumberFormatException e) {
                return "Error in cordinate";
            }

        listOfDrawsLines.put(command[5], new StrokeLine (
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

        listOfDrawsCircles.put(command[4], new Circle(
                Double.parseDouble(command[1]) - Double.parseDouble(command[3])/2,
                Double.parseDouble(command[2]) - Double.parseDouble(command[3])/2,
                Double.parseDouble(command[3]),
                command[4]));

        return null;
    }

    public static String showParam(Object box){
        String result = "";

        if ( listOfDrawsLines.containsKey((String) box)) {
            result = "Object: line;  Name = " + listOfDrawsLines.get((String) box).getId()
                    + ";  X1 = " + listOfDrawsLines.get((String) box).getPointX1()
                    + ";  Y1 = " + listOfDrawsLines.get((String) box).getPointY1()
                    + ";  X2 = " + listOfDrawsLines.get((String) box).getPointX2()
                    + ";  Y2 = " + listOfDrawsLines.get((String) box).getPointY2();
        }

        if ( listOfDrawsCircles.containsKey((String) box)){
            result = "Object: line;  Name = " + listOfDrawsCircles.get((String) box).getId()
                    + ";  X = " + listOfDrawsCircles.get((String) box).getPointX1()
                    + ";  Y = " + listOfDrawsCircles.get((String) box).getPointY1()
                    + ";  radius = " + listOfDrawsCircles.get((String) box).getRadius();
        }

        return result;
    }

    private String delDraw(String name,GraphicsContext gc){

        listOfDrawsLines.remove(name);
        listOfDrawsCircles.remove(name);
        drawShapes(gc);
        for (Map.Entry< String, Circle> entry : listOfDrawsCircles.entrySet()){
            gcDrawLines(gc, entry.getKey());
        }

        for (Map.Entry< String, StrokeLine> entry : listOfDrawsLines.entrySet()){
            gcDrawLines(gc, entry.getKey());
        }

        return null;
    }

    private static void update(GraphicsContext gc, String id){
        drawShapes(gc);
        for (Map.Entry< String, Circle> entry : listOfDrawsCircles.entrySet()){
            if (entry.getKey().equals("id")) continue;
            gcDrawCircles(gc, entry.getKey());
        }

        for (Map.Entry< String, StrokeLine> entry : listOfDrawsLines.entrySet()){
            if (entry.getKey().equals("id")) continue;
            gcDrawLines(gc, entry.getKey());
        }
    }

    private void delAll (){
        listOfDrawsLines.clear();
        listOfDrawsCircles.clear();
    }

    private String changeDrawDash(String id, double dash, GraphicsContext gc){
        if (listOfDrawsLines.containsKey(id)) {
            listOfDrawsLines.get(id).setDashes(dash);
            update(gc,id);
            gcDrawLines(gc,id);
            return null;
        }
        if (listOfDrawsCircles.containsKey(id)) {
            listOfDrawsCircles.get(id).setDashes(dash);
            update(gc,id);
            gcDrawCircles(gc, id);
            return null;
        }
        return "Wrong name";
    }

    private String changeDrawColor(String color, String id, GraphicsContext gc) {


        if (listOfDrawsLines.containsKey(id)) {
            switch (color) {
                case "black":
                    listOfDrawsLines.get(id).setColor(Color.BLACK);
                    break;
                case "blue":
                    listOfDrawsLines.get(id).setColor(Color.BLUE);
                    break;
                case "yellow":
                    listOfDrawsLines.get(id).setColor(Color.YELLOW);
                    break;
                case "red":
                    listOfDrawsLines.get(id).setColor(Color.RED);
                    break;
                case "green":
                    listOfDrawsLines.get(id).setColor(Color.GREEN);
                    break;
                default:
                    return "Wrong color";
            }
            gcDrawLines(gc,id);
            return null;
        }
        if (listOfDrawsCircles.containsKey(id)) {
            switch (color) {
                case "black":
                    listOfDrawsCircles.get(id).setColor(Color.BLACK);
                    break;
                case "blue":
                    listOfDrawsCircles.get(id).setColor(Color.BLUE);
                    break;
                case "yellow":
                    listOfDrawsCircles.get(id).setColor(Color.YELLOW);
                    break;
                case "red":
                    listOfDrawsCircles.get(id).setColor(Color.RED);
                    break;
                case "green":
                    listOfDrawsCircles.get(id).setColor(Color.GREEN);
                    break;
                default:
                    return "Wrong color";
            }
            gcDrawCircles(gc, id);
        } else return "Wrong name";

        return null;
    }

    }

