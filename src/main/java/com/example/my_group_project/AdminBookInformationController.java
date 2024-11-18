import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AdminBookInformationController extends BaseController {

    @FXML
    private Button backButton;

    @FXML
    private Button bookBorrowButton;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label categoryBookText;

    @FXML
    private Text contentBook;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Label goalBookText;

    @FXML
    private Button homeScene1Button;

    @FXML
    private Label limitBookText;

    @FXML
    private Button logOutButton;

    @FXML
    private Label nameAuthorText;

    @FXML
    private Label nameBookText;

    @FXML
    private Button reportButton;

    @FXML
    private Button saveButon;

    @FXML
    private ScrollPane scrollpane1;

    @FXML
    private ScrollPane scrollpane2;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button userManagementButton;

    @FXML
    private VBox vBox2;

    @FXML
    private VBox vbox1;

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

    // load thong tin cua sach
    // xoa sach khoi csdl
    // chinh sua thong tin sach
    // luu thong tin sach
    // load cao pane o muc ben duoi giup ntny

}
