package org.moddingx.cursewrapper.api;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestException extends IOException {
    
    public final int httpStatusCode;

    public RequestException(int httpStatusCode) {
        super("HTTP " + httpStatusCode);
        this.httpStatusCode = httpStatusCode;
    }
    
    public static <T> HttpResponse<T> send(HttpClient client, HttpRequest request, HttpResponse.BodySubscriber<T> body) throws IOException {
        try {
            return client.send(request, info -> {
                throwIfNeeded(info);
                return body;
            });
        } catch (RequestExceptionWrapper e) {
            throw e.exception;
        } catch (IOException e) {
            if (e.getCause() instanceof RequestExceptionWrapper wrapper) {
                throw wrapper.exception;
            } else {
                throw e;
            }
        } catch (InterruptedException e) {
            throw new IOException("Interrupted");
        }
    }
    
    private static void throwIfNeeded(HttpResponse.ResponseInfo info) {
        if (!isSuccessWithContent(info.statusCode())) {
            throw new RequestExceptionWrapper(new RequestException(info.statusCode()));
        }
    }
    
    private static boolean isSuccessWithContent(int code) {
        if (code < 0) return false;
        int group = code / 100;
        return group != 3 && group != 4 && group != 5 && code != 204;
    }
    
    private static class RequestExceptionWrapper extends RuntimeException {
        
        public final RequestException exception;

        private RequestExceptionWrapper(RequestException exception) {
            this.exception = exception;
        }
    }
}
