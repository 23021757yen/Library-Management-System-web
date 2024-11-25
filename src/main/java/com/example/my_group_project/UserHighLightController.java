package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class UserHighLightController extends UserMenuController {
    @FXML
    private VBox vBoxBooks;

    @FXML
    void initialize(){
        try {
            highlightBooksHere(User.getCurrentUser().getSavedBooks());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //hien danh sach book da ghim tren phan highlight books
    void highlightBooksHere(List<Book> highlightBooks) throws IOException {
        if(highlightBooks.isEmpty()){
            Text text = new Text("No book high light!");
            vBoxBooks.getChildren().add(text);
            return;
        }

        for(int i = 0; i < highlightBooks.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookProfileHighLight.fxml"));
            Pane newBook = fxmlLoader.load();
            Book book = highlightBooks.get(i);
            ImageView image = (ImageView) newBook.lookup("#bookImageView");
            Label name = (Label) newBook.lookup("#nameOfBookTextField");
            Text author = (Text) newBook.lookup("#authorTextField");
            ScrollPane scrollPane = (ScrollPane) newBook.lookup("#scrollPane");
            Text description = null;

            if (scrollPane != null) {
                Node content = scrollPane.getContent(); // Lấy nội dung của ScrollPane
                if (content instanceof Text) {
                    description = (Text) content;
                } else {
                    System.out.println("Content is not a Text node: " + content);
                }
            }

            if (description != null) {
                description.setText(book.getDescription());
            } else {
                System.out.println("Description (Text) not found!");
            }

            //description.setText("Heloo chuy ne may cung!");
            //System.out.println();
            Button button = (Button) newBook.lookup("#highlightButton");

            image.setImage(new Image(book.getImageUrl()));
            name.setText(book.getTitle());
            author.setText(book.getAuthors());




            button.setOnMouseClicked(mouseEvent -> {
                //Code chỗn này
                System.out.println("He lo me ne con !!!");
                if(BaseController.showAlter("Hoi lai", "Ban co muon huy luu sach khong")){
                    User.getCurrentUser().removeBook(book);
                    UserHomeController.showIntro("Ban da huy luu sach thanh cong!", BaseController.getMainStage());
                }else {
                    mouseEvent.consume();
                }

            });

            image.setOnMouseClicked(mouseEvent -> {
                try {
                    //Code chỗn này
                    UserHomeController homeController = new UserHomeController();
                    SoundManager.playSound("src/main/resources/soundEffects/SEFE_BellTransition.wav");
                    homeController.bookProfile(image, book);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            vBoxBooks.getChildren().add(newBook);
        }
    }
}
