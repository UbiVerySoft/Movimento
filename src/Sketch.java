import processing.core.PApplet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sketch extends PApplet {
    DatagramSocket socket;
    DatagramPacket packet;

    float nose_x;
    float nose_y;
    float r_wrist_x;
    float r_wrist_y;
    float l_wrist_x;
    float l_wrist_y;

    Movimento m;

    byte[] buf = new byte[24]; //Set your buffer size as desired

    public static void main(String[] args) {
        PApplet.main("Sketch");
    }

    public void settings() {
        fullScreen();
    }

    public void setup() {
        m = new Movimento(this);
        background(189,183,107);
        try {
            socket = new DatagramSocket(4124); // Set your port here
        } catch (Exception e) {
            e.printStackTrace();
            println(e.getMessage());
        }
    }

    public void draw() {
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);

            nose_x = Float.intBitsToFloat( (buf[0]& 0xFF) ^ (buf[1]& 0xFF)<<8 ^ (buf[2]& 0xFF)<<16 ^ (buf[3]& 0xFF)<<24 );
            nose_y = Float.intBitsToFloat( (buf[4]& 0xFF) ^ (buf[5]& 0xFF)<<8 ^ (buf[6]& 0xFF)<<16 ^ (buf[7]& 0xFF)<<24 );
            r_wrist_x = Float.intBitsToFloat( (buf[8]& 0xFF) ^ (buf[9]& 0xFF)<<8 ^ (buf[10]& 0xFF)<<16 ^ (buf[11]& 0xFF)<<24 );
            r_wrist_y = Float.intBitsToFloat( (buf[12]& 0xFF) ^ (buf[13]& 0xFF)<<8 ^ (buf[14]& 0xFF)<<16 ^ (buf[15]& 0xFF)<<24 );
            l_wrist_x = Float.intBitsToFloat( (buf[16]& 0xFF) ^ (buf[17]& 0xFF)<<8 ^ (buf[18]& 0xFF)<<16 ^ (buf[19]& 0xFF)<<24 );
            l_wrist_y = Float.intBitsToFloat( (buf[20]& 0xFF) ^ (buf[21]& 0xFF)<<8 ^ (buf[22]& 0xFF)<<16 ^ (buf[23]& 0xFF)<<24 );

            println("DATA:");
            println(nose_x);
            println(nose_y);
            println(r_wrist_x);
            println(r_wrist_y);
            println(l_wrist_x);
            println(l_wrist_y);
            println("DATA END\n");

        } catch (IOException e) {
            e.printStackTrace();
            println(e.getMessage());
        }

        m.show(nose_x, nose_y);
    }

    public void mousePressed() {
        m.reset();
    }
}