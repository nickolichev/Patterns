package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class PatternsDeliveryTest {
  String correctCity = DataGenerator.Registration.generateCorrectCity();
  String incorrectCity = DataGenerator.Registration.searchInvalidCity();
  String incorrectCityLatin = DataGenerator.Registration.generateCityLatin();
  String correctFullName = DataGenerator.Registration.generateCorrectFullName();
  String correctNameWithHyphen = DataGenerator.Registration.generateCorrectNameWithHyphen();
  String incorrectNameLatin = DataGenerator.Registration.generateIncorrectNameLatin();
  String incorrectNameUnderline = DataGenerator.Registration.generateIncorrectNameUnderline();
  String incorrectNameLettersWithUmlaut = DataGenerator.Registration.generateIncorrectNameWithLettersUmlaut();
  String correctPhone = DataGenerator.Registration.generateCorrectPhone();
  String incorrectPhoneFewerDigits = DataGenerator.Registration.generateIncorrectPhoneFewerDigits();
  String incorrectPhoneCodeCountry = DataGenerator.Registration.generateIncorrectPhoneCodeCountry();
  String firstMeeting = DataGenerator.Registration.generateDate(3);
  String nextMeeting = DataGenerator.Registration.generateDate(6);
  String incorrectMeeting = DataGenerator.Registration.generateDate(2);


  @BeforeEach
  public void setUp() {
    open("http://localhost:9999/");
  }

  @Test
  void testCorrectFilling() {
    $x("//input[@placeholder='Город']").setValue(correctCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").setValue(firstMeeting);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//div[@class='notification__title']").should(visible, Duration.ofSeconds(5))
            .should(Condition.text("Успешно"));
  }

  @Test
  void testCorrectFillingWithReschedulingMeeting() {
    $x("//input[@placeholder='Город']").setValue(correctCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").setValue(firstMeeting);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//div[@class='notification__title']").should(visible).should(Condition.text("Успешно"));
    $x("//div[@class='notification__content']")
            .should(Condition.text("Встреча успешно запланирована на " + firstMeeting))
            .should(visible, Duration.ofSeconds(5));
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").setValue(nextMeeting);
    $x("//*[@class='button__text']").click();
    $x("//div[@data-test-id='replan-notification']")
            .should(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
            .should(visible, Duration.ofSeconds(5));
    $x("//div[@data-test-id='replan-notification']//*[@class='button__text']").click();
    $x("//div[@class='notification__content']")
            .should(Condition.text("Встреча успешно запланирована на " + nextMeeting))
            .should(visible, Duration.ofSeconds(5));
  }

  // Не захардкорен невалидный город, чтобы в случае изменения базы валидных городов тест оставался актуальным
  @Test
  void testIncorrectFillingCity() {
    $x("//input[@placeholder='Город']").setValue(incorrectCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Доставка в выбранный город недоступна')]")
            .shouldBe(Condition.text("Доставка в выбранный город недоступна"));
  }

  // тестирование бага "Сервис не позволяет оформить доставку карты в валидный город Гатчина"
  // см. issues #1
  @Test
  void testIncorrectFillingCityGatchina() {
    $x("//input[@placeholder='Город']").setValue("Гатчина");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Доставка в выбранный город недоступна')]")
            .shouldBe(Condition.text("Доставка в выбранный город недоступна"));
  }

  // тестирование бага "Сервис не позволяет оформить доставку карты в валидный город Красногорск"
  // см. issues #2
  @Test
  void testIncorrectFillingCityKrasnogorsk() {
    $x("//input[@placeholder='Город']").setValue("Красногорск");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Доставка в выбранный город недоступна')]")
            .shouldBe(Condition.text("Доставка в выбранный город недоступна"));
  }

  @Test
  void testIncorrectFillingCityLatin() {
    $x("//input[@placeholder='Город']").setValue(incorrectCityLatin);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Доставка в выбранный город недоступна')]")
            .shouldBe(Condition.text("Доставка в выбранный город недоступна"));
  }

  @Test
  void testIncorrectFillingCityEmpty() {
    $x("//input[@placeholder='Город']").setValue("");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Поле обязательно для заполнения')]")
            .shouldBe(Condition.text("Поле обязательно для заполнения"));
  }

  @Test
  void testIncorrectFillingDate() {
    $x("//input[@placeholder='Город']").setValue(correctCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(incorrectMeeting);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Заказ на выбранную дату невозможен')]")
            .shouldBe(Condition.text("Заказ на выбранную дату невозможен"));
  }

  @Test
  void testIncorrectFillingDateEmpty() {
    $x("//input[@placeholder='Город']").setValue(correctCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Неверно введена дата')]")
            .shouldBe(Condition.text("Неверно введена дата"));
  }

  @Test
  void testCorrectFillingNameWithHyphen() {
    $x("//input[@placeholder='Город']").setValue(correctCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(correctNameWithHyphen);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//div[@class='notification__title']").should(visible, Duration.ofSeconds(5))
            .should(Condition.text("Успешно"));
    $x("//div[@class='notification__content']")
            .should(Condition.text("Встреча успешно запланирована на " + firstMeeting))
            .should(visible, Duration.ofSeconds(5));
  }

  @Test
  void testIncorrectFillingNameLatin() {
    $x("//input[@placeholder='Город']").setValue(correctCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(incorrectNameLatin);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]")
            .shouldBe(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
  }

  @Test
  void testInCorrectFillingNameEmpty() {
    $x("//input[@placeholder='Город']").setValue(correctCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue("");
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Поле обязательно для заполнения')]")
            .shouldBe(Condition.text("Поле обязательно для заполнения"));
  }

  @Test
  void testInCorrectFillingNameUnderline() {
    $x("//input[@placeholder='Город']").setValue(correctCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(incorrectNameUnderline);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]")
            .shouldBe(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
  }

  @Test
  public void testInCorrectFillingNameLettersWithUmlaut() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(incorrectNameLettersWithUmlaut);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(correctPhone);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]")
            .shouldBe(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
  }

  @Test
  void testInCorrectFillingPhoneEmpty() {
    $x("//input[@placeholder='Город']").setValue(correctCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue("");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Поле обязательно для заполнения')]")
            .shouldBe(Condition.text("Поле обязательно для заполнения"));
  }

  // тестирование бага "Сервис позволяет ввести невалидное количество цифр номера мобильного телефона"
  // см. issues #3
  @Test
  void testInCorrectFillingPhoneFewerDigits() {
    $x("//input[@placeholder='Город']").setValue(correctCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(incorrectPhoneFewerDigits);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//div[@class='notification__title']").should(visible, Duration.ofSeconds(5))
            .should(Condition.text("Успешно"));
    $x("//div[@class='notification__content']")
            .should(Condition.text("Встреча успешно запланирована на " + firstMeeting))
            .should(visible, Duration.ofSeconds(5));
  }

  @Test
  void testInCorrectFillingPhoneCodeCountry() {
    $x("//input[@placeholder='Город']").setValue(correctCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@placeholder='+7 000 000 00 00']").setValue(incorrectPhoneCodeCountry);
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//div[@class='notification__title']").should(visible, Duration.ofSeconds(5))
            .should(Condition.text("Успешно"));
    $x("//div[@class='notification__content']")
            .should(Condition.text("Встреча успешно запланирована на " + firstMeeting))
            .should(visible, Duration.ofSeconds(5));
  }

  @Test
  void testCheckboxEmptyChecked() {
    $x("//input[@placeholder='Город']").setValue(correctCity);
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(firstMeeting);
    $x("//input[@name='name']").setValue(correctFullName);
    $x("//input[@name='phone']").setValue(correctPhone);
    $x("//*[@class='button__text']").click();
    $x("//*[@role='presentation']")
            .shouldBe(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
  }
}
