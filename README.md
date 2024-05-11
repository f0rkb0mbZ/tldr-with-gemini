# TL;DR with Gemini
Document Summarizer AI with Google Gemini
<img width="1369" alt="image" src="https://github.com/f0rkb0mbZ/tldr-with-gemini/assets/32464867/be2ec1fa-9f35-4cff-a414-9d3e93c71010">



## How to run
There are several ways to run this application. But firstly - 
1. Go to [Google AI Studio](https://aistudio.google.com/app) and generate your API Key.
2. Store the key somewhere safe.
### Run with JRE/JDK
1. Download JDK / JRE (version 21 recommended) from [here](https://www.oracle.com/in/java/technologies/downloads/) and install it.
2. Download the JAR from the [release](https://github.com/f0rkb0mbZ/tldr-with-gemini/releases) page.
3. Run like this in your shell:
    ```bash
    $ java -jar -DAPI_KEY=<your_api_key> tldr-0.0.1-SNAPSHOT.jar
    ```
4. Visit [localhost:8080](http://localhost:8080) and enjoy!

### Run with Docker
1. Download and install [docker](https://www.docker.com/products/docker-desktop/).
2. Download the source code.
3. Open a shell and go inside the source code directory.
4. Run the following commands to build and run the application:
    ```bash
   $ docker build -t tldr .
   $ docker run -it -p 8080:8080 -e API_KEY='<your_api_key' tldr
   ```
5. Visit [localhost:8080](http://localhost:8080) and enjoy!
