package sample;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import sample.ActionJ.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        HBox hbox = new HBox();
        //Action.CreateAll();
        ActionJ action = new ActionJ();

        ComboBox listOfDraws = new ComboBox<String>();

        Label commandWritted = new Label();
        TextField commandLine = new TextField();
        commandLine.setMaxWidth(Double.MAX_VALUE);
        commandLine.setPromptText("command...");
        Button confirmCommand = new Button("Confirm");
        Button viewParam = new Button("View");

        Canvas canvas = new Canvas(800,500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        confirmCommand.setOnAction(event -> commandWritted.setText("Input: " +  action.runCommand(gc,listOfDraws,commandLine.getText())));
        viewParam.setOnAction(event -> commandWritted.setText(ActionJ.showParam(listOfDraws.getValue())));



        hbox.getChildren().addAll(commandLine, confirmCommand, listOfDraws,viewParam);
        FlowPane src = new FlowPane(Orientation.VERTICAL, 10, 10, hbox, commandWritted);
        src.getChildren().addAll(canvas);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(src,800,600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
