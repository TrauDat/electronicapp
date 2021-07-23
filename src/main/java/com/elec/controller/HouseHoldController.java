package com.elec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.elec.entity.Account;
import com.elec.entity.HouseHold;
import com.elec.popup.HouseHoldEdit;
import com.elec.service.AccountService;
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

	@Autowired
	private AccountService accountService;

	@FXML
	private TextField fullName;

	@FXML
	private void initialize() {
		MenuItem edit = new MenuItem("Cập nhật");
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
		Account account = MainFrameController.loadAccount();
		if (houseHold.getId() != null) {
			List<HouseHold> listHouseHold = houseHoldService.findByAccountid(account.getId());
			listHouseHold.forEach(h -> {
				if (h.getId().equals(houseHold.getId())) {
					listHouseHold.set(listHouseHold.indexOf(h), houseHold);
				}
			});
			account.setListHouseHold(listHouseHold);
		} else {
			account.addHouseHold(houseHold);
		}

		accountService.save(account);
		search();
	}

}
