package toby.user.domain;

public enum Level {
	GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER); 
	
	private final int value;
	private final Level nextLevel;
	
	Level(int value, Level nextGrade){
		this.value = value;
		this.nextLevel = nextGrade;
	}
	
	public int intValue() {
		return this.value;
	}
	
	public Level nextLevel() {
		return this.nextLevel;
	}
	
	public static Level valueOf(int value) {
		switch (value) {
		case 1:
			return BASIC;
		case 2:
			return Level.SILVER;
		case 3:
			return Level.GOLD;
		default:
			throw new AssertionError("Unknown value: " + value);
		}
	}
}
