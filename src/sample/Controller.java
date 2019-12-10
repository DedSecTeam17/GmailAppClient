package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.models.Log;
import sample.models.UserInfo;
import sample.singletons.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public AnchorPane main_scene;
    public JFXButton login;
    public JFXPasswordField password;
    public JFXTextField gmail;


    private String passwordTxt;
    private String emailTxt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        login.setOnAction(event -> {

            if (!gmail.getText().isEmpty() && !password.getText().isEmpty()) {
                UserSession.getInstance().saveUserInfo(new UserInfo(gmail.getText().trim(), password.getText().trim()));
                if (UserSession.getInstance().weHaveAuser()) {
                    try {
                        RedirectUser(main_scene,"./send_email.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });


    }

    public void RedirectUser(Pane CurrentScene, String destinationScene) throws IOException {
        Parent secondRoot = FXMLLoader.load(getClass().getResource(destinationScene));
        Scene newScene = new Scene(secondRoot);
        Stage curStage = (Stage) CurrentScene.getScene().getWindow();
        curStage.setScene(newScene);
    }


}
