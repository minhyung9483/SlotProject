<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ page import="com.slot.Model.*,com.slot.Common.*" %>
<%@ page import="com.slot.Common.Protocol" %>
<%@ page import="com.slot.Model.Member" %>
<%@ page import="com.slot.Common.ContextUtil" %>
<%
	try{
		request.setCharacterEncoding("utf-8");
		Member m = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
%>
<!--  Header Start -->
<header class="app-header">
	<nav class="navbar navbar-expand-lg navbar-light">
		<ul class="navbar-nav">
			<li class="nav-item d-block d-xl-none">
				<a class="nav-link sidebartoggler nav-icon-hover" id="headerCollapse" href="javascript:void(0)">
					<i class="ti ti-menu-2"></i>
				</a>
			</li>
		</ul>
		<div class="navbar-collapse justify-content-end px-0" id="navbarNav">
			<ul class="navbar-nav flex-row ms-auto align-items-center justify-content-end">

				<li class="nav-item dropdown d-flex align-items-center">
					<a class="bg-primary rounded-circle p-2 text-white me-2" href="/My">
						<svg stroke-linejoin="round" stroke-linecap="round" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24" height="20" width="20" class="icon icon-tabler icon-tabler-user-heart" xmlns="http://www.w3.org/2000/svg">
							<path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
							<path d="M8 7a4 4 0 1 0 8 0a4 4 0 0 0 -8 0"></path>
							<path d="M6 21v-2a4 4 0 0 1 4 -4h.5"></path>
							<path d="M18 22l3.35 -3.284a2.143 2.143 0 0 0 .005 -3.071a2.242 2.242 0 0 0 -3.129 -.006l-.224 .22l-.223 -.22a2.242 2.242 0 0 0 -3.128 -.006a2.143 2.143 0 0 0 -.006 3.071l3.355 3.296z"></path>
						</svg>
					</a>
					<h6 class="fw-normal mb-0"><span class="text-primary fw-semibold"><%=m.getUSER_ID()%></span>님, 안녕하세요!</h6>
				</li>
				<a class="btn btn-outline-dark ms-3" href="/Logout" >로그아웃</a>
			</ul>
		</div>
	</nav>
</header>
<!--  Header End -->
<%
	}catch(Exception e){ e.printStackTrace(); }
%>