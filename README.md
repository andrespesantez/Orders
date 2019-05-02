# Orders API
Tecnologías: Spring-boot + JPA (Hibernate) + H2 + REST + docker config

## Abstract
Aplicación basada en un sencillo sistema que consta de productos, pedidos de dichos productos, moneda y control de stock.
La aplicación esta construida con SpringBoot, versión 2.1.4 y h2 como base de datos para produccion y testing. 
En si es una API REST CRUD con métodos para acceder a los detalles de los productos y ordenes.
Incluye la configuración para poder ser desplegada en un contenedor Docker. Para ello incluye el DokerFile
y la dependencia necesaria.

###Servicios Rest
Los Servicio Rest son el punto de acceso desde el exterior. 

###Fixer API

Fixer API es una fuente de datos de tipo de cambio, la API Fixer es capaz de entregar datos de tipo de cambio en tiempo real , en el el plan FREE la moneda base es EUR.

Los parametros necesario son los siguientes:

API Key

Su clave de API es la clave única que se pasa al parámetro clave de acceso de URL base de la API para autenticarse con la API del reparador.

Base URL:

http://data.fixer.io/api/

Agregue su clave de API: Aquí se explica cómo autenticar con la API del Fixer:

http://data.fixer.io/api/latest?access_key=API_KEY&symbols=USD,EUR,CHF,GBP

Para la configuracion del APPI tenemos la clase FixerAPI.java, la cual implementa las peticiones al servicio.

###H2 Database

H2 es un motor de bases de datos SQL que se escribe en Java™ que implementa la API de JDBC. Se incluye una aplicación de consola basada en navegador.

Para acceder a la consola:

http://localhost:8080/h2-console

url: jdbc:h2:mem:test
user: sa
pass: sa


### Test unitarios: Probando los servicios
Para realizar los test, existe la clase OrdersApplicationTests.

Para ejecutar test, cargamos un dataset inicial. En el directorio src/test/resources, incluimos un fichero data.sql con los insert de los datos. 

### Documentando la aplicación con Swagger
Swagger es un framework que permite tanto documentar apis como crearlas. De igual forma, una API como es la nuestra, con las anotaciones de Swagger hace que el framework genere una UI accesible desde la web, capaz de explicar que hace cada método así como lanzar peticiones.

Para documentar usaremos varias anotaciones. Existen tanto para los controladores, como para las clases de modelo. Si observamos la UI, a la que podemos acceder mediante http://localhost:8080/api/swagger-ui.html, vemos que contiene anotaciones personalizadas tanto de lo que hace cada método como de las posibles respuestas. Además las clases de modelo que aparecen abajo, también tienen documentados sus atributos. Todo esto se logra gracias a las anotaciones @ApiOperation, @ApiResponse y @ApiModelProperty. Existen más, pero en este ejemplo son las que se han utilizado para manejar incluir Swagger.

## Construyendo la aplicación
La aplicación está lista para su despliegue y construcción. Debemos tener maven instalado en el ordenador. Si todo es correcto, podemos ejecutar el comando que aparece debajo. Con el se construye la aplicación y se crea el .jar listo para su despliegue.
```sh
mvn clean install

java -jar app-name.jar
```

### Construir docker
`./mvn install dockerfile:build`

### Visializar docker imagenes
`docker images`

### Correr nuestra imagen
`docker run -p 8080:8080 -t orderdocker/orders`