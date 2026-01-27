package hw2;

import hw2.models.User;
import hw2.service.UserService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static int inputInt(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.printf("Введите число. Ошибка ввода %s\n", e.getMessage());
                scanner.nextLine();
            }
        }
    }

    public static String inputString(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.printf("Введите строку. Ошибка ввода %s\n", e.getMessage());
                scanner.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        System.setProperty("java.util.logging.config.file", "log.config");
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();

        while (true) {
            System.out.println("Выберите действие над таблицей Users:");
            System.out.println("1.Добавить");
            System.out.println("2.Удалить");
            System.out.println("3.Обновить");
            System.out.println("4.Вывод");
            System.out.println("5.Выход");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    User user = new User();
                    user.setName(inputString("Введите имя: "));
                    user.setEmail(inputString("Введите email: "));
                    user.setAge(inputInt("Введите возраст: "));
                    userService.createUser(user);
                    break;
                case 2:
                    int id = inputInt("Введите Id: ");
                    userService.deleteUser(userService.findUser(id));
                    break;
                case 3:
                    User userUp = userService.findUser(inputInt("Введите Id: "));
                    if (userUp == null) {
                        System.out.println("Пользователь не найден");
                        break;
                    }
                    userUp.setName(inputString("Введите имя: "));
                    userUp.setEmail(inputString("Введите email: "));
                    userUp.setAge(inputInt("Введите возраст: "));
                    userService.updateUser(userUp);
                    break;
                case 4:
                    List<User> users = userService.findAllUsers();
                    System.out.println("Вывод списка:");
                    System.out.println(String.format("%-5s %-26s %-20s %-30s %-5s", "ID", "Created At", "Name", "Email", "Age"));
                    for (User item : users) {
                        System.out.println(item);
                    }
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Введено неверное значение");
                    break;

            }
        }
    }
}
