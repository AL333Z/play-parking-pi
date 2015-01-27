package msglib;

public class MsgToMePattern implements MsgPattern {

	private String name;

	public MsgToMePattern(String name) {
		this.name = name;
	}

	public boolean match(Msg m) {
		// System.out.println("MATCH "+m.getReceiver()+" --> me: "+name);
		return name.equals(m.getReceiver());
	}

}
