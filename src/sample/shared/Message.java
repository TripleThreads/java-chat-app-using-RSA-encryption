package sample.shared;

import java.io.Serializable;

public class Message implements Serializable {

    private String encryptedMessage;

    private String digitalSignature;


    String getEncryptedMessage() {
        return encryptedMessage;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    void setEncryptedMessage(String encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }

    void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    @Override
    public String toString() {
        System.out.println( "********** message **********");
        System.out.println("enc msg " + getEncryptedMessage());
        System.out.println("dig sign " + getDigitalSignature());
        return super.toString();
    }
}
