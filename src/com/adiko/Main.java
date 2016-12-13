package com.adiko;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Stage window;
    TextField tfOutput;
    List<String> lines = new ArrayList();
    String[] columns = new String[5];

    StringBuilder message;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        tfOutput = new TextField();
        Button btnGo = new Button("GO");
        btnGo.setOnAction(e -> processInput());

        layout.getChildren().addAll(btnGo, tfOutput);

        StackPane root = new StackPane();
        root.getChildren().add(layout);
        window.setScene(new Scene(root, 300, 300));
        window.show();
    }

    private void processInput() {
        parseInput();
        parseLines();
        countCharactersInColumns();
        tfOutput.setText(message.toString());

    }

    private void countCharactersInColumns() {
        for (String column : columns) {
            for (int i = 0; i < column.length(); i++) {
                message.append(getMostAbundantChar(column));
            }
        }
    }

    private char getMostAbundantChar(String text) {
        List<Map.Entry<Character, Integer>> charCounts = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Integer count = charCounts.get(c);
            if (count == null) {
                charCounts.put(text.charAt(i), 0);
            } else {
                charCounts.put(text.charAt(i), count++);
            }
        }

    }

    private void parseLines() {
        for (String line : lines) {
            for (int i = 0; i < 5; i++) {
                StringBuilder sb = new StringBuilder(columns[i]);
                sb.append(line.charAt(i));
            }
        }

    }

    private void parseInput() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")))) {
            line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
            System.out.println("FILE NOT FOUND: " + e.getMessage());
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println("IOEXCEPTION: " + e.getMessage());
        }
    }
}
