# Dapr Workflow Sample

In this example, we'll use Dapr to test workflow features.

Visit [this](https://docs.dapr.io/developing-applications/building-blocks/actors/) link for more information about the Actor pattern.

This example contains the follow classes:

* DemoWorkflow: The interface for the actor. Exposes the different actor features.
* DemoActorImpl: The implementation for the DemoActor interface. Handles the logic behind the different actor features.
* DemoWorkflowService: A Spring Boot application service that registers the actor into the Dapr actor runtime.
* DemoWorkflowClient: This class will create and execute actors and its capabilities by using Dapr.
 
## Pre-requisites

* [Dapr and Dapr Cli](https://docs.dapr.io/getting-started/install-dapr/).
* Java JDK 11 (or greater):
    * [Microsoft JDK 11](https://docs.microsoft.com/en-us/java/openjdk/download#openjdk-11)
    * [Oracle JDK 11](https://www.oracle.com/technetwork/java/javase/downloads/index.html#JDK11)
    * [OpenJDK 11](https://jdk.java.net/11/)
* [Apache Maven](https://maven.apache.org/install.html) version 3.x.

### Checking out the code

Clone this repository:

```sh
git clone https://github.com/dapr/java-sdk.git
cd java-sdk
```

Then build the Maven project:

```sh
# make sure you are in the `java-sdk` directory.
mvn install
```

Get into the examples directory.
```sh
cd examples
```

### Running the Demo workflow service

The first Java class is `DemoWorkflowService`. Its job is to register an implementation of `DemoWorkflow` in the Dapr's workflow runtime engine. In `DemoWorkflowService.java` file, you will find the `DemoWorkflowService` class and the `main` method. See the code snippet below:

```java
public class DemoActorService {

  public static void main(String[] args) throws Exception {
    // Register the Workflow with the runtime.
    WorkflowRuntime.getInstance().registerWorkflow(DemoWorkflow.class);
    WorkflowRuntime.getInstance().start();
  }
}
```

This application uses `WorkflowRuntime.getInstance().registerWorkflow()` in order to register `DemoWorkflow` as a Workflow in the Dapr Workflow runtime.
 

`WorkflowRuntime.getInstance().start()` method will build and start the engine within the Dapr workflow runtime.

Now, execute the following script in order to run DemoWorkflowService:
```sh
dapr run --app-id demoworkflowservice --dapr-grpc-port 4001

java -jar target/dapr-java-sdk-examples-exec.jar io.dapr.examples.actors.DemoWorkflowService
```

### Running the Workflow client

> TODO

Use the follow command to execute the DemoWorkflowClient:

```sh
dapr run ...
```

### Limitations

> TODO
