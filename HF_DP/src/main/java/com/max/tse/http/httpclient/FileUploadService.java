package com.max.tse.http.httpclient;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-13
 * Time: 下午5:57
 * To change this template use File | Settings | File Templates.
 */
@Service("fileUploadService")
public class FileUploadService {


    private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);
    /*
    * 文件临时目录
    * */
    private static final String tempPath = "/tmp";
    /**
     * 缓冲区大小
     * */
    private static final int bufferSize = 10240;//10kb
    /**
     * 文件最大尺寸
     * */
    private static final int maxSize = 10485760;//10MB


    private static final String MULTIPART_FORM_CONTENT_TYPE = "multipart/form-data";

    private static final HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());

    static {
        client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
        client.getHttpConnectionManager().getParams().setMaxTotalConnections(100);
        client.getHttpConnectionManager().getParams().setSoTimeout(5000);
    }


    public void upload (String url, String[] fileNames, String filePath, Map<String, String> parameters) {

        Preconditions.checkArgument(StringUtils.isNotBlank(url));
        Preconditions.checkNotNull(fileNames);
        Preconditions.checkArgument(fileNames.length > 0);
        Preconditions.checkArgument(StringUtils.isNotBlank(filePath));

        String[] fileNamesWithPath = fileNameAddPath(fileNames, filePath);
        upload(url, fileNamesWithPath, parameters);

    }

    public void upload(String url, List<File> files, Map<String, String> parameters){


        try {
            PostMethod postMethod = new PostMethod(url);


            Part[] parts = buildFilePart(files);
            postMethod.getParams().setContentCharset("UTF-8");
            postMethod.getParams().setHttpElementCharset("UTF-8");
            postMethod.setRequestEntity(new MultipartRequestEntity(parts, postMethod.getParams()));
            postMethod.setRequestHeader("Content-Type", postMethod.getRequestEntity().getContentType());

            int status = client.executeMethod(postMethod);
            System.out.println("status=" + status);
            if (status == HttpStatus.SC_OK) {
                String response = postMethod.getResponseBodyAsString();

                System.out.println(response);
            }


        } catch (Exception e) {
            logger.error("upload error", e);
        }

    }

    public void upload(String url, String[] fileNames, Map<String, String> parameters) {

        List<File> files = buildFiles(fileNames);

        upload(url, files, parameters);

    }

    private String[] fileNameAddPath(String[] fileNames, String filePath) {
        String[] handledFileNames  = new String[fileNames.length];
        String theFilePath = reSetFilePath(filePath);

        for (int i = 0; i < fileNames.length; i++) {
            handledFileNames[i] = theFilePath + fileNames[i];
        }

        return handledFileNames;
    }

    private String reSetFilePath(String filePath) {
        if (filePath.endsWith("/")) {
            return filePath;
        } else {
            return filePath + "/";
        }

    }

    private Part[] buildFilePart(List<File> files) {


        List<Part> fileParts = Lists.newArrayList();

        for (File file : files) {
            FilePart filePart = buildFilePart(file);
            if (filePart != null) {
                fileParts.add(filePart);
            }
        }

        return fileParts.toArray(new FilePart[] {});
    }

    private FilePart buildFilePart(File file) {
        if (file == null || !file.exists()) {
            return null;
        }

        try {
            return new FilePart(file.getName(), file, MULTIPART_FORM_CONTENT_TYPE, "UTF-8");
        } catch (FileNotFoundException e) {
            return null;
        }
    }


    private List<File> buildFiles (String[] fileNames) {
        List<File> files = Lists.newArrayList();

        for (String fileName : fileNames) {
            File file = new File(fileName);
            if (file != null && file.exists()) {
                files.add(file);
            }
        }
        return files;
    }

    /**
     * 从request请求里获取文件
     * */
    public List<FileItem> getFileItems(HttpServletRequest request) throws FileUploadException {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setSizeThreshold(bufferSize);
        diskFileItemFactory.setRepository(new File(tempPath));
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        servletFileUpload.setHeaderEncoding("UTF-8");
        servletFileUpload.setFileSizeMax(maxSize);
        return servletFileUpload.parseRequest(request);

    }





}
