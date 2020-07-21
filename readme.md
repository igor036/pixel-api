# ✨ - ***Pixel-Api***  


## ***Pixel-Api*** is an image processing API created using ***OpenCV***, ***Java***, ***Spring Boot*** and ***Docker***.

### The API has some filters and image manipulations.


# 🎯 - How it works ?
This API is running into a docker container, created by the image <a href="https://hub.docker.com/r/asuprun/opencv-java">***asuprun/opencv-java***</a> that have a binary of ***Opencv*** and ***JDK 8*** installed.

The ***Pixel-Api*** use the <a href="https://github.com/openpnp/opencv">***org.openpnp***</a> dependency with a opencv JNI interface.

    ⚠ So you don't need to: 
        ↪ Have OpenCV installed. 
        ↪ Set up the native library in your IDE (link native file in your JNDI lib).

# 🏃 - How to run ?
To run the project, you will need have the ***Docker*** and if you want to rebuild ***.jar*** file you need ***Maven*** installed on your PC

    ↪ If you only want to run the current build .jar file

        ▶ docker-compose up --build
    
    ↪ If you want to run all unit test and build .jar file and run.
        
        (⚠ Need the JDK 8 and Maven installed.)

        ▶ mvn -DskipTests=false -Pbuild; docker-compose up --build

    ↪ If you want build .jar file for run without run unit test.
        
        (⚠ Need the JDK 8 and Maven installed.)

        ▶ mvn -DskipTests=false -Pbuild; docker-compose up --build

# 🐞 - How to debug ?
For debugging the project, you need to configure ***remote debug***, in your IDE listen, the ***localhost:5005***

VScode: https://code.visualstudio.com/docs/editor/debugging <br/>
Intelij: https://code.visualstudio.com/docs/editor/debugging <br/>
Eclipse: https://docs.alfresco.com/5.2/tasks/sdk-debug-eclipse.html 
