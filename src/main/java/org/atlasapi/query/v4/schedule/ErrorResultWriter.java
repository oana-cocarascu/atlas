package org.atlasapi.query.v4.schedule;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.atlasapi.output.ErrorSummary;


public class ErrorResultWriter {

    private static final ErrorSummaryWriter errorSummaryWriter = new ErrorSummaryWriter();

    public void write(ErrorSummary summary, ResponseWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (writer == null) {
            writer = new JsonResponseWriter(request, response);
        }
        response.setStatus(summary.statusCode().code());
        writer.startResponse();
        errorSummaryWriter.write(summary, writer, null);
        writer.finishResponse();
    }

}
