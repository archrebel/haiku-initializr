package rs.alexradul.haiku.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import rs.alexradul.haiku.springinitializr.SpringInitializrRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "label",
        "group",
        "shape",
        "icon",
        "visible",
        "properties",
        "type",
        "note",
        "count",
        "width",
        "height",
        "fontsize"
})
public class Node {

    @JsonProperty("name")
    private String name;
    @JsonProperty("label")
    private String label;
    @JsonProperty("group")
    private String group;
    @JsonProperty("shape")
    private String shape;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("visible")
    private Boolean visible;
    @JsonProperty("properties")
    private List<Property> properties = null;
    @JsonProperty("type")
    private String type;
    @JsonProperty("note")
    private String note;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("width")
    private String width;
    @JsonProperty("height")
    private String height;
    @JsonProperty("fontsize")
    private String fontsize;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("group")
    public String getGroup() {
        return group;
    }

    @JsonProperty("group")
    public void setGroup(String group) {
        this.group = group;
    }

    @JsonProperty("shape")
    public String getShape() {
        return shape;
    }

    @JsonProperty("shape")
    public void setShape(String shape) {
        this.shape = shape;
    }

    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    @JsonProperty("icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @JsonProperty("visible")
    public Boolean getVisible() {
        return visible;
    }

    @JsonProperty("visible")
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @JsonProperty("properties")
    public List<Property> getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("note")
    public String getNote() {
        return note;
    }

    @JsonProperty("note")
    public void setNote(String note) {
        this.note = note;
    }

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("width")
    public String getWidth() {
        return width;
    }

    @JsonProperty("width")
    public void setWidth(String width) {
        this.width = width;
    }

    @JsonProperty("height")
    public String getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(String height) {
        this.height = height;
    }

    @JsonProperty("fontsize")
    public String getFontsize() {
        return fontsize;
    }

    @JsonProperty("fontsize")
    public void setFontsize(String fontsize) {
        this.fontsize = fontsize;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Optional<String> getApplicationType() {
        return getProperties().stream()
                .filter(Property::isApplicationType)
                .findFirst()
                .map(Property::getValue);
    }

    public SpringInitializrRequest toSpringProject() {
        SpringInitializrRequest project = new SpringInitializrRequest();
        project.setName(getName());
        project.setArtifactId(getName().toLowerCase().replace(" ", "-"));
        project.setApplicationName(getName().replace(" ", "") + "App");
        project.setDescription(getNote());
        return project;
    }
}
