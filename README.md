# Bishop Scores

This is a complete redesign of the Bishop Scores app for Android. This app was written in collaboration with Joshua Steinberg.

The primary goal in creating this app was to create a fully functional, shippable product within the project deadline. The secondary goal was to try to create an app foundation that is solid, allowing for future additions and features. There are many little touches added to the app that are often taken for granted.

Cool things:
* All controls, such as the calculator buttons, are customized to best fit the look of the app. Not just basic things like the background color, but also interactions like the color when tapped.
* Every piece of text in the app is stored in the "strings.xml" file. This means translating this app is extremely trivial and can easily be done without having to change any code at all.
* The app targets the most recent version of Android, but supports Android 5.0, which is now 4 years old. This allows for a extremely wide range of devices to run on.
* All layouts in this app are made with different screen sizes in mind. All views are reactive and change shape and size to best fit on the screen. The app scales horizontally perfectly, and allows the user to scroll vertically if their phone size is really that small.
* This app is also forward thinking. The app icon uses the new "Adaptive Icon" feature added to Android 8, Oreo. This allows the user to change the icon of the app to whatever shape they please. The app will fallback to a legacy icon if detected to be running on an older version of Android.
* I honest to God tried my best to follow Material Design Guidelines. The color choices in the app are all made intentionally, in order to provide visual clarity.