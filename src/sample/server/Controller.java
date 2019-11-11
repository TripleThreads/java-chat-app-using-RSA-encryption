package sample.server;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import sample.shared.Message;
import sample.shared.PublicKeys;
import sample.shared.RSA;
import sample.shared.ServerConnectionEvents;

import java.math.BigInteger;
import java.util.Base64;

public class Controller extends ServerConnectionEvents {

    private RSA rsa;

    private Server server;

    private PublicKeys clientPublicKeys;

    @FXML
    Text server_status;

    @FXML
    TextArea public_key;

    @FXML
    TextArea public_exponent;

    @FXML
    TextArea message_box;

    @FXML
    TextArea message_list;


    public Controller() {
        rsa = new RSA(2048);

        server = new Server(8686, this);
    }

    public void initialize() {
        public_key.setText(rsa.getModulus().toString());
        public_key.setWrapText(true);
        public_exponent.setText(rsa.getPublicKey().toString());
    }

    public void sendMessage() {

        Message encrypted = encrypt(rsa, message_box, clientPublicKeys);

        server.sendMessage(encrypted);

    }

    public void startServer() {

        server.setPublicKeys(getPublicKeys(rsa));

        server.start();

        server_status.setText("running on port " + server.port);
    }

    void onMessageArrived(Message message) {

        messageArrived(message, rsa, clientPublicKeys, message_list);
    }


    @Override
    public void onPublicKeyArrived(PublicKeys clientPublicKeys) {
        this.clientPublicKeys = clientPublicKeys;
    }
}


