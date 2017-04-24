package havaBol;

public class ActivationRecord {

	public SymbolTable symbolTable;
	public StorageManager storageManager;

	public ActivationRecord(SymbolTable symbolTable, StorageManager storageManager) {
		this.symbolTable = symbolTable;
		this.storageManager = storageManager;
	}
}
