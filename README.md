# Android-Souvenir-App

Android application project created as a part of Sensor Mobile Application course at Metropolia UAS Helsinki

## How to work with an app

Remember to put the changes only on **your own branch**

Remember to always **pull the master branch** containing the newest version of the application before working on a new utility.

Remember to always **communicate** about ending the work on a utility by calling a pull request or messaging the admin. This way, your version of the code can be merged to the master branch.

## App Architecture
The application will be built in MVVM architecture. This means that it is structured into three main modules:
- **model** - data and business login
- **view** - UI display layer
- **viewModel** - connection between model and view, providing lifecycle management

### UI Structure
UI Structure of our application is based on packages containing specific parts of UI:
- **components** - reusable UI components such as buttons, input fields etc.
- **navigation** - classes connected with navigating between different screens
- **screens** - functions presenting different screens of our applications (map/list/create etc.)
- **theme** - material design theme related files

## Contributors
- Mateusz Czarnecki ([czaacza](https://github.com/czaacza))
- Julian Marc ([JulianMarc](https://github.com/JulianMarc))
- Hugo Mater ([hugo66297](https://github.com/hugo66297))
