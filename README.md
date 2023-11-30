# Introduction 
Servicio que se encarga de las siguientes tareas:

-Validación de existencia de patron en BDTU

#Variables de entorno

Para que este microservicio pueda ser ejecutado deber\u00E1n de existir las siguietnes variables de entorno en el SO annfitri\u00F3n donde se ejecute el jar

============== AMBIENTE DE QA ===================

- urlPatronMSValicacionAlmacenes http://serviciosdigitalesinterno-stage.imss.gob.mx/serviciosDigitales-rest/v1/patrones/         
- portMSPatrones 9016
- fileLogPatrones /home/weblogic/mspmc/logs/mspmc-patrones.log

============== AMBIENTE DE UAT ===================

- urlPatronMSValicacionAlmacenes http://serviciosdigitalesinterno-stage.imss.gob.mx/serviciosDigitales-rest/v1/patrones/         
- portMSPatrones 9016
- fileLogPatrones /home/weblogic/mspmc/logs/mspmc-patrones.log

============== AMBIENTE DE PROD ==================

- urlPatronMSValicacionAlmacenes http://serviciosdigitalesinterno.imss.gob.mx/serviciosDigitales-rest/v1/patrones/       
- portMSPatrones 9016
- fileLogPatrones /home/weblogic/mspmc/logs/mspmc-patrones.log

Nota: Ip de serviciosdigitalesinterno.imss.gob.mx = 172.16.23.209:80 en caso de que sea necesario agregarlo al hosts

# Getting Started
TODO: Guide users through getting your code up and running on their own system. In this section you can talk about:
1.	Installation process
2.	Software dependencies
3.	Latest releases
4.	API references

# Build and Test
TODO: Describe and show how to build your code and run the tests. 

# Contribute
TODO: Explain how other users and developers can contribute to make your code better. 

If you want to learn more about creating good readme files then refer the following [guidelines](https://docs.microsoft.com/en-us/azure/devops/repos/git/create-a-readme?view=azure-devops). You can also seek inspiration from the below readme files:
- [ASP.NET Core](https://github.com/aspnet/Home)
- [Visual Studio Code](https://github.com/Microsoft/vscode)
- [Chakra Core](https://github.com/Microsoft/ChakraCore)