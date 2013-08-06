# Styx

Styx is an extremely simplified implementation of a distributed message queue inspired by Kestrel. It uses one
(or more) standalone Redis servers for persistence. The Redis instances don't need to talk to each other or even know
about each other's existence. Messages in the queue are loosely ordered.

## Install

Version: 0.1.2 (alpha)

### Maven
Styx will be available on Maven Central soon.

###Manual installation
Clone this repo and run `mvn clean assembly:single` in the root. It'll produce an artifact called
`styx-jar-with-dependencies.jar` that can be added to your project by including it on the classpath.

## Use

```java
import com.crowdriff.styx.*;

Styx styx = new Styx(String[]{ "redis1.example.com", "redis2.example.com:9000", "redis3.example.com" });
StyxQueue q = styx.getQueue("myTestQueue");

q.put("Hello World!");
q.size(); // returns 1
q.get(); // returns "Hello World!"

styx.deleteQueue(q);
```

### Unit Tests
To run the unit tests, you must have 3 redis instances on localhost at ports 6700, 6701 & 6702.

## License
Copyright 2013 Abhinav Ajgaonkar

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.