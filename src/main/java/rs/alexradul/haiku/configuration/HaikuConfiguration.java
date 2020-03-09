package rs.alexradul.haiku.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import rs.alexradul.haiku.model.Node;
import rs.alexradul.haiku.springinitializr.SpringInitializrRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HaikuConfiguration {
    private String groupId;
    private String javaVersion;
    private String packaging;
    private Map<String, String> componentMapping = new HashMap<>();

    private static final HaikuConfiguration defaultConfiguration;

    static {
        defaultConfiguration = new HaikuConfiguration();
        defaultConfiguration.setGroupId("com.archetypesoftware");
        defaultConfiguration.setJavaVersion("1.8");
        defaultConfiguration.setPackaging("jar");
        defaultConfiguration.addComponentMapping("gateway", "cloud-zuul, cloud-eureka, actuator");
        defaultConfiguration.addComponentMapping("service-discovery", "cloud-eureka-server");
        defaultConfiguration.addComponentMapping("service-registry", "cloud-eureka-server, cloud-config-server");
        defaultConfiguration.addComponentMapping("service", "web, devtools, actuator, cloud-eureka");
    }

    private void addComponentMapping(String componentType, String mapping) {
        componentMapping.put(componentType, mapping);
    }

    public static HaikuConfiguration getDefaultConfiguration() {
        return defaultConfiguration;
    }

    public String getGroupId() {
        return groupId;
    }

    public HaikuConfiguration setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public Map<String, String> getComponentMapping() {
        return componentMapping;
    }

    public HaikuConfiguration setComponentMapping(Map<String, String> componentMapping) {
        this.componentMapping = componentMapping;
        return this;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public HaikuConfiguration setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
        return this;
    }

    public String getPackaging() {
        return packaging;
    }

    public HaikuConfiguration setPackaging(String packaging) {
        this.packaging = packaging;
        return this;
    }

    public Optional<SpringInitializrRequest> springInitializrRequest(Node node) {
        return node.getApplicationType()
                .flatMap(applicationType -> springInitializrRequest(node, applicationType));
    }

    Optional<SpringInitializrRequest> springInitializrRequest(Node node, String applicationType) {
        Optional<String> dependencyMapping = Optional
                .ofNullable(getComponentMapping().get(applicationType));

        return dependencyMapping
                .map(dependencies -> node
                        .toSpringProject()
                        .setDependencies(dependencies)
                        .setJavaVersion(getJavaVersion())
                        .setPackaging(getPackaging())
                        .setGroupId(getGroupId()));

    }
}
