package sample.shared;

import javafx.scene.control.TextArea;

import java.math.BigInteger;

/**
 * This class has shared methods of clients and server
 */

public abstract class ServerConnectionEvents {

    protected PublicKeys getPublicKeys(RSA rsa) {

        PublicKeys publicKeys = new PublicKeys();

        publicKeys.setPublicExponent(rsa.getPublicKey());

        publicKeys.setPublicModulo(rsa.getModulus());

        return publicKeys;
    }

    /**
     * accepts
     * : RSA class that has implementation of RSA
     * : TextArea that contains a message to be sent
     * : PublicKeys a receiver public keys
     * : returns encrypted Message
     * */
    protected Message encrypt(RSA rsa, TextArea message_box, PublicKeys publicKeys) {


        String text = message_box.getText();

        String encrypted = rsa.encrypt(text, publicKeys.getPublicExponent(), publicKeys.getPublicModulo());

        String signature = rsa.signature(text);

        System.out.println(signature);

        Message message = new Message();

        message.setDigitalSignature(signature);

        message.setEncryptedMessage(encrypted);

        return message;
    }
    /**
     * method that checks if digital signature is valid and displays the arrived on GUI
     * accepts Message a serialized class contains both signature and hashed message,
     * RSA class to verify signature,
     * PublicKeys a key to verify digital sign
     * TextArea to append message on
     * */
    protected void messageArrived(Message message, RSA rsa, PublicKeys serverPublicKey, TextArea message_list) {

        BigInteger decryptedMsg = rsa.decrypt(message.getEncryptedMessage());

        byte[] bytes = decryptedMsg.toByteArray();

        String msg = new String(bytes);

        System.out.println(msg);

        boolean verified = rsa.verifySignature(message.getDigitalSignature(),
                serverPublicKey.getPublicExponent(), serverPublicKey.getPublicModulo(), decryptedMsg);

        if (verified) {

            message_list.appendText(msg + "\n");

        } else {

            message_list.appendText("Unverified message \n");

        }
    }

    /**
     * this method notify subscriber that key is arrived
     * */
    public abstract void onPublicKeyArrived(PublicKeys publicKeys);
}
