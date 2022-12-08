# Разработка системы автоматизации работы школ иностранных языков

Студент группы 073601 Тарасов Артем Сергеевич

##### Требования к программе
2 роли. 
Клиент может регаться, войти. После входа увидит свой личный кабинет со всеми купленными курсами и возможностью приобретать новые. При открытии любого курса в нем будет вложена информация с описанием и т.п.. А кроме того возможность запросить курс на почту и каталог доступных курсов отдельно где он может купить новые. Пользователь еще в купленных курсах увидит последовательный список тем с рядом стоящими чекбоксами. Отмечаясь в этих чекбоксах он отмечает успешное изучение темы.
Админ в свою очередь управляет курсами, одобряет заявки на скачивание курсов (после этого идет отправка курса на почту), может блокировать пользователя или лишать доступа к курсу, если клиент не ввел правильный ключ активации. 
##### MVP
• Регистрация

• Авторизация

• Поиск курсов по заданному языку

• Фильтрация курсов по ценовому диапазону

• Ввод ключа на курс (например 16-значного, будет автоматически генерироваться и высылаться на почту после действий админа. После этого ключ можно использовать в приложении для активации курса)

• Редактирование личного профиля

• Запрос отправки курса на почту и его одобрение на отправку (будет отдельная кнопка загрузить курса, она будет отправлять запрос администратору, тот будет решать отправлять или нет, если да, то на почту, привязанную к аккаунту придет сообщение с файлом) 

• Просмотр и регистрация продвижения по курсу (прогресс)

• Блокировка пользователей

• Лишение прав доступа к курсу при неправильном вводе ключа

##### NTH
• Просмотр профиля купивших курс

• Оценка курсов
