import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AdminReportOneUserController extends BaseController{

    @FXML
    private Button backButton;

    @FXML
    private Button bookBorrowButton;

    @FXML
    private Text contentText;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button homeScene1Button;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField noteTextField;

    @FXML
    private Button reportButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private CheckBox statusCheckBox;

    @FXML
    private Label userEmailTextField;

    @FXML
    private Label userFullNameTextField;

    @FXML
    private Label userIdTextField;

    @FXML
    private Button userManagementButton;

    @FXML
    private Label userNameTextField;

    @FXML
    private Label userPhoneNumberTextField;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private VBox vBox;

    @FXML
    private ImageView userImage;

    @FXML
    void backButtonOnAction(ActionEvent event) {
        sceneChange("AdminHomeScene1.fxml", "AdminHomeScene1")
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

    //load thong tin dung theo report ( lay tu email tai trang MoreInformation cua nguoi dung)
    //tick trang thai sau khi su ly thong tin
    //note ghi chu

}
