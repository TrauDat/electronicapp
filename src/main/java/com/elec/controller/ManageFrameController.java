package com.elec.controller;

import org.springframework.stereotype.Controller;

import com.elec.common.Dialog;
import com.elec.common.FXMLLoaderManage;
import com.elec.view.FxmlView;
import com.elec.view.Menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Controller
public class ManageFrameController {

	@FXML
	private VBox sideBar;

	@FXML
	private StackPane manageView;

	@FXML
	private void initailze() {
		loadView(Menu.ManageAccount);
	}

	@FXML
	private void clickMenu(MouseEvent event) {
		Node node = (Node) event.getSource();

		if (node.getId().equals("Exit")) {
			// Confirm and display custom dialog
			Dialog.DialogBuilder.builder().title("Thoát").message("Bạn có muốn thoát khỏi ElecApp?")
					.okActionListenter(() -> sideBar.getScene().getWindow().hide()).build().show();

		} else {
			Menu menu = Menu.valueOf(node.getId());
			loadView(menu);
		}
	}

	private void loadView(Menu menu) {
		try {
			for (Node node : sideBar.getChildren()) {
				node.getStyleClass().remove("active");

				if (node.getId().equals(menu.name())) {
					node.getStyleClass().add("active");
				}
			}

			manageView.getChildren().clear();

			FXMLLoader loader = FXMLLoaderManage.fXMLLoader(menu.getFxml());
			Parent view = loader.load();

			AbstractController controller = loader.getController();
			controller.setTitle(menu);
			manageView.getChildren().add(view);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void show() {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = FXMLLoaderManage.fXMLLoader(FxmlView.MANAGEFRAME.getFxmlFile());
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.setFullScreen(true);
			stage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
