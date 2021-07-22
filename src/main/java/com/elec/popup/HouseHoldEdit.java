package com.elec.popup;

import java.util.function.Consumer;

import org.springframework.stereotype.Controller;

import com.elec.common.FXMLLoaderManage;
import com.elec.common.ValidationUtil;
import com.elec.entity.HouseHold;
import com.elec.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Controller
public class HouseHoldEdit {

	private static final String patternEmail = "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+";
	private static final String patternName = "[a-zA-Z]+";
	private static final String patternIdentityCard = "[0-9]{9}";

	@FXML
	private ToggleGroup gender;

	@FXML
	private Label title;

	@FXML
	private Label message;

	@FXML
	private TextField fullName;

	@FXML
	private TextField identityCard;

	@FXML
	private TextField email;

	@FXML
	private DatePicker dob;

	@FXML
	private RadioButton rbMale;

	@FXML
	private RadioButton rbFemale;

	private HouseHold houseHold;

	private Consumer<HouseHold> saveHandler;

	public String getGender() {
		return rbMale.isSelected() ? "Male" : "Female";
	}

	public static void addNew(Consumer<HouseHold> saveHandler) {
		edit(null, saveHandler);
	}

	public static void edit(HouseHold houseHold, Consumer<HouseHold> saveHandler) {
		try {
			Stage stage = new Stage(StageStyle.UNDECORATED);
			FXMLLoader loader = FXMLLoaderManage.fXMLLoader(FxmlView.HOUSEHOLDEDIT.getFxmlFile());
			stage.setScene(new Scene(loader.load()));
			stage.initModality(Modality.APPLICATION_MODAL);

			HouseHoldEdit controller = loader.getController();
			controller.init(houseHold, saveHandler);

			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void save() {
		try {
			if (ValidationUtil.emptyValidation("Tên chủ hộ", ValidationUtil.isEmpty(fullName.getText()))
					&& ValidationUtil.validate("Tên chủ hộ", fullName.getText(), patternName)
					&& ValidationUtil.emptyValidation("CMND ", ValidationUtil.isEmpty(identityCard.getText()))
					&& ValidationUtil.validate("CMND", identityCard.getText(), patternIdentityCard)
					&& ValidationUtil.emptyValidation("Năm sinh", ValidationUtil.isEmpty(dob.getEditor().getText()))
					&& ValidationUtil.emptyValidation("Email", ValidationUtil.isEmpty(email.getText()))
					&& ValidationUtil.validate("Email", email.getText(), patternEmail)) {

				houseHold.setFullName(fullName.getText());
				houseHold.setGender(getGender());
				houseHold.setIdentityCard(identityCard.getText());
				houseHold.setDob(dob.getValue());
				houseHold.setEmail(email.getText());
				saveHandler.accept(houseHold);
				close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void close() {
		email.getScene().getWindow().hide();
	}

	private void init(HouseHold houseHold, Consumer<HouseHold> saveHandler) {
		this.houseHold = houseHold;
		this.saveHandler = saveHandler;

		if (null == houseHold) {
			title.setText("Tạo mới hộ gia đình");
			this.houseHold = new HouseHold();
		} else {
			title.setText("Cập nhật hộ gia đình");
			this.houseHold = houseHold;
			if (this.houseHold.getGender().equals("Male")) {
				rbMale.setSelected(true);
			} else {
				rbFemale.setSelected(true);
			}
		}

		fullName.setText(this.houseHold.getFullName());
		identityCard.setText(this.houseHold.getIdentityCard());
		dob.setValue(this.houseHold.getDob());
		email.setText(this.houseHold.getEmail());
	}
}
