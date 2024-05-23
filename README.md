# Bill of Materials Maven Plugin

[![License](https://img.shields.io/github/license/eitco/bom-maven-plugin.svg?style=for-the-badge)](https://opensource.org/license/mit)
[![Build status](https://img.shields.io/github/actions/workflow/status/eitco/bom-maven-plugin/deploy.yaml?branch=main&style=for-the-badge&logo=github)](https://github.com/eitco/bom-maven-plugin/actions/workflows/deploy.yaml)
[![Maven Central Version](https://img.shields.io/maven-central/v/de.eitco.cicd/bom-maven-plugin?style=for-the-badge&logo=apachemaven)](https://central.sonatype.com/artifact/de.eitco.cicd/bom-maven-plugin)


This goal scans the current project and creates a bill of materials (bom) pom containing the current project 
and all of its (sub-) modules with the current version as dependency management.

activate it in the following way:

```xml
<project>
    ...
    <build>
        ...
        <plugins>
            ...
            <plugin>
                <groupId>de.eitco.cicd</groupId>
                <artifactId>bom-maven-plugin</artifactId>
                <version>5.0.0</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>create</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
Check for the latest version [here](https://central.sonatype.com/artifact/de.eitco.cicd/bom-maven-plugin). This enables the following goals:


It can be customized with the following parameters:

## additionalBoms

This parameter specifies a list of additional pom files whose dependency management 
will be included in the generated bom.

Consider using the [flatten-maven-plugin](https://github.com/mojohaus/flatten-maven-plugin) to generate a pom containing the projects dependency management and 
specifying the generated file as `additionalBom`.

## typesByPackaging

This parameter holds a map of artifact types indexed by their packaging. The plugin needs this map to deduce
the types of artifacts given their packaging. The default types (pom, jar, war) are always known. Use this 
parameter if you have some custom packaging to use in your bom.