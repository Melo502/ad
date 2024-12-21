//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.commons.fileupload.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;

public class ServletFileUpload extends FileUpload {
    public static final boolean isMultipartContent(HttpServletRequest request) {
        if (!"post".equals(request.getMethod().toLowerCase())) {
            return false;
        } else {
            String contentType = request.getContentType();
            if (contentType == null) {
                return false;
            } else {
                return contentType.toLowerCase().startsWith("multipart/");
            }
        }
    }

    public ServletFileUpload() {
    }

    public ServletFileUpload(FileItemFactory fileItemFactory) {
        super(fileItemFactory);
    }

    public List parseRequest(HttpServletRequest request) throws FileUploadException {
        return this.parseRequest(new ServletRequestContext(request));
    }

    public FileItemIterator getItemIterator(HttpServletRequest request) throws FileUploadException, IOException {
        return super.getItemIterator(new ServletRequestContext(request));
    }
}
