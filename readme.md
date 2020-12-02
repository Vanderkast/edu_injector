# Система внедрения зависимостей
###### VSU EDU 
### Runtime reflection based dependency injection system with property bean declaration support


`@Autowired` - аннотация конструктора, который будет использоваться для создания объекта. 
Если ни один из конструкторов класса не помечен как `@Autowired`, то будет исползован _конструктор по умолчанию_.
Если _конструктор по умолчанию_ недоступен, будет выброшено исключение `ConstructorNotFoundException`.

`@Value` - аннотация аргумента, указывающая, что значение параметра должно быть получено по ключу - `Value#key`.

`Injector` - интерфейс провайдера зависимостей.

`PropertyBasedInjector` реализует интерфейс `Injector` и использует бины описанные в `Properties`.  
Определение зависимостей:
1. Если реализация указана в `Properties`, при создании объекта, будет использована данная реализация.
2. Если реализация не указана в `Properties`, но у объявленного аргумента есть доступные конструкторы, будет использован один из них (см. `@Autowired`)
3. В остальных случаях будет выброшена ошибка `PropertyBasedInjector.NoImplementationDeclaredException`

Заполение файла .properties:
1. Оъявление бина имеет вид: package.declaration=package.implementation  
(пример: com.vanderkast.edu.car.model.Body=com.vanderkast.edu.car.model.WhiteBody)
2. Если аргумент имеет аннотация @Value, то значение должно объявление бина имеет следующий вид: значение_поля_value=значение. Поодерживаются строки, числа, логический тип.  
(пример: car-number=a228ye).  

Система сборки и обработки зависимостей: Gradle 6.3  
