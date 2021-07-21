package com.elec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.elec.entity.HouseHold;
import com.elec.popup.HouseHoldEdit;
import com.elec.service.HouseHoldService;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Controller
public class HouseHoldController extends AbstractController {

	@Autowired
	private HouseHoldService houseHoldService;

	@FXML
	private TextField fullName;

	@FXML
	private void initialize() {
		MenuItem edit = new MenuItem("Chỉnh sửa");
		edit.setOnAction(event -> {
			HouseHold houseHold = tableView.getSelectionModel().getSelectedItem();
			if (null != houseHold) {
				HouseHoldEdit.edit(houseHold, this::save);
			}
		});
		
		tableView.setContextMenu(new ContextMenu(edit));
		search();
	}

	@FXML
	private TableView<HouseHold> tableView;


	@FXML
	private void search() {
		tableView.getItems().clear();
		List<HouseHold> list = houseHoldService.search(fullName.getText());
		tableView.getItems().addAll(list);
	}
	
	@FXML
	private void clear() {
		fullName.clear();
		tableView.getItems().clear();
	}

	@FXML
	private void addNew() {
		HouseHoldEdit.addNew(this::save);
	}

	private void save(HouseHold houseHold) {
		houseHoldService.save(houseHold);
		search();
	}

}
