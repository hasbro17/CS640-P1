import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class IperfClient{
	Socket socket;
	DataOutputStream os;
	int dataSent;//KB

	public IperfClient(String hostname, int portNumber)throws UnknownHostException, IOException{
		socket=new Socket(hostname,portNumber);
		os=new DataOutputStream(socket.getOutputStream());
	}

	//returns bytes of data sent
	public int sendTimedData(double time) throws IOException{
		long totalTime=(long)time * 1000000000;//ns
		boolean toFinish=false;
		byte[] packet= new byte[1000];//1000byte packet
		long startTime=System.nanoTime();

		//run for approximately t seconds
		dataSent=0;//data send
		while(!toFinish)
		{
			os.write(packet,0,packet.length);
			toFinish = (System.nanoTime() - startTime >= totalTime);
			dataSent++;
		}
		//cleanup
		os.close();
		socket.close();

		double rate= (dataSent*Math.pow(10,-3)*8) / time;//Mbps(Mega bits per second)
		System.out.println("sent="+dataSent+" KB"+" rate="+rate+" Mbps");
		return dataSent;
	}



}
