package com.mariten.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.mariten.kanatools.KanaConverter;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

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

        BufferedReader request_reader = new BufferedReader(new InputStreamReader(api_gateway_request));
        JSONParser json_parser = new JSONParser();
        JSONObject request_json;
        try {
            request_json = (JSONObject) json_parser.parse(request_reader);
            logger.log("Request JSON: " + request_json.toJSONString());
        } catch (ParseException parse_ex) {
            logger.log("Error while parsing request JSON: " + parse_ex.getMessage());
        }

        JSONObject response_json = new JSONObject();
        response_json.put("statusCode", "200");
        response_json.put("body", "OK");

        OutputStreamWriter stream_writer = new OutputStreamWriter(api_gateway_response, "UTF-8");
        stream_writer.write(response_json.toJSONString());
        stream_writer.close();
    }
}
