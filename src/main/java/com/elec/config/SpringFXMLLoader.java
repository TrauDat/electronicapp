package com.elec.config;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/*
 * Will load FXM hierarchy as specified in the load method and register
 * Spring as the FXML Controller Factory. Allows Spring and Java FX to coexist
 * once the Spring Application context has been bootstrapped.
 * 
 */
@Component
public class SpringFXMLLoader {

	private static ApplicationContext context;
	private static ResourceBundle resourceBundle;
	
	@Autowired
	public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
		SpringFXMLLoader.resourceBundle = resourceBundle;
		SpringFXMLLoader.context = context;	
	}
	
	
	public Parent load(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory(context::getBean);
		loader.setResources(resourceBundle);
		loader.setLocation(getClass().getResource(fxmlPath));
		return loader.load();
	}
	
	public static FXMLLoader getFXMLLoader(String fxmlPath) {
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory(context::getBean);
		loader.setResources(resourceBundle);
		loader.setLocation(SpringFXMLLoader.class.getResource(fxmlPath));
		return loader;
	}
}
