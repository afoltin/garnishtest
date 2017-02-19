package org.garnishtest.steps.db;

import org.garnishtest.modules.generic.db_util.scripts.DbScriptsExecutor;
import org.garnishtest.modules.it.test_utils_dbunit.compare.database.DbUnitDatabaseComparer;
import org.garnishtest.modules.it.test_utils_dbunit.insert.DbUnitInserter;
import org.garnishtest.steps.vars.resource_files_vars.ResourceFilesVariables;
import org.garnishtest.steps.vars.scenario_user_vars.ScenarioUserVariables;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Collections;
import java.util.List;

public class DatabaseSteps {

    @NonNull private final DbScriptsExecutor dbScriptsExecutor;
    @NonNull private final DbUnitInserter dbUnitInserter;
    @NonNull private final DbUnitDatabaseComparer dbUnitDatabaseComparer;
    @NonNull private final List<Resource> cleanupScripts;

    @Autowired
    public DatabaseSteps(@NonNull @Qualifier("garnishStepsDb_dbScriptsExecutor")      final DbScriptsExecutor dbScriptsExecutor,
                         @NonNull @Qualifier("garnishStepsDb_dbUnitInserter")         final DbUnitInserter dbUnitInserter,
                         @NonNull @Qualifier("garnishStepsDb_dbUnitDatabaseComparer") final DbUnitDatabaseComparer dbUnitDatabaseComparer,

                         // using @Value because a spring bug (SPR-12180) prevents autowiring collections with @Qualifier
                         // todo: remove this hack once Spring 4.3 is released
                         @NonNull @Value("#{garnishStepsDb_cleanupScripts}")          final List<Resource> cleanupScripts) {
        this.dbScriptsExecutor = dbScriptsExecutor;
        this.dbUnitInserter = dbUnitInserter;
        this.dbUnitDatabaseComparer = dbUnitDatabaseComparer;
        this.cleanupScripts = cleanupScripts;
    }

    @Given("^a clean database$")
    public void given_a_clean_database() {
        this.dbScriptsExecutor.executeScripts(this.cleanupScripts);
    }

    @Given("^the database state from '(.+)'$")
    @When("^I apply the database state from '(.+)'$")
    public void given_the_database_state_from_file(@NonNull final String initDbFile) {
        given_a_clean_database();

        if (initDbFile.endsWith(".xml")) {
            given_the_database_state_from_xml_file(initDbFile);
        } else if (initDbFile.endsWith(".sql")) {
            given_the_database_state_from_sql_file(initDbFile);
        }
    }

    private void given_the_database_state_from_xml_file(final @NonNull String initDbXmlFile) {
        this.dbUnitInserter.insert(
                new ClassPathResource(ResourceFilesVariables.getResourceFilesPrefix() + initDbXmlFile),
                ScenarioUserVariables.getResolver()
        );
    }

    private void given_the_database_state_from_sql_file(final @NonNull String initDbSqlFile) {
        dbScriptsExecutor.executeScripts(
                ScenarioUserVariables.getResolver(),
                Collections.singletonList(new ClassPathResource(ResourceFilesVariables.getResourceFilesPrefix() + initDbSqlFile))
        );
    }

    @Then("^the database state should be like in '(.+)'$")
    public void then_the_database_state_should_be_like_in_expectedDbFile(@NonNull final String expectedDbFile) {
        // todo: make a difference between failure and error
        this.dbUnitDatabaseComparer.compareDatabaseWith(
                new ClassPathResource(ResourceFilesVariables.getResourceFilesPrefix() + expectedDbFile),
                ScenarioUserVariables.getResolver()
        );
    }

}
