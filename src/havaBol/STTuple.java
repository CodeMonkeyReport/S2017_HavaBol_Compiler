package havaBol;

import java.util.ArrayList;
import java.util.HashMap;

public class STTuple extends STEntry implements Cloneable {

	HashMap<String, STEntry> memberHash = new HashMap<String, STEntry>();
	String type;

	public STTuple(String symbol, int primClassif, int subClassif, int structureType, int environmentVector) {
		super(symbol, primClassif, subClassif, environmentVector);
		this.structureType = structureType;
		this.type = symbol;
	}

	public void addMember(STEntry member) {
		memberHash.put(member.symbol, member);
	}

	public STEntry getMember(String key) {
		return memberHash.get(key);
	}

	public boolean checkMember(STEntry member) {
		return (memberHash.get(member.symbol) != null);
	}

	public STTuple Clone() {
		STTuple newTuple = new STTuple(this.type, this.primClassif, this.subClassif, this.structureType,
				this.environmentVector);
		newTuple.memberHash = (HashMap<String, STEntry>) this.memberHash.clone();
		newTuple.declaredSize = this.declaredSize;
		newTuple.symbol = this.symbol;
		return newTuple;
	}
}
