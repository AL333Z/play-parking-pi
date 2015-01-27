package msglib;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MsgServiceUDPMulticast implements IMsgService {

	private MulticastSocket ms;
	private InetAddress group;
	private byte[] buf;
	private String owner;
	private static final String SEPARATOR = "" + '\31';
	private static final String ANY = "*";
	// private static final String DEFAULT_MULTICAST_ADDR = "224.111.112.113";
	private static final String DEFAULT_MULTICAST_ADDR = "224.0.0.1";
	private String udpAddress;

	/**
	 * Install the message service on the default address.
	 * 
	 * @throws Exception
	 */
	public MsgServiceUDPMulticast() throws Exception {
		udpAddress = DEFAULT_MULTICAST_ADDR;
	}

	/**
	 * Install a message service on the specified IP/UDP multicast address.
	 * 
	 * @param udpMulticastNet
	 *            es: "224.0.0.1"
	 * @throws Exception
	 */
	public MsgServiceUDPMulticast(String udpMulticastNet) throws Exception {
		this.udpAddress = udpMulticastNet;
	}

	/**
	 * Join the message service with the specified name
	 * 
	 * @param owner
	 *            name of the participant
	 * @param udpMulticastNet
	 *            udp multicast address
	 */
	public void init(String owner) throws Exception {
		ms = new MulticastSocket(20001);
		group = InetAddress.getByName(udpAddress);
		ms.setReuseAddress(true);
		ms.setLoopbackMode(false);
		ms.setTimeToLive(2);
		ms.joinGroup(group);
		buf = new byte[1000];
		this.owner = owner;
	}

	/**
	 * Send a message
	 * 
	 * @param msg
	 *            message to send
	 */
	public void sendMsg(String msg) throws Exception {
		String msg1 = owner + SEPARATOR + ANY + SEPARATOR + msg;
		DatagramPacket dp = new DatagramPacket(msg1.getBytes(), msg1.length(),
				group, 20001);
		ms.send(dp);
	}

	/**
	 * Send a message to a specified agent
	 * 
	 * @param dest
	 *            target of the message
	 * @param msg
	 *            message to send
	 */
	public void sendMsgTo(String dest, String msg) throws Exception {
		String msg1 = owner + SEPARATOR + dest + SEPARATOR + msg;
		DatagramPacket dp = new DatagramPacket(msg1.getBytes(), msg1.length(),
				group, 20001);
		ms.send(dp);
	}

	/**
	 * Receive a message - blocking.
	 */
	public Msg receiveMsg() throws Exception {
		while (true) {
			DatagramPacket recv = new DatagramPacket(buf, buf.length);
			ms.receive(recv);
			String content = new String(recv.getData(), 0, recv.getLength());
			// System.out.println(">> "+content);
			String[] parts = content.split(SEPARATOR);
			String sender = parts[0];
			String receiver = parts[1];
			String msg = parts[2];

			// System.out.println("> "+sender+" received from "+who);
			if (!sender.equals(owner)) {
				if (receiver.equals(ANY) || receiver.equals(owner)) {
					return new Msg(sender, receiver, msg);
				}
			}
		}
	}

	/**
	 * Receive a message satisrfying the specified pattern.
	 * 
	 * @param pattern
	 *            message pattern
	 */
	public Msg receiveMsg(MsgPattern pattern) throws Exception {
		while (true) {

			DatagramPacket recv = new DatagramPacket(buf, buf.length);
			ms.receive(recv);
			String content = new String(recv.getData(), 0, recv.getLength());
			// System.out.println(">> " + content);
			String[] parts = content.split(SEPARATOR);
			String sender = parts[0];
			String receiver = parts[1];
			String msg = parts[2];

			Msg m = new Msg(sender, receiver, msg);

			// System.out.println("> " + msg + " received from " + sender
			// + " receiver " + receiver + " owner " + owner);
			if (!sender.equals(owner)) {
				if ((receiver.equals(ANY) || receiver.equals(owner))
						&& pattern.match(m)) {
					return m;
				}
			}
		}
	}
}