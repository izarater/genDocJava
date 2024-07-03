Guía de Compilación y Uso


Requisitos Previos


Programas Necesarios

1. Java Development Kit (JDK) 8 o superior

Asegúrate de que JAVA_HOME esté configurado correctamente y que el directorio bin del JDK esté en tu PATH.

2. wkhtmltopdf

Instala wkhtmltopdf y asegúrate de que el directorio bin de wkhtmltopdf esté en tu PATH.

3. Graphviz

Instala Graphviz y asegúrate de que el directorio bin de Graphviz esté en tu PATH.


Librerías Necesarias

Asegúrate de que las siguientes librerías se encuentren en la carpeta lib de tu proyecto:

antlr-runtime-4.13.1
batik-all-1.14.jar
batik-dom-1.14.jar
batik-ext-1.14.jar
batik-svg-dom-1.14.jar
batik-svggen-1.14.jar
batik-util-1.14.jar
batik-xml-1.14.jar
commons-exec-1.3.jar
graphviz-java-0.18.1.jar
j2v8_win32_x86_64-4.6.0.jar
jacocoagent.jar
jacococli.jar
org.jacoco.agent-0.8.7.jar
org.jacoco.agent-0.8.12.202403310830.jar
org.jacoco.cli-0.8.7-nodeps.jar
plantuml-1.2024.5.jar
slf4j-api-1.7.36.jar
slf4j-simple-1.7.36.jar
xml-apis-ext-1.3.04.jar
xmlgraphics-commons-2.6.jar



Compilación y Ejecución en IntelliJ IDEA


Configuración

Abrir el Proyecto en IntelliJ IDEA

Abre IntelliJ IDEA y selecciona "Open" para abrir el proyecto GenDoc.

Instala el plugin de ANTLR V4 buscándolo en la rueda de Configuración>Plugins

Agregar todas las librerías con clic derecho en la carpeta del proyecto, luego Open Module Setting y en el + en Dependencies, finalmente clic en Apply y clic en OK

Configurar el Archivo de Entrada

En el menú superior, ve a Run > Edit Configurations.
En la configuración de Application, cambia el valor de Program Arguments para que apunte al archivo de entrada que deseas procesar, por ejemplo: input/UserManagementSystem2.java

Compilación

Compilar el Proyecto

En IntelliJ IDEA, selecciona Build > Build Project en el menú superior para compilar todos los archivos fuente.

Ejecución

Ejecutar el Generador de Documentación

En IntelliJ IDEA, selecciona Run > Run 'JavaDocGenerator' para ejecutar el generador de documentación.


Salida

Los archivos generados se guardarán en la carpeta output:

documentation.html: Documentación generada en formato HTML.
documentation.pdf: Documentación generada en formato PDF.
documentation.txt: Documentación generada en formato de texto plano.
classDiagram.png: Diagrama de clases.
sequenceDiagram.png: Diagrama de secuencia.
activityDiagram.png: Diagrama de actividad.
jacoco-report/: Reporte de cobertura de código.


¡Eso es todo! Ahora deberías poder compilar y ejecutar tu proyecto fácilmente para generar documentación de tus archivos Java.