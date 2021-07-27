package com.elec;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.elec.config.StageManager;
import com.elec.repository.BaseRepositoryImpl;
import com.elec.view.FxmlView;

import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class Main extends Application {

	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;
	
	public static void main(final String[] args) {
		Application.launch(args);
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
		stageManager = springContext.getBean(StageManager.class, stage);
		displayInitialScene();
	}

	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
	}

	@Override
	public void stop() throws Exception {
		springContext.close();
	}
	
	protected void displayInitialScene() {
		stageManager.switchScene(FxmlView.LOGIN);
	}
	
	
	// This method init 
	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}
	

}
