package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.singletons.ChatProvider;
import sample.singletons.UserSession;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root;

        if ( UserSession.getInstance().weHaveAuser()) {
                  root = FXMLLoader.load(getClass().getResource("./send_email.fxml"));

        }else {
            root   = FXMLLoader.load(getClass().getResource("./sample.fxml"));

        }

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 747, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
