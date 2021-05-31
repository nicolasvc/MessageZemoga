# Message Zemoga

Aplicación con el fin de poder visualizar multiples post consumiendo el api de https://jsonplaceholder.typicode.com/ como adición visualizar los comentarios de los usuarios con su 
respectiva foto de perfil , seleccionar cualquier post como favorito y tambien marcarlo como leido y de igual forma tener su propia sección de favoritos para visualizarlos




### Abrir y correr Proyecto en  Android studio

1. Abrir android studio , dar click en check out project from version control y elegir github y poner en la ruta https://github.com/nicolasvc/MessageZemoga.git y
   esperar que se descargue toda el proyecto y empieze a hacer todo el build del mismo

### Que funcionalidades contiene la aplicación

Poder visualizar 100 posts de usuarios a travez de una paginación de 20 en 20 para hacer la lectura mas sencilla para el usuario

```
- Poder seleccionar que post seran los favoritos por el usuario

- Eliminar todos los post de una vez o eliminar uno a uno a travez de un swipe a la izquiera o derecha sobre la card del pos

- Visualizar los comentarios de los usuarios sobre un post especifico y acceder a la información de contacto con el celular que escribio el post

- Contiene un OnBoarding de la actividad que explica y simplifica que es la aplicación 

- Almacenar los post que son favoritos y demas
```

## Tecnologia aplicativo

* Se hizo uso de varios componentes de la nueva arquitectura de Jetpack como Room2 ,ViewModel, ViewPager2 , LifeCycle
* Se desarrollo con el fin de que pueda ser mantenible usando el patron MVVM
* Se usaron las siguientes  librerias : 
* Retrofit & Gson : La encargada de poder consumir los servicios REST y a su vez deserializarlos para volverlos objetos concretos
* RxJava : Para acceder a los observadores y asi tener mayor control sobre las peticiones realizadas a travez de observador tipo Single 
* Room : Se uso con el fin de poder almacenar la información relevante para el usuario y garantizar un buen funcionamiento de la aplicación
* Image circle : Libreria que se uso para poder dar un toque estetico a las fotos de perfil de los usuarios
* Shrim : Libreria que se uso para que sea nuestro objeto de carga en la aplicación


## Imagenes aplicativo


![post](https://user-images.githubusercontent.com/40839023/120142608-25ecb800-c1a4-11eb-8c70-193d7cad3f11.PNG)
![Cortinas](https://user-images.githubusercontent.com/40839023/120142609-26854e80-c1a4-11eb-8552-5b311c584cf9.PNG)
![Description](https://user-images.githubusercontent.com/40839023/120142610-26854e80-c1a4-11eb-82e2-25052682b6c2.PNG)
![favorites](https://user-images.githubusercontent.com/40839023/120142611-26854e80-c1a4-11eb-8f3a-1aaeeb4e8985.PNG)
![OnBoarding](https://user-images.githubusercontent.com/40839023/120142612-271de500-c1a4-11eb-93ad-52171827a27c.PNG)


## Authors

* Nicolas vergara cifuentes


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

