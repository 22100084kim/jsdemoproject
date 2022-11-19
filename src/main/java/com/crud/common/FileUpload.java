package com.crud.common;

import com.crud.bean.MemberVO;
import com.crud.dao.MemberDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


public class FileUpload {
    public MemberVO uploadPhoto(HttpServletRequest request) {
        String filename = "";
        int sizeLimit = 15 * 1024 * 1024;
        String realPath = request.getServletContext().getRealPath("upload");

        File dir = new File(realPath);
        if (!dir.exists()) dir.mkdirs();

        MemberVO one = null;
        MultipartRequest multipartRequest = null;
        try {
            multipartRequest = new MultipartRequest(request, realPath, sizeLimit, "utf-8",new DefaultFileRenamePolicy());
            filename = multipartRequest.getFilesystemName("photo");

            one = new MemberVO();
            String sid = multipartRequest.getParameter("sid");
            one.setPhoto(multipartRequest.getParameter("photo"));
            one.setCategory(multipartRequest.getParameter("category"));
            one.setTitle(multipartRequest.getParameter("title"));
            one.setWriter(multipartRequest.getParameter("writer"));
            one.setContent(multipartRequest.getParameter("content"));
            //one.setRegdate(multipartRequest.getParameter("regdate"));
            String cnt = multipartRequest.getParameter("cnt");

            if(sid!=null&&!sid.equals("")){
                MemberDAO dao = new MemberDAO();
                String oldfilename = dao.getPhotoFilename(Integer.parseInt(sid));
                if(filename!=null && oldfilename!=null)
                    FileUpload.deleteFile(request, oldfilename);
                else if(filename==null && oldfilename!=null)
                    filename = oldfilename;
            }
            one.setPhoto(filename);
        }catch (IOException e){
            e.printStackTrace();
        }
        return one;
    }
    public static void deleteFile(HttpServletRequest request, String filename){
        String filePath = request.getServletContext().getRealPath("upload");
        File f= new File(filePath + "/" + filename);
        if( f.exists()) f.delete();
    }
}

