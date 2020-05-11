# Baking App

Android Developer Nanodegree Baking App.

A Video Recipe App as a part of Advanced Android App Development

>This app will allow a user to select a recipe and see video-guided steps for how to complete it. It requests the data from Internet in form of JSON. The JSON file contains the recipes' instructions, ingredients, videos and images you will need to complete this project. Don’t assume that all steps of the recipe have a video. Some may have a video, an image, or no visual media at all. This app makes use of fragments very efficiently on the tablet as well as phone screen.
The fragments functionalities are being re-used in both screens.


### Used libraries and Concepts

- Fragments
- Exoplayer
- Widgets
- ButterKnife
- Retrofit ( A type-safe HTTP client )
- Scrolling Activity
- Material Design
- JSON Parsing
- RecyclerView with ViewHolder
- Cardview
- Espresso


## How it works

#### Phone
- As the app launches, Home screen with dishes arranged in a cards list appears to the user.
- User taps a dish, the recipe screen opens with ingredients and the steps to cook the dish.
- User clicks on any step in the step-list, gets redirected to the step details screen
- The step detail screen contains video and text instructions for the clicked step.
- If that particular step contains any video instructions, its played automatically, otherwise a placeholder image is displayed with the text instructions
- The user can also navigate between steps in the step details screen through next and previous buttons.
- A homescreen widget is also available that displays ingredients of the last dished viewed on the app.

#### Tablet
- As the app launches, Home screen with dishes arranged in a cards list appears to the user.
- User taps a dish, the recipe screen opens with ingredients and the steps to cook the dish.
- As the tablet screen have much space, the Recipe screen and the step screen as in phone screen are clubbed together as master-list layout
- The screen is divided into left and right pane
- Left pane contains the ingredients list as well the steps of cooking list
- As the user taps a step of cooking the respective step details are displayed on the right pane.
- A homescreen widget is also available that displays ingredients of the last dished viewed on the app.

## Screens
![Alt text](/Screenshots/s1.png?raw=true)
![Alt text](/Screenshots/s2.png?raw=true)
![Alt text](/Screenshots/s3.png?raw=true)
![Alt text](/Screenshots/s4.png?raw=true)
![Alt text](/Screenshots/s5.png?raw=true)
![Alt text](/Screenshots/s6.png?raw=true)
![Alt text](/Screenshots/s7.png?raw=true)
![Alt text](/Screenshots/s8.png?raw=true)
![Alt text](/Screenshots/s9.png?raw=true)
![Alt text](/Screenshots/t1.png?raw=true)
![Alt text](/Screenshots/t2.png?raw=true)
![Alt text](/Screenshots/t3.png?raw=true)
![Alt text](/Screenshots/t4.png?raw=true)
