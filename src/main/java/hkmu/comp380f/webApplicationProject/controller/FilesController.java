package hkmu.comp380f.webApplicationProject.controller;

import com.sun.deploy.net.HttpResponse;
import hkmu.comp380f.webApplicationProject.dao.CommentRepository;
import hkmu.comp380f.webApplicationProject.dao.CourseRepository;
import hkmu.comp380f.webApplicationProject.dao.UserRepository;
import hkmu.comp380f.webApplicationProject.model.Comment;
import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.CourseFile;
import hkmu.comp380f.webApplicationProject.model.User;
import hkmu.comp380f.webApplicationProject.services.UserInformationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class FilesController {
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private CommentRepository commentRepository;
    @Autowired
    private UserInformationServices userInformationServices;


    @PostMapping("/upload")
    public RedirectView fileUploadEvent(@RequestParam("materials")MultipartFile[] files, ModelMap modelMap,
                                        @RequestParam String courseID, HttpSession session) {

        try {
            String uploadDir = "/FILES/";
            String dataDirectory = session.getServletContext().getRealPath(uploadDir);

            System.out.println(dataDirectory );
            if(!new File(dataDirectory ).exists()) {
                new File(dataDirectory ).mkdir();
            }

            for(MultipartFile file : files) {
                String filePath = dataDirectory  + file.getOriginalFilename();
                file.transferTo(new File(filePath));

                CourseFile courseFile = new CourseFile(UUID.randomUUID().toString(),
                        courseID,
                        file.getContentType(),
                        file.getOriginalFilename(),
                        new Timestamp(System.currentTimeMillis()),
                        true);

                courseRepository.addFile(courseFile);
            }

            modelMap.addAttribute("OK", "File uploaded successfully");
        } catch (IOException e) {
            modelMap.addAttribute("error", e.getMessage());
        }

        return new RedirectView("course?courseSelected=" + courseID);
    }

    @RequestMapping(value = "/download/{fileName:.+}")
    public void downloadResources(HttpSession session,
                                  @PathVariable("fileName") String fileName,
                                  HttpServletResponse response) {

        System.out.println("it works");

        String downloadDir = "/FILES/";
        String dataDirectory  = session.getServletContext().getRealPath(downloadDir);

        Path filePath = Paths.get(dataDirectory, fileName);

        if(Files.exists(filePath)) {
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);

            System.out.println(filePath);
        }

        try
        {
            Files.copy(filePath, response.getOutputStream());
            response.getOutputStream().flush();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
