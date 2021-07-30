package com.elec.popup;

import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.elec.common.FXMLLoaderManage;
import com.elec.common.ValidationUtil;
import com.elec.entity.Bill;
import com.elec.entity.HouseHold;
import com.elec.service.HouseHoldService;
import com.elec.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Controller
public class BillEdit {

	private static final String phonePattern = "\\d{3}-\\d{2}-\\d{7}";

	@FXML
	private Label title;

	@FXML
	private TextField address;

	@FXML
	private TextField phone;

	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;

	@FXML
	private TextField idCustomer;

	@FXML
	private TextField consumptionNumOld;

	@FXML
	private TextField consumptionNumNew;

	@Autowired
	private HouseHoldService houseHoldService;

	private Bill bill;

	private Consumer<Bill> saveHandler;

	public static void addNew(Consumer<Bill> saveHandler) {
		edit(null,saveHandler);
	}

	public static void edit(Bill bill, Consumer<Bill> saveHandler) {
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

	private void init(Bill bill, Consumer<Bill> saveHandler) {
		this.bill = bill;
		this.saveHandler = saveHandler;

		if (null == bill) {
			title.setText("Tạo mới hóa đơn");
			this.bill = new Bill();
		} else {
			title.setText("Cập nhật hóa đơn");
			this.bill = bill;
		}
		fromDate.setValue(this.bill.getFromDate());
		toDate.setValue(this.bill.getToDate());
		address.setText(this.bill.getAddress());
		phone.setText(this.bill.getPhone());
		consumptionNumOld.setText(String.valueOf(this.bill.getConsumptionNumOld()));
		consumptionNumNew.setText(String.valueOf(this.bill.getConsumptionNumNew()));
	}

	@FXML
	private void save() {
		try {
			if (ValidationUtil.emptyValidation("Mã khách hàng", idCustomer.getText().isEmpty())) {
				Optional<HouseHold> houseHoldOpt = houseHoldService.findById(Long.parseLong(idCustomer.getText()));
//				Optional<HouseHold> houseHold = houseHoldService.seachByJPQL(Long.parseLong(idCustomer.getText()));
				if (houseHoldOpt.isPresent()) {
					if (ValidationUtil.emptyValidation("Địa chỉ", ValidationUtil.isEmpty(address.getText()))
							&& ValidationUtil.emptyValidation("Số điện thoại", ValidationUtil.isEmpty(phone.getText()))
							&& ValidationUtil.validate("Số điện thoại", phone.getText(), phonePattern)
							&& ValidationUtil.emptyValidation("Ngày bắt đầu", ValidationUtil.isEmpty(fromDate.getEditor().getText()))
							&& ValidationUtil.emptyValidation("Ngày kết thúc", ValidationUtil.isEmpty(toDate.getEditor().getText()))
							&& ValidationUtil.emptyValidation("Chỉ số cũ", ValidationUtil.isEmpty(consumptionNumOld.getText()))
							&& ValidationUtil.emptyValidation("Chỉ số mới", ValidationUtil.isEmpty(consumptionNumNew.getText()))) {
						if (Long.parseLong(consumptionNumOld.getText()) < Long.parseLong(consumptionNumNew.getText())) {
							bill.setFromDate(fromDate.getValue());
							bill.setToDate(toDate.getValue());
							bill.setAddress(address.getText());
							bill.setPhone(phone.getText());
							bill.setConsumptionNumOld(Long.parseLong(consumptionNumOld.getText()));
							bill.setConsumptionNumNew(Long.parseLong(consumptionNumNew.getText()));
							HouseHold houseHoldAdd = houseHoldOpt.get();
							houseHoldAdd.add(bill);
							saveHandler.accept(bill);
							close();
						} else {
							ValidationUtil.validateField("Chỉ số mới không được nhỏ hơn chỉ số cũ");
						}
					}
				} else {
					ValidationUtil.validateField("Không tìm thấy mã khách hàng");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void close() {
		address.getScene().getWindow().hide();
	}
}
