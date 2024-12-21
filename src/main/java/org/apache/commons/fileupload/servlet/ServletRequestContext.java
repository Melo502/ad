//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.commons.fileupload.servlet;

import java.io.IOException;
import java.io.InputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.RequestContext;

public class ServletRequestContext implements RequestContext {
    private HttpServletRequest request;

    public ServletRequestContext(HttpServletRequest request) {
        this.request = request;
    }

    public String getCharacterEncoding() {
        return this.request.getCharacterEncoding();
    }

    public String getContentType() {
        return this.request.getContentType();
    }

    public int getContentLength() {
        return this.request.getContentLength();
    }

    public InputStream getInputStream() throws IOException {
        return this.request.getInputStream();
    }

    public String toString() {
        return "ContentLength=" + this.getContentLength() + ", ContentType=" + this.getContentType();
    }
}
