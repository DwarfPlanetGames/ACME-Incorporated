package tk.dpgames.acme.inc.game;

public class Inventory {
	public ItemSlot[] slots = new ItemSlot[(int)Values.inventorySize.i];
	
	public class ItemSlot {
		public Item item;
		public int value;
		public int location;
		public ItemSlot(Item item, int value, int loc) {
			this.item = item;
			this.value = value;
			this.location = loc;
		}
		public void add(int v) {
			value += v;
			if (value <= 0) {
				slots[location] = null;
			}
		}
	}
	
	public boolean add(Item item, int v) {
		for (int i = 0; i < slots.length;i++) {
			if (slots[i] == null) {
				slots[i] = new ItemSlot(item, v, i);
				return true;
			} else if (slots[i].equals(item) && slots[i].item.stackable) {
				slots[i].add(v);
				return true;
			}
		}
		return false;
	}
}
