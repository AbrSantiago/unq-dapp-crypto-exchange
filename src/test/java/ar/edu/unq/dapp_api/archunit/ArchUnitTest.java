package ar.edu.unq.dapp_api.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

@AnalyzeClasses(packages = "ar.edu.unq.dapp_api", importOptions = {ImportOption.DoNotIncludeTests.class})
class ArchUnitTest {
    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(location -> !location.contains("/test/")) // Exclude tests classes
            .importPackages("ar.edu.unq.dapp_api");

    @Test
    void services_should_only_be_accessed_by_webservice_validator_and_other_services() {
        ArchRuleDefinition.classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..webservice..", "..service..", "..validation..")
                .check(classes);
    }

    @Test
    void repositories_should_only_be_accessed_by_services() {
        ArchRuleDefinition.classes()
                .that().resideInAPackage("..repositories..")
                // .and().areNotAnnotatedWith(ExcludeFromArchitectureCheck.class)
                .should().onlyBeAccessed().byAnyPackage("..service..", "..test..")
                .check(classes);
    }

//    @Test
//    void controllers_should_not_depend_on_model_or_repositories_directly() {
//        ArchRuleDefinition.noClasses()
//                .that().resideInAPackage("..webservice..")
//                .should().dependOnClassesThat().resideInAPackage("..model..")
//                .orShould().dependOnClassesThat().resideInAPackage("..repositories..")
//                .check(classes);
//    }
//
//    @Test
//    void dto_classes_should_only_be_accessed_by_webservice() {
//        ArchRuleDefinition.classes()
//                .that().resideInAPackage("..webservice.dto..")
//                .should().onlyBeAccessed().byAnyPackage("..webservice..")
//                .check(classes);
//    }

    @Test
    void model_classes_should_not_depend_on_any_other_layer() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..model..")
                .should().dependOnClassesThat().resideInAnyPackage("..webservice..", "..service..", "..repositories..")
                .check(classes);
    }

}


