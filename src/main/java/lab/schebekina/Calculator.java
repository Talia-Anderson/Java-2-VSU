package lab.schebekina;
import java.util.*;
public class Calculator
{
    private final Map<String, Double> variables = new HashMap<>();
    /**проверяет приоритетность операторов
     * @param operator1 - алгебраический оператор
     * @param operator2 - алгебраический оператор
     * @return operator1 имеет более высокий приоритет чем operator2*/
    private boolean hasHigherPrecedence(String operator1, String operator2)
    {
        return (operator1.equals("*") || operator1.equals("/")) && (operator2.equals("+") || operator2.equals("-"));
    }

    /**применяет опертор для operand1 и operand2
     * @param operator - алгебраический оператор (передается мтрокой)
     * @param operand2 - применяемый
     * @param operand1 - изменяемый
     * @return результат выражения operand1(operator)operand2*/
    private double applyOperator(String operator, double operand2, double operand1)
    {
        switch (operator)
        {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0)
                {
                    throw new ArithmeticException("Division by zero");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    /**проверяет токен на число с плавающей точкой
     * @param token - пришедший токен
     * @return token является числом с плавающей точкой*/
    private boolean isNumeric(String token)
    {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    /**проверяет токен на принадлежность оператору
     * @param token - пришедший токен
     * @return token является оператором*/
    private boolean isOperator(String token)
    {
        return "+-*/".contains(token);
    }
    /**
     * является ли токен переменной
     * @param token - пришедший токен
     * @return token является переменной
     */
    private boolean isVariable(String token)
    {
        return token.matches("[a-zA-Z]+");
    }

    /**
     * считывает информацию от пользователя
     * @param variableName - название переменной
     * @return значение для variableName
     */
    private double getVariableValueFromUser(String variableName) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter value for variable " + variableName + ": ");
        return scanner.nextDouble();
    }

    /**
     * рассчет выражения.
     * Алгоритм:
     * Алгоритм начинается с разбиения входной строки на массив токенов с
     * помощью пробелов. Затем создаются два стека - values и operators,
     * которые инициализируются. Далее происходит обработка каждого токена в цикле:
     * если это число, то оно добавляется в стек values;
     * если это открывающая скобка, то она добавляется в стек operators;
     * если это закрывающая скобка, то выполняется операция внутри скобок и
     * результат заменяет значения в стеке values, а скобки удаляются из operators;
     * если это оператор, то выполняется соответствующая операция для значений в стеке values,
     * если он имеет высший приоритет.
     * Если токен - переменная, то проверяется, была ли она ранее введена,
     * и если нет, то вызывается функция getVariableValueFromUser
     * и переменная добавляется в variables.
     * Если переменная уже была введена, то ее количество увеличивается.
     * Если после выполнения всех операций в стеке operators остались элементы,
     * то выбрасывается исключение об ошибке введенного выражения.
     * @param expression - передаваемое выражение в виде строки ( <переменная1> <оператор1> <переменная2> )
     * @return результат выражения
     * @throws Exception - некорректность введенного выражения
     */
    public double calculateExpression(String expression) throws Exception {
        String[] tokens = expression.split("\\s+");
        Stack<Double> values = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (isNumeric(token)) {
                values.push(Double.parseDouble(token));
            }
            else if (token.equals("(")) {
                operators.push(token);
            }
            else if (token.equals(")")) {
                while (!operators.peek().equals("(")) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            }
            else if (isOperator(token)) {
                while (!operators.isEmpty() && hasHigherPrecedence(token, operators.peek())) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(token);
            }
            else if (isVariable(token)) {
                if (!variables.containsKey(token)) {
                    double value = getVariableValueFromUser(token);
                    variables.put(token, value);
                }
                values.push(variables.get(token));
            }
            else {
                throw new Exception("Invalid token: " + token);
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        if (values.size() != 1) {
            throw new Exception("Invalid expression");
        }

        return values.pop();
    }

}

