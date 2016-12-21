package com.mariten.lambda.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.json.simple.DeserializationException;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

public class ApiGatewayHelpers
{
    public static JsonObject parseRequest(InputStream api_request, LambdaLogger logger)
    {
        try {
            BufferedReader request_reader = new BufferedReader(new InputStreamReader(api_request));
            // On Success, return parsed JSON structure
            return (JsonObject) Jsoner.deserialize(request_reader);

        } catch (IOException io_ex) {
            logger.log("[EXCEPTION] Error while reading stream: " + io_ex.getMessage());
        } catch (DeserializationException parse_ex) {
            logger.log("[EXCEPTION] Error parsing request JSON: " + parse_ex.getMessage());
        } catch (Exception ex) {
            logger.log("[EXCEPTION] Unexpected error: " + ex.getMessage());
        }

        // On Failure, return null after catching exception
        return null;
    }
}
