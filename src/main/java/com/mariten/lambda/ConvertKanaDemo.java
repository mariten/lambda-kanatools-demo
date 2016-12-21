package com.mariten.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
        if (request_json == null) {
            ApiGatewayHelpers.respondError(api_gateway_response, 500, "Failure parsing request");
            return;
        }


        // Produce output JSON for response
        JsonObject response_json = new JsonObject();
        response_json.put("success", true);
        response_json.put("converted_str", "blargh");

        // Send response and finish
        ApiGatewayHelpers.respondSuccess(api_gateway_response, response_json);
        logger.log("Finished ConvertKanaDemo\n");
    }
}
