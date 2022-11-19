package com.crud.dao;

import com.crud.bean.MemberVO;
import com.crud.common.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class MemberDAO {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    private final String MEMBER_INSERT = "insert into member (photo, category, title, writer, content) values (?,?,?,?,?)";
    private final String MEMBER_UPDATE = "update member set photo=?, category=?, title=?, writer=?, content=? where sid=?";
    private final String MEMBER_DELETE = "delete from member  where sid=?";
    private final String M_SELECT = "select * from member  where sid=?";
    private final String M_LIST = "select * from member order by sid desc";

    public int insertMember(MemberVO vo) {
        int result=0;
        System.out.println("===> JDBC로 insertMember() 기능 처리");
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement(MEMBER_INSERT);
            stmt.setString(1, vo.getPhoto());
            stmt.setString(2, vo.getCategory());
            stmt.setString(3, vo.getTitle());
            stmt.setString(4, vo.getWriter());
            stmt.setString(5, vo.getContent());
            result= stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 글 삭제
    public void deleteMember(MemberVO vo) {
        System.out.println("===> JDBC로 deleteMember() 기능 처리");
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement(MEMBER_DELETE);
            stmt.setInt(1, vo.getSid());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int updateMember(MemberVO vo) {
        System.out.println("===> JDBC로 updateMember() 기능 처리");
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement(MEMBER_UPDATE);
            stmt.setString(1, vo.getPhoto());
            stmt.setString(2, vo.getCategory());
            stmt.setString(3, vo.getTitle());
            stmt.setString(4, vo.getWriter());
            stmt.setString(5, vo.getContent());
            stmt.setInt(6, vo.getSid());


            System.out.println(vo.getPhoto() + "-" + vo.getCategory() + "-" + vo.getTitle() + "-" + vo.getWriter() + "-" + vo.getContent() + "-" + vo.getSid());
            stmt.executeUpdate();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public MemberVO getOne(int sid) {
        MemberVO one = null;
        System.out.println("===> JDBC로 getMember() 기능 처리");
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement(M_SELECT);
            stmt.setInt(1, sid);
            rs = stmt.executeQuery();
            if(rs.next()) {
                one = new MemberVO();
                one.setSid(rs.getInt("sid"));
                one.setPhoto(rs.getString("photo"));
                one.setCategory(rs.getString("category"));
                one.setTitle(rs.getString("title"));
                one.setWriter(rs.getString("writer"));
                one.setContent(rs.getString("content"));
                one.setCnt(rs.getInt("cnt"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return one;
    }

    public ArrayList<MemberVO> getList(){
        ArrayList<MemberVO> list = null;
        System.out.println("===> JDBC로 getMemberList() 기능 처리");
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement(M_LIST);
            rs = stmt.executeQuery();
            while(rs.next()) {
                MemberVO one = new MemberVO();
                one.setSid(rs.getInt("sid"));
                one.setPhoto(rs.getString("photo"));
                one.setCategory(rs.getString("category"));
                one.setTitle(rs.getString("title"));
                one.setWriter(rs.getString("writer"));
                one.setContent(rs.getString("content"));
                one.setRegdate(rs.getDate("regdate"));
                one.setCnt(rs.getInt("cnt"));
                list.add(one);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getPhotoFilename(int sid) {
        String filename = null;
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement(M_SELECT);
            stmt.setInt(1, sid);
            rs = stmt.executeQuery();
            if(rs.next()){
                filename= rs.getString("photo");
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("===> JDBC로 getPhotoFilename() 기능 처리");
        return filename;
    }
}
