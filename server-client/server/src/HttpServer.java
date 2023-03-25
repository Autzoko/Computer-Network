import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static final int _DEFAULT_PORT_ = 8787;

    public static void start() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(_DEFAULT_PORT_);
            System.out.println("Server monitoring port: " + serverSocket.getLocalPort());
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("Linked to client by TCP connection, client address: " + socket.getInetAddress()
                        + ":" + socket.getPort());
                service(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void service(Socket socket) throws Exception {
        InputStream socketIn = socket.getInputStream();
        Thread.sleep(500);
        int size = socketIn.available();
        byte[] b = new byte[size];
        socketIn.read(b);
        String request = new String(b);
        System.out.println(request);

        // Parsing request method, url, protocol, get url
        String typeUrlHttp = request.substring(0, request.indexOf("\r\n"));
        String url = typeUrlHttp.split(" ")[1];

        // Parsing response head
        String contentType;
        if (url.indexOf("html") != -1 || url.indexOf("htm") != -1) {
            contentType = "text/html";
        } else if (url.indexOf("jpg") != -1 || url.indexOf("jpeg") != -1) {
            contentType = "image/jpeg";
        } else if (url.indexOf("gif") != -1) {
            contentType = "image/gif";
        } else {
            contentType = "application/octet-stream";
        }

        // Create HTTP response result
        // Create response protocol, state
        String httpStatus = "HTTP/1.1 200 OK\r\n";
        // Create head
        String responseHeader = "Content-Type:" + contentType + "\r\n\r\n";
        InputStream in = HttpServer.class.getResourceAsStream("/webRoot" + url);
        OutputStream socketOut = socket.getOutputStream();

        // Send response protocol, status code, header and content
        socketOut.write(httpStatus.getBytes());
        socketOut.write(responseHeader.getBytes());
        int len = 0;
        b = new byte[1024];
        while ((len = in.read(b)) != -1) {
            socketOut.write(b, 0, len);
        }
        Thread.sleep(1000);

    }
}