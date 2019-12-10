package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.junit4.SpringRunner;

import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@RunWith(SpringRunner.class)
@SpringBootTest public abstract class MockMvcBase {

    @Rule public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(resolveOutputDir());

    @Autowired protected ObjectMapper objectMapper;

    private String resolveOutputDir() {
        String outputDir = System.getProperties().getProperty("org.springframework.restdocs.outputDir");

        if (outputDir == null) {
            outputDir = "target/generated-snippets";
        }
        return outputDir;
    }

    protected RestDocumentationResultHandler commonDocumentation() {
        return document("{class-name}/{method-name}", preprocessRequest(), commonResponsePreprocessor());
    }

    protected OperationResponsePreprocessor commonResponsePreprocessor() {
        return preprocessResponse(replaceBinaryContent(), limitJsonArrayLength(objectMapper), prettyPrint());
    }
}