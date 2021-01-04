package com.cdkpatterns;

import software.amazon.awscdk.core.App;

import com.cdkpatterns.TheScalableWebhookStack;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TheScalableWebhookTest {
    private final static ObjectMapper JSON =
        new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);

    @Test
    public void testStack() throws IOException {
        App app = new App();
        TheScalableWebhookStack stack = new TheScalableWebhookStack(app, "test");

        // synthesize the stack to a CloudFormation template and compare against
        // a checked-in JSON file.
        JsonNode actual = JSON.valueToTree(app.synth().getStackArtifact(stack.getArtifactId()).getTemplate());

        // After Synth, performs some basic tests
        
        // AWS::Lambda::EventSourceMapping exists test
        assertThat(actual.toString()).contains("AWS::Lambda::EventSourceMapping");
        
        // AWS::Lambda::Function exists test
        assertThat(actual.toString()).contains("AWS::Lambda::Function");
        
        // AWS::DynamoDB::Table exists test
        assertThat(actual.toString()).contains("AWS::DynamoDB::Table");
        
        // AWS::ApiGateway::Method exists test
        assertThat(actual.toString()).contains("AWS::ApiGateway::Method");
    }
}
