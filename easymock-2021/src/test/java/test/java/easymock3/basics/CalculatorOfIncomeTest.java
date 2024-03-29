package test.java.easymock3.basics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CalculatorOfIncomeTest {

  private CalcMethod method; // it is a interface and no implementor, so mock it!!!
  private CalculatorOfIncome calculator;

  @Before
  public void setUp() {
    method = EasyMock.createMock(CalcMethod.class); // mock CalcMethod implementing class!!!
    calculator = new CalculatorOfIncome();
  }

  // test as there is exception
  @Test(expected = CalcMethodException.class)
  public void _1_no_calculator() {
    calculator.setPosition(Position.SURFER);
    calculator.calculate();
  }

  // test as there is exception
  @Test(expected = PositionException.class)
  public void _2_no_position() {
    EasyMock.expect(method.calc(Position.BOSS)).andReturn(70000.0);
    EasyMock.replay(method);
    calculator.setCalcMethod(method);
    calculator.calculate();
  }

  @Test
  public void _3_calculate() {
    // Setup mockup
    // Setting up the expected value of the method call calc
    // if boss. then return 70000, use 2 times
    EasyMock.expect(method.calc(Position.BOSS)).andReturn(70000.0).times(3);
    EasyMock.expect(method.calc(Position.PROGRAMMER)).andReturn(50000.0);
    EasyMock.replay(method); // Setup is finished need to activate the mock

    calculator.setCalcMethod(method);
    try {
      calculator.calculate();
      fail("Exception did not occur"); // should be failed
    } catch (PositionException e) {
      // e.printStackTrace();
    }
    // start the real logic
    calculator.setPosition(Position.BOSS);
    // calculator.calculate() return 70000
    System.out.println(calculator.calculate());
    assertEquals(70000.0, calculator.calculate(), 0);
    assertEquals(70000.0, calculator.calculate(), 0);

    calculator.setPosition(Position.PROGRAMMER);
    assertEquals(50000.0, calculator.calculate(), 0);

    calculator.setPosition(Position.SURFER);
    // to check mockup all method is executed or not
    // 3 times boss, 1 time programmer
    EasyMock.verify(method);
  }

  @Test(expected = PositionException.class)
  public void _4_calculate() {
    // Setting up the expected value of the method call calc
    EasyMock.expect(method.calc(Position.SURFER))
        .andThrow(new PositionException("Mock exception here!!! Do not care if position exists"))
        .times(1);

    // Setup is finished need to activate the mock
    EasyMock.replay(method);
    calculator.setPosition(Position.SURFER);
    calculator.setCalcMethod(method);

    // even the position exist, but throw excepted because of mock
    calculator.calculate();
  }
}
