package in.kyle.skype.skypebot2.commands.all;

import expr.Expr;
import expr.Parser;
import expr.SyntaxException;
import in.kyle.commands.Bind;
import in.kyle.commands.Command;

import java.math.BigDecimal;

/**
 * Created by Kyle on 12/13/2015.
 */
public class CCalc {
    
    @Command(description = "Calculate the value of a basic equation")
    public String calc(@Bind(name = "expression", infinite = true) String equation) {
        try {
            Expr expr = Parser.parse(equation);
            return equation + " = " + new BigDecimal(expr.value()).toPlainString();
        } catch (SyntaxException e) {
            return "Syntax error: " + e.explain();
        } catch (NumberFormatException e) {
            return "Error " + e.getMessage();
        } catch (Exception e) {
            return "Error " + e.getMessage();
        }
    }
}
