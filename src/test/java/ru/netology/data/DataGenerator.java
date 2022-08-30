package ru.netology.data;

import com.github.javafaker.Faker;

import java.util.*;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataGenerator {
  private DataGenerator() {
  }

  public static class Registration {
    private Registration() {
    }

    public static String generateRandomLastNamePartOne() {
      Faker faker = new Faker(new Locale("ru"));
      return faker.name().lastName();
    }

    public static String generateRandomLastNamePartTwo() {
      Faker faker = new Faker(new Locale("ru"));
      return faker.name().lastName();
    }

    public static String generateRandomFirstName() {
      Faker faker = new Faker(new Locale("ru"));
      return faker.name().firstName();
    }

    public static String generateCorrectFullName() {
      while (true) {
        String randomLastName = generateRandomLastNamePartOne();
        String randomFirstName = generateRandomFirstName();
        if (checkIfRandomFullNameIsInvalid(generateRandomLastNamePartOne(), generateRandomFirstName())) {
          return randomLastName + " " + randomFirstName;
        }
      }
    }

    public static boolean checkIfRandomFullNameIsInvalid(String lastName, String firstName) {
      if (lastName.contains("ё") || firstName.contains("ё")) {
        return true;
      }
      return false;
    }

    public static String generateCorrectNameWithHyphen() {
      while (true) {
        String randomLastNamePartOne = generateRandomLastNamePartOne();
        String randomLastNamePartTwo = generateRandomLastNamePartTwo();
        String randomFirstName = generateRandomFirstName();
        if (checkIfRandomPartsOfNameIsInvalid(generateRandomLastNamePartOne(), generateRandomLastNamePartTwo(), generateRandomFirstName())) {
          return randomLastNamePartOne + "-" + randomLastNamePartTwo + " " + randomFirstName;
        }
      }
    }

    public static boolean checkIfRandomPartsOfNameIsInvalid(String lastNamePartOne, String lastNamePartTwo, String firstName) {
      if (lastNamePartOne.contains("ё") || lastNamePartTwo.contains("ё") || firstName.contains("ё")) {
        return true;
      }
      return false;
    }

    public static String generateIncorrectNameUnderline() {
      while (true) {
        String randomLastNamePartOne = generateRandomLastNamePartOne();
        String randomLastNamePartTwo = generateRandomLastNamePartTwo();
        String randomFirstName = generateRandomFirstName();
        if (checkIfRandomPartsOfNameIsInvalid(generateRandomLastNamePartOne(), generateRandomLastNamePartTwo(), generateRandomFirstName())) {
          return randomLastNamePartOne + "_" + randomLastNamePartTwo + "_" + randomFirstName;
        }
      }
    }

    public static String generateIncorrectNameLatin() {
      Faker faker = new Faker(new Locale("en"));
      return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generateIncorrectNameWithLettersUmlaut() {
      Faker faker = new Faker(new Locale("ru"));
      return faker.name().lastName() + " Фёдор";
    }

    public static String generateCorrectPhone() {
      Faker faker = new Faker(new Locale("ru"));
      return faker.phoneNumber().phoneNumber();
    }

    public static String generateIncorrectPhoneFewerDigits() {
      Faker faker = new Faker(new Locale("ru"));
      return faker.numerify("7123456789");
    }

    public static String generateIncorrectPhoneCodeCountry() {
      Faker faker = new Faker(new Locale("en"));
      return faker.phoneNumber().phoneNumber();
    }

    public static String generateDate(int days) {
      return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    // Из списка удалены Гатчина и Красногорск для исключения падения тестов с использованием валидных городов
    // На баги с Гатчиной и Красногорском сделаны отдельные тесты
    public static String generateCorrectCity() {
      Random rand = new Random();
      List<String> cityList = Arrays.asList("Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Магас", "Нальчик", "Элиста", "Черкесск", "Петрозаводск", "Сыктывкар", "Симферополь", "Йошкар-Ола", "Саранск", "Якутск", "Владикавказ", "Казань", "Кызыл", "Ижевск", "Абакан", "Грозный", "Чебоксары", "Барнаул", "Чита", "Петропавловск-Камчатский", "Краснодар", "Красноярск", "Пермь", "Владивосток", "Ставрополь", "Хабаровск", "Благовещенск", "Архангельск", "Астрахань", "Белгород", "Брянск", "Владимир", "Волгоград", "Вологда", "Воронеж", "Иваново", "Иркутск", "Калининград", "Калуга", "Кемерово", "Киров", "Кострома", "Курган", "Курск", "Липецк", "Магадан", "Мурманск", "Нижний Новгород", "Великий Новгород", "Новосибирск", "Омск", "Оренбург", "Орёл", "Пенза", "Псков", "Ростов-на-Дону", "Рязань", "Самара", "Саратов", "Южно-Сахалинск", "Екатеринбург", "Смоленск", "Тамбов", "Тверь", "Томск", "Тула", "Тюмень", "Ульяновск", "Челябинск", "Ярославль", "Москва", "Санкт-Петербург", "Севастополь", "Биробиджан", "Нарьян-Мар", "Ханты-Мансийск", "Анадырь", "Салехард");
      return cityList.get(rand.nextInt(cityList.size()));
    }

    public static String generateCityRu() {
      Faker faker = new Faker(new Locale("ru"));
      return faker.address().city();
    }

    public static String generateCityLatin() {
      Faker faker = new Faker(new Locale("en"));
      return faker.address().city();
    }

    // генерация невалидных городов РФ
    public static String searchInvalidCity() {
      while (true) {
        String randomCity = generateCityRu();
        if (checkIfCityIsInvalid(randomCity)) {
          return randomCity;
        }
      }
    }

    // Из списка удалены Гатчина и Красногорск для исключения падения теста на невалидные города
    // На баги с Гатчиной и Красногорском сделаны отдельные тесты
    public static boolean checkIfCityIsInvalid(String city) {
      List<String> cityList = Arrays.asList("Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Магас", "Нальчик", "Элиста", "Черкесск", "Петрозаводск", "Сыктывкар", "Симферополь", "Йошкар-Ола", "Саранск", "Якутск", "Владикавказ", "Казань", "Кызыл", "Ижевск", "Абакан", "Грозный", "Чебоксары", "Барнаул", "Чита", "Петропавловск-Камчатский", "Краснодар", "Красноярск", "Пермь", "Владивосток", "Ставрополь", "Хабаровск", "Благовещенск", "Архангельск", "Астрахань", "Белгород", "Брянск", "Владимир", "Волгоград", "Вологда", "Воронеж", "Иваново", "Иркутск", "Калининград", "Калуга", "Кемерово", "Киров", "Кострома", "Курган", "Курск", "Липецк", "Магадан", "Мурманск", "Нижний Новгород", "Великий Новгород", "Новосибирск", "Омск", "Оренбург", "Орёл", "Пенза", "Псков", "Ростов-на-Дону", "Рязань", "Самара", "Саратов", "Южно-Сахалинск", "Екатеринбург", "Смоленск", "Тамбов", "Тверь", "Томск", "Тула", "Тюмень", "Ульяновск", "Челябинск", "Ярославль", "Москва", "Санкт-Петербург", "Севастополь", "Биробиджан", "Нарьян-Мар", "Ханты-Мансийск", "Анадырь", "Салехард");
      for (String validCity : cityList) {
        if (Objects.equals(city, validCity)) {
          return false;
        }
      }
      return true;
    }
  }
}
