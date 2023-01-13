import java.util.Scanner; // Импортируем из покета util класс Scanner, для ввода в консоль с клавиатуры

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String zadacha = scanner.nextLine();
        zadacha = zadacha.replace(" ",""); // Удаляем пробелы, т.к. не понятно из тз, должен быть 1 или несколько, + при вводе можно ошибиться
        try {
            Calculator f = new Calculator();
            System.out.println(f.calc(zadacha));
        } catch (ThrowException e) {
            System.out.println(e.getMessage());
        }
    }
    String calc (String input) throws ThrowException {
        Calculator reshenie = new Calculator();
        byte [] informaciya = reshenie.informaciya(input); // Собираем начальную информацию о строке введенной с клавиатуры
        if (informaciya[2] == 1) { // Путь если задание в римских числах
            input = reshenie.perevodRimArab(input, informaciya); // Переводим римское число в арабское
            String otvet = reshenie.raschet(input, informaciya); // Считаем в действие арабских числах
            informaciya[2] = 0;
            otvet = reshenie.perevodRimArab(otvet, informaciya); // Переводим ответ в римские числа
            return otvet;
        }
        else { // Путь если задание в арабских числах
            // Считаем в действие арабских числах
            return reshenie.raschet(input, informaciya);
        }
    }
    byte [] informaciya (String zadacha) throws ThrowException {

        byte [] inf = {0,0,0};

        if ((zadacha.contains("I")) || (zadacha.contains("V")) || (zadacha.contains("X"))) {
            inf[2] = 1;
            if ((zadacha.contains("1")) || (zadacha.contains("2")) || (zadacha.contains("3")) || (zadacha.contains("4")) ||
                    (zadacha.contains("5")) || (zadacha.contains("6")) || (zadacha.contains("7")) || (zadacha.contains("8")) ||
                    (zadacha.contains("9")) || (zadacha.contains("0"))) throw new ThrowException("throws Exception //т.к. используются одновременно разные системы счисления");
        }

        for (byte i = 0; i < zadacha.length(); ++i) {
            switch ((int)(zadacha.charAt(i))) {
                case 42 : {
                    inf[0] = i;
                    inf[1] += 42;
                    break;
                }
                case 43 : {
                    inf[0] = i;
                    inf[1] += 43;
                    break;
                }
                case 45 : {
                    inf[0] = i;
                    inf[1] += 45;
                    break;
                }
                case 47: {
                    inf[0] = i;
                    inf[1] += 47;
                    break;
                }
                case 44, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 67, 73, 76, 86, 88 : break;
                default: {
                    throw new ThrowException("throws Exception //т.к. в строке неизвестные символы");
                }
            }
        }
        switch (inf[1]) { // Проверка на орифметич операции
            case 0: throw new ThrowException("throws Exception //т.к. строка не является математической операцией");
            case 42, 43, 45, 47: break;
            default: throw new ThrowException("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        if ((inf[2] == 0) & (zadacha.contains(","))) throw new ThrowException("throws Exception //т.к. числа должны быть целыми");
        return inf;
    }
    String perevodRimArab (String zadacha,byte [] rezultatProverki) throws ThrowException {
        // Пытался решать без поиска готовой логики, и сам не додумался как переводить из арабской системы исчисления в римскую
        // Когда закончил пошёл искать, нашел, понравилось, но было жаль стирать свой рукописный "шедевр")
        String[] strPerevodRimArab = {"I", "1", "II", "2", "III", "3", "IV", "4", "V", "5", "VI", "6", "VII", "7", "IIX", "8", "IX", "9", "X", "10",
                "XI", "11", "XII", "12", "XIII", "13", "XIV", "14", "XV", "15", "XVI", "16", "XVII", "17", "XVIII", "18", "XIX", "19", "XX", "20",
                "XXI", "21", "XXII", "22", "XXIII", "23", "XXIV", "24", "XXV", "25", "XXVI", "26", "XXVII", "27", "XXVIII", "28", "XXIX", "29", "XXX", "30",
                "XXXI", "31", "XXXII", "32", "XXXIII", "33", "XXXIV", "34", "XXXV", "35", "XXXVI", "36", "XXXVII", "37", "XXXVIII", "38", "XXXIX", "39", "XL", "40",
                "XLI", "41", "XLII", "42", "XLIII", "43", "XLIV", "44", "XLV", "45", "XLVI", "46", "XLVII", "47", "XLVIII", "48", "XLIX", "49", "L", "50",
                "LI", "51", "LII", "52", "LIII", "53", "LIV", "54", "LV", "55", "LVI", "56", "LVII", "57", "LVIII", "58", "LIX", "59", "LX", "60",
                "LXI", "61", "LXII", "62", "LXIII", "63", "LXIV", "64", "LXV", "65", "LXVI", "66", "LXVII", "67", "LXVIII", "68", "LXIX", "69", "LXX", "70",
                "LXXI", "71", "LXXII", "72", "LXXIII", "73", "LXXIV", "74", "LXXV", "75", "LXXVI", "76", "LXXVII", "77", "LXXVIII", "78", "LXXIX", "79", "LXXX", "80",
                "LXXXI", "81", "LXXXII", "82", "LXXXIII", "83", "LXXXIV", "84", "LXXXV", "85", "LXXXVI", "86", "LXXXVII", "87", "LXXXVIII", "88", "LXXXIX", "89", "XC", "90",
                "XCI", "91", "XCII", "92", "XCIII", "93", "XCIV", "94", "XCV", "95", "XCVI", "96", "XCVII", "97", "XCVIII", "98", "XCIX", "99", "C", "100",};

        if (rezultatProverki[2] == 1) {
            zadacha = zadacha.replace((char) (rezultatProverki[1]), ' ');
            String[] promStr = zadacha.split(" ");
            for (byte i = 0; i < 2; ++i) {
                for (short ii = 0; ; ii += 2) {
                    if (promStr[i].equals(strPerevodRimArab[ii])) {
                        promStr[i] = strPerevodRimArab[ii + 1];
                        break;
                    }
                }
            }
            return promStr[0] + (char) (rezultatProverki[1]) + promStr[1];
        } else {
            if (zadacha.contains("-"))
                throw new ThrowException("throws Exception //т.к. в римской системе нет отрицательных чисел");
            if (zadacha.equals("0")) throw new ThrowException("throws Exception //т.к. в римской системе нет нуля");
            for (short ii = 1; ; ii += 2) {
                if (zadacha.equals(strPerevodRimArab[ii])) {
                    zadacha = strPerevodRimArab[ii - 1];
                    break;
                }
            }
            return zadacha;
        }
    }
    String raschet(String zadacha, byte[] informaciya) throws ThrowException {
        // Перевод строки в числа
        zadacha = zadacha.replace((char) (informaciya[1]), ' ');
        String[] chisla = zadacha.split(" ");
        byte chislo1 = Byte.parseByte(chisla[0]);
        byte chislo2 = Byte.parseByte(chisla[1]);

        if ((chislo1 < 1) || (chislo1 > 10) || (chislo2 < 1) || (chislo2 > 10)) throw new ThrowException("throws Exception // Первое и/или второе чисчло не от 0 до 10");

        // Вычисление заданного мат. действия
        return switch (informaciya[1]) {
            case 43 -> "" + ((byte) (chislo1 + chislo2));
            case 45 -> "" + ((byte) (chislo1 - chislo2));
            case 42 -> "" + ((byte) (chislo1 * chislo2));
            case 47 -> "" + ((byte) (chislo1 / chislo2));
            default -> "";
        };
    }
}

class ThrowException extends Exception {
    ThrowException(String description) {
        super(description);
    }
}