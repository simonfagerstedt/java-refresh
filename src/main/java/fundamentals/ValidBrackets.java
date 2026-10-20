package fundamentals;

import java.util.*;
public class ValidBrackets {
    static boolean areBracketsValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            switch (c) {
                case '(' , '[' , '{' -> stack.push(c);
                case ')' , ']' , '}' -> {
                    if (stack.isEmpty()) {
                        return false;
                    }
                    char lastLeftBracket = stack.pop();
                    char expected = switch (c) {
                        case ')' -> '(';
                        case ']' -> '[';
                        default  -> '{';
                    };
                    if (expected != lastLeftBracket) {
                        return false;
                    }
                }
                default -> {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
    public static void main(String[] args) {
        System.out.println(areBracketsValid("()"));         //true
        System.out.println(areBracketsValid("()[]{}"));     //true    side by side is fine
        System.out.println(areBracketsValid("{[()()]}"));   //true    nested is fine
        System.out.println(areBracketsValid("(]"));         //false   wrong type
        System.out.println(areBracketsValid("([)]"));       //false   crossed: [ is still open when ) arrives
        System.out.println(areBracketsValid("(("));         //false   never closed
        System.out.println(areBracketsValid("))"));         //false   closed without opening
        System.out.println(areBracketsValid(""));           //true    nothing to balance
        System.out.println(areBracketsValid("((abc))"));    //false   invalid chars
    }
}