package com.study.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("*.do")
public class BoardControllerServlet  extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //        String uri = request.getRequestUri();
        //
        //        if( uri.startsWith("list") ){
        //            //list 서비스 담당자 호출
        //            commandMap.get("list").doService(request, response);
        //
        //        }else if( uri.startsWith("write") ){
        //            //write 서비스 담당자 호출
        //            commandMap.get("write").doService(request, response);
        //
        //        }else if( uri.startsWith("read") ){
        //            //read 서비스 담당자 호출
        //            commandMap.get("read").doService(request, response);
        //
        //        }
    }
}
