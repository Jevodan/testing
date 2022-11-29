import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя  числами
 */

public class Test {

    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        Boolean classic;        // определяет систему счисления
        String result;     // итоговый результат
        String symbol;  //оператор
        String[] symb = new String[]{"*", "/", "+", "-"}; // массив операторов последовательности вычисления, сначала */ , затем по порядку выражения +-
        System.out.print("Калькулятор на связи: ");
        String line = scan.nextLine();
        line = getClear(line);
        ArrayList elements = new ArrayList(Arrays.asList(line.split("[*/+-]"))); // массив операндов заданного выражения
        if (elements.size() != 2) {
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор");
        }
        try {
            checkSystem(line);
        } catch (Exception e) {
            System.out.println(e + "используются одновременно разные системы счисления");
            System.exit(0);
        }
        classic = getRomeOrClassic(line);
        symbol = getSymbol(line, symb);
        if (elements.size() < 1 || symbol.equals(""))
            throw new Exception("строка не является математической операцией");
        if (!classic)
            romeToClassic(elements);

        result = String.valueOf(getCalculation(symbol, elements));
        if (!classic) {
            try {
                result = classicToRome(Integer.valueOf(result));
            } catch (Exception e) {
                System.out.println("В римской системе нет отрицательных чисел");
                System.exit(0);
            }
        }

        System.out.println("Результат: " + result);
    }


    private static String getSymbol(String line, String[] symb) throws Exception {

        String symbol = "";
        for (String sym : symb) {
            if (line.indexOf(sym) > 0) {
                if (symbol!="")
                    throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор");
                symbol = sym;
                if (line.indexOf(sym) != line.lastIndexOf(sym) && line.lastIndexOf(sym) != -1)
                    throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор");
            }
        }
        return symbol;
    }

    /**
     * Проверка на систему исчисления
     *
     * @param line
     * @throws Exception
     */
    private static void checkSystem(String line) throws Exception {
        Integer[] sum = new Integer[]{0, 0};
        for (char el : line.toCharArray()) {
            if (Character.getNumericValue(el) == 33 ||
                    Character.getNumericValue(el) == 31 ||
                    Character.getNumericValue(el) == 18) {
                sum[0] = 1;
            }
            if (Character.getNumericValue(el) <= 10 && Character.getNumericValue(el) > 0) {
                sum[1] = 1;
            }
        }
        if (sum[0] > 0 && sum[1] > 0)
            throw new Exception("");
    }

    /**
     * Получение результата в римской системе*
     *
     * @param res - результат в классической системе
     */
    private static String classicToRome(Integer res) throws Exception {
        String letter = "";
        if (res > 0) {

            switch (res / 10) {
                case (1):
                    letter += "X";
                    break;
                case (2):
                    letter += "XX";
                    break;
                case (3):
                    letter += "XXX";
                    break;
                case (4):
                    letter += "XL";
                    break;
                case (5):
                    letter += "L";
                    break;
                case (6):
                    letter += "LX";
                    break;
                case (7):
                    letter += "LXX";
                    break;
                case (8):
                    letter += "LXXX";
                    break;
                case (9):
                    letter += "XC";
                    break;
                case (10):
                    letter += "C";
                    break;
                default:
                    letter += "";
                    break;
            }

            switch (res % 10) {
                case (1):
                    letter += "I";
                    break;
                case (2):
                    letter += "II";
                    break;
                case (3):
                    letter += "III";
                    break;
                case (4):
                    letter += "IV";
                    break;
                case (5):
                    letter += "V";
                    break;
                case (6):
                    letter += "VI";
                    break;
                case (7):
                    letter += "VII";
                    break;
                case (8):
                    letter += "VIII";
                    break;
                case (9):
                    letter += "IX";
                    break;
                case (10):
                    letter += "X";
                    break;
                default:
                    letter += "";
                    break;
            }

        } else {
            throw new Exception("");
        }
        return letter;
    }

    /**
     * Определение решения между римским и классическим
     *
     * @param line
     * @return
     */
    private static Boolean getRomeOrClassic(String line) {
        int check = Character.getNumericValue(line.charAt(0));
        return check != 33 && check != 31 && check != 18;
    }

    /**
     * Конвертация римских чисел в классические
     *
     * @param elements- массив элементов
     */
    private static void romeToClassic(ArrayList elements) {
        elements.replaceAll(s -> s.equals("I") ? 1 : s);
        elements.replaceAll(s -> s.equals("II") ? 2 : s);
        elements.replaceAll(s -> s.equals("III") ? 3 : s);
        elements.replaceAll(s -> s.equals("IV") ? 4 : s);
        elements.replaceAll(s -> s.equals("V") ? 5 : s);
        elements.replaceAll(s -> s.equals("VI") ? 6 : s);
        elements.replaceAll(s -> s.equals("VII") ? 7 : s);
        elements.replaceAll(s -> s.equals("VIII") ? 8 : s);
        elements.replaceAll(s -> s.equals("IX") ? 9 : s);
        elements.replaceAll(s -> s.equals("X") ? 10 : s);
    }


    /**
     * Основные вычисления
     *
     * @param symbol   - оператор
     * @param elements -   массив элементов
     */
    private static int getCalculation(String symbol, ArrayList<String> elements) {
        int element;

        switch (symbol) {
            case ("*"):
                return Integer.parseInt(String.valueOf(elements.get(0))) * Integer.parseInt(String.valueOf(elements.get(1)));
            case ("/"):
                return Integer.parseInt(String.valueOf(elements.get(0))) / Integer.parseInt(String.valueOf(elements.get(1)));
            case ("+"):
                return Integer.parseInt(String.valueOf(elements.get(0))) + Integer.parseInt(String.valueOf(elements.get(1)));
            case ("-"):
                return Integer.parseInt(String.valueOf(elements.get(0))) - Integer.parseInt(String.valueOf(elements.get(1)));
            default:
                break;
        }
        return 0;
    }

    /**
     * Убирает все пробелы в изначальном уравнении
     *
     * @param line - введенная пользователем строка
     * @return
     */
    private static String getClear(String line) {
        return line.replaceAll("\\s+", "");
    }
}
