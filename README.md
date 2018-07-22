# Resumable FileDownloader

'fdownloader' is a project which helps downloading of resources like (http, https, ftp etc ...)
NOTE: Currently Only supports HTTP resources

### Prerequisites

Java 8 and Maven 3

### Installing

Use maven to create executable jar as follows: 
```
mvn clean install
```

### Example
 
```
java -jar target/fdownloader-1.0-SNAPSHOT-jar-with-dependencies.jar <HTTP_RESOURCE> -o <OUTPUT_FILE_PATH>
```
##### Example 1 with download url and outputfile path
```
java -jar ./target/fdownloader-1.0-SNAPSHOT-jar-with-dependencies.jar http://ftp.halifax.rwth-aachen.de/apache/tomcat/tomcat-8/v8.5.32/src/apache-tomcat-8.5.32-src.zip -o ~/apache-tomcat-8.zip 
```

##### Example 2 with only download url, which will derive the filename from download url and will download to current directory
```
java -jar ./target/fdownloader-1.0-SNAPSHOT-jar-with-dependencies.jar http://ftp.halifax.rwth-aachen.de/apache/tomcat/tomcat-8/v8.5.32/src/apache-tomcat-8.5.32-src.zip
```

## Running the tests

```
mvn clean verify
```
