package com.elec.config;

import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

@Component
public class SpringFXMLLoader {

	private final ApplicationContext context;
	private final ResourceBundle resourceBundle;
	
	public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
		this.context = context;
		this.resourceBundle = resourceBundle;
	}
	
	
	public Parent load(String xmlPath) {
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory(context::getBean);
	}
}
