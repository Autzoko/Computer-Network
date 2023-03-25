import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpClient {
    //Using GET to visit server
    public static void doGet(String host, int port, String url){
        Socket socket = null;
        try{
            socket = new Socket(host, port);
            StringBuffer sb = new StringBuffer("GET " + url + " HTTP/1.1\r\n");

            //Construct header
            sb.append("Accept */*\r\n");
            sb.append("Accept-Language: zh-cn\r\n");
            sb.append("Accept-Encoding: gzip, deflate\r\n");
            sb.append("User-Agent: HTTPClient\r\n");
            sb.append("Host: localhost:8787\r\n");
            sb.append("Connection: Keep-Alive\r\n");

            //Send HTTP request
            OutputStream socketOut = socket.getOutputStream();
            socketOut.write(sb.toString().getBytes());
            Thread.sleep(2000);
            InputStream socketIn = socket.getInputStream();
            int size = socketIn.available();
            byte[] b = new byte[size];
            socketIn.read(b);

            System.out.println(new String(b));
        }catch(IOException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            try{
                socket.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
