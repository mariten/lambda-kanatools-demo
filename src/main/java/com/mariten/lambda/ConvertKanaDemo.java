package com.mariten.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.mariten.kanatools.KanaConverter;
import org.json.simple.JsonObject;

import com.mariten.lambda.util.ApiGatewayHelpers;

public class ConvertKanaDemo
{
    public void handleLambdaRequest(InputStream     api_gateway_request,
                                    OutputStream    api_gateway_response,
                                    Context         lambda_context
    )
    throws IOException
    {
        LambdaLogger logger = lambda_context.getLogger();
        logger.log("Starting ConvertKanaDemo\n");

        // Parse request JSON structure received from API Gateway
        JsonObject request_json = ApiGatewayHelpers.parseRequest(api_gateway_request, logger);
        logger.log("Request JSON: " + request_json.toJson());

        JsonObject response_json = new JsonObject();
        response_json.put("statusCode", "200");
        response_json.put("body", "OK");

        OutputStreamWriter stream_writer = new OutputStreamWriter(api_gateway_response, "UTF-8");
        stream_writer.write(response_json.toJson());
        stream_writer.close();
    }
}
