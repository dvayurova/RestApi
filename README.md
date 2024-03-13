# RestApi

REST API для перенаправления запросов на https://jsonplaceholder.typicode.com/

## Цель проекта
- создать REST API для перенаправления запросов на https://jsonplaceholder.typicode.com/

## Примененные технологии

* Java 17
* Spring Boot
* Spring MVC
* Spring Data JPA
* Spring Security
* Spring Cache
* Caffeine
* Spring AOP
* Maven
* PostgreSQL

## Результат
1) Реализованы обработчики (GET, POST, PUT, DELETE), которые проксируют запросы к
https://isonplaceholder.typicode.com/
- /api/posts/**
- /api/users/**
- /api/albums/**
2) Реализована базовая авторизация, с несколькими аккаунтами, у которых разные роли.
3) Проработана расширенная ролевая модель доступа. Предусмотрено 7 ролей - ROLE_ADMIN, ROLE_POSTS_VIEWER, ROLE_POSTS_EDITOR, ROLE_USERS_VIEWER, ROLE_USERS_EDITOR, ROLE_ALBUMS_VIEWER, ROLE_ALBUMS_EDITOR.
- ROLE_ADMIN -- имеет доступ ко всем обработчикам
- ROLE_POSTS_VIEWER - имеет доступ к обработчикам /posts/** на чтение
- ROLE_POSTS_EDITOR - имеет доступ к обработчикам /posts/** на чтение и редактирование
- ROLE_USERS_VIEWER -- имеет доступ к обработчикам /users/** на чтение
- ROLE_USERS_EDITOR - имеет доступ к обработчикам /users/** на чтение и редактирование
- ROLE_ALBUMS_VIEWER -- имеет доступ к обработчикам /albums/** на чтение
- ROLE_ALBUMS_EDITOR - имеет доступ к обработчикам /albums/** на чтение и редактирование
4) Реализовано ведение аудита действий. (дата-время, пользователь, имеет ли доступ, параметры запроса) с сохранением в БД (PostgreSQL).
5) Реализован in-memory кэш, чтобы уменьшить число запросов к jsonplaceholder: изменения данных сначала происходят в кэше, а потом отправляются запросы на jsonplaceholder.
6) Сборка приложения с использованием maven.
