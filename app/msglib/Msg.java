package msglib;

public class Msg {

	private String sender;
	private String receiver;
	private String content;
	
	public Msg(String sender, String receiver, String content){
		this.sender = sender;
		this.content = content;
		this.receiver = receiver;
	}

	public Msg(String sender, String content){
		this.sender = sender;
		this.content = content;
	}
	
	public String getSender(){
		return sender;
	}
	
	public String getContent(){
		return content;
	}
	
	public String getReceiver(){
		return receiver;
	}
}
