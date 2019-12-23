package sample;

import javafx.beans.InvalidationListener;
import javafx.collections.*;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import sample.GeometricObject.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

class ActionJ {
    private static HashMap<String, StrokeLine> listOfDrawsLines = new HashMap<>();
    private static HashMap<String, Circle> listOfDrawsCircles = new HashMap<>();
    private static HashMap<String, BezierLine> listOfDrawsBezier = new HashMap<>();
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

    static void setListOfDraws(ComboBox listOfDraws) {

        listOfDraws.getItems().clear();

        for (Map.Entry<String, StrokeLine> entry : listOfDrawsLines.entrySet()) {
            listOfDraws.getItems().add(entry.getKey() + " line");
        }

        for (Map.Entry<String, Circle> entry : listOfDrawsCircles.entrySet()) {
            listOfDraws.getItems().add(entry.getKey() + " circle");
        }

        for (Map.Entry<String, BezierLine> entry : listOfDrawsBezier.entrySet()) {
            listOfDraws.getItems().add(entry.getKey() + " Bezier curve");
        }
    }

    String runCommand(GraphicsContext gc, ComboBox listOfDraws, String command) {
        String check;
        String[] commandLine = command.split(" ");
        setListOfDraws(listOfDraws);

//        for(String word : commandLine){
//            System.out.println("com " +  word);
//        }
        switch (commandLine[0]) {
            case "circle":
                if (commandLine.length != 5) return "Wrong command circle";
                check = createCircle(commandLine);
                if (check != null) return check;
                gcDrawCircles(gc, commandLine[4]);
                break;
            case "line":
                if (commandLine.length != 6) return "Wrong command line";
                check = createLine(commandLine);
                gcDrawLines(gc, commandLine[5]);
                if (check != null) return check;
                break;
            case "bez":
                if (commandLine.length != 8) return "Wrong command bez";
                check = createBezier(commandLine);
                gcDrawBezier(gc, commandLine[7]);
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
                if (commandLine.length != 3) return "Wrong command color";
                check = changeDrawColor(commandLine[1], commandLine[2], gc);
                if (check != null) return check;
                break;
            case "dash":
                if (commandLine.length != 3) return "Wrong command dash";
                check = changeDrawDash(commandLine[2], Double.parseDouble(commandLine[1]), gc);
                if (check != null) return check;
                break;
            case "rotation":
                if (commandLine.length != 3) return "Wrong command rotation";
                check = rotationDraw(commandLine[2], Double.parseDouble(commandLine[1]), gc);
                if (check != null) return check;
                break;
            case "near":
                if (commandLine.length != 4) return "Wrong command near";
                check = nearPoint(Double.parseDouble(commandLine[1]), Double.parseDouble(commandLine[2]), commandLine[3], gc);
                if (check != null) return check;
                break;
            case "proj":
                if (commandLine.length != 2) return "Wrong command projection";
                check = projDraw(commandLine[1], gc);
                if (check == null) return check;
                break;
            case "copy":
                if (commandLine.length != 2) return "Wrong command projection";
                check = copyOf(commandLine[1], gc);
                if (check != null) return check;
                break;
            case "clip":
                if ( commandLine.length != 7) return "Wrong command projection";
                check = clipDraw(commandLine, gc);
                if ( check != null) return check;
                break;
            case "cut":
                if ( commandLine.length != 6) return "Wrong command projection";
                check = cutDraw(commandLine, gc);
                if ( check != null) return check;
                break;
            case "save":
                break;
            case "load":
                break;
            default:
                drawShapes(gc);
                return "Error";
        }
        //drawShapes(gc);
        setListOfDraws(listOfDraws);
        return command;
    }

    private static void gcDrawLines(GraphicsContext gc, String id) {
        gc.setStroke(listOfDrawsLines.get(id).getColor());
        gc.setLineDashes(listOfDrawsLines.get(id).getDashes());
        gc.strokeLine(listOfDrawsLines.get(id).getPointX1(), listOfDrawsLines.get(id).getPointY1(),
                listOfDrawsLines.get(id).getPointX2(), listOfDrawsLines.get(id).getPointY2());

    }

    private static void gcDrawCircles(GraphicsContext gc, String id) {
        gc.setStroke(listOfDrawsCircles.get(id).getColor());
        gc.setLineDashes(listOfDrawsCircles.get(id).getDashes());
        //gc.strokeOval(listOfDrawsCircles.get(id).getPointX1() - listOfDrawsCircles.get(id).getRadius(),
          //      listOfDrawsCircles.get(id).getPointY1() - listOfDrawsCircles.get(id).getRadius(),
            //    listOfDrawsCircles.get(id).getRadius() * 2, listOfDrawsCircles.get(id).getRadius() * 2);
       // circle 200 200 50 a
        gc.strokeArc(listOfDrawsCircles.get(id).getPointX1() - listOfDrawsCircles.get(id).getRadius(),
                listOfDrawsCircles.get(id).getPointY1() - listOfDrawsCircles.get(id).getRadius(),
                listOfDrawsCircles.get(id).getRadius() * 2,
                listOfDrawsCircles.get(id).getRadius() * 2,
                listOfDrawsCircles.get(id).getStartAngle(),
                listOfDrawsCircles.get(id).getArcExtent(),
                ArcType.OPEN);
        //gc.strokeArc(100-25, 500-100-25, 50, 50, 0, 360, ArcType.OPEN);
    }

    private static void gcDrawBezier(GraphicsContext gc, String id) {
        gc.beginPath();
        gc.setStroke(listOfDrawsBezier.get(id).getColor());
        gc.setLineDashes(listOfDrawsBezier.get(id).getDashes());//110 102 130 80 130 62.5
        gc.bezierCurveTo(listOfDrawsBezier.get(id).getPointX1(), listOfDrawsBezier.get(id).getPointY1(),
                listOfDrawsBezier.get(id).getPointX2(), listOfDrawsBezier.get(id).getPointY2(),
                listOfDrawsBezier.get(id).getPointX3(), listOfDrawsBezier.get(id).getPointY3());
        gc.stroke();
    }

    private static void drawShapes(GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 500);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,800,500);
        gc.strokeRect(0, 0, 800, 500);

        gc.strokeArc(100-25,400-25, 50,50, 225, 180, ArcType.OPEN);

        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);

    }

    private String createLine(String[] command) {

        if (listOfDrawsLines.containsKey(command[5])) return "Error, line already exist";

        try {
            for (int i = 1; i < 5; i++) {
                Double.parseDouble(command[i]);
            }
        } catch (NumberFormatException e) {
            return "Error in cordinate";
        }

        listOfDrawsLines.put(command[5], new StrokeLine(
                Double.parseDouble(command[1]),
                Double.parseDouble(command[2]),
                Double.parseDouble(command[3]),
                Double.parseDouble(command[4]),
                command[5]));
        return null;
    }

    private String createCircle(String[] command) {

        if (listOfDrawsCircles.containsKey(command[4])) return "Error";

        try {
            for (int i = 1; i < 4; i++) {
                Double.parseDouble(command[i]);
            }
        } catch (NumberFormatException e) {
            return "Error at input value";
        }

        listOfDrawsCircles.put(command[4], new Circle(
                Double.parseDouble(command[1]),
                Double.parseDouble(command[2]),
                Double.parseDouble(command[3]),
                command[4]));

        return null;
    }

    private String createBezier(String[] command) {
        if (listOfDrawsBezier.containsKey(command[7])) return "Error, Bezier curve already exist";

        try {
            for (int i = 1; i < 7; i++) {
                Double.parseDouble(command[i]);
            }
        } catch (NumberFormatException e) {
            return "Error in cordinate";
        }

        listOfDrawsBezier.put(command[7], new BezierLine(
                Double.parseDouble(command[1]),
                Double.parseDouble(command[2]),
                Double.parseDouble(command[3]),
                Double.parseDouble(command[4]),
                Double.parseDouble(command[5]),
                Double.parseDouble(command[6]),
                command[7]));
        return null;
    }

    static String showParam(Object box) {
        String result = "";
        String str = box.toString();
        String[] id = str.split(" ");
        if (listOfDrawsLines.containsKey(id[0])) {
            result = "Object: line;  Name = " + listOfDrawsLines.get(id[0]).getId()
                    + ";  X1 = " + listOfDrawsLines.get(id[0]).getPointX1()
                    + ";  Y1 = " + (500 - listOfDrawsLines.get(id[0]).getPointY1())
                    + ";  X2 = " + listOfDrawsLines.get(id[0]).getPointX2()
                    + ";  Y2 = " + (500 - listOfDrawsLines.get(id[0]).getPointY2());
        }

        if (listOfDrawsCircles.containsKey(id[0])) {
            result = "Object: line;  Name = " + listOfDrawsCircles.get(id[0]).getId()
                    + ";  X = " + listOfDrawsCircles.get(id[0]).getPointX1() + listOfDrawsCircles.get(id[0]).getRadius() / 2
                    + ";  Y = " + (500 - listOfDrawsCircles.get(id[0]).getPointY1() - listOfDrawsCircles.get(id[0]).getRadius() / 2)
                    + ";  radius = " + listOfDrawsCircles.get(id[0]).getRadius();
        }

        if (listOfDrawsBezier.containsKey(id[0])) {
            result = "Object: line;  Name = " + listOfDrawsBezier.get(id[0]).getId()
                    + ";  X1 = " + listOfDrawsBezier.get(id[0]).getPointX1()
                    + ";  Y1 = " + (500 - listOfDrawsBezier.get(id[0]).getPointY1())
                    + ";  X2 = " + listOfDrawsBezier.get(id[0]).getPointX2()
                    + ";  Y2 = " + (500 - listOfDrawsBezier.get(id[0]).getPointY2())
                    + ";  X3 = " + listOfDrawsBezier.get(id[0]).getPointX3()
                    + ";  Y3 = " + (500 - listOfDrawsBezier.get(id[0]).getPointY3());
        }

        return result;
    }

    private String delDraw(String name, GraphicsContext gc) {

        listOfDrawsLines.remove(name);
        listOfDrawsCircles.remove(name);
        listOfDrawsBezier.remove(name);
        drawShapes(gc);
        for (Map.Entry<String, Circle> entry : listOfDrawsCircles.entrySet()) {
            gcDrawLines(gc, entry.getKey());
        }

        for (Map.Entry<String, StrokeLine> entry : listOfDrawsLines.entrySet()) {
            gcDrawLines(gc, entry.getKey());
        }
        for (Map.Entry<String, BezierLine> entry : listOfDrawsBezier.entrySet()) {
            gcDrawBezier(gc, entry.getKey());
        }

        return null;
    }

    private static void update(GraphicsContext gc, String id) {
        drawShapes(gc);
        for (Map.Entry<String, Circle> entry : listOfDrawsCircles.entrySet()) {
            if (entry.getKey().equals(id)) continue;
            gcDrawCircles(gc, entry.getKey());
        }

        for (Map.Entry<String, StrokeLine> entry : listOfDrawsLines.entrySet()) {
            if (entry.getKey().equals(id)) continue;
            gcDrawLines(gc, entry.getKey());
        }
        for (Map.Entry<String, BezierLine> entry : listOfDrawsBezier.entrySet()) {
            if (entry.getKey().equals(id)) continue;
            gcDrawBezier(gc, entry.getKey());
        }
    }

    private void delAll() {
        listOfDrawsLines.clear();
        listOfDrawsCircles.clear();
        listOfDrawsBezier.clear();
    }

    private String changeDrawDash(String id, double dash, GraphicsContext gc) {
        if (listOfDrawsLines.containsKey(id)) {
            listOfDrawsLines.get(id).setDashes(dash);
            update(gc, id);
            gcDrawLines(gc, id);
            return null;
        }
        if (listOfDrawsCircles.containsKey(id)) {
            listOfDrawsCircles.get(id).setDashes(dash);
            update(gc, id);
            gcDrawCircles(gc, id);
            return null;
        }
        if (listOfDrawsBezier.containsKey(id)) {
            listOfDrawsBezier.get(id).setDashes(dash);
            update(gc, id);
            gcDrawBezier(gc, id);
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
            gcDrawLines(gc, id);
            return null;
        }

        if (listOfDrawsBezier.containsKey(id)) {
            switch (color) {
                case "black":
                    listOfDrawsBezier.get(id).setColor(Color.BLACK);
                    break;
                case "blue":
                    listOfDrawsBezier.get(id).setColor(Color.BLUE);
                    break;
                case "yellow":
                    listOfDrawsBezier.get(id).setColor(Color.YELLOW);
                    break;
                case "red":
                    listOfDrawsBezier.get(id).setColor(Color.RED);
                    break;
                case "green":
                    listOfDrawsBezier.get(id).setColor(Color.GREEN);
                    break;
                default:
                    return "Wrong color";
            }
            gcDrawBezier(gc, id);
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

    private String rotationDraw(String id, double angle, GraphicsContext gc) {
        if (listOfDrawsLines.containsKey(id)) {
            angle = angle * Math.PI / 180;
            double x1, y1, x2, y2;
            double centerX = (Math.abs(listOfDrawsLines.get(id).getPointX2() + listOfDrawsLines.get(id).getPointX1())) / 2;
            double centerY = (Math.abs(listOfDrawsLines.get(id).getPointY2() + listOfDrawsLines.get(id).getPointY1())) / 2;
            x1 = listOfDrawsLines.get(id).getPointX1() - centerX;
            y1 = listOfDrawsLines.get(id).getPointY1() - centerY;
            x2 = listOfDrawsLines.get(id).getPointX2() - centerX;
            y2 = listOfDrawsLines.get(id).getPointY2() - centerY;

            listOfDrawsLines.get(id).setPoints(x1 * Math.cos(angle) - y1 * Math.sin(angle) + centerX
                    , x1 * Math.sin(angle) + y1 * Math.cos(angle) + centerY);

            listOfDrawsLines.get(id).setPointL(x2 * Math.cos(angle) - y2 * Math.sin(angle) + centerX
                    , x2 * Math.sin(angle) + y2 * Math.cos(angle) + centerY);

            update(gc, id);
            gcDrawLines(gc, id);
            return null;
        }

        if (listOfDrawsBezier.containsKey(id)) {
            angle = angle * Math.PI / 180;
            double x1, y1, x2, y2;
            double centerX = listOfDrawsBezier.get(id).getPointX1();
            double centerY = listOfDrawsBezier.get(id).getPointY1();
            x1 = listOfDrawsBezier.get(id).getPointX2() - centerX;
            y1 = listOfDrawsBezier.get(id).getPointY2() - centerY;
            x2 = listOfDrawsBezier.get(id).getPointX3() - centerX;
            y2 = listOfDrawsBezier.get(id).getPointY3() - centerY;

            listOfDrawsBezier.get(id).setPointB2(x1 * Math.cos(angle) - y1 * Math.sin(angle) + centerX
                    , x1 * Math.sin(angle) + y1 * Math.cos(angle) + centerY);

            listOfDrawsBezier.get(id).setPointB3(x2 * Math.cos(angle) - y2 * Math.sin(angle) + centerX
                    , x2 * Math.sin(angle) + y2 * Math.cos(angle) + centerY);

            update(gc, id);
            gcDrawBezier(gc, id);
            return null;
        }

        return "Wrong name";
    }

    private String nearPoint(double x1, double y1, String id, GraphicsContext gc) {
        String points;
        if (listOfDrawsLines.containsKey(id)) {
            points = listOfDrawsLines.get(id).getFunc(x1, y1);
            return points;
        }
        if (listOfDrawsCircles.containsKey(id)) {
            points = listOfDrawsCircles.get(id).getFunc(x1, y1);
            return points;
        }
        if (listOfDrawsBezier.containsKey(id)) {
            points = listOfDrawsBezier.get(id).getFunc(x1, y1);
            return points;
        }
        return null;
    }

    private String projDraw(String id, GraphicsContext gc) {
        if (listOfDrawsLines.containsKey(id)) {
            gc.setLineWidth(4);
            gc.setStroke(Color.GREEN);
            gc.strokeLine(listOfDrawsLines.get(id).getPointX1(), 500,
                    listOfDrawsLines.get(id).getPointX2(), 500);
            return null;
        }
        if (listOfDrawsCircles.containsKey(id)){
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(4);
            gc.strokeLine(listOfDrawsCircles.get(id).getPointX1()+listOfDrawsCircles.get(id).getRadius(), 500,
                    listOfDrawsCircles.get(id).getPointX1()-listOfDrawsCircles.get(id).getRadius(), 500   );
            return null;
        }
        return "Error name";
    }

    private String copyOf(String id, GraphicsContext gc){
        if ( listOfDrawsLines.containsKey(id)){
            listOfDrawsLines.get(id).incCopyCounter();
            listOfDrawsLines.put( listOfDrawsLines.get(id).getId()+listOfDrawsLines.get(id).getCopyCounter(), new StrokeLine(listOfDrawsLines.get(id)) );
            //listOfDrawsLines.get(id+listOfDrawsLines.get(id).getCopyCounter()).setId(id+listOfDrawsLines.get(id).getCopyCounter());
            gcDrawLines(gc, id+listOfDrawsLines.get(id).getCopyCounter());
            return null;
        }
        if (listOfDrawsCircles.containsKey(id)){
            listOfDrawsCircles.get(id).incCopyCounter();
            listOfDrawsCircles.put(listOfDrawsCircles.get(id).getId()+listOfDrawsCircles.get(id).getCopyCounter(), new Circle(listOfDrawsCircles.get(id)));
            gcDrawCircles(gc , id + listOfDrawsCircles.get(id).getCopyCounter());
            return null;
        }
        if( listOfDrawsBezier.containsKey(id)){
            listOfDrawsBezier.get(id).incCopyCounter();
            listOfDrawsBezier.put(listOfDrawsBezier.get(id).getId()+listOfDrawsBezier.get(id).getCopyCounter(), new BezierLine(listOfDrawsBezier.get(id)));
            gcDrawBezier(gc, id + listOfDrawsBezier.get(id).getCopyCounter());
            return null;
        }
        return "Id does not exist";
    }

    private String clipDraw( String[] com, GraphicsContext gc){
        gc.setFill(Color.WHITE);
        try {
            gc.fillPolygon(new double[]{ Double.parseDouble(com[1]), Double.parseDouble(com[3]),
            Double.parseDouble(com[5])}, new double[]{500-Double.parseDouble(com[2]),
                    500-Double.parseDouble(com[4]), 500-Double.parseDouble(com[6])}, 3);

        } catch (NumberFormatException e) {
            return "Error in cordinate";
        }
        return null;
    }

    private String cutDraw(String[] com, GraphicsContext gc){ // line 20 100 100 100 a   cut 50 0 65 200 a  cut 50 0 50 200 a
        String errorCheck;
        if (listOfDrawsLines.containsKey(com[5])){
            errorCheck = copyOf(com[5] , gc);
            if ( errorCheck == null){
                listOfDrawsLines.get(com[5]).setCut(Double.parseDouble(com[1]), Double.parseDouble(com[2]),
                        Double.parseDouble(com[3]), Double.parseDouble(com[4]), 1);
                listOfDrawsLines.get(com[5]+listOfDrawsLines.get(com[5]).getCopyCounter()).setCut(Double.parseDouble(com[1]), Double.parseDouble(com[2]),
                        Double.parseDouble(com[3]), Double.parseDouble(com[4]), 2);
                gcDrawLines(gc, com[5]+listOfDrawsLines.get(com[5]).getCopyCounter());
                //update(gc, com[5]);
                gcDrawLines(gc, listOfDrawsLines.get(com[5]).getId());
            }
        }

        if (listOfDrawsCircles.containsKey(com[5])){
            errorCheck = copyOf(com[5] , gc);
            if ( errorCheck == null){
                listOfDrawsCircles.get(com[5]).setCut(Double.parseDouble(com[1]), Double.parseDouble(com[2]),
                        Double.parseDouble(com[3]), Double.parseDouble(com[4]), 1);
                listOfDrawsCircles.get(com[5]+listOfDrawsCircles.get(com[5]).getCopyCounter()).setCut(Double.parseDouble(com[1]), Double.parseDouble(com[2]),
                        Double.parseDouble(com[3]), Double.parseDouble(com[4]), 2);
                gcDrawCircles(gc, com[5]+listOfDrawsCircles.get(com[5]).getCopyCounter());
                //update(gc, com[5]);
                gcDrawCircles(gc, listOfDrawsCircles.get(com[5]).getId());

            }
        }

        return null;
    }
}