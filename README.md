# wifiboard-android

## Presentation
This is an Android app to control your PC over WiFi. It uses TCP to connect to a NodeJS app on the PC which can be found
[here](https://github.com/Daxidz/wifiboard-desktop).  
I used (RobotJS)[https://github.com/octalmage/robotjs] to control the computer on the NodeJS side.

## Features
* v1.0 (current):  
  * Roman Keyboard without accented characters.
  * Enter, Backspace.
  * Volume up/down with the phone buttons.
  * Mouse control (not very smooth).
  * Support Windows (I didn't check MacOS and Linus for now).
  
## Known bugs
* Socket closing isn't handled well for now.
* Mouse "cycle over" the screen.
