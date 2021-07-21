package com.elec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.elec.common.FXMLLoaderManage;
import com.elec.config.StageManager;
import com.elec.entity.Account;
import com.elec.view.FxmlView;
import com.elec.view.Menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Controller
public class MainFrameController {

	@Autowired
	@Lazy
	private StageManager stageManager;
	
	@FXML
	private VBox sideBar;

	@FXML
	private StackPane contentView;

	@FXML
	private void initialize() {
		loadView(Menu.Home);
	}
	
	@FXML
	private Label title;

	@FXML
	private Label message;

	@FXML
	private Button okBtn;

	@FXML
	private Button closeBtn;

	private Stage stage;
	
	private static Account accountStatic;

	@FXML
	private void cancel() {
		okBtn.getScene().getWindow().hide();
	}

	public void show2() {
		stage.show();
	}

	@FXML
	private void clickMenu(MouseEvent event) {
		Node node = (Node) event.getSource();

		if (node.getId().equals("Exit")) {
			// Confirm and display custom dialog
			MainFrameController.DialogBuilder.builder().title("Thoát").message("Bạn có muốn thoát khỏi ElecApp")
					.okActionListener(() -> sideBar.getScene().getWindow().hide()).build().show2();
		
		} else {
			Menu menu = Menu.valueOf(node.getId());
			loadView(menu);
		}
	}
	
	public static class DialogBuilder {
		
		private String title;
		private String message;

		private ActionListener okActionListener;

		private DialogBuilder() {

		}

		public DialogBuilder okActionListener(ActionListener okActionListener) {
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

		public MainFrameController build() {
			try {
				Stage stage = new Stage(StageStyle.UNDECORATED);
				FXMLLoader loader = FXMLLoaderManage.fXMLLoader(FxmlView.DIALOG.getFxmlFile());
				Parent view = loader.load();
				stage.setScene(new Scene(view));

				MainFrameController controller = loader.getController();
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
					controller.closeBtn.setText("Đóng");
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
	
	private void loadView(Menu menu) {
		try {
			for (Node node : sideBar.getChildren()) {
				node.getStyleClass().remove("active");

				if (node.getId().equals(menu.name())) {
					node.getStyleClass().add("active");
				}
			}

			contentView.getChildren().clear();

			FXMLLoader loader = stageManager.fXMLLoader(menu.getFxml());
			Parent view = loader.load();

			AbstractController controller = loader.getController();
			controller.setTitle(menu);
			contentView.getChildren().add(view);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Account getAccount(Account account) {
		accountStatic = account;
		return accountStatic;
	}
	
	public static Account loadAccount() {
		return accountStatic;
	}

	public static void show() {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = FXMLLoaderManage.fXMLLoader(FxmlView.MAINFRAME.getFxmlFile());
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.setFullScreen(true);
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
