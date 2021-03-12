package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TextArea textArea;
    @FXML
    public TextField textField;
    @FXML
    public HBox authPanel;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public HBox messagePanel;
    @FXML
    public ListView<String> clientList;

    private final String IP_ADDRESS = "localhost";
    private final int PORT = 8189;

    private Socket socket;
    DataInputStream in;
    DataOutputStream out;

    private boolean authenticated;
    private String nickName;
    private final String TITLE = "Setting Chat";
    private Stage stage;
    private Stage regStage;
    private RegController regController;

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
        authPanel.setVisible(!authenticated);
        authPanel.setManaged(!authenticated);
        messagePanel.setVisible(authenticated);
        messagePanel.setManaged(authenticated);
        clientList.setVisible(authenticated);
        clientList.setManaged(authenticated);

        if (!authenticated) {
            nickName = "";
        }
        setTitle(nickName);
    }

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        setAuthenticated(false);
        createRegWindow();
        Platform.runLater(() -> {
            stage = (Stage) textField.getScene().getWindow();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEve) {
                    if (socket != null && !socket.isClosed()) {
                        try {
                            out.writeUTF("/end");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        });
    }

    public void sendMessage (ActionEvent actionEvent) {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth (ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF(String.format("/auth %s %s", loginField.getText().trim().toLowerCase(),
                    passwordField.getText().trim()));
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(true) {
                            String string = in.readUTF();
                            if (string.startsWith("/authOK")) {
                                nickName = string.split(" ", 2)[1];
                                setAuthenticated(true);
                                break;
                            }

                            if (string.startsWith("regOK")) {
                                regController.addMsgToTextArea("Регистрация прошла успешно");
                            }

                            if (string.startsWith("regNO")) {
                                regController.addMsgToTextArea("Регистрация не получилась \n возможно логин или ник заняты");
                            }
                            textArea.appendText(string + "\n");
                        }

                        while (true) {
                            String string = in.readUTF();
                            if (string.startsWith("/")) {
                                if (string.equals("/end")) {
                                    break;
                                }
                                if (string.startsWith("/clientList")) {
                                    String [] token = string.split("\\s+");
                                    Platform.runLater(() -> {
                                        clientList.getItems().clear();
                                        for (int i = 1; i < token.length; i++) {
                                            clientList.getItems().add(token[i]);
                                        }
                                    });
                                }
                            } else {
                                textArea.appendText(string + "\n");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("Мы отключились от сервера");
                        setAuthenticated(false);
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTitle(String nick) {
        Platform.runLater(() -> {
            ((Stage) textField.getScene().getWindow()).setTitle(TITLE + " " + nick);
        });
    }

    public void clickClientList(MouseEvent mouseEvent) {
        String receiver = clientList.getSelectionModel().getSelectedItem();
        textField.setText("/w " + receiver + " ");
    }

    public void createRegWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reg.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            regStage = new Stage();
            regStage.setTitle("Reg window");
            regStage.setScene(new Scene(root, 400, 250));

            regController = fxmlLoader.getController();
            regController.setController(this);

            regStage.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registration(ActionEvent actionEvent) {
        regStage.show();
    }

    public void tryToReg(String login, String password, String nickName) {
        String message = String.format("/reg %s %s %s", login, password, nickName);

        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
