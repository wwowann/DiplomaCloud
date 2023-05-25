## Дипломный проект "Облачное хранилище"

### Описание проекта
Разработан REST-сервис. Сервис предоставляет REST-интерфейс для загрузки файлов и вывода списка уже загруженных файлов пользователя.

Все запросы к сервису авторизуются. Заранее подготовленное веб-приложение (FRONT) подключаться к разработанному сервису без доработок, а также использует функционал FRONT для авторизации, загрузки и вывода списка файлов пользователя.

### Требования к приложению
* cервис предоставляtт REST-интерфейс для интеграции с FRONT.
* сервис реализует все методы, описанные в [yaml-файле](https://github.com/netology-code/jd-homeworks/blob/master/diploma/CloudServiceSpecification.yaml):
    * вывод списка файлов.
    * добавление файла.
    * удаление файла.
    * авторизация.
* все настройки вычитываются из файла настроек (yml).
* информация о пользователях сервиса (логины для авторизации) и данные храниться в базе данных MySQL.
### Требования к реализации
* приложение разработано с использованием Spring Boot.
* использован сборщик пакетов maven.
* код размещён на Github.
