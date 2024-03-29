package test.java.easymock3.basics;

public class CalculatorOfIncome {
	// get the income by position
	// no CalcMethod implement method / class (maybe from other system)
	private CalcMethod calcMethod;
	private Position position;

	public void setCalcMethod(CalcMethod calcMethod) {
		this.calcMethod = calcMethod;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public double calculate() {
		if (calcMethod == null) {
			throw new CalcMethodException("CalcMethod not yet maintained");
		}
		if (position == null) {
			throw new PositionException("Position not yet maintained");
		}
		return calcMethod.calc(position);
	}
}
