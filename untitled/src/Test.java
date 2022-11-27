import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя и более числами: a + b -c * d
 * Невнимательно прочитал первый пункт и сделал с более,чем одним оператором...
 */


public class Test {

    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        Boolean classic;        // определяет систему счисления
        String result = "";     // итоговый результат
        ArrayList matSymbols;   // массив операторов заданного выражения
        ArrayList elements;     // массив операндов заданного выражения
        String[] symb = new String[]{"*", "/", "+"}; // массив операторов последовательности вычисления, сначала */ , затем по порядку выражения +-
        System.out.print("Калькулятор на связи: ");
        String line = scan.nextLine();
        line = getClear(line);
        try {
            checkSystem(line);
        } catch (Exception e) {
            System.out.println(e + "используются одновременно разные системы счисления");
        }
        classic = getRomeOrClassic(line);
        if (classic)
            matSymbols = new ArrayList(Arrays.asList(line.split("10|0|1|2|3|4|5|6|7|8|9")));
        else
            matSymbols = new ArrayList(Arrays.asList(line.split("II|III|I|IV|V|VI|VII|VIII|IX|X")));

        matSymbols.removeAll(Arrays.asList("", null));
        elements = new ArrayList(Arrays.asList(line.split("[*/+-]")));

        if (elements.size() < 1 || matSymbols.size() == 0)
            throw new Exception("строка не является математической операцией");

        if (!classic)
            romeToClassic(elements);

        for (String syb : symb) {
            getCalculation(matSymbols, elements, syb);
        }

        if (!classic) {
            try {
                result = classicToRome(Integer.parseInt(String.valueOf(elements.get(0))));
            } catch (Exception e) {
                System.out.println("В римской системе нет отрицательных чисел");
            }
        } else
            result = (String) elements.get(0);
        System.out.println("Результат: " + result);
    }

    /**
     * Проверка на систему исчисления
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
            if (Character.getNumericValue(el) < 10 && Character.getNumericValue(el) > 10) {
                sum[1] = 1;
            }
        }

        if (sum[0] > 0 && sum[1] > 0)
            throw new Exception("");


    }

    /**
     * Получение результата в римской системе
     * @param res - результат в классической системе
     * @return
     * @throws Exception
     */
    private static String classicToRome(Integer res) throws Exception {
        if (res > 0) {
            String romeResult = "";
            Integer[] ch = new Integer[]{1000, 500, 100, 50, 10, 5};
            String[] letter = new String[]{"M", "D", "C", "L", "X", "V"};
            for (int i = 0; i < ch.length; i++) {
                while (res >= ch[i]) {
                    res -= ch[i];
                    romeResult += letter[i];
                }
            }
            return romeResult;
        } else {
            throw new Exception("");
        }
    }

    /**
     * Определение решения между римским и классическим
     * @param line
     * @return
     */
    private static Boolean getRomeOrClassic(String line) {
        int check = Character.getNumericValue(line.charAt(0));
        return check != 33 && check != 31 && check != 18;
    }

    /**
     * Конвертация римских чисел в классические
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
     * @param matSymbols- массив математических операторов
     * @param elements- массив элементов
     * @param syb
     */
    private static void getCalculation(ArrayList matSymbols, ArrayList<String> elements, String syb) {
        int element;
        if (syb.equals("+")) {
            int index = 0;
            while (index < matSymbols.size()) {
                element = getElement(elements, index, (String) matSymbols.get(index));
                elements.remove(index);
                elements.remove(index);
                elements.add(index, String.valueOf(element));
                matSymbols.remove(index);
            }
        } else {
            int index = matSymbols.indexOf(syb);
            while (index != -1) {
                element = getElement(elements, index, syb);
                elements.remove(index);
                elements.remove(index);
                elements.add(index, String.valueOf(element));
                matSymbols.remove(index);
                index = matSymbols.indexOf(syb);

            }
        }

    }

    /**
     * Совершение одной из четырех математической операции и возвращение результата
     * @param elements - массив элементов
     * @param index
     * @param syb
     * @return
     */
    private static int getElement(ArrayList<String> elements, int index, String syb) {
        switch (syb) {
            case ("*"):
                return Integer.parseInt(String.valueOf(elements.get(index))) * Integer.parseInt(String.valueOf(elements.get(index + 1)));
            case ("/"):
                return Integer.parseInt(String.valueOf(elements.get(index))) / Integer.parseInt(String.valueOf(elements.get(index + 1)));
            case ("+"):
                return Integer.parseInt(String.valueOf(elements.get(index))) + Integer.parseInt(String.valueOf(elements.get(index + 1)));
            case ("-"):
                return Integer.parseInt(String.valueOf(elements.get(index))) - Integer.parseInt(String.valueOf(elements.get(index + 1)));
            default:
                break;
        }
        return 0;
    }

    /**
     * Убирает все пробелы в изначальном уравнении
     * @param line - введенная пользователем строка
     * @return
     */
    private static String getClear(String line) {
        return line.replaceAll("\\s+", "");
    }
}
