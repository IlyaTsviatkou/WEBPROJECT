package com.example.topoftops.controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.RequestHelper;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.resource.ConfigurationManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.example.topoftops.controller.command.RequestParam.*;


/**
 * File uploading servlet receive multipart/form-data request from client
 *
 * @author Ilya Tsvetkov
 * @see HttpServlet
 */
@WebServlet(name = "upload", urlPatterns = {"/upload"})
//@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@MultipartConfig
public class FileUploadingServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final String BUNDLE_NAME = "path";
    private static final String PATH_IMG = "path.img";
    private static final String FORMAT_DELIMITER = "\\.";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String imageName = request.getParameter(PARAM_NAME_IMAGE_NAME);
        String path = ResourceBundle.getBundle(BUNDLE_NAME).getString(PATH_IMG);
        File file = new File(path + File.separator + imageName);
        BufferedImage bufferedImage = ImageIO.read(file);
        String fileFormat = imageName.split(FORMAT_DELIMITER)[1];
        try (OutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(bufferedImage, fileFormat, outputStream);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String path = ResourceBundle.getBundle(BUNDLE_NAME).getString(PATH_IMG);
            String title = request.getParameter(PARAM_NAME_TITLE);
            Part file = request.getPart("imageName");
            String imageName = title + "_" + file.getSubmittedFileName();
            file.write(path + File.separator + imageName);
            request.setAttribute(PARAM_NAME_IMAGE_NAME,imageName);
        } catch (Exception ex) {
            request.setAttribute("message", "File Upload Failed due to " + ex);
        }

        RequestHelper requestHelper = RequestHelper.getInstance();
        String action = request.getParameter(PARAM_NAME_COMMAND);
        Command command = requestHelper.getCommand(action);
        Router router = command.execute(request);
        switch (router.getRouteType()) {
            case REDIRECT:
                response.sendRedirect(request.getContextPath() + router.getPagePath());
                break;
            case FORWARD:
                RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPagePath());
                dispatcher.forward(request, response);
                break;
            default:
                logger.error("incorrect route type " + router.getRouteType());
                String page;
                page = ConfigurationManager.getProperty(PagePath.INDEX);
                response.sendRedirect(request.getContextPath() + page);
        }
    }

}

