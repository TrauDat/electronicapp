package com.elec.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.elec.bean.Mail;
import com.elec.config.StageManager;
import com.elec.entity.Account;
import com.elec.service.AccountService;
import com.elec.service.MailService;
import com.elec.view.FxmlView;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

@Controller
public class AccountController implements Initializable {

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnSendMail;

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

	@FXML
	private Button reset;

	@FXML
	private Button saveUser;

	@FXML
	private TableView<Account> accountTable;

	@FXML
	private TableColumn<Account, Long> colUserId;

	@FXML
	private TableColumn<Account, String> colFirstName;

	@FXML
	private TableColumn<Account, String> colLastName;

	@FXML
	private TableColumn<Account, LocalDate> colDOB;

	@FXML
	private TableColumn<Account, String> colGender;

	@FXML
	private TableColumn<Account, String> colRole;

	@FXML
	private TableColumn<Account, String> colEmail;

	@FXML
	private TableColumn<Account, CheckBox> colSendEmail;

	@FXML
	private TableColumn<Account, Boolean> colEdit;

	@FXML
	private MenuItem deleteUsers;

	@Autowired
	@Lazy
	private StageManager stageManager;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MailService mailService;

	private ObservableList<Account> userList = FXCollections.observableArrayList();
	private ObservableList<String> roles = FXCollections.observableArrayList("Admin", "User");

	@FXML
	private void exit(ActionEvent event) {
		Platform.exit();
	}

	/*
	 * Logout and go to the login page
	 */
	@FXML
	private void logout(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	@FXML
	void reset(ActionEvent event) {
		clearFields();
	}

	@FXML
	private void saveUser(ActionEvent event) {
		if (validate("First Name", getFirstName(), "[a-zA-Z]+") && validate("Last Name", getLastName(), "[a-zA-Z]+")
				&& emptyValidation("DOB", dob.getEditor().getText().isEmpty())
				&& emptyValidation("Role", getRole() == null)) {

			if (userId.getText() == null || userId.getText() == "") {
				if (validate("Email", getEmail(), "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")
						&& emptyValidation("Password", getPassword().isEmpty())) {

					Account user = new Account();
					user.setFirstName(getFirstName());
					user.setLastName(getLastName());
					user.setDob(getDob());
					user.setGender(getGender());
					user.setRole(getRole());
					user.setEmail(getEmail());
					user.setPassword(getPassword());

					Account newUser = accountService.save(user);

					saveAlert(newUser);
				}

			} else {
				Account user = accountService.find(Long.parseLong(userId.getText()));
				user.setFirstName(getFirstName());
				user.setLastName(getLastName());
				user.setDob(getDob());
				user.setGender(getGender());
				user.setRole(getRole());
				Account updatedUser = accountService.update(user);
				updateAlert(updatedUser);
			}

			clearFields();
			loadUserDetails();
		}
	}

	@FXML
	private void sendMail(ActionEvent event) {
		Mail mail = new Mail();
		mail.setMailFrom("hoang.hutech.97@gmail.com");
		mail.setMailSubject("Spring Boot - Email Example");
        mail.setMailContent("This is email test from ElecApp");
		userList.forEach(user -> {
			if (user.getIsSendMail()) {
				mail.setMailTo(user.getEmail());
				mailService.sendMail(mail);
			}
		});
		loadUserDetails();
	}

	@FXML
	private void deleteUsers(ActionEvent event) {
		List<Account> users = accountTable.getSelectionModel().getSelectedItems();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			accountService.deleteInBatch(users);

		loadUserDetails();
	}

	private void clearFields() {
		userId.setText(null);
		firstName.clear();
		lastName.clear();
		dob.getEditor().clear();
		rbMale.setSelected(false);
		rbFemale.setSelected(true);
		cbRole.getSelectionModel().clearSelection();
		email.clear();
		password.clear();
	}

	private void saveAlert(Account user) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The user " + user.getFirstName() + " " + user.getLastName() + " has been created and \n"
				+ getGenderTitle(user.getGender()) + " id is " + user.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Account user) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The user " + user.getFirstName() + " " + user.getLastName() + " has been updated.");
		alert.showAndWait();
	}

	private String getGenderTitle(String gender) {
		return (gender.equals("Male")) ? "his" : "her";
	}

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

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		cbRole.setItems(roles);

		accountTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		setColumnProperties();

		// Add all users into table
		loadUserDetails();
	}

	/*
	 * Set all accountTable column properties
	 */
	private void setColumnProperties() {
		colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
		colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colSendEmail.setCellValueFactory(booleanCellValueFactory);
		colEdit.setCellFactory(cellFactory);
	}

	Callback<CellDataFeatures<Account, CheckBox>, ObservableValue<CheckBox>> booleanCellValueFactory = new Callback<CellDataFeatures<Account, CheckBox>, ObservableValue<CheckBox>>() {
		@Override
		public ObservableValue<CheckBox> call(CellDataFeatures<Account, CheckBox> arg0) {
			Account user = arg0.getValue();
			CheckBox checkBox = new CheckBox();
			
			checkBox.selectedProperty().setValue(user.getIsSendMail());
			
			checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
					user.setIsSendMail(new_val);
				}
				
			});
			
			return new SimpleObjectProperty<CheckBox>(checkBox);
		}
	};

	Callback<TableColumn<Account, Boolean>, TableCell<Account, Boolean>> cellFactory = new Callback<TableColumn<Account, Boolean>, TableCell<Account, Boolean>>() {
		@Override
		public TableCell<Account, Boolean> call(final TableColumn<Account, Boolean> param) {
			final TableCell<Account, Boolean> cell = new TableCell<Account, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							Account user = getTableView().getItems().get(getIndex());
							updateUser(user);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateUser(Account user) {
					userId.setText(Long.toString(user.getId()));
					firstName.setText(user.getFirstName());
					lastName.setText(user.getLastName());
					dob.setValue(user.getDob());
					if (user.getGender().equals("Male"))
						rbMale.setSelected(true);
					else
						rbFemale.setSelected(true);
					cbRole.getSelectionModel().select(user.getRole());
				}
			};
			return cell;
		}
	};

	/*
	 * Add All users to observable list and update table
	 */
	private void loadUserDetails() {
		userList.clear();
		userList.addAll(accountService.findAll());

		accountTable.setItems(userList);
	}

	/*
	 * Validations
	 */
	private boolean validate(String field, String value, String pattern) {
		if (!value.isEmpty()) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(value);
			if (m.find() && m.group().equals(value)) {
				return true;
			} else {
				validationAlert(field, false);
				return false;
			}
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	private boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	private void validationAlert(String field, boolean empty) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		if (field.equals("Role"))
			alert.setContentText("Please Select " + field);
		else {
			if (empty)
				alert.setContentText("Please Enter " + field);
			else
				alert.setContentText("Please Enter Valid " + field);
		}
		alert.showAndWait();
	}

}
