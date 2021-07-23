package com.elec.popup;

import java.time.LocalDate;
import java.util.function.Consumer;

import org.springframework.stereotype.Controller;

import com.elec.common.FXMLLoaderManage;
import com.elec.common.ValidationUtil;
import com.elec.entity.Account;
import com.elec.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Controller
public class ManageAccountEdit {
	private static final String patternEmail = "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+";
	private static final String patternName = "[a-zA-Z]+";

	@FXML
	private Label title;

	@FXML
	private Label message;

	@FXML
	private Label userId;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private DatePicker dob;

	@FXML
	private RadioButton rbMale;

	@FXML
	private ToggleGroup gender;

	@FXML
	private RadioButton rbFemale;

	@FXML
	private ComboBox<String> cbRole;

	@FXML
	private TextField email;

	@FXML
	private PasswordField password;

	private Account account;
	private Consumer<Account> saveHandler;
	private ObservableList<String> roles = FXCollections.observableArrayList("Admin", "User");

	public String getFirstName() {
		return firstName.getText();
	}

	public String getLastName() {
		return lastName.getText();
	}

	public LocalDate getDob() {
		return dob.getValue();
	}

	public String getGender() {
		return rbMale.isSelected() ? "Male" : "Female";
	}

	public String getRole() {
		return cbRole.getSelectionModel().getSelectedItem();
	}

	public String getEmail() {
		return email.getText();
	}

	public String getPassword() {
		return password.getText();
	}

	@FXML
	private void initialize() {
		cbRole.setItems(roles);
	}

	public static void addNew(Consumer<Account> accountHandler) {
		edit(null, accountHandler);
	}

	public static void edit(Account account, Consumer<Account> saveHandler) {
		try {
			Stage stage = new Stage(StageStyle.UNDECORATED);
			FXMLLoader loader = FXMLLoaderManage.fXMLLoader(FxmlView.MANAGEACCOUNTEDIT.getFxmlFile());
			stage.setScene(new Scene(loader.load()));
			stage.initModality(Modality.APPLICATION_MODAL);

			ManageAccountEdit controller = loader.getController();
			controller.init(account, saveHandler);

			stage.show();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void init(Account account, Consumer<Account> saveHandler) {
		this.account = account;
		this.saveHandler = saveHandler;

		if (null == account) {
			title.setText("Tạo mới tài khoản");
			this.account = new Account();
		} else {
			title.setText("Cập nhật tài khoản");
			this.account = account;
			if (this.account.getGender().equals("Male")) {
				rbMale.setSelected(true);
			} else {
				rbFemale.setSelected(true);
			}
		}
		firstName.setText(this.account.getFirstName());
		lastName.setText(this.account.getLastName());
		dob.setValue(this.account.getDob());
		cbRole.setValue(this.account.getRole());
		email.setText(this.account.getEmail());
		password.setText(this.account.getPassword());

	}

	@FXML
	private void save() {
		try {
			if (ValidationUtil.validate("First Name", getFirstName(), patternName)
					&& ValidationUtil.validate("Last Name", getLastName(), patternName)
					&& ValidationUtil.emptyValidation("DOB", dob.getEditor().getText().isEmpty())
					&& ValidationUtil.emptyValidation("Role", getRole() == null)
					&& ValidationUtil.validate("Email", getEmail(), patternEmail)
					&& ValidationUtil.emptyValidation("Password", getPassword().isEmpty())) {

				account.setFirstName(getFirstName());
				account.setLastName(getLastName());
				account.setDob(getDob());
				account.setGender(getGender());
				account.setRole(getRole());
				account.setEmail(getEmail());
				account.setPassword(getPassword());

				saveHandler.accept(account);
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

}
