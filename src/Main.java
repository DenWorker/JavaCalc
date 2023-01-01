
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputD = scanner.nextLine();
        try {
            System.out.println(calc(inputD));
        } catch (Exception e) {
            System.out.println("Обнаружены ошибки!");
        }

    }

    public static String calc(String input) throws Exception {
        // Поиск операции.
        char operation = '0';
        for (int i = 0; i < input.length(); i++) {
            if (!(Character.isLetter(input.charAt(i)) || Character.isDigit(input.charAt(i)))) {
                operation = input.charAt(i);
                break;
            }
        }

        // Проверка корректности операции.
        if (operation != '+' && operation != '-' && operation != '*' && operation != '/') {
            throw new Exception();
        }

        // Создание массива строк чисел.
        String[] strings = input.split("\\%s".formatted(Character.toString(operation)));

        // Проверка количества чисел.
        if (strings.length > 2) {
            throw new Exception();
        }

        // Проверка типажа чисел.
        if (Rome.isRome(strings[0]) != Rome.isRome(strings[1])) {
            throw new Exception();
        }

        // Если на входе римские числа.
        boolean Flag = false;
        if (Rome.isRome(strings[0]) && Rome.isRome(strings[1])) {
            Flag = true;
            strings[0] = Rome.toArab(strings[0]);
            strings[1] = Rome.toArab(strings[1]);
        }

        // Расчёт результата.
        int Result = 0;
        switch (operation) {
            case '+' -> Result = Integer.parseInt(strings[0]) + Integer.parseInt(strings[1]);
            case '-' -> Result = Integer.parseInt(strings[0]) - Integer.parseInt(strings[1]);
            case '*' -> Result = Integer.parseInt(strings[0]) * Integer.parseInt(strings[1]);
            case '/' -> Result = Integer.parseInt(strings[0]) / Integer.parseInt(strings[1]);
        }

        // Вывод результата (при римском вводе необходим перевод результат в римское число).
        if (Flag) {
            // Если римское число меньше 1.
            if (Result <= 0) {
                throw new Exception();
            }
            return Rome.toRome(Integer.toString(Result));
        } else return Integer.toString(Result);

    }
}

enum Rome {
    I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9),
    X(10), XX(20), XXX(30), XL(40), L(50), LX(60), LXX(70), LXXX(80), XC(90), C(100);

    private final int arab;

    Rome(int arab) {
        this.arab = arab;
    }

    public static boolean isRome(String input) {
        for (Rome element : Rome.values()) {
            if (element.name().equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static String toArab(String input) {
        for (Rome element : Rome.values()) {
            if (element.name().equals(input)) {
                return Integer.toString(element.arab);
            }
        }
        return "-1";
    }

    public static String toRome(String input) {
        int dozens = Integer.parseInt(input) / 10 * 10;
        int units = Integer.parseInt(input) % 10;
        input = "";
        for (Rome element : Rome.values()) {
            if (element.arab == dozens) {
                input = element.name();
                break;
            }
        }
        for (Rome element : Rome.values()) {
            if (element.arab == units) {
                input += element.name();
                break;
            }
        }
        return input;
    }
}