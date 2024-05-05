import java.io.*;
import java.net.*;

public class Client{
    private static int SERVER_PORT = 8080;

    private static int x,y;
    
    public static void main(String[] args) throws IOException{
        InetAddress addr = InetAddress.getByName("192.168.56.1");
        Socket socket = new Socket(addr,SERVER_PORT);
        try{
            System.out.println("socket = " + socket);
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(
                        socket.getInputStream()));//データ受信用バッファの設定
            PrintWriter out =
                new PrintWriter(
                    new BufferedWriter(
                        new OutputStreamWriter(
                            socket.getOutputStream())),true);//送信バッファ設定
            for(int i=0;i<5;i++){
                x = i*10+1;
                y = i*3 + 10;
                Thread.sleep(1000);//1秒ごとに送信ｎ
                
                out.println(i+"回目の送信：");
                //String str = in.readLine();
                out.println(x);
                //x = Integer.valueOf(in.readLine());
                out.println(y);
                //y = Integer.valueOf(in.readLine());
                System.out.println(i+"回目の送信　by client");
                String str;
                do{
                    str = in.readLine();
                    System.out.println(str);
                }while(str.equals("LOOPEND"));
            }
            out.println("END");
        } catch(InterruptedException e){
            //例外の時
            e.getStackTrace();
        } finally{
            System.out.println("closing...");
            socket.close();
        }
    }
}