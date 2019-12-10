package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.nio.sctp.Notification;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.models.Log;
import sample.singletons.MailProvider;
import sample.singletons.UserSession;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SendEmail implements Initializable {


    public JFXButton send_email;
    public JFXTextField r_email;
    public JFXTextField r_subject;
    public JFXTextField r_body;
    public JFXRadioButton ssl;
    public JFXRadioButton tls;
    public JFXButton log_out;
    public AnchorPane send_mail_scene;
    public JFXProgressBar progress;
    private ToggleGroup toggleGroup;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        toggleGroup = new ToggleGroup();
        ssl.setToggleGroup(toggleGroup);
        ssl.setSelected(true);
        tls.setToggleGroup(toggleGroup);
        tls.setSelected(false);

        send_email.setOnAction(event -> {
            send_email.setText("Sending..");
            progress.setVisible(true);
            send_email.setDisable(true);

            if (ssl.isSelected()) {

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        boolean sent = MailProvider.getInstance().sendMailUsingSsl(r_email.getText().trim(), r_subject.getText().trim(), r_body.getText().trim());

                        if (sent) {
                            showAlert();

                        }
                        this.stop();
                    }
                }
                        .start();

            } else if (tls.isSelected()) {


                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        boolean sent = MailProvider.getInstance().sendMailUsingTransportLayer(r_email.getText().trim(), r_subject.getText().trim(), r_body.getText().trim());
                        if (sent) {
                            showAlert();

                        }

                        this.stop();
                    }
                }
                        .start();
            }
        });


        log_out.setOnAction(event -> {
            UserSession.getInstance().destroyUserInfo();
            try {
                RedirectUser(send_mail_scene, "./sample.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    private void showAlert() {
        progress.setVisible(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                r_body.setText("");
                r_email.setText("");
                r_subject.setText("");
                send_email.setText("Send Email");
                TrayNotification tray = new TrayNotification("Alert", "Email sent successfully", NotificationType.SUCCESS);
                tray.showAndWait();
                send_email.setDisable(false);


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
