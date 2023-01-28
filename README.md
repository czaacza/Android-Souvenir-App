# Android-Souvenir-App

Android application helping people to keep their memories from visited places.

## App utilities:
- Creating and storing memories each including a picture, title, description and location.
- Showing memories on a map based on their location.

<p float="left">
  <img src="https://github.com/czaacza/Android-Souvenir-App/blob/master/img/Screenshot_20230128-232053_Souvenir%20App.jpg" width="280px"/> 
  <img src="https://github.com/czaacza/Android-Souvenir-App/blob/master/img/Screenshot_20230128-232104_Souvenir%20App.jpg" width="280px"/> 
  <img src="https://github.com/czaacza/Android-Souvenir-App/blob/master/img/Screenshot_20230128-232109_Souvenir%20App.jpg" width="280px"/> 
</p>

## App Architecture
The application will be built in MVVM architecture. This means that it is structured into three main modules:
- **model** - data and business login
- **view** - UI display layer
- **viewModel** - connection between model and view, providing lifecycle management

UI Structure of our application is based on packages containing specific parts of UI:
- **components** - reusable UI components such as buttons, input fields etc.
- **navigation** - classes connected with navigating between different screens
- **screens** - functions presenting different screens of our applications (map/list/create etc.)
- **theme** - material design theme related files

## Contributors
- Mateusz Czarnecki ([czaacza](https://github.com/czaacza))
- Julian Marc ([JulianMarc](https://github.com/JulianMarc))
- Hugo Mater ([hugo66297](https://github.com/hugo66297))

Application project has been created as a part of Sensor Mobile Application course at Metropolia UAS Helsinki.

