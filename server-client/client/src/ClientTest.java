public class ClientTest {
    public static void main(String[] args){
        HttpClient.doGet("localhost", 8787, "/test.html");
    }
}