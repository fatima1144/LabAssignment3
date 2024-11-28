package com.example.loginform;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LoginApp extends Application {

    private final String USER_FILE = "user_data.txt";

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Label bannerLabel = new Label("Welcome to Login Form");
        bannerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        bannerLabel.setTextFill(Color.WHITE);

        Image bannerImage = new Image(getClass().getResource("/images/img.png").toExternalForm());
        ImageView bannerImageView = new ImageView(bannerImage);
        bannerImageView.setFitWidth(300);
        bannerImageView.setFitHeight(150);
        bannerImageView.setPreserveRatio(false);

        StackPane banner = new StackPane();
        banner.getChildren().addAll(bannerImageView, bannerLabel);
        banner.setAlignment(Pos.CENTER);
        banner.setPadding(new Insets(10));
        root.setTop(banner);

        Label lblUsername = new Label("User Name:");
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Enter username");

        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Enter password");

        Label lblErrorMessage = new Label();
        lblErrorMessage.setTextFill(Color.RED);
        lblErrorMessage.setVisible(false);

        Button btnLogin = new Button("Login");
        Button btnExit = new Button("Exit");

        btnLogin.setOnAction(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            if (username.isEmpty() || password.isEmpty()) {
                lblErrorMessage.setText("Fields cannot be empty!");
                lblErrorMessage.setVisible(true);
                return;
            }

            if (authenticateUser(username, password)) {
                openNewPage(username);
            } else {
                lblErrorMessage.setText("Incorrect Username or Password");
                lblErrorMessage.setVisible(true);
            }
        });

        btnExit.setOnAction(e -> primaryStage.close());

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(lblUsername, 0, 1);
        grid.add(txtUsername, 1, 1);
        grid.add(lblPassword, 0, 2);
        grid.add(txtPassword, 1, 2);
        grid.add(lblErrorMessage, 0, 3, 2, 1);
        grid.add(btnLogin, 0, 4);
        grid.add(btnExit, 1, 4);

        root.setStyle("-fx-background-image: url('" + getClass().getResource("/images/img_6.png").toExternalForm() + "'); "
                + "-fx-background-size: cover;");
        root.setCenter(grid);

        Scene scene = new Scene(root, 350, 350);
        primaryStage.setTitle("Login Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        createUserFile();
    }


    private void createUserFile() {
        try {
            File file = new File(USER_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating user file: " + e.getMessage());
        }
    }
    private boolean authenticateUser(String username, String password) {
        try (Scanner scanner = new Scanner(new File(USER_FILE))) {
            while (scanner.hasNextLine()) {
                String[] userData = scanner.nextLine().split(",");
                if (userData[0].equals(username) && userData[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }
        return false;
    }

    private void openNewPage(String username) {
        Stage newStage = new Stage();

        Label lblMessage = new Label("Welcome, " + username + "!");
        lblMessage.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblMessage.setTextFill(Color.DARKGREEN);

        VBox vbox = new VBox(lblMessage);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 300, 200);
        newStage.setTitle("Welcome Page");
        newStage.setScene(scene);
        newStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
