import java.io.*;
import java.net.*;
//テストブランチテスト

import javax.xml.crypto.Data;

class DataHolder{
    public static int[] players_x = new int[100];
    public static int player_num = 0;
}

public class MultiClientServer{
    private static int SERVER_PORT = 8080;
    public static int[] players_x = new int[100];
    public static int player_num = 0;
 
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("サーバ起動：serverSocket is "+serverSocket);

        try{
            while(true){//新しいクライアントが来るたびにループが1周する
                Socket socket = serverSocket.accept();//新しいクライアントが来るまでここで待機
                new ClientDealer(socket).start();//来たら、新しく並列処理開始。
            }
        } finally{
            serverSocket.close();//サーバのソケットを閉じる
        }
    }
}

class ClientDealer extends Thread{
    private Socket socket;

    public ClientDealer(Socket socket){
        this.socket = socket;
    }

    private int my_max(int a,int b){
        if(a>b) return a;
        return b;
    }

    public void run(){
        try{
            //送受信設定
            BufferedReader in = 
                    new BufferedReader(
                        new InputStreamReader(
                            socket.getInputStream()));//データ受信用バッファの設定
            PrintWriter out = 
                    new PrintWriter(
                        new BufferedWriter(
                            new OutputStreamWriter(
                                socket.getOutputStream())),true);//送信バッファの設定    
            String threadName = Thread.currentThread().getName();
            int thread_num = (threadName.charAt(threadName.length()-1)-'0');

            DataHolder.player_num = my_max(DataHolder.player_num, thread_num+1);
            //実際の送受信
            while(true){
                Thread.sleep(1000);//1秒ごとに送信ｎ
                System.out.println(thread_num);
                String str = in.readLine();
                //out.println(str);
                
                if(str.equals("END")) break;
                
                int x = Integer.valueOf(in.readLine());
                //out.println(x);
                DataHolder.players_x[thread_num] = x;
                int y = Integer.valueOf(in.readLine());
                //out.println(y);

                for(int i=0;i<DataHolder.player_num;i++){
                    System.out.println(str + "クライアント" + threadName + "　座標： (" + x + "," + y+ ")");
                    out.println(str + " from SERVER!" + "　座標： (" + DataHolder.players_x[i] + "," + y+ ")");
                }
                out.println("LOOPEND");
            }
        
        } catch(IOException e){
            e.printStackTrace();
        } catch(InterruptedException e){
            e.printStackTrace();
        } finally {//(例外の発生有無に寄らず実行される)
                System.out.println("closing...");
                try{
                    socket.close();//抜けたクライアントに関するソケットを閉じる
                }catch(IOException e){
                    e.printStackTrace();
                }
               
        }
    }
}