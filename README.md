# play-parking-pi
A parking lot management system, implemented with Arduino and Raspberry pi.

Companion project of [https://github.com/AL333Z/arduino-parking](arduino-parking)

##This is still a WIP, so you may wait some days until i complete a decent documentation. Or, if yuo are an hacker, just look at the code <3

##What is this
A dead simple app made with play framework, that can run on a raspberry pi.

## Circuit
The raspberry pi has one green led connected on GPIO 1 and one red lead on GPIO0.

//TODO add fritzing scheme

## Up and running
- clone or download the project
- `cd <your folder>`
- `play dist` or `sbt dist`
- unzip and move the folder to raspberry pi
- cd `your folder on raspberry`
- `chmod +x bin/play-parking-pi`
- `sudo ./bin/play-parking-pi -J-Xms64M -J-Xmx128M`
