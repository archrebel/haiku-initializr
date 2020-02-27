/*
 * Copyright (c) 2020.
 */

package rs.alexradul.haiku.springinitializr;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class SpringInitializrProject contains options that are recognized by the Spring Initializr.
 */
public class SpringInitializrRequest {
    private String artifactId;
    private String groupId;
    private String name;
    private String applicationName;
    private String description;
    private String dependencies;
    private String javaVersion;
    private String packaging;

    public String getArtifactId() {
        return artifactId;
    }

    public SpringInitializrRequest setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public SpringInitializrRequest setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getName() {
        return name;
    }

    public SpringInitializrRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SpringInitializrRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public SpringInitializrRequest setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public String getDependencies() {
        return dependencies;
    }

    public SpringInitializrRequest setDependencies(String dependencies) {
        this.dependencies = dependencies;
        return this;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public SpringInitializrRequest setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
        return this;
    }

    public String getPackaging() {
        return packaging;
    }

    public SpringInitializrRequest setPackaging(String packaging) {
        this.packaging = packaging;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("artifactId", artifactId)
                .append("groupId", groupId)
                .append("name", name)
                .toString();
    }
}
