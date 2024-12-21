package org.apache.commons.fileupload;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.fileupload.util.Closeable;
import org.apache.commons.fileupload.util.FileItemHeadersImpl;
import org.apache.commons.fileupload.util.LimitedInputStream;
import org.apache.commons.fileupload.util.Streams;

public abstract class FileUploadBase {
    public static final String CONTENT_TYPE = "Content-type";
    public static final String CONTENT_DISPOSITION = "Content-disposition";
    public static final String CONTENT_LENGTH = "Content-length";
    public static final String FORM_DATA = "form-data";
    public static final String ATTACHMENT = "attachment";
    public static final String MULTIPART = "multipart/";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final String MULTIPART_MIXED = "multipart/mixed";
    /** @deprecated */
    public static final int MAX_HEADER_SIZE = 1024;
    private long sizeMax = -1L;
    private long fileSizeMax = -1L;
    private String headerEncoding;
    private ProgressListener listener;

    public FileUploadBase() {
    }

    public static final boolean isMultipartContent(RequestContext ctx) {
        String contentType = ctx.getContentType();
        if (contentType == null) {
            return false;
        } else {
            return contentType.toLowerCase().startsWith("multipart/");
        }
    }

    /** @deprecated */
    public static boolean isMultipartContent(HttpServletRequest req) {
        return ServletFileUpload.isMultipartContent(req);
    }

    public abstract FileItemFactory getFileItemFactory();

    public abstract void setFileItemFactory(FileItemFactory var1);

    public long getSizeMax() {
        return this.sizeMax;
    }

    public void setSizeMax(long sizeMax) {
        this.sizeMax = sizeMax;
    }

    public long getFileSizeMax() {
        return this.fileSizeMax;
    }

    public void setFileSizeMax(long fileSizeMax) {
        this.fileSizeMax = fileSizeMax;
    }

    public String getHeaderEncoding() {
        return this.headerEncoding;
    }

    public void setHeaderEncoding(String encoding) {
        this.headerEncoding = encoding;
    }

    /** @deprecated */
    public List parseRequest(HttpServletRequest req) throws FileUploadException {
        return this.parseRequest((RequestContext)(new ServletRequestContext(req)));
    }

    public FileItemIterator getItemIterator(RequestContext ctx) throws FileUploadException, IOException {
        return new FileItemIteratorImpl(ctx);
    }

    public List parseRequest(RequestContext ctx) throws FileUploadException {
        try {
            FileItemIterator iter = this.getItemIterator(ctx);
            List items = new ArrayList();
            FileItemFactory fac = this.getFileItemFactory();
            if (fac == null) {
                throw new NullPointerException("No FileItemFactory has been set.");
            } else {
                FileItem fileItem;
                for(; iter.hasNext(); items.add(fileItem)) {
                    FileItemStream item = iter.next();
                    fileItem = fac.createItem(item.getFieldName(), item.getContentType(), item.isFormField(), item.getName());

                    try {
                        Streams.copy(item.openStream(), fileItem.getOutputStream(), true);
                    } catch (FileUploadIOException e) {
                        throw (FileUploadException)e.getCause();
                    } catch (IOException e) {
                        throw new IOFileUploadException("Processing of multipart/form-data request failed. " + e.getMessage(), e);
                    }

                    if (fileItem instanceof FileItemHeadersSupport) {
                        FileItemHeaders fih = item.getHeaders();
                        ((FileItemHeadersSupport)fileItem).setHeaders(fih);
                    }
                }

                return items;
            }
        } catch (FileUploadIOException e) {
            throw (FileUploadException)e.getCause();
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage(), e);
        }
    }

    protected byte[] getBoundary(String contentType) {
        ParameterParser parser = new ParameterParser();
        parser.setLowerCaseNames(true);
        Map params = parser.parse(contentType, new char[]{';', ','});
        String boundaryStr = (String)params.get("boundary");
        if (boundaryStr == null) {
            return null;
        } else {
            byte[] boundary;
            try {
                boundary = boundaryStr.getBytes("ISO-8859-1");
            } catch (UnsupportedEncodingException var7) {
                boundary = boundaryStr.getBytes();
            }

            return boundary;
        }
    }

    /** @deprecated */
    protected String getFileName(Map headers) {
        return this.getFileName(this.getHeader(headers, "Content-disposition"));
    }

    protected String getFileName(FileItemHeaders headers) {
        return this.getFileName(headers.getHeader("Content-disposition"));
    }

    private String getFileName(String pContentDisposition) {
        String fileName = null;
        if (pContentDisposition != null) {
            String cdl = pContentDisposition.toLowerCase();
            if (cdl.startsWith("form-data") || cdl.startsWith("attachment")) {
                ParameterParser parser = new ParameterParser();
                parser.setLowerCaseNames(true);
                Map params = parser.parse(pContentDisposition, ';');
                if (params.containsKey("filename")) {
                    fileName = (String)params.get("filename");
                    if (fileName != null) {
                        fileName = fileName.trim();
                    } else {
                        fileName = "";
                    }
                }
            }
        }

        return fileName;
    }

    protected String getFieldName(FileItemHeaders headers) {
        return this.getFieldName(headers.getHeader("Content-disposition"));
    }

    private String getFieldName(String pContentDisposition) {
        String fieldName = null;
        if (pContentDisposition != null && pContentDisposition.toLowerCase().startsWith("form-data")) {
            ParameterParser parser = new ParameterParser();
            parser.setLowerCaseNames(true);
            Map params = parser.parse(pContentDisposition, ';');
            fieldName = (String)params.get("name");
            if (fieldName != null) {
                fieldName = fieldName.trim();
            }
        }

        return fieldName;
    }

    /** @deprecated */
    protected String getFieldName(Map headers) {
        return this.getFieldName(this.getHeader(headers, "Content-disposition"));
    }

    /** @deprecated */
    protected FileItem createItem(Map headers, boolean isFormField) throws FileUploadException {
        return this.getFileItemFactory().createItem(this.getFieldName(headers), this.getHeader(headers, "Content-type"), isFormField, this.getFileName(headers));
    }

    protected FileItemHeaders getParsedHeaders(String headerPart) {
        int len = headerPart.length();
        FileItemHeadersImpl headers = this.newFileItemHeaders();
        int start = 0;

        while(true) {
            int end = this.parseEndOfLine(headerPart, start);
            if (start == end) {
                return headers;
            }

            String header = headerPart.substring(start, end);

            for(start = end + 2; start < len; start = end + 2) {
                int nonWs;
                for(nonWs = start; nonWs < len; ++nonWs) {
                    char c = headerPart.charAt(nonWs);
                    if (c != ' ' && c != '\t') {
                        break;
                    }
                }

                if (nonWs == start) {
                    break;
                }

                end = this.parseEndOfLine(headerPart, nonWs);
                header = header + " " + headerPart.substring(nonWs, end);
            }

            this.parseHeaderLine(headers, header);
        }
    }

    protected FileItemHeadersImpl newFileItemHeaders() {
        return new FileItemHeadersImpl();
    }

    /** @deprecated */
    protected Map parseHeaders(String headerPart) {
        FileItemHeaders headers = this.getParsedHeaders(headerPart);
        Map result = new HashMap();
        Iterator iter = headers.getHeaderNames();

        while(iter.hasNext()) {
            String headerName = (String)iter.next();
            Iterator iter2 = headers.getHeaders(headerName);

            String headerValue;
            for(headerValue = (String)iter2.next(); iter2.hasNext(); headerValue = headerValue + "," + iter2.next()) {
            }

            result.put(headerName, headerValue);
        }

        return result;
    }

    private int parseEndOfLine(String headerPart, int end) {
        int index = end;

        while(true) {
            int offset = headerPart.indexOf(13, index);
            if (offset == -1 || offset + 1 >= headerPart.length()) {
                throw new IllegalStateException("Expected headers to be terminated by an empty line.");
            }

            if (headerPart.charAt(offset + 1) == '\n') {
                return offset;
            }

            index = offset + 1;
        }
    }

    private void parseHeaderLine(FileItemHeadersImpl headers, String header) {
        int colonOffset = header.indexOf(58);
        if (colonOffset != -1) {
            String headerName = header.substring(0, colonOffset).trim();
            String headerValue = header.substring(header.indexOf(58) + 1).trim();
            headers.addHeader(headerName, headerValue);
        }
    }

    /** @deprecated */
    protected final String getHeader(Map headers, String name) {
        return (String)headers.get(name.toLowerCase());
    }

    public ProgressListener getProgressListener() {
        return this.listener;
    }

    public void setProgressListener(ProgressListener pListener) {
        this.listener = pListener;
    }

    private class FileItemIteratorImpl implements FileItemIterator {
        private final MultipartStream multi;
        private final MultipartStream.ProgressNotifier notifier;
        private final byte[] boundary;
        private FileItemStreamImpl currentItem;
        private String currentFieldName;
        private boolean skipPreamble;
        private boolean itemValid;
        private boolean eof;

        FileItemIteratorImpl(RequestContext ctx) throws FileUploadException, IOException {
            if (ctx == null) {
                throw new NullPointerException("ctx parameter");
            } else {
                String contentType = ctx.getContentType();
                if (null != contentType && contentType.toLowerCase().startsWith("multipart/")) {
                    InputStream input = ctx.getInputStream();
                    if (FileUploadBase.this.sizeMax >= 0L) {
                        int requestSize = ctx.getContentLength();
                        if (requestSize == -1) {
                            input = new LimitedInputStream(input, FileUploadBase.this.sizeMax) {
                                protected void raiseError(long pSizeMax, long pCount) throws IOException {
                                    FileUploadException ex = new SizeLimitExceededException("the request was rejected because its size (" + pCount + ") exceeds the configured maximum" + " (" + pSizeMax + ")", pCount, pSizeMax);
                                    throw new FileUploadIOException(ex);
                                }
                            };
                        } else if (FileUploadBase.this.sizeMax >= 0L && (long)requestSize > FileUploadBase.this.sizeMax) {
                            throw new SizeLimitExceededException("the request was rejected because its size (" + requestSize + ") exceeds the configured maximum (" + FileUploadBase.this.sizeMax + ")", (long)requestSize, FileUploadBase.this.sizeMax);
                        }
                    }

                    String charEncoding = FileUploadBase.this.headerEncoding;
                    if (charEncoding == null) {
                        charEncoding = ctx.getCharacterEncoding();
                    }

                    this.boundary = FileUploadBase.this.getBoundary(contentType);
                    if (this.boundary == null) {
                        throw new FileUploadException("the request was rejected because no multipart boundary was found");
                    } else {
                        this.notifier = new MultipartStream.ProgressNotifier(FileUploadBase.this.listener, (long)ctx.getContentLength());
                        this.multi = new MultipartStream(input, this.boundary, this.notifier);
                        this.multi.setHeaderEncoding(charEncoding);
                        this.skipPreamble = true;
                        this.findNextItem();
                    }
                } else {
                    throw new InvalidContentTypeException("the request doesn't contain a multipart/form-data or multipart/mixed stream, content type header is " + contentType);
                }
            }
        }

        private boolean findNextItem() throws IOException {
            if (this.eof) {
                return false;
            } else {
                if (this.currentItem != null) {
                    this.currentItem.close();
                    this.currentItem = null;
                }

                while(true) {
                    boolean nextPart;
                    if (this.skipPreamble) {
                        nextPart = this.multi.skipPreamble();
                    } else {
                        nextPart = this.multi.readBoundary();
                    }

                    if (nextPart) {
                        FileItemHeaders headers = FileUploadBase.this.getParsedHeaders(this.multi.readHeaders());
                        if (this.currentFieldName == null) {
                            String fieldName = FileUploadBase.this.getFieldName(headers);
                            if (fieldName != null) {
                                String subContentType = headers.getHeader("Content-type");
                                if (subContentType == null || !subContentType.toLowerCase().startsWith("multipart/mixed")) {
                                    String fileName = FileUploadBase.this.getFileName(headers);
                                    this.currentItem = new FileItemStreamImpl(fileName, fieldName, headers.getHeader("Content-type"), fileName == null, this.getContentLength(headers));
                                    this.notifier.noteItem();
                                    this.itemValid = true;
                                    return true;
                                }

                                this.currentFieldName = fieldName;
                                byte[] subBoundary = FileUploadBase.this.getBoundary(subContentType);
                                this.multi.setBoundary(subBoundary);
                                this.skipPreamble = true;
                                continue;
                            }
                        } else {
                            String fileName = FileUploadBase.this.getFileName(headers);
                            if (fileName != null) {
                                this.currentItem = new FileItemStreamImpl(fileName, this.currentFieldName, headers.getHeader("Content-type"), false, this.getContentLength(headers));
                                this.notifier.noteItem();
                                this.itemValid = true;
                                return true;
                            }
                        }

                        this.multi.discardBodyData();
                    } else {
                        if (this.currentFieldName == null) {
                            this.eof = true;
                            return false;
                        }

                        this.multi.setBoundary(this.boundary);
                        this.currentFieldName = null;
                    }
                }
            }
        }

        private long getContentLength(FileItemHeaders pHeaders) {
            try {
                return Long.parseLong(pHeaders.getHeader("Content-length"));
            } catch (Exception var3) {
                return -1L;
            }
        }

        public boolean hasNext() throws FileUploadException, IOException {
            if (this.eof) {
                return false;
            } else {
                return this.itemValid ? true : this.findNextItem();
            }
        }

        public FileItemStream next() throws FileUploadException, IOException {
            if (!this.eof && (this.itemValid || this.hasNext())) {
                this.itemValid = false;
                return this.currentItem;
            } else {
                throw new NoSuchElementException();
            }
        }

        private class FileItemStreamImpl implements FileItemStream {
            private final String contentType;
            private final String fieldName;
            private final String name;
            private final boolean formField;
            private final InputStream stream;
            private boolean opened;
            private FileItemHeaders headers;

            FileItemStreamImpl(String pName, String pFieldName, String pContentType, boolean pFormField, long pContentLength) throws IOException {
                this.name = pName;
                this.fieldName = pFieldName;
                this.contentType = pContentType;
                this.formField = pFormField;
                final MultipartStream.ItemInputStream itemStream = FileItemIteratorImpl.this.multi.newInputStream();
                InputStream istream = itemStream;
                if (FileUploadBase.this.fileSizeMax != -1L) {
                    if (pContentLength != -1L && pContentLength > FileUploadBase.this.fileSizeMax) {
                        FileUploadException e = new FileSizeLimitExceededException("The field " + this.fieldName + " exceeds its maximum permitted " + " size of " + FileUploadBase.this.fileSizeMax + " characters.", pContentLength, FileUploadBase.this.fileSizeMax);
                        throw new FileUploadIOException(e);
                    }

                    istream = new LimitedInputStream(itemStream, FileUploadBase.this.fileSizeMax) {
                        protected void raiseError(long pSizeMax, long pCount) throws IOException {
                            itemStream.close(true);
                            FileUploadException e = new FileSizeLimitExceededException("The field " + FileItemStreamImpl.this.fieldName + " exceeds its maximum permitted " + " size of " + pSizeMax + " characters.", pCount, pSizeMax);
                            throw new FileUploadIOException(e);
                        }
                    };
                }

                this.stream = istream;
            }

            public String getContentType() {
                return this.contentType;
            }

            public String getFieldName() {
                return this.fieldName;
            }

            public String getName() {
                return this.name;
            }

            public boolean isFormField() {
                return this.formField;
            }

            public InputStream openStream() throws IOException {
                if (this.opened) {
                    throw new IllegalStateException("The stream was already opened.");
                } else if (((Closeable)this.stream).isClosed()) {
                    throw new FileItemStream.ItemSkippedException();
                } else {
                    return this.stream;
                }
            }

            void close() throws IOException {
                this.stream.close();
            }

            public FileItemHeaders getHeaders() {
                return this.headers;
            }

            public void setHeaders(FileItemHeaders pHeaders) {
                this.headers = pHeaders;
            }
        }
    }

    public static class FileUploadIOException extends IOException {
        private static final long serialVersionUID = -7047616958165584154L;
        private final FileUploadException cause;

        public FileUploadIOException(FileUploadException pCause) {
            this.cause = pCause;
        }

        public Throwable getCause() {
            return this.cause;
        }
    }

    public static class InvalidContentTypeException extends FileUploadException {
        private static final long serialVersionUID = -9073026332015646668L;

        public InvalidContentTypeException() {
        }

        public InvalidContentTypeException(String message) {
            super(message);
        }
    }

    public static class IOFileUploadException extends FileUploadException {
        private static final long serialVersionUID = 1749796615868477269L;
        private final IOException cause;

        public IOFileUploadException(String pMsg, IOException pException) {
            super(pMsg);
            this.cause = pException;
        }

        public Throwable getCause() {
            return this.cause;
        }
    }

    protected abstract static class SizeException extends FileUploadException {
        private final long actual;
        private final long permitted;

        protected SizeException(String message, long actual, long permitted) {
            super(message);
            this.actual = actual;
            this.permitted = permitted;
        }

        public long getActualSize() {
            return this.actual;
        }

        public long getPermittedSize() {
            return this.permitted;
        }
    }

    /** @deprecated */
    public static class UnknownSizeException extends FileUploadException {
        private static final long serialVersionUID = 7062279004812015273L;

        public UnknownSizeException() {
        }

        public UnknownSizeException(String message) {
            super(message);
        }
    }

    public static class SizeLimitExceededException extends SizeException {
        private static final long serialVersionUID = -2474893167098052828L;

        /** @deprecated */
        public SizeLimitExceededException() {
            this((String)null, 0L, 0L);
        }

        /** @deprecated */
        public SizeLimitExceededException(String message) {
            this(message, 0L, 0L);
        }

        public SizeLimitExceededException(String message, long actual, long permitted) {
            super(message, actual, permitted);
        }
    }

    public static class FileSizeLimitExceededException extends SizeException {
        private static final long serialVersionUID = 8150776562029630058L;

        public FileSizeLimitExceededException(String message, long actual, long permitted) {
            super(message, actual, permitted);
        }
    }
}
