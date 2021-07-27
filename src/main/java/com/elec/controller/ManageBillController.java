package com.elec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.elec.entity.Bill;
import com.elec.popup.BillEdit;
import com.elec.service.BillService;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@Controller
public class ManageBillController extends AbstractController {

	@Autowired
	private BillService billService;

	@FXML
	private TableView<Bill> tableView;

	@FXML
	private void initialize() {
//		MenuItem edit = new MenuItem("Cập nhật");
//		edit.setOnAction(event -> {
//			Bill bill = tableView.getSelectionModel().getSelectedItem();
//			if (null != bill) {
//				BillEdit.edit(bill, this::save);
//			}
//		});
//		
//		tableView.setContextMenu(new ContextMenu(edit));
		search();
	}

	@FXML
	private void search() {
		tableView.getItems().clear();
		List<Bill> listBill = billService.findAll();
		tableView.getItems().addAll(listBill);
	}

	@FXML
	private void addNew() {
		BillEdit.addNew(this::save);
	}
	
	private void save(Bill bill) {
		billService.save(bill);
		search();
	}
}
