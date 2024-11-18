import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminReportController extends BaseController {

    @FXML
    private Button backButton;

    @FXML
    private Button bookBorrowButton;

    @FXML
    private Button homeScene1Button;

    @FXML
    private Button logOutButton;

    @FXML
    private Button reportButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button userManagementButton;

    @FXML
    void backButtonOnAction(ActionEvent event) {
        sceneChange("AdminHomeScene1.fxml", "AdminHomeScene1");
    }

    @FXML
    void homeScene1ButtononOnAction(ActionEvent event) {
        sceneChange("AdminHomeScene1.fxml", "AdminHomeScene1");
    }

    @FXML
    void bookBorrowButtonOnAction(ActionEvent event) {
        sceneChange("AdminBorrowBook.fxml", "AdminBorrowBook");
    }

    @FXML
    void reportButtonOnAction(ActionEvent event) {
        sceneChange("AdminReport.fxml", "AdminReport");
    }

    @FXML
    void logOutButtonOnAction(ActionEvent event) {
        sceneChange("welcomToWebsite.fxml", "welcomeToWebsite");
    }

    @FXML
    void searchTextFieldOnAction(ActionEvent event) {
    }

    // dang tinh la hien pane :))) nho co nhieu noi dung
    // ban dau se la tat ca cac report hien ve, muon tim nhanh thi search ten nguoi dung
}
