# MiniBank (Practical 1-9)

Console banking application. Packages: `model`, `exception`, `util`, `service`.

## Compile and run
    javac -d out model/*.java model/annotation/*.java exception/*.java util/*.java service/*.java
    java -cp out service.MiniBank

## Build and run the JAR
    jar cfm MiniBank.jar manifest.txt -C out .
    java -jar MiniBank.jar

Type `5` at the menu to exit; the demos for Practical 2-9 then run.
