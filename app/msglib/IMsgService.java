package msglib;


/**
 * Interface for simple messaging services.
 * 
 * @author aricci
 *
 */
public interface IMsgService {

	/**
	 * Join the message service with the specified name
	 * 
	 * @param owner name of the participant
	 */
	void init(String name) throws Exception;
	
	
	/**
	 * Send a message
	 * 
	 * @param msg message to send
	 */
	void sendMsg(String msg) throws Exception;

	/**
	 * Send a message to a specified agent
	 * 
	 * @param dest target of the message
	 * @param msg message to send
	 */
	void sendMsgTo(String dest, String msg) throws Exception;
	
	/**
	 * Receive a message.
	 * 
	 * Blocking behaviour.
	 * 
	 * @return message received
	 * @throws Exception
	 */
	Msg receiveMsg() throws Exception;
	
	/**
	 * Receive a message satisfying a specified message pattern.
	 * 
	 * @param pattern message pattern.
	 * 
	 * @return the message.
	 * @throws Exception
	 */
	Msg receiveMsg(MsgPattern pattern) throws Exception;
	
}