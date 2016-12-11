package com.mariten.lambda;

import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.mariten.kanatools.KanaConverter;

public class ConvertKanaDemo
{
    public String handleLambdaRequest(Object input, Context lambda_context)
    {
        LambdaLogger logger = lambda_context.getLogger();
        logger.log("Starting ConvertKanaDemo\n");

        HashMap<String, String> input_params = (HashMap<String, String>) input;
        if (input_params.containsKey("input_str")) {
            int conv_op_flags = 0;
            conv_op_flags |= KanaConverter.OP_HAN_KATA_TO_ZEN_KATA;
            conv_op_flags |= KanaConverter.OP_ZEN_ASCII_TO_HAN_ASCII;

            String input_str = input_params.get("input_str");
            logger.log("input_str: '" + input_str + "'\n");
            String output_str = KanaConverter.convertKana(input_str, conv_op_flags);
            logger.log("output_str: '" + output_str + "'\n");
            return "{'output_str':'" + output_str + "'}";
        } else {
            return "{'success':false}";
        }
    }
}
