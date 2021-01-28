package hellocucumber;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StepUtils {

    public static String callApi(String operation, String body) throws IOException {

        return Request.Post("http://localhost:8080/" + operation)
                .useExpectContinue()
                .addHeader("Content-Type", "application/json")
                .version(HttpVersion.HTTP_1_1)
                .bodyString(body, ContentType.DEFAULT_TEXT)
                .execute().handleResponse(new ResponseHandler<String>() {

                    public String handleResponse(final HttpResponse response) throws IOException {
                        StatusLine statusLine = response.getStatusLine();
                        HttpEntity entity = response.getEntity();
                        if (statusLine.getStatusCode() >= 300) {
                            throw new HttpResponseException(
                                    statusLine.getStatusCode(),
                                    statusLine.getReasonPhrase());
                        }
                        if (entity == null) {
                            throw new ClientProtocolException("Response contains no content");
                        }
                        return IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8.name());
                    }

                });
    }

    public static String buildRequestBody(int a, int b) {
        return new StringBuilder()
                .append("{\"a\":\"")
                .append(a)
                .append("\",\"b\":\"")
                .append(b)
                .append("\"}")
                .toString();
    }

}
