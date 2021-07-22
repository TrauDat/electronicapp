package com.elec.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Objects;

import org.slf4j.Logger;

import com.elec.view.FxmlView;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {
	
	private static final Logger LOG = getLogger(StageManager.class);
	private final Stage primaryStage;
	private final SpringFXMLLoader springFXMLLoader;
	
	public StageManager(Stage primaryStage, SpringFXMLLoader springFXMLLoader) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = primaryStage;
	}
	
	public void switchScene(final FxmlView view) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
		show(viewRootNodeHierarchy, view.getTitle());
	}
	
	private void show(final Parent rootNode, String title) {
		Scene scene = prepareScene(rootNode);
		
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();
		
		try {
			primaryStage.show();
		} catch (Exception exception) {
			logAndExit("Unable to show scene for title" + title, exception);
		}
		
	}
	
	private Scene prepareScene(Parent rootNode) {
		Scene scene = primaryStage.getScene();
		
		if (scene == null) {
			scene = new Scene(rootNode);
		}
		scene.setRoot(rootNode);
		return scene;
	}
	
	private Parent loadViewNodeHierarchy(String fxmlFilePath) {
		Parent rootNode = null;
		try {
			rootNode = springFXMLLoader.load(fxmlFilePath);
			Objects.requireNonNull(rootNode, "A root FXML node must no be null");
		} catch (Exception exception) {
			logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
		}
		return rootNode;
	}
	
	private void logAndExit(String errorMsg, Exception exception) {
		LOG.error(errorMsg, exception, exception.getCause());
		Platform.exit();
	}
	
}
