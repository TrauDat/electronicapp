package com.elec.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.elec.config.StageManager;
import com.elec.service.AccountService;
import com.elec.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class LoginController implements Initializable{

	@Autowired
	private AccountService accountService;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private TextField username;
	
	@FXML
	private Label lblLogin;
	
	@FXML
	private void login(ActionEvent event) {
		if(accountService.authenticate(getUsername(), getPassword())) {
			stageManager.switchScene(FxmlView.USER);
		} else {
			lblLogin.setText("Login Failed");
		}
	}
	
	public String getPassword() {
		return password.getText();
	}
	
	public String getUsername() {
		return username.getText();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
	}

}
