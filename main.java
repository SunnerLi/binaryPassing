import java.net.*;
import java.io.*;

public class main {
  public static void main (String [] args ) throws IOException {
	 int filesize=10000000; //設定file size
	 long start = System.currentTimeMillis();
	 int bytesRead;
	 int current = 0;
    ServerSocket servsock = new ServerSocket(8059);
    
    //不斷監聽是否有新的client連線
    while (true) {
      System.out.println("Waiting...");
      
      //起始連線
      Socket sock = servsock.accept();
      System.out.println("Accepted connection : " + sock);
      
      //準備傳送資料
      byte [] mybytearray  = new byte [filesize];
      InputStream is = sock.getInputStream();
      FileOutputStream fos = new FileOutputStream("source-copy.mp3"); //將收到檔案存為source-copy.mp3
      BufferedOutputStream bos = new BufferedOutputStream(fos);
      bytesRead = is.read(mybytearray,0,mybytearray.length);
      current = bytesRead;
      
      //不斷讀入byte直到傳送完畢
      do {
         bytesRead =
            is.read(mybytearray, current, (mybytearray.length-current));
         System.out.println(bytesRead + " bytes is sent.");
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      bos.write(mybytearray, 0 , current);
      bos.flush();
      long end = System.currentTimeMillis();
      System.out.println(current);
      System.out.println(end-start);
      bos.close();
      sock.close();
      //開啟音樂檔並播放
      String cmd="rundll32 url.dll FileProtocolHandler source-copy.mp3"; 
      Process p = Runtime.getRuntime().exec(cmd);  
      }
    }
}
