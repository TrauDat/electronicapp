package com.elec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.elec.config.StageManager;
import com.elec.entity.Account;
import com.elec.popup.ManageAccountEdit;
import com.elec.service.AccountService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Controller
public class ManageAccountController extends AbstractController {

	@Autowired
	private AccountService accountService;

	@Autowired
	@Lazy
	private StageManager stageManager;

	@FXML
	private Pagination pagination;

	@FXML
	private TextField searchEmail;

	@FXML
	private TableView<Account> accountTable;

	private ObservableList<Account> userList = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		loadListAccount();
		MenuItem edit = new MenuItem("Cập nhật");
		edit.setOnAction(event -> {
			Account account = accountTable.getSelectionModel().getSelectedItem();
			if (null != account) {
				ManageAccountEdit.edit(account, this::save);
			}
		});
		accountTable.setContextMenu(new ContextMenu(edit));

	}

	@FXML
	private void search() {
		Account account = accountService.findByEmail(searchEmail.getText());

		if (account != null) {
			accountTable.getItems().clear();
			accountTable.getItems().add(account);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Không tìm thấy");
			alert.setHeaderText(null);
			alert.setContentText("Không tìm thấy người dùng");
			alert.showAndWait();
		}
	}

	@FXML
	private void clear() {
		searchEmail.clear();
		loadListAccount();
	}

	private void loadListAccount() {
		userList.clear();
		userList.addAll(accountService.findAll());
		accountTable.setItems(userList);
	}

	@FXML
	private void addNew() {
		ManageAccountEdit.addNew(this::save);
	}

	private void save(Account account) {
		accountService.save(account);
		loadListAccount();
	}
}
