package rs.alexradul.haiku;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import rs.alexradul.haiku.configuration.HaikuConfiguration;
import rs.alexradul.haiku.model.ProjectObjectModel;
import rs.alexradul.haiku.springinitializr.SpringInitializrRequest;
import rs.alexradul.haiku.springinitializr.SpringInitializrRequestExecutor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.kohsuke.args4j.OptionHandlerFilter.ALL;

public class HaikuInitializr {
    private final ObjectMapper objectMapper = configureObjectMapper();

    @Option(name = "-conf", usage = "Configuration file to use")
    private String configFile;

    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "FieldMayBeFinal"})
    @Argument
    private List<String> arguments = new ArrayList<>();


    private ProjectObjectModel haikuObjectModel;
    private HaikuConfiguration haikuConfiguration;


    public static void main(String[] args) {
        new HaikuInitializr().execute(args);
    }

    private void execute(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            processArguments(args, parser)
                    .execute();

        } catch (CmdLineException e) {
            handleInvalidArgumentsException(e, parser);
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    private HaikuInitializr processArguments(String[] args, CmdLineParser parser) throws CmdLineException, IOException {
        parser.parseArgument(args);

        if (arguments.isEmpty())
            throw new CmdLineException(parser, "No argument is given", null);

        String haikuModelFilename = arguments.get(0);

        haikuObjectModel = loadModel(haikuModelFilename);
        haikuConfiguration = loadConfiguration(configFile);

        return this;
    }

    private void execute() {
        haikuObjectModel.getNodes().stream()
                .map(haikuConfiguration::springInitializrRequest)
                .flatMap(Optional::stream)
                .forEach(this::generateProjects);
    }

    private void generateProjects(SpringInitializrRequest request) {
        SpringInitializrRequestExecutor requestExecutor = new SpringInitializrRequestExecutor(request, objectMapper);
        try {
            requestExecutor.execute(new File(request.getArtifactId()));
        } catch (IOException e) {
            throw new RuntimeException("Cannot process " + request, e);
        }
    }

    private static void httpieSpringStarterRequest(SpringInitializrRequest project) {
        String request = "http https://start.spring.io/starter.zip artifactId==" + project.getArtifactId() +
                " groupId==" + project.getGroupId() +
                " name==" + project.getName() +
                " applicationName==" + project.getApplicationName() +
                " description==" + project.getDescription() +
                " dependencies==" + project.getDependencies();

        System.out.println("********************************************************************");
        System.out.println(request);
    }

    private HaikuConfiguration loadConfiguration(String configFile) throws IOException {
        return StringUtils.isBlank(configFile) ?
                HaikuConfiguration.getDefaultConfiguration() :
                loadConfiguration(new File(configFile));
    }

    private HaikuConfiguration loadConfiguration(File file) throws IOException {
        return objectMapper.readValue(file, HaikuConfiguration.class);
    }

    private ProjectObjectModel loadModel(String haikuModelFilename) throws IOException {
        return objectMapper
                .readValue(
                        new FileInputStream(haikuModelFilename),
                        ProjectObjectModel.class);
    }

    private static ObjectMapper configureObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        return objectMapper;
    }

    private void handleInvalidArgumentsException(CmdLineException e, CmdLineParser parser) {
        System.err.println(e.getMessage());
        System.err.println("java -jar haiku-initializr.jar [options...] arguments...");

        // print the list of available options
        parser.printUsage(System.err);
        System.err.println();

        // print option sample. This is useful some time
        System.err.println("  Example: java -jar haiku-initializr.jar " + parser.printExample(ALL));
    }

    private void handleIOException(IOException e) {
        System.err.println("Cannot execute Haiku Initializr: " + e);
    }
}
