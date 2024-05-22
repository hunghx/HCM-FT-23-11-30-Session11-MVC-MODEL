package ra.mvc_model.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "UploadController", value = "/upload")
@MultipartConfig( // cấu hình kich thươc upload tối
        maxFileSize = 50 * 1024 * 1024,
        maxRequestSize = 100 * 1024 * 1024,
        fileSizeThreshold = 10*1024 * 1024
)
public class UploadController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String uploadPath = getServletContext().getRealPath("/uploads");
       File file = new File(uploadPath);
       if (!file.exists()) {
           file.mkdirs();
       }

        // nận về 1 danh sách file cần upload
       Collection<Part> parts =  request.getParts();
       List<String> filNames = new ArrayList<>();
       for (Part part : parts) {
           if (part.getName().equals("files")){
               // xử lí upload
               // lưu lại tên file
               String fileName = part.getSubmittedFileName();
               filNames.add(fileName);
               part.write(uploadPath+File.separator+fileName);
           }
       }

       request.setAttribute("urls",filNames);
       request.getRequestDispatcher("image.jsp").forward(request, response);

    }
}