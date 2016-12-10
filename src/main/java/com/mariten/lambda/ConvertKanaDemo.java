package com.mariten.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class ConvertKanaDemo
{
    public String handleLambdaRequest(Object input, Context lambda_context)
    {
        LambdaLogger logger = lambda_context.getLogger();
        logger.log("Starting ConvertKanaDemo\n");
        return "Blargh";
    }
}
