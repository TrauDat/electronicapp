package com.elec.config;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class FXMLLoaderManager {
	private static final Logger LOG = getLogger(FXMLLoaderManager.class);
	
	private final Stage primaryStage;
	private final SpringFXMLLoader springFXMLLoader;
	
	public FXMLLoaderManager(Stage primaryStage, SpringFXMLLoader springFXMLLoader) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = primaryStage;
	}
	
	public FXMLLoader fXMLLoader(String fxmlFilePath) {
		FXMLLoader loader = springFXMLLoader.getFXMLLoader(fxmlFilePath);
		return loader;
	}
}
