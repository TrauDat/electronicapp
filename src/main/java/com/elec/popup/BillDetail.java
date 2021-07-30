package com.elec.popup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.elec.common.FXMLLoaderManage;
import com.elec.controller.MainFrameController;
import com.elec.entity.Account;
import com.elec.entity.Bill;
import com.elec.entity.ElecPrice;
import com.elec.entity.HouseHold;
import com.elec.service.ElecPriceService;
import com.elec.service.HouseHoldService;
import com.elec.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Controller
public class BillDetail {

	@FXML
	private Label customerName;

	@FXML
	private Label address;

	@FXML
	private Label phoneNumber;

	@FXML
	private Label oldNum;

	@FXML
	private Label newNum;

	@FXML
	private Label numPlus;

	@FXML
	private Label powerUse;

	@FXML
	private Label amountUnit;

	@FXML
	private Label amount;

	@Autowired
	HouseHoldService houseHoldService;

	@Autowired
	ElecPriceService elecPriceService;

	private Bill bill;

	@FXML
	private void close() {
		address.getScene().getWindow().hide();
	}

	public static void showDetail(Bill bill) {
		try {
			Stage stage = new Stage(StageStyle.UNDECORATED);
			FXMLLoader loader = FXMLLoaderManage.fXMLLoader(FxmlView.DETAILBILL.getFxmlFile());
			stage.setScene(new Scene(loader.load()));
			stage.initModality(Modality.APPLICATION_MODAL);

			BillDetail controller = loader.getController();
			controller.init(bill);

			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private int countHouseHole() {
		Account account = MainFrameController.loadAccount();
		List<HouseHold> listHouseHold = houseHoldService.findByAccountid(account.getId());
		return listHouseHold.size();
	}

	private double unitMount() {
		int powserUse = (int) (bill.getConsumptionNumNew() - bill.getConsumptionNumOld());

		List<ElecPrice> listElecPrice = elecPriceService.listElecPrice();

		Map<Long, ElecPrice> mapElecBill = listElecPrice.stream()
				.collect(Collectors.toMap(ElecPrice::getId, Function.identity()));

		double total = 0;

		if (powserUse > 401) {
			ElecPrice elecPrice6 = mapElecBill.get(6L);
			total += (powserUse - 400) * elecPrice6.getUnitPrice();
			powserUse = powserUse - (powserUse - 400);
		}
		if (powserUse >= 300) {
			ElecPrice elecPrice5 = mapElecBill.get(5L);
			total += (powserUse - 300) * elecPrice5.getUnitPrice();
			powserUse = powserUse - (powserUse - 300);
		}
		if (powserUse >= 200) {
			ElecPrice elecPrice4 = mapElecBill.get(4L);
			total += (powserUse - 200) * elecPrice4.getUnitPrice();
			powserUse = powserUse - (powserUse - 200);
		}
		if (powserUse >= 100) {
			ElecPrice elecPrice3 = mapElecBill.get(3L);
			total += (powserUse - 100) * elecPrice3.getUnitPrice();
			powserUse = powserUse - (powserUse - 100);
		}
		if (powserUse >= 50) {
			ElecPrice elecPrice2 = mapElecBill.get(2L);
			total += (powserUse - 50) * elecPrice2.getUnitPrice();
			powserUse = powserUse - (powserUse - 50);
		}
		if (powserUse <= 50) {
			ElecPrice elecPrice1 = mapElecBill.get(1L);
			total += (powserUse) * elecPrice1.getUnitPrice();
		}

		return total + (total * 0.1);
	}

	private void init(Bill bill) {
		this.bill = bill;
		int powerUser = (int) (bill.getConsumptionNumNew() - bill.getConsumptionNumOld());
		double total = unitMount();
		int countHouseHold = countHouseHole();
		double totalBill = total * countHouseHold;

		customerName.setText(this.bill.getHouseHold().getFullName());
		address.setText(this.bill.getAddress());
		phoneNumber.setText(this.bill.getPhone());
		oldNum.setText(String.valueOf(this.bill.getConsumptionNumOld()));
		newNum.setText(String.valueOf(this.bill.getConsumptionNumNew()));
		numPlus.setText(String.valueOf(countHouseHole()));
		powerUse.setText(String.valueOf(powerUser));
		amountUnit.setText(Double.toString(totalBill));
	}

}
