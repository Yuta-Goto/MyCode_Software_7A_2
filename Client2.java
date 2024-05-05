import java.io.*;
import java.net.*;

public class Client2{
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

            //ログインしたときの1度きりのデータを送信

            //ログイン時の1℃きりの受信

            while(true){
                Thread.sleep(20);//50分の1秒ごとに処理を行う。適宜値は変更する

                //フロントエンドから今のデータを持ってくる

                //ログアウト時にwhileを抜ける処理

                
                
                //送信
                out.println(1+"回目の送信：");
                
                out.println(x);
                
                out.println(y);
                
                
                
               
                //受信
                String str = in.readLine();
                x = Integer.valueOf(in.readLine());
                y = Integer.valueOf(in.readLine());
                str = in.readLine();
                //フロントエンドに、受信した全プレイヤーのデータを渡す
                System.out.println(str);
            }
            //ログアウト時の適切な送信
            out.println("END");
            //ログアウト時の適切な受信

            
        } catch(InterruptedException e){
            //例外の時
            e.getStackTrace();
        } finally{
            System.out.println("closing...");
            socket.close();
        }
    }
}