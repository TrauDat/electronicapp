package com.elec.config;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import javafx.stage.Stage;

/*
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {
	
	private static final Logger LOG = getLogger(StageManager.class);
	private final Stage primaryStage;
	private final SpringFXMLLoader springFXMLLoader;
	
	public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = stage;
	}
	
	public void switchScene(final FxmlView view) {
		
	}
	
	
}
