Основной функционал:

1.  Запускаем Registry – Gateway – Authorization – Account – Resource.
2.  AccountService через прописанные client-id и secret получает токен по client_credentials flow. Через него регистрируем сотрудника Ведомства.
    POST http://localhost:8080/registration/ с json данными username+password.
3.  POST http://localhost:8080/department/request по форме multipart/form-data со всеми паспортными данными, названиями услуги, приложенными документами. – отправляет пользователь без регистрации через форму.
4.  Токен для сотрудника получаем посредством password flow, для этого отправляем
    POST http://localhost:8080/uaa/oauth/token с BasicAuth rest-api:secret (для клиента апи)
    и с form датой, содержащей grant_type:password, username, password. И получаем access_token для доступа к rest-api.
5.  Обращаемся с rest-api по полученном Bearer token:
    GET http://localhost:8080/department/api - получаем список всех заявок, в зависимости от переданных json данных можно фильтровать/сортировать asc по полям даты, названия услуги, имени, фамилии, отчеству, статусу.
    GET http://localhost:8080/department/api/1 - получаем заявку с id = 1, вместе с названиями документов и историей операций.
    PUT http://localhost:8080/department/api/1 - переводим статус на TERMINATED, в истории событий появляется отметка со временем.
    GET http://localhost:8080/department/api/file/1 - скачивает указанный документ.