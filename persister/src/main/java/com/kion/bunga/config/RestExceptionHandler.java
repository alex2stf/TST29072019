package com.kion.bunga.config;


import java.io.PrintWriter;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@SuppressWarnings("unused")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {




    private final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);




    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> handleThrowableErrors(Throwable ex) {
        ex.printStackTrace();
        return null;
    }

    /**
     * Add stacktrace to the response object
     */
    private void addStackTrace(Throwable ex, ApiError apiError) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        apiError.setStack(stringWriter.toString());
    }

    private class ApiError {

        private String message;
        private HttpStatus status;
        private String stack;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public String getStack() {
            return stack;
        }

        public void setStack(String stack) {
            this.stack = stack;
        }
    }
}

