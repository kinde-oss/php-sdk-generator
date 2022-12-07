/*
 * Copyright 2018 OpenAPI-Generator Contributors (https://openapi-generator.tech)
 * Copyright 2018 SmartBear Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kinde.codegen;

import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CliOption;
import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.CodegenType;
import org.openapitools.codegen.SupportingFile;
import org.openapitools.codegen.languages.*;
import org.openapitools.codegen.meta.features.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class KindePhpGenerator extends AbstractPhpCodegen {
    @SuppressWarnings("hiding")
    private final Logger LOGGER = LoggerFactory.getLogger(KindePhpGenerator.class);
    
    protected String sdkFolder = "Sdk";

    protected String sdkPackage = invokerPackage + "\\" + sdkFolder;

    public KindePhpGenerator() {
        super();

        modifyFeatureSet(features -> features
                .includeDocumentationFeatures(DocumentationFeature.Readme)
                .wireFormatFeatures(EnumSet.of(WireFormatFeature.JSON, WireFormatFeature.XML))
                .securityFeatures(EnumSet.noneOf(SecurityFeature.class))
                .excludeGlobalFeatures(
                        GlobalFeature.XMLStructureDefinitions,
                        GlobalFeature.Callbacks,
                        GlobalFeature.LinkObjects,
                        GlobalFeature.ParameterStyling
                )
                .excludeSchemaSupportFeatures(
                        SchemaSupportFeature.Polymorphism
                )
        );

        // clear import mapping (from default generator) as php does not use it
        // at the moment
        importMapping.clear();

        setInvokerPackage("Kinde\\KindeSDK");
        setApiPackage(getInvokerPackage() + "\\" + apiDirName);
        setModelPackage(getInvokerPackage() + "\\" + modelDirName);
        setPackageName("Kinde PHP SDK");
        supportsInheritance = true;
        setOutputDir("generated-code" + File.separator + "php");
        modelTestTemplateFiles.put("model_test.mustache", ".php");
        embeddedTemplateDir = templateDir = "out/kinde-php-sdk";

        // default HIDE_GENERATION_TIMESTAMP to true
        hideGenerationTimestamp = Boolean.TRUE;

        // provide primitives to mustache template
        List sortedLanguageSpecificPrimitives = new ArrayList(languageSpecificPrimitives);
        Collections.sort(sortedLanguageSpecificPrimitives);
        String primitives = "'" + StringUtils.join(sortedLanguageSpecificPrimitives, "', '") + "'";
        additionalProperties.put("primitives", primitives);

        cliOptions.add(new CliOption(CodegenConstants.HIDE_GENERATION_TIMESTAMP, CodegenConstants.ALLOW_UNICODE_IDENTIFIERS_DESC)
                .defaultValue(Boolean.TRUE.toString()));

        
        additionalProperties.put("sdkSrcPath", "./" + toSrcPath(sdkPackage, srcBasePath));
        additionalProperties.put("sdkTestPath", "./" + testBasePath + "/" + sdkFolder);
    }

    @Override
    public CodegenType getTag() {
        return CodegenType.CLIENT;
    }

    @Override
    public String getName() {
        return "kinde-php";
    }

    @Override
    public String getHelp() {
        return "Generates a Kinde PHP library.";
    }

    @Override
    public void processOpts() {
        super.processOpts();

        supportingFiles.add(new SupportingFile("ApiException.mustache", toSrcPath(invokerPackage, srcBasePath), "ApiException.php"));
        supportingFiles.add(new SupportingFile("OAuthException.mustache", toSrcPath(invokerPackage, srcBasePath), "OAuthException.php"));
        supportingFiles.add(new SupportingFile("Configuration.mustache", toSrcPath(invokerPackage, srcBasePath), "Configuration.php"));
        supportingFiles.add(new SupportingFile("ObjectSerializer.mustache", toSrcPath(invokerPackage, srcBasePath), "ObjectSerializer.php"));
        supportingFiles.add(new SupportingFile("ModelInterface.mustache", toSrcPath(modelPackage, srcBasePath), "ModelInterface.php"));
        supportingFiles.add(new SupportingFile("HeaderSelector.mustache", toSrcPath(invokerPackage, srcBasePath), "HeaderSelector.php"));
        supportingFiles.add(new SupportingFile("composer.mustache", "", "composer.json"));
        supportingFiles.add(new SupportingFile("README.mustache", "", "README.md"));
        supportingFiles.add(new SupportingFile("phpunit.xml.mustache", "", "phpunit.xml.dist"));
        supportingFiles.add(new SupportingFile("php-cs-fixer.dist.mustache", "", ".php-cs-fixer.dist.php"));

        supportingFiles.add(new SupportingFile("KindeClientSDK.mustache", toSrcPath(invokerPackage, srcBasePath), "KindeClientSDK.php"));

        /* /Enums/ */
        supportingFiles.add(new SupportingFile("Sdk/Enums/GrantType.mustache", toSrcPath(invokerPackage, srcBasePath) + File.separator + sdkFolder + File.separator + "Enums", "GrantType.php"));
        supportingFiles.add(new SupportingFile("Sdk/Enums/AuthStatus.mustache", toSrcPath(invokerPackage, srcBasePath) + File.separator + sdkFolder + File.separator + "Enums", "AuthStatus.php"));
        supportingFiles.add(new SupportingFile("Sdk/Enums/Additional.mustache", toSrcPath(invokerPackage, srcBasePath) + File.separator + sdkFolder + File.separator + "Enums", "Additional.php"));
        
        /* /OAuth2/ */
        supportingFiles.add(new SupportingFile("Sdk/OAuth2/AuthorizationCode.mustache", toSrcPath(invokerPackage, srcBasePath) + File.separator + sdkFolder + File.separator + "OAuth2", "AuthorizationCode.php"));
        supportingFiles.add(new SupportingFile("Sdk/OAuth2/ClientCredentials.mustache", toSrcPath(invokerPackage, srcBasePath) + File.separator + sdkFolder + File.separator + "OAuth2", "ClientCredentials.php"));
        supportingFiles.add(new SupportingFile("Sdk/OAuth2/PKCE.mustache", toSrcPath(invokerPackage, srcBasePath) + File.separator + sdkFolder + File.separator + "OAuth2", "PKCE.php"));
        
        /* /Utils/ */
        supportingFiles.add(new SupportingFile("Sdk/Utils/Utils.mustache", toSrcPath(invokerPackage, srcBasePath) + File.separator + sdkFolder + File.separator + "Utils", "Utils.php"));
        
        /* /test/ */
        supportingFiles.add(new SupportingFile("tests/KindeClientTest.mustache", toSrcPath(invokerPackage, testBasePath) + File.separator + "Sdk", "KindeClientTest.php"));
        supportingFiles.add(new SupportingFile("tests/OAuth2AuthorizationCodeFlowTest.mustache", toSrcPath(invokerPackage, testBasePath) + File.separator + "Sdk", "OAuth2AuthorizationCodeFlowTest.php"));
        supportingFiles.add(new SupportingFile("tests/OAuth2ClientCredentialTest.mustache", toSrcPath(invokerPackage, testBasePath) + File.separator + "Sdk", "OAuth2ClientCredentialTest.php"));
        supportingFiles.add(new SupportingFile("tests/OAuth2PKCETest.mustache", toSrcPath(invokerPackage, testBasePath) + File.separator + "Sdk", "OAuth2PKCETest.php"));
        supportingFiles.add(new SupportingFile("tests/UtilsTest.mustache", toSrcPath(invokerPackage, testBasePath) + File.separator + "Sdk", "UtilsTest.php"));

        supportingFiles.add(new SupportingFile("bootstrap_test.mustache", toSrcPath(invokerPackage, testBasePath), "bootstrap.php"));
    }
}