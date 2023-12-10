import lab.schebekina.Calculator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TestCalculator
{

    @Test
    public void  testCorrectInput() throws Exception
    {
        Calculator calculator=new Calculator();
        String input = "( 5 + 2 ) * 3";
        double result = calculator.calculateExpression(input);
        double expectedResult = 21.0;
        Assert.assertEquals("Result should be equal 21",expectedResult,result,0.0001);
    }
    @Test
    public void testIncorrectInput()
    {
        Calculator calculator=new Calculator();
        String input = "(5+2)*3";
        try
        {
            calculator.calculateExpression(input);
        } catch (Exception e) {
            Assert.assertEquals("Wrong exception","Invalid token: (5+2)*3",e.getMessage());
        }
    }
}
