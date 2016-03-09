# Spring-Event-Bus
## How to use in your project

Example for Maven:
```xml
<build>
	<plugins>
		<plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>aspectj-maven-plugin</artifactId>
            <version>1.6</version>
            <dependencies>
                <dependency>
                    <groupId>org.aspectj</groupId>
                    <artifactId>aspectjrt</artifactId>
                    <version>1.7.2</version>
                </dependency>
                <dependency>
                    <groupId>org.aspectj</groupId>
                    <artifactId>aspectjtools</artifactId>
                    <version>1.7.2</version>
                </dependency>
            </dependencies>
            <executions>
                <execution>
                    <phase>process-sources</phase>
                    <goals>
                        <goal>compile</goal>
                        <goal>test-compile</goal>
                    </goals>
                    <configuration>
                        <showWeaveInfo>true</showWeaveInfo>
                        <complianceLevel>1.6</complianceLevel>
                        <source>${java.build.version}</source>
                        <target>${java.build.version}</target>
                        <Xlint>ignore</Xlint>
                        <Xset>
						    <generateStackMaps>true</generateStackMaps>
						</Xset>
                        <aspectLibraries>
                            <aspectLibrary>
                                <groupId>org.springframework</groupId>
                                <artifactId>spring-aspects</artifactId>
                            </aspectLibrary>
                            <aspectLibrary>
                                <groupId>be.dabla</groupId>
                                <artifactId>spring-event-bus</artifactId>
                            </aspectLibrary>
                        </aspectLibraries>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>

<dependencies>
	<dependency>
	    <groupId>be.dabla</groupId>
	    <artifactId>spring-event-bus</artifactId>
	    <version>1.0</version>
	</dependency>
</dependencies>
```
Maven artifact can be found at [https://oss.sonatype.org/content/repositories/public/be/dabla/spring-event-bus](https://oss.sonatype.org/content/repositories/public/be/dabla/spring-event-bus)
