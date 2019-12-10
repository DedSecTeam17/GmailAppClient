package sample.models;

public class ChatMessage {

    private String senderPk;
    private String receiverPk;
    private String encryptedMessage;


    public ChatMessage(String senderPk, String receiverPk, String encryptedMessage) {
        this.senderPk = senderPk;
        this.receiverPk = receiverPk;
        this.encryptedMessage = encryptedMessage;
    }


    public String getSenderPk() {
        return senderPk;
    }

    public void setSenderPk(String senderPk) {
        this.senderPk = senderPk;
    }

    public String getReceiverPk() {
        return receiverPk;
    }

    public void setReceiverPk(String receiverPk) {
        this.receiverPk = receiverPk;
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }

    public void setEncryptedMessage(String encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }
}
