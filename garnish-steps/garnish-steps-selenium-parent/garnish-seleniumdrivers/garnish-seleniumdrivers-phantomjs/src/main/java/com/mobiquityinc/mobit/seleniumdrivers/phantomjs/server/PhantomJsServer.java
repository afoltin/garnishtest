package com.mobiquityinc.mobit.seleniumdrivers.phantomjs.server;

import com.google.common.collect.ImmutableList;
import com.mobiquityinc.mobit.seleniumdrivers.phantomjs.server.finder.PhantomJsClasspathLocationFinder;
import com.mobiquityinc.mobit.seleniumdrivers.utils.ClassPathExtractor;
import com.mobiquityinc.mobit.seleniumdrivers.utils.OsUtils;
import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Level;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class PhantomJsServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhantomJsServer.class);

    private static final String SERVER_LOGGER_NAME = PhantomJsServer.class.getName() + ".PHANTOM_JS";
    private static final Level SERVER_OUTPUT_USE_LOG_LEVEL = Level.DEBUG;

    @NonNull private final Path phantomJsExecutable;
    private final int port;
    @NonNull private final ImmutableList<String> additionalOptions;

    public PhantomJsServer(@NonNull final Path phantomJsExecutable,
                           final int port,
                           @Nullable final List<String> additionalOptions) {
        if (!Files.exists(phantomJsExecutable)) {
            throw new IllegalArgumentException("phantomJsExecutable [" + phantomJsExecutable.toAbsolutePath() + "] does not exist");
        }
        if (!Files.isRegularFile(phantomJsExecutable)) {
            throw new IllegalArgumentException("phantomJsExecutable [" + phantomJsExecutable.toAbsolutePath() + "] is not a file");
        }

        this.phantomJsExecutable = phantomJsExecutable;
        this.port = port;
        this.additionalOptions = ImmutableList.copyOf(
                ObjectUtils.defaultIfNull(additionalOptions, Collections.<String>emptyList())
        );
    }

    @NonNull
    public static PhantomJsServer exeFromClasspath(final int port,
                                                   @Nullable final String... additionalOptions) {
        final List<String> additionalOptionsList;
        if (additionalOptions == null) {
            additionalOptionsList = Collections.emptyList();
        } else {
            additionalOptionsList = Arrays.asList(additionalOptions);
        }

        return exeFromClasspath(port, additionalOptionsList);
    }

    @NonNull
    public static PhantomJsServer exeFromClasspath(final int port,
                                                   @Nullable final List<String> additionalOptions) {
        return new PhantomJsServer(
                getPhantomJsTempPath(),
                port,
                additionalOptions
        );
    }

    @NonNull
    private static Path getPhantomJsTempPath() {
        final PhantomJsClasspathLocationFinder finder = new PhantomJsClasspathLocationFinder();
        final ClassPathExtractor classPathExtractor = new ClassPathExtractor();

        final String classpathLocation = finder.getClasspathLocationForCurrentOperatingSystem();

        LOGGER.debug("Based on the current OS, PhantomJS binary is the classpath at [{}]; extracting to temp dir", classpathLocation);
        final Path fileToExecute = classPathExtractor.extractToTemp(classpathLocation, "phantomjs-");

        if (OsUtils.isLinux() || OsUtils.isMac()) {
            LOGGER.debug("PhantomJS binary extracted to [{}]; marking it as executable", fileToExecute.toAbsolutePath());

            final boolean success = fileToExecute.toFile().setExecutable(true);
            if (!success) {
                throw new IllegalArgumentException("failed to make extracted file executable");
            }
        }

        return fileToExecute;
    }

    /**
     * Starts the PhantomJS WebDriver server.
     * The server will be automatically stopped when the JVM exits
     */
    public void start() {
        try {
            tryToStart();
        } catch (final Exception e) {
            throw new PhantomJsServerException("failed to start PhantomJs WebDriver server", e);
        }
    }

    private void tryToStart() throws IOException {
        final ImmutableList.Builder<String> commandItems = ImmutableList.builder();
        commandItems.add(phantomJsExecutable.toAbsolutePath().toString());
        commandItems.add("--webdriver=" + port);
        commandItems.addAll(additionalOptions);

        final ImmutableList<String> commandLine = commandItems.build();

        LOGGER.info("Executing [{}]; sub-process will be stopped when this JVM stops", commandLine);

        new ProcessExecutor()
                .command(commandLine)
                .redirectOutput(
                        Slf4jStream.of(SERVER_LOGGER_NAME)
                                   .as(SERVER_OUTPUT_USE_LOG_LEVEL)
                )
                .destroyOnExit()
                .start();
    }

}
