# Weather App

Aplicación del clima que hace uso del api https://www.metaweather.com/api/ para obtener la información del clima de disntintas ciudades en el mundo y 
poder mostrar multiples climas de ciudades al tiempo a travez de un ViewPager2 



### Abrir y correr Proyecto en  Android studio

1. Abrir android studio , dar click en check out project from version control y elegir github y poner en la ruta https://github.com/nicolasvc/WeatherApp.git y
   esperar que se descargue toda el proyecto y empieze a hacer todo el build del mismo

### Que funcionalidades contiene la aplicación

Poder seleccionar multiples ciudades para visualizar el clima de esta , en el cual se encontrara un información detallada de cada una desde la temperatura minima,maxima

```
- Poder seleccionar multiples ciudades para visualizar el clima de estas

- Información detallada del clima desde Temperatura minima , maxima , actual , 6 dias de reportes posteriores a la fecha de la consulta

- Detalles del clima actual : Temperatura , Visibilidad , Presión , Humedad, Velocidad, Dirección del viento

- Hacer scroll horizontal para poder hacer cambio de las diversas ciudades elegidas 

- Un fondo de color e iconos alusivos al clima actual de la ciudad 
```

## Tecnologia aplicativo

* Se hizo uso de varios componentes de la nuev arquitectura de Jetpack como Room2 ,ViewModel, ViewPager2 , LifeCycle
* Se desarrollo con el fin de que pueda ser mantenible usando el patron MVVM
* Se usaron las librerias Retrofit , Flexbox , Dagger , Picasso y glide pero se descartaron por que las imagenes estaban dañadas para el uso de estas librerias



## Imagenes aplicativo

![SplashScreen](https://user-images.githubusercontent.com/40839023/118240928-2a556900-b461-11eb-875d-1bb2243db6b4.png)
![Captura](https://user-images.githubusercontent.com/40839023/118232648-7fd84880-b456-11eb-9ff5-763a953cceef.PNG)
![CapturaCiudades](https://user-images.githubusercontent.com/40839023/118232848-c62da780-b456-11eb-9392-5c83035c35ef.PNG)
![Day](https://user-images.githubusercontent.com/40839023/118232853-c75ed480-b456-11eb-936e-1c8a81312984.PNG)
![Fog](https://user-images.githubusercontent.com/40839023/118232860-c9c12e80-b456-11eb-82b6-9993c8b84178.PNG)
![detalles](https://user-images.githubusercontent.com/40839023/118232859-c9289800-b456-11eb-9a10-1f37a5df239d.PNG)


## Authors

* Nicolas vergara cifuentes


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


