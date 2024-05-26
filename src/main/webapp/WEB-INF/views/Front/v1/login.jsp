<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.slot.Common.ContextUtil" %>
<%@ page import="com.slot.Common.Protocol" %>

<%
    //////////////////////
    //// ERROR 발생 시 ////
    //////////////////////
    try{
        request.setCharacterEncoding("utf-8");
        String Message = (String) request.getAttribute(Protocol.ALERT);
        if(Message!=null && Message.length() > 0){
            out.print("<script>alert('"+Message+"'); </script>");
        }
    }catch(Exception e){ e.printStackTrace(); }
    ////////////////////////
%>

<%
    LocalDate current_date = LocalDate.now();
    int current_Year = current_date.getYear();
%>
<!doctype html>
<html lang="ko">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>로그인</title>
    <%--<link rel="shortcut icon" type="image/png" href="/img/Front/common/Thor_logo.png" />--%>
    <link rel="stylesheet" href="/assets/css/styles.min.css" />
</head>
<script type="text/javascript" >
    window.onload = function() {
        init();
    }

    function init() {
    }

    function doLogin() {
        var form = document.form1;

        if (!form.p_USER_ID.value) // form 에 있는 name 값이 없을 때
        {
            alert("아이디를 입력하세요."); // 경고창 띄움
            form.p_USER_ID.focus(); // form 에 있는 name 위치로 이동
            return;
        }

        if (!form.p_USER_PWD.value) {
            alert("비밀번호를 입력하세요.");
            form.p_USER_PWD.focus();
            return;
        }

        form.submit();
    }
</script>
<body>
<!--  Body Wrapper -->
<div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
     data-sidebar-position="fixed" data-header-position="fixed">
    <div
            class="position-relative overflow-hidden radial-gradient min-vh-100 d-flex align-items-center justify-content-center">
        <div class="d-flex align-items-center justify-content-center w-100">
            <div class="row justify-content-center w-100">
                <div class="col-md-8 col-lg-6 col-xxl-3">
                    <div class="card mb-0">
                        <div class="card-body px-5">
<%--
                            <a class="text-nowrap logo-img text-center d-block w-100 mb-1">
                                <img src="/img/Front/common/Thor_logo.png"  width="270" alt="">
                            </a>
--%>
                            <p class="fs-3 fw-bold text-center mb-5"></p>
                            <form id="form1" name="form1" method="post" action="/setLogin">
                                <div class="mb-3">
                                    <label for="p_USER_ID" class="form-label">아이디</label>
                                    <input type="text" class="form-control" id="p_USER_ID" name="p_USER_ID">
                                </div>
                                <div class="mb-4">
                                    <label for="p_USER_PWD" class="form-label">비밀번호</label>
                                    <input type="password" class="form-control" id="p_USER_PWD" name="p_USER_PWD">
                                </div>
                                <%--<div class="text-end mb-2">
                                    <a class="text-primary fs-3" href="/Join">Create My Setting
                                   		<i class="ti ti-arrow-right ms-1"></i>
                                    </a>
                                </div>--%>
                                <button type="button" OnClick="javascript:doLogin();" class="btn btn-primary w-100 py-8 fs-4 mb-5 rounded-2">로그인</button>
                                
                                <div class="d-flex align-items-center justify-content-center">
                                    <p class="mb-0 text-center fs-2 text-muted">Copyright © <%=current_Year%></p>
                                </div>
                                <%--<div class="d-flex align-items-center justify-content-center">
                                    <p class="mb-0 text-center fs-2 text-muted">
                                        <img src="/img/Front/common/Thor_logo.png"  width="50px" alt="">
                                    </p>
                                </div>--%>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/assets/libs/jquery/dist/jquery.min.js"></script>
<script src="/assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
<script>
    $(document).keyup(function(event) {
        if (event.which === 13) {
            doLogin();
        }
    });
</script>
</body>

</html>