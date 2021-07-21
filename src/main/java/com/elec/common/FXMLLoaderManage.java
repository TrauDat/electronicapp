package com.elec.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.elec.config.SpringFXMLLoader;
import com.elec.config.StageManager;

import javafx.fxml.FXMLLoader;

public class FXMLLoaderManage {
	private static StageManager stageManagerStatic;
	private static SpringFXMLLoader springFXMLLoaderStatic;
	
	@Autowired
	private SpringFXMLLoader springFXMLLoader;

	@Autowired
	@Lazy
	private StageManager stageManager;
	
	@PostConstruct
	private void init() {
		stageManagerStatic = this.stageManager;
		springFXMLLoaderStatic = this.springFXMLLoader;
	}
	
	public static FXMLLoader fXMLLoader(String fxmlFilePath) {
		FXMLLoader loader = springFXMLLoaderStatic.getFXMLLoader(fxmlFilePath);
		return loader;
	}
}
