import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;


public class IperfServer{
	ServerSocket serverSocket;
	Socket clientSocket;
	DataInputStream is;
	int data;

	public IperfServer(int portNumber)throws IOException{
		serverSocket=new ServerSocket(portNumber);
	}

	//returns bytes of data sent
	public int acceptAndRead() throws IOException{
		
		long startTime;
		byte[] readBuff= new byte[1000];
		clientSocket=serverSocket.accept();
		is=new DataInputStream(clientSocket.getInputStream());

		data=0;
		startTime= System.nanoTime();//ns
		while( is.read(readBuff,0,readBuff.length)!=-1 )
			data++;
		double totalTime=System.nanoTime()-startTime;
		totalTime=totalTime*Math.pow(10,-9);//in seconds 

		double rate=  (data*Math.pow(10,-3)*8) / totalTime;//KBps
		System.out.println("received="+data+" KB"+" rate="+rate+" Mbps");
		return data;
	}


}
