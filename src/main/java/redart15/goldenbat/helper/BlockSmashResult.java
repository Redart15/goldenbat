package redart15.goldenbat.helper;

import net.minecraft.core.util.collection.NamespaceID;

public class BlockSmashResult {
	protected int BlockID;
	protected NamespaceID namespaceID;
	protected int amount;
	protected int metadata;

	private BlockSmashResult(int BlockID, NamespaceID namespaceID, int amount, int metadata){
		assert(BlockID > 0 && namespaceID != null && amount > 0);
		this.BlockID = BlockID;
		this.namespaceID = namespaceID;
		this.amount	= amount;
		this.metadata = metadata;
	}

	public int getBlockID() {
		return BlockID;
	}

	public NamespaceID getNamespaceID() {
		return namespaceID;
	}

	public int getAmount() {
		return amount;
	}

	public int getMetadata() {
		return metadata;
	}

	public static class BlockSmashResultBuilder {
		private int BlockID = 0;
		private NamespaceID namespaceID = null;
		private int amount = 0;      // default amount
		private int metadata = 0;    // default metadata

		public BlockSmashResult build(){
			return new BlockSmashResult(BlockID, namespaceID, amount, metadata);
		}

		public BlockSmashResult.BlockSmashResultBuilder setBlockID(int id) { this.BlockID = id; return this; }
		public BlockSmashResult.BlockSmashResultBuilder setnamespaceID(NamespaceID id)  {this.namespaceID = id;return this;}
		public BlockSmashResult.BlockSmashResultBuilder setAmount(int amt) { this.amount = amt; return this; }
		public BlockSmashResult.BlockSmashResultBuilder setMetadata(int md) { this.metadata = md; return this; }
	}
}
