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
import java.util.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Stage window;
    TextField tfOutput;

    private List<String> lines;
    private String[] columns = new String[]{"", "", "", "", "", "", "", ""};

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
        linesToColumns();
        String message = getMessageFromColumns();
        tfOutput.setText(message);
    }

    private String getMessageFromColumns() {
        char c;
        StringBuilder sb = new StringBuilder();
        for (String column : columns) {
            sb.append(mostAbundantCharIn(column));
        }
        return sb.toString();
    }

    private char mostAbundantCharIn(String column) {
        Map<Character, Integer> charCounts = new HashMap<>();

        // count chars
        for (int i = 0; i < column.length(); i++) {
            char c = column.charAt(i);
            Integer oldValue = charCounts.put(c, 0);
            if (oldValue != null) {
                charCounts.put(c, oldValue + 1);
            }
        }

        List<Map.Entry<Character, Integer>> countedChars = new ArrayList<>();

        // check
        for (Map.Entry<Character, Integer> entry : charCounts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            countedChars.add(entry);
        }
        // count
        countedChars.sort((o1, o2) -> o1.getValue().compareTo(o2.getValue()));

        char mostAbundantChar = countedChars.get(0).getKey();
        System.out.println("most abundant: " + mostAbundantChar);
        return mostAbundantChar;

    }

    private void linesToColumns() {
        for (String line : lines) {
            for (int i = 0; i < columns.length; i++) {
                columns[i] = new StringBuilder(columns[i]).append(line.charAt(i)).toString();
            }
        }
    }


    private void parseInput() {
        String line;
        lines = new ArrayList<>();
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
