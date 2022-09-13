# JAVA Grep App

## Introduction

The Java Grep tool is designed to search through all files recursively under a specified directory with given text pattern. The function of it is same as `grep` command in Linux.

The project was written in Java 8 and utilizes its features such as Lambdas and Streams for efficient data processing.

Techonologies that been used: Java8, Maven, Lambdas and Streams API, Docker.


## Quick Start
### How to use the app? 
- 1st Approach: Creating jar package using Maven

1.Compile and package the Java code using Maven(Maven download:[https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi))
```
mvn clean compile package
```

2.Run the jar file, with the following arguments:  
```
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp <regex> <inputFilePath> <outFilePath>
# <regex>:a special text string for describing a search pattern
# <inputFilePath>:the path of the directory/file to be searched
# <outFilePath>:the path to the file where the search result is written to.
```

- 2nd Approach: Using a prebuilt Docker image:

1.pull the docker image from DockerHub   
```
docker pull dj717/grep
```

2.start a container from that image 
```
docker run --rm \ 
-v <local_data_path>:<container_data_path> \
-v <local_output_path>:<container_output_path> \
dj717/grep <regex_pattern> <inputFilePath> <outFilePath>
```
- <local_xxx_path>: the entire path of shared folder on local machine
- <container_xxx_path>:the path within the container where shared folder can be found


## Implemenation
In this app, we have `process` method which is for high-level workflow purpose (like a state machine). It should be easy to read and calling helper functions which contains detailed implementation.

### The `process` method Pseudocode
```Java
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```
- Write a main method to handle CLI(Command Line.) arguments.
- Save argument to private member variables via Getters and Setters
- Use self4j to log any message

### Performance Issue
The execution may encouter the `OutOfMemoryError` exception due to the large data file reading and limited JVM heap memory. We can fix it by using Java Streams instead of for-loop to load files not in one time. It allows lazy processing of lines in a memory-efficient way.This solution is implemented in `JavaGrepLambdaImp.java` file.

## Test
The application is tested manually by running in on sample files and verifying the output result. Additionally, the application is tested with incorrect inputs (such as nonexistent and unreadable files) to ensure that it only processes the correct inputs and is capable of handling exceptions.

## Deployment
1.The Dockerfile is used to create the image

2.Package the java app
```
mvn clean package
```

3.Build a new docker image locally
```
docker_user=dj717
docker build -t ${docker_user}/grep .
```

4.Push the image to Docke Hub
```
docker push ${docker_user}/grep
```
## Improvement
- Testing it with different sizes of data file, running it on the `JavaGrepImp.java` and `JavaGrepLambdaImp.java` respectively. 
- To optimize memory usage, considering updating the interface (or create a new one). For instance, certain methods (e.g. readLines and listFiles) shall return a stream (lazy operation) rather than a List (data stored in memory).
- Additional arguments can be added,like the count of the matching.
