package com.elec.popup;

import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.elec.common.FXMLLoaderManage;
import com.elec.dto.BillDTO;
import com.elec.entity.Bill;
import com.elec.entity.HouseHold;
import com.elec.service.HouseHoldService;
import com.elec.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Controller
public class BillEdit {

	@FXML
	private Label title;

	@FXML
	private TextField address;

	@FXML
	private TextField phone;

	@FXML
	private TextField idCustomer;

	@FXML
	private TextField consumptionNumOld;

	@FXML
	private TextField consumptionNumNew;

	@Autowired
	private HouseHoldService houseHoldService;

	private BillDTO bill;

	private Consumer<BillDTO> saveHandler;

	public static void addNew(Consumer<BillDTO> saveHandler) {
		edit(null, saveHandler);
	}

	public static void edit(BillDTO bill, Consumer<BillDTO> saveHandler) {
		try {
			Stage stage = new Stage(StageStyle.UNDECORATED);
			FXMLLoader loader = FXMLLoaderManage.fXMLLoader(FxmlView.MANAGEBILL.getFxmlFile());
			stage.setScene(new Scene(loader.load()));
			stage.initModality(Modality.APPLICATION_MODAL);

			BillEdit controller = loader.getController();
			controller.init(bill, saveHandler);

			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void init(BillDTO bill, Consumer<BillDTO> saveHandler) {
		this.bill = bill;
		this.saveHandler = saveHandler;

		if (null == bill) {
			title.setText("Tạo mới hóa đơn");
			this.bill = new BillDTO();
		} else {
			title.setText("Cập nhật hóa đơn");
			this.bill = bill;
		}
		address.setText(this.bill.getAddress());
		phone.setText(this.bill.getPhone());
		consumptionNumOld.setText(String.valueOf(this.bill.getConsumptionNumOld()));
		consumptionNumNew.setText(String.valueOf(this.bill.getConsumptionNumNew()));

	}

	@FXML
	private void save() {
		try {
			Optional<HouseHold> houseHold = houseHoldService.findById(Long.parseLong(idCustomer.getText()));
			if (houseHold.isPresent()) {
				bill.setHouseHold(houseHold.get());
				bill.setAddress(address.getText());
				bill.setPhone(phone.getText());
				bill.setConsumptionNumOld(Long.parseLong(consumptionNumOld.getText()));
				bill.setConsumptionNumNew(Long.parseLong(consumptionNumNew.getText()));
				saveHandler.accept(bill);
			}
			close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void close() {
		address.getScene().getWindow().hide();
	}
}
