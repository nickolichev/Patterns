[![Build status](https://ci.appveyor.com/api/projects/status/h3hmpgpf551q772g/branch/master?svg=true)](https://ci.appveyor.com/project/nickolichev/patterns/branch/master)

Подключение Report Portal

#1 Настроить конфигурацию build.gradle

#2 Зайти на сайт https://reportportal.io/installation (под VPN)

#3 Сохранить себе данные авторизации 
For user access: login: default password: 1q2w3e или For admin access: login: superadmin password: erebus

#4 Настроить docker-compose.yml под свою ОС (взять типовые настройки)

#5 Произвести интеграцию под фрейморк (Выберите необходимую интеграцию по языку:)
- в resources создать директорию META-INF/services с файлом org.junit.jupiter.api.extension.Extension
- в resources создать файлы:
- log4j2.xml
- logback.xml
- reportportal.properties
- создать директорию java/ru/netology/util, в данной директории создать файлы:
- LoggingUtils 
- ScreenShooterReportPortalExtension
(взять код из репозитория https://github.com/netology-code/aqa-code/tree/master/reporting/selenide-reportportal)

#6 Запустить Docker Desktop

#7 Подключить VPN

#8 В случае, если запуск docker-compose -p reportportal up -d --force-recreate под VPN упадет с подобной ошибкой 
"error pulling image configuration: download failed after attempts=6: dialing d2iks1dkcwqcbx.cloudfront.net:443 no HTTPS proxy: connecting to 52.85.114.81:443: dial tcp 52.85.114.81:443: connect: connection refused" и загрузка контейнеров прервется, необходимо к VPN подключить еще и прокси сервер (например, на https://proxy-list.org) 

#9 Повторить загрузку контейнеров. 

#10 В случае успешной загрузки и запуска контейнеров перейти на localhost/8080

#11 Авторизоваться (login: superadmin, password: erebus)

#12 Зайти в профиль и скопировать базовые настройки в reportportal.properties в IDEA

#13 Запустить приложение app-card-delivery.jar

#14 Модифицировать тестовый файл 

- путем добавления аннотации @ExtendWith(ScreenShooterReportPortalExtension.class) перед объявлением тестового класса
- путем добавления в тесты методов логгирования после каждого необходимого шага выполнения теста
- 
#15 Запустить тесты

#16 В случае падения тестов с ошибкой подобной этой 

"class ru.netology.test.PatternsDeliveryTest (in unnamed module @0x330a0ac3) cannot access class sun.java2d.marlin.MarlinUtils (in module java.desktop) because module java.desktop does not export sun.java2d.marlin to unnamed module @0x330a0ac3
java.lang.IllegalAccessError: class ru.netology.test.PatternsDeliveryTest (in unnamed module @0x330a0ac3) cannot access class sun.java2d.marlin.MarlinUtils (in module java.desktop) because module java.desktop does not export su"
переписать метод logInfo() на logger.info()

#17 Прописать зависимости 

- import org.slf4j.Logger;
- import org.slf4j.LoggerFactory;
- инициализировать private static final Logger logger = LoggerFactory.getLogger(PatternsDeliveryTest.class);

#18 Перезапустить тесты

#19 Просмотреть отчеты на http://localhost:8080/ui/#superadmin_personal/launches/all

<img width="1280" alt="Снимок экрана 2023-03-12 в 21 39 51" src="https://user-images.githubusercontent.com/102659571/224567120-2ce3bdf3-a915-48ae-be70-b2faef04c016.png">

