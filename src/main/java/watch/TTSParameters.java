package watch;

public class TTSParameters {
    public final String ak = "4279dab51f634bc5a4635133f2a8d71e";
    public final String uid = "A83BBE5FBBB8AAD69BB7EC665978F5BE";
    public String text;
    public final int tone = 21;
    public final int tts = 3;
    public final int speed = 4;
    public String token = "";

    public TTSParameters(String text){
        this.text = text;
    }
}