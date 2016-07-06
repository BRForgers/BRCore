# BRCore

Unstable builds: [Go to **Jenkings**](http://jenkins.cyberdynecc.com/job/BRCore/)

License: [BRForgers **DBaJ**](https://github.com/BRForgers/BRCore/blob/master/LICENSE.md)

## Building the Core:
Before start:
* Know that `./` is the folder that has the folder `src` and the files `gradlew`, `gradlew.bat` and `build.gradle`
* If you are on Windows, replace `./gradlew` with `gradlew.bat`

(***If not yet,*** `./gradlew setupCIWorkspace` at least. (You can also do either `setupDevWorkspace` or `setupDecompWorkspace` instead, but it will take more time.))

***EXECUTE*** `./gradlew build`

In `./build/libs` you'll find:
`BRCore.jar` is for playing
`BRCore-dev.jar` is for modding (Also have the sources inside)
