package com.mariten.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

        // Check for required params
        List<String> param_errors = new ArrayList<String>();
        //// "input_str"
        String input_str = ApiGatewayHelpers.getStringQueryParam(request_json, "input_str", null);
        if (input_str == null) {
            param_errors.add("Query param [input_str] is required");
        }
        //// "conv_ops"
        int conv_ops = ApiGatewayHelpers.getIntegerQueryParam(request_json, "conv_ops", 0);
        if (conv_ops < 1) {
            param_errors.add("Query param [conv_ops] is required, must be positive integer");
        }

        // If any errors occurred, stop and return 400
        if (param_errors.size() > 0) {
            ApiGatewayHelpers.respondError(api_gateway_response, 400, param_errors);
            return;
        }

        // Convert string
        String output_str = KanaConverter.convertKana(input_str, conv_ops);

        // Produce output JSON for response
        JsonObject response_json = new JsonObject();
        response_json.put("success", true);
        response_json.put("converted_str", output_str);

        // Send response and finish
        ApiGatewayHelpers.respondSuccess(api_gateway_response, response_json);
        logger.log("Finished ConvertKanaDemo\n");
    }
}
