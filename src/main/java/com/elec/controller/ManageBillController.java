package com.elec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.elec.dto.BillDTO;
import com.elec.entity.Account;
import com.elec.entity.Bill;
import com.elec.entity.HouseHold;
import com.elec.popup.BillEdit;
import com.elec.service.AccountService;
import com.elec.service.BillService;
import com.elec.service.HouseHoldService;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@Controller
public class ManageBillController extends AbstractController {

	@Autowired
	private BillService billService;

	@FXML
	private TableView<Bill> tableView;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private HouseHoldService houseHoldService;

	@FXML
	private void initialize() {

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
	
	private void save(BillDTO billDTO) {
		//Account account = MainFrameController.loadAccount();
//		bill.get
		Bill bill = new Bill();
		if (bill.getId() != null) {
			bill.setId(billDTO.getId());
		}
//		bill.setAccountBill(billDTO.getAccountBill());
		bill.setAddress(billDTO.getAddress());
		bill.setPhone(billDTO.getPhone());
		bill.setConsumptionNumOld(billDTO.getConsumptionNumOld());
		bill.setConsumptionNumNew(billDTO.getConsumptionNumNew());
		HouseHold houseHold = billDTO.getHouseHold();
//		if (bill.getId() != null) {
//
//		} else {
//			
			houseHold.setBill(bill);
//		}
			houseHoldService.save(houseHold);
		search();
	}
}
