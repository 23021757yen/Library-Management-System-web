import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminUserManagementController extends BaseController {

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
    private Button addButton;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private VBox vBox;

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
     // search ra nguoi dung
    // add them nguoi dung tai day tuong tu add sach (xem lai o homescene1 co note)

}
