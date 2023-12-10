package lab.schebekina;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Calculator calculator = new Calculator();
        System.out.println("Expression: ( x / y ) * z ");
        System.out.println(calculator.calculateExpression("( x / y ) * z"));
    }
}
