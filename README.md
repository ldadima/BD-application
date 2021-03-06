# DB-Zoo-project
* Задание по самой базе данных лежит в файле `task.pdf`
* Задание по приложению - `DB-project description.pdf`
* Схема БД - `Zoo_schema.png`
## Описание (использовал для сдачи)
1. Реализация триггеров и хранимых процедур - src\main\resources\db\migration\V1__Create_DB.sql : 322 строка. Использование хранимой функции src\main\java\org\fit\linevich\services\AnimalService.java .create && .update
2. файл с sql-скриптами, содержащими создание, наполнение таблиц --- src\main\resources\db\migration\  
3. select-скрипты, используемые в Клиенте - где реализован в коде каждый из запросов в задании (для каждого метода с запросом сделал javadoc с указанием номера, иногда для просмотра скрипта нужно зайти в метод объекта repository), как этот запрос найти в гуе
    1. `org\fit\linevich\services\EmployeeService.java` со строки 112. В гуе на странице "Список сотрудников" есть кнопки внизу списка (страницы) там же кол-во всех сотрудников (вообще всех, всегда)
    2. `org\fit\linevich\services\EmployeeService.java` со строки 165. В гуе на той же странице сверху форма, запрос вызывается при вводе всех полей (и дат, и вида), нажать на кнопку искать
    3. `org\fit\linevich\services\EmployeeService.java` со строки 174. В гуе на той же странице сверху форма, запрос вызывается при не вводе дат, нажать на кнопку искать  
    4. `org\fit\linevich\services\AnimalService` строка 280.  В гуе не реализовал (нужна отдельная страница-компонент, мне не хватило времени доделать)
    5. `org\fit\linevich\services\AnimalService` строка 304. В гуе форма "Поиск требующих тепла данного возраста"
    6. `org\fit\linevich\services\AnimalService` строка 325. В гуе форма "Поиск животных по болезни".  `org\fit\linevich\services\AnimalService` строка 348. В гуе форма "Поиск вакцинированных животных"
    7. `org\fit\linevich\services\AnimalService` строка 354. В гуе форма "Поиск совместимых с".  `org\fit\linevich\services\AnimalService`  строка 368. В гуе форма "Нуждаются в отапливаемом помещении".  `org\fit\linevich\services\AnimalService` строка 377. В гуе форма "Нуждаются в переселении"  
    8. `org\fit\linevich\services\ProviderService` строка 116 и тд. В гуе страница "Список поставщиков", кол-во указано снизу также как с сотрудниками и сверху 2 формы "Заключалась сделка в период" для метода supplyForPeriod и "Специализируется на" для specFeed
    9. `org\fit\linevich\services\FeedService` строка 60 и тд. В гуе страница "Список еды", сверху форма "Производится самостоятельно   " для producedYourself и нет формы для метода notNeed также по причине того, что другой формат вывода (новая страница нужна)
    10. `org\fit\linevich\services\AnimalService` строка 392. В гуе страница "Список животных", форма "Требуется еда в данном сезоне" .
    11. Также есть на back, но нет на гуе по причине формата и новой страницы.  `org\fit\linevich\services\AnimalService`  строка 433
    12. `org\fit\linevich\services\AnimalService`  строка 451. В гуе форма "Фертильны"
    13. `org\fit\linevich\services\AnimalService`  строка 451. В гуе страница "Список зоопарков", форма "Производился обмен"  
4. идеальная форма вывода, выше указаны страницы для некоторых основных сущностей (животные, еда-корм, сотрудники, зоопарки, поставщики) Болезни  - страница список Болезней
5. идеальная форма ввода, все то же самое только добавить, также для каждой строки в выводе, есть кнопка для обновления (синяя), при обновлении все прошлые данные будут в форме для обновления изначально
6. надо применить транзакции - `org\fit\linevich\services\AnimalService` строка 90, 2 метода подряд. В гуе при добавлении или обновлении животного
7. Для каждой таблицы: в принципе выше описано, про добавление и обновление. Сверху справа будет message (синее окно)
8. Для каждого запроса: заполнить форму и/или нажать на кнопку "Искать/лупа", снизу форм будет постраничный вывод для каждой формы запроса

## Запуск
В корне проекта есть docker-compose.yml для запуска GUI. Команда в терминале `docker-compose up [-d]`, флаг -d для запуска в фоне. Back запускается из идеи (MyApplication.class).
Для запуска, желательно, нужна PostgreSQL. В файле `src/main/resources/application.properties` указать необходимые данные:
* `spring.datasource.url=jdbc:postgresql://localhost:5432/postgres` (возможно url к БД также другой)
* `spring.datasource.username=Имя-Пользователя`
* `spring.datasource.password=Пароль`
