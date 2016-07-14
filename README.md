# BRCore (Branch 1.7.10)

[![Build Status](http://jenkins.cyberdynecc.com/job/BRCore/badge/icon)](http://jenkins.cyberdynecc.com/job/BRCore/)

Unstable builds: [Go to **Jenkins**](http://jenkins.cyberdynecc.com/job/BRCore/)

1.7.10 is now over. Currently, this branch will stay here for (maybe) future Bugfixes.

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
