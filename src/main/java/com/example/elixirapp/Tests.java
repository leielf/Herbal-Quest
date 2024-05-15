package com.example.elixirapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Tests {

    public Tests() {
    }

    public Stage addDialogWindow(){
        Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Button button = new Button("PURCHASE");
        button.setId("purchaseButton");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                button.setText("purchased");
            }
        });
        VBox vbox = new VBox(new Text("herb was not collected. You will have to buy it. Cost: 5 coins"), button);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(15));
        dialogStage.setScene(new Scene(vbox));
        return dialogStage;
//        dialogStage.show();/
    }
}
