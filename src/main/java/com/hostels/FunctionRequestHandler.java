
package com.hostels;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.hostels.beans.Hostels;
import com.hostels.services.HostelsService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import io.micronaut.json.JsonMapper;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.Map;

@Introspected
public class FunctionRequestHandler extends MicronautRequestHandler<
    APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
    @Inject
    private JsonMapper objectMapper;
    @Inject
    private HostelsService hostelsService;
    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            Map<String, String> jsonObject = objectMapper.readValue(input.getBody(), Map.class);
            Hostels user = hostelsService.save(
                Hostels
                    .builder()
                    .hostelAddress(jsonObject.get("hostelAddress"))
                    .hostelName(jsonObject.get("hostelName"))
                    .build()
            );
            response.setStatusCode(200);
            response.setBody(user.toString());
        } catch (IOException e) {
            response.setStatusCode(500);
        }
        return response;
    }
}