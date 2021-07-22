package com.elec.common;

import org.springframework.stereotype.Controller;

import com.elec.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Controller
public class Dialog {

    @FXML
    private Label title;

    @FXML
    private Label message;

    @FXML
    private Button okBtn;

    @FXML
    private Button closeBtn;

    private Stage stage;

    @FXML
    private void cancel() {
        okBtn.getScene().getWindow().hide();
    }

    public void show() {
        stage.show();
    }

    public static class DialogBuilder {
        private String title;
        private String message;

        private ActionListener okActionListener;

        private DialogBuilder() {

        }

        public DialogBuilder okActionListenter(ActionListener okActionListener) {
            this.okActionListener = okActionListener;
            return this;
        }

        public DialogBuilder message(String message) {
            this.message = message;
            return this;
        }

        public DialogBuilder title(String title) {
            this.title = title;
            return this;
        }

        public Dialog build() {
            try {
                Stage stage = new Stage(StageStyle.UNDECORATED);
                FXMLLoader loader =
                		FXMLLoaderManage.fXMLLoader(FxmlView.DIALOG.getFxmlFile());
                Parent view = loader.load();
                stage.setScene(new Scene(view));

                Dialog controller = loader.getController();
                controller.stage = stage;

                controller.title.setText(this.title);
                controller.message.setText(this.message);

                if (null != okActionListener) {
                    controller.okBtn.setOnAction(event -> {
                        controller.cancel();
                        okActionListener.doAction();
                    });
                } else {
                    controller.okBtn.setVisible(false);
                    controller.closeBtn.setText("CLOSE");
                }

                return controller;
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }

        public static DialogBuilder builder() {
            return new DialogBuilder();
        }
    }

    public static interface ActionListener {
        void doAction();
    }

}
