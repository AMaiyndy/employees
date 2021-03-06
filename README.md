# О проекте

Данный проект создан в учебных целях для освоения стэка технологий Java EE. Проект позволяет производить простые манипуляции над структурой компании. API проекта находиться по [ссылке](http://docs.stuffengine.apiary.io/#). Предполагается что все данные передаются в кодировке UTF-8.

## Описание тестовой компании

Компания занимается продажей домашних животных и зоотоваров к ним. Данные о компании хранятся в базе данных под управлением Oracle. База данных хранит в себе структуру подразделений компании, сотрудников, которые работают  в компании, их сертификаты. Так же дополнительно ведеться учет изменения информации о сотрудниках и отделах.

Отдел представлен своим уникальным индетификатором `ID`, неуникальным `именем`, ссылкой на `головное подразделение` и ссылку на `начальника отдела`.

Для сотрудника известно его уникальный индетификатор `ID`, `ФИО`, `дата рождения` в виде **dd.mm.yyyy**, ссылка на `отдел`, в котором работает сотрудник, ссылка на `грейд` и `должность` сотрудника. Так же хранятся сведения о `зарплате` сотрудника. Грейды и должности считаются не уникальными. 

Сертификаты может предоставить свой уникальный `номер`, иначе говоря номер сертификата, ссылку на его `владельца`, `дата` получения сертификата, `название компании` его выдавшая, а так же набор отсканированных `страниц`.

Список доступного функционала находится в `release notes`, в корне проекта.

# Развертывание приложения

Приложения написано на Java. Для его успешного запуска необходимо настроить систему. Далее будет представлено описание для разворачивание приложение на Windows, под управлением сервера приложений TomCat.

## Настройка системы

Для запуска приложения нам понадобиться TomCat, который в свою очередь требует настроенного jdk.

**Note: в целом tomcat требует только JRE, но на данный момент нет никаких гарантий что его хватит для нормального функционирования приложения. В случае использования JRE необходимо 'JAVA_HOME заменить на JRE_HOME, подробнее читайте в инструкции в корне tomcat'а**

### Установка и настройка jdk

Если у Вас нет установленного jdk, то выполняем следующие действия:

1. Скачиваем jdk [тык](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. Устанавлиаем его согласно мастеру установки.
3. После успешной установке необходимо прописать java в системные переменные.
4. Заходим в ***пуск/мой компьютер/ПКМ щелчок -> свойства/дополнительные параметры системы/переменные среды***
5. В нижней части появившегося окна, видим системные переменные. Необходимо добавить две переменные (Имя переменной, значение):
6. JAVA_HOME, `C:\Program Files\Java\jdk1.8.0_92` (путь взял для примера)
7. PATH, `C:\Program Files\Java\jdk1.8.0_92\bin` (путь взял для примера)
8. Все, если все сделано правильно, то написав в командой строке 'java -version' Вы получите информацию о текущей установленной версии. В противном случае, что-то было сделано неправильно. Рекомендуется проверить пути системных переменных.

### Установка TomCat

1. Скачать 9 версию TomCat по [ссылке](http://tomcat.apache.org/download-90.cgi)
2. Разархивировать в любую папку. (Желательно чтобы путь к нему не содержал кириллицы)

### Сборка проекта с помощью Maven

Проект разрабатывается с помощью автоматической системы сборки maven. Предполагается что он у Вас уже установлен и настроен. В противном случае можно перейти к следующему шагу.
Для сборки проекта необходимо:

1. Скачать архив из ветки master.
2. Разархивировать в любую папку (Желательно не содержащую кириллицы)
3. Через консоль перейти в корневую папку проекта (та, которая содержиит файл pom.xml) и выполнить команду  
`mvn clean install`
4. Собранный файл будет лежать по пути `/target/stuffEngine.war`

### Развертывание и запуск проекта на TomCat

Перейдем к запуску проекта. Для этого понадобиться war - файл полученный в предыдущем пункте.

1. Перейдите в корневую папку установленного TomCat.
2. Поместите war файл по адресу `/webapps/{ваш war файл}.war`
3. Перейдите по адресу `/bin` и запустите `startup.bat`
4. После некоторого времени, если все сделано верно появится ряд информационных сообщений о запуске сервера и деплоя проекта. Это все произойдет автоматически. В конце будет написано `server strated`.
5. Теперь можно пользоваться проектом. Доступ осуществляется по url: `localhost:8080/{имя war файла}/{пути из api}`
6*. Для работы sopa web service потребуеться переобпределить бин  вконфигурации и поднять jms сервер.
