# KPO_LAST

Команда для сборки: docker-compose up

Swagger-документация: 
http://localhost:8085/swagger-ui/index.html# (1 микросервис)
http://localhost:8090/swagger-ui/index.html# (2 микросервис)

Для правильной работы базы данных следует запустить микросервисы, потом выполнить следующий запрос ко 2 БД:

INSERT INTO station (station_name) VALUES ('Vykhino'), ('Tekstilshiki'), ('Kitay-gorod'), ('Perovo'), 
('Novogireevo'), ('Pervomayskaya'), ('Kuzminki'), ('Novokosino'), ('Taganskaya'), ('Arbatskaya')

Использовалось Spring Security, PostgreSQL, Docker, JPA
