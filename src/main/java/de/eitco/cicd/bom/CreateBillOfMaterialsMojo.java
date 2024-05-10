/*
 * Copyright (c) 2023 EITCO GmbH
 * All rights reserved.
 *
 * Created on 12.01.2023
 *
 */
package de.eitco.cicd.bom;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.eitco.cicd.bom.xml.BillOfMaterials;
import de.eitco.cicd.bom.xml.Dependency;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mojo(name = "create", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class CreateBillOfMaterialsMojo extends AbstractBillOfMaterialsMojo {

    public static final Map<String, String> DEFAULT_TYPES_BY_PACKAGING = new HashMap<>() {
        {
            put("pom", "pom");
            put("jar", "jar");
            put("war", "war");
        }
    };

    @Parameter
    private List<File> additionalBoms = new ArrayList<>();

    @Parameter
    private Map<String, String> typesByPackaging = new HashMap<>();

    private Map<String, String> usedTypesByPackaging;

    public Map<String, String> getTypesByPackaging() {

        if (usedTypesByPackaging != null) return usedTypesByPackaging;

        usedTypesByPackaging = typesByPackaging;
        usedTypesByPackaging.putAll(DEFAULT_TYPES_BY_PACKAGING);

        return usedTypesByPackaging;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        BillOfMaterials billOfMaterials = new BillOfMaterials();

        billOfMaterials.setGroupId(groupId);
        billOfMaterials.setArtifactId(artifactId);
        billOfMaterials.setVersion(version);

        addProject(billOfMaterials, project);

        for (MavenProject collectedProject : project.getCollectedProjects()) {

            addProject(billOfMaterials, collectedProject);
        }

        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Map<String, Dependency> dependencies = new LinkedHashMap<>();

        billOfMaterials.getDependencyManagement().getDependencies().forEach(dependency -> {

            dependencies.put(dependency.dependencyKey(), dependency);
        });

        try {

            for (File file : additionalBoms) {

                final BillOfMaterials bom = mapper.readValue(file, BillOfMaterials.class);

                bom.getDependencyManagement().getDependencies().forEach(dependency -> {

                    dependencies.put(dependency.dependencyKey(), dependency);
                });
            }

            billOfMaterials.getDependencyManagement().setDependencies(new ArrayList<>(dependencies.values()));

            FileUtils.forceMkdir(targetFile.getParentFile());

            mapper.writeValue(targetFile, billOfMaterials);

        } catch (IOException e) {

            throw new MojoExecutionException(e.getMessage(), e);
        }

    }

    private void addProject(BillOfMaterials billOfMaterials, MavenProject collectedProject) {

        String type = getTypesByPackaging().get(collectedProject.getPackaging());

        if (type == null) {

            return;
        }

        if (type.equals("jar")) type = null;

        final Dependency dependency = new Dependency();

        dependency.setGroupId(collectedProject.getGroupId());
        dependency.setArtifactId(collectedProject.getArtifactId());
        dependency.setVersion(collectedProject.getVersion());
        dependency.setType(type);

        billOfMaterials.getDependencyManagement().getDependencies().add(dependency);
    }
}
