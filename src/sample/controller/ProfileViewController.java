package sample.controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.model.UserData;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User profile class
 */
public class ProfileViewController{
    private UserData userData;

    private Label userNameLabel;
    private Label userSurnameLabel;
    private Label userMoneyLabel;
    private Label totalWalletValueLabel;
    private Label transactionHistoryLabel;

    /**
     * sets user data
     * @param userDataSource
     */
    public void setUserDataSource(UserData userDataSource) {
        this.userData = userDataSource;
    }

    /**
     * Sets up a profile view
     * @return profileView node with user profile view
     */
    public VBox loadProfileView() {
        VBox profileView = new VBox();
        profileView.setPadding(new Insets(10,10,10,10));
        profileView.getChildren().addAll(
                userDetails(),
                userMoney(),
                userTransactionHistory()
        );

        return profileView;
    }


    private HBox userDetails() {
        HBox userDetails = new HBox();

        setStyle(userDetails);
        userDetails.setPrefHeight(80);
        userDetails.setPadding(new Insets(10,10,10,10));
        userDetails.getChildren().addAll(userDetailsVBox());

        return userDetails;
    }

    private VBox userDetailsVBox() {
        VBox userDetails = new VBox();

        userDetails.setPrefHeight(80);
        userNameLabel = new Label("Name: ");
        userSurnameLabel = new Label("Surname: ");

        userNameLabel.setText(userNameLabel.getText() + userData.getName());
        userSurnameLabel.setText(userSurnameLabel.getText() + userData.getSurname());

        userNameLabel.setPadding(new Insets(0,10,10,0));
        userSurnameLabel.setPadding(new Insets(0,10,10,0));
        userDetails.getChildren().addAll(userNameLabel, userSurnameLabel);

        return userDetails;
    }

    private HBox userMoney() {
        HBox userMoney = new HBox();

        userMoney.setAlignment(Pos.CENTER_LEFT);
        userMoney.setPrefHeight(80);
        userMoneyLabel = new Label("Money left: ");

        userMoneyLabel.setText(userMoneyLabel.getText() + String.valueOf(userData.getCash()));

        setStyle(userMoney);
        userMoney.setPadding(new Insets(10,10,10,10));
        userMoney.getChildren().addAll(userMoneyLabel);

        return userMoney;
    }

    private HBox userTransactionHistory() {
        HBox userTransactionHistory = new HBox();

        userTransactionHistory.setPrefHeight(80);
        transactionHistoryLabel = new Label("Transaction history: ");
        userTransactionHistory.setAlignment(Pos.CENTER_LEFT);
        Button transactionHistoryButton = new Button("View history");
        transactionHistoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                userTransactionHistoryVBox();
            }
        });

        setStyle(userTransactionHistory);
        userTransactionHistory.setPadding(new Insets(10,10,10,10));
        userTransactionHistory.getChildren().addAll(transactionHistoryLabel, transactionHistoryButton);

        return userTransactionHistory;
    }

    private void userTransactionHistoryVBox() {
        Stage stage = new Stage();
        TextArea textArea = new TextArea();

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            textArea.setText(readFile(file));
        }

        VBox mainLayout = new VBox(textArea);
        Scene scene = new Scene(mainLayout,400,200);
        stage.setScene(scene);
        stage.show();
    }

    private void setStyle(HBox box) {
        box.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        box.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
    }

    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader;
        bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProfileViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProfileViewController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(ProfileViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return stringBuffer.toString();
    }

    public void updateView() {
        userMoneyLabel.setText("Money left: " + userData.getCash());
    }
}