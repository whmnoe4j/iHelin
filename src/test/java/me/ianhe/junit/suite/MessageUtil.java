package me.ianhe.junit.suite;

/**
 * @author iHelin
 * @create 2017-02-04 17:42
 */
public class MessageUtil {

    private String message;

    //Constructor
    //@param message to be printed
    public MessageUtil(String message){
        this.message = message;
    }

    // prints the message
    public String printMessage(){
        System.out.println(message);
        return message;
    }

}
