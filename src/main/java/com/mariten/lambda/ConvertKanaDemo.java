package com.mariten.lambda;

import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class ConvertKanaDemo
{
    public String handleLambdaRequest(Object input, Context lambda_context)
    {
        LambdaLogger logger = lambda_context.getLogger();
        logger.log("Starting ConvertKanaDemo\n");

        HashMap<String, String> input_params = (HashMap<String, String>) input;
        if (input_params.containsKey("input_str")) {
            String input_str = input_params.get("input_str");
            logger.log("input_str: '" + input_str + "'\n");
            return "Success";
        } else {
            return "Failure";
        }
    }
}
