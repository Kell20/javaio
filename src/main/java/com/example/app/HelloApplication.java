package com.example.app;
import java.io.*;
import java.net.URI;
import java.net.http.*;
import java.nio.file.Path;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.Duration;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        TextArea text=new TextArea();
        TextField tex=new TextField();
        tex.setMaxWidth(900);
        text.setMaxWidth(1000);
        Button but=new Button();
        but.setText("search");

        Text xt=new Text();
        xt.setText("NETWORLD BROWSER");
        xt.setFont(Font.font("",FontWeight.BOLD,26));
        Text te=new Text();
        te.setText("search url example:www.google.com");
        te.setFont(Font.font("",FontWeight.BOLD,10));

        tex.setAccessibleText("url");
        tex.setStyle("-fx-placeholder:search google or type url");

        Screen screen=Screen.getPrimary();
        Rectangle2D rect=screen.getVisualBounds();

        VBox box=new VBox(50);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color:lightblue");

        tex.setMaxWidth(rect.getWidth()-tex.getMaxWidth());
        text.setMaxWidth(rect.getWidth()-text.getMaxWidth());
        box.getChildren().addAll(xt,te,tex,text,but);


        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String url=tex.getText();
                text.setText("url http://"+url);

                Httpclient http=new Httpclient("http://"+url,text);
                http.client();
                http.request();
                http.response();
            }
        });

        StackPane root=new StackPane();
        HBox b=new HBox();
        Button max=new Button("\u25A1");
        Button min=new Button("-");
        Button close=new Button("x");
        max.setStyle("-fx-background:lightpink");
        min.setStyle("-fx-background:lightpink");
        close.setStyle("-fx-background:lightpink");

        max.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setMaximized(!stage.isMaximized());
            }
        });

        min.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setIconified(true);
            }
        });

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });

        b.setAlignment(Pos.TOP_RIGHT);
        b.getChildren().addAll(min,max,close);
        root.getChildren().addAll(box,b);
        Scene scene=new Scene(root,0,0);

        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);
        stage.setHeight((rect.getHeight()- scene.getHeight())/2);
        stage.setWidth((rect.getWidth()- scene.getWidth())/2);
        stage.setTitle("browse here");
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

class Httpclient{
    private HttpClient client;
    private HttpRequest req;
    private String url;
    private TextArea area;

    Httpclient(String url,TextArea area){
        this.url=url;
        this.area=area;
    }

    protected void client(){
        HttpClient.Builder client=HttpClient.newBuilder();

        //config
        client.followRedirects(HttpClient.Redirect.ALWAYS);
        client.connectTimeout(Duration.ofSeconds(200));
        client.version(HttpClient.Version.HTTP_1_1);
        this.client=client.build();
    }

    protected void request(){
        HttpRequest.Builder req= HttpRequest.newBuilder();
        req.uri(URI.create(url));
        req.GET();
        req.version(HttpClient.Version.HTTP_1_1);
        req.header("content-type","text/html");
        this.req=req.build();
    }

    protected void response(){
        try{
            Path path=Path.of("C:\\Users\\HP\\Documents\\kelvin\\docoment\\download.html");
            HttpResponse<String> res=client.send(req,HttpResponse.BodyHandlers.ofString());

           area.setText(res.body());

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}