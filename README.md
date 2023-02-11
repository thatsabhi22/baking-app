# Baking App

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
![s1](https://user-images.githubusercontent.com/24782276/218275594-034776c5-aaa8-4ef1-9629-1e9e99f12996.png)
![s2](https://user-images.githubusercontent.com/24782276/218275602-59843793-76a2-49c8-915d-49c842c74700.png)
![s3](https://user-images.githubusercontent.com/24782276/218275603-f5c36f55-8a6a-4ef9-9e52-f02cb925c87a.png)
![s4](https://user-images.githubusercontent.com/24782276/218275604-412431e0-9d30-4c4a-b488-d91dc78b40f2.png)
![s5](https://user-images.githubusercontent.com/24782276/218275605-7c53b76d-5aa8-4c15-9200-c507b4c630f5.png)
![s6](https://user-images.githubusercontent.com/24782276/218275606-78aa6e96-d316-4b6e-a200-74f164a4bc2e.png)
![s7](https://user-images.githubusercontent.com/24782276/218275609-44fa98b0-cf28-43f6-87c3-9bb125632b99.png)
![s8](https://user-images.githubusercontent.com/24782276/218275610-18542927-a936-4787-95fb-b4d0db5173dc.png)
![s9](https://user-images.githubusercontent.com/24782276/218275612-2e1cb3cf-8997-4bbd-88b9-9b479610382d.png)
![t1](https://user-images.githubusercontent.com/24782276/218275613-de30b49b-2e4f-46ee-8da7-c04da16e7316.png)
![t2](https://user-images.githubusercontent.com/24782276/218275615-a3f0cc5e-8ed6-4a50-b936-f75e09f82b43.png)
![t3](https://user-images.githubusercontent.com/24782276/218275616-572354e1-e00c-4076-9370-172dcd2370ec.png)
![t4](https://user-images.githubusercontent.com/24782276/218275618-cefc1589-2c19-4058-b758-35fa527cbae2.png)

