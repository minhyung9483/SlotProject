<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ page import="com.slot.Model.*,com.slot.Common.*" %>
<%@ page import="com.slot.Common.Protocol" %>
<%@ page import="com.slot.Model.Member" %>
<%@ page import="com.slot.Common.DBConnector" %>
<%@ page import="com.slot.Common.ContextUtil" %>
<%
	try{
		request.setCharacterEncoding("utf-8");
		Member m = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		String MENU = (String) request.getAttribute("MENU");
%>

<!-- Sidebar Start -->
<aside class="left-sidebar">
	<!-- Sidebar scroll-->
	<div>
		<div class="brand-logo d-flex align-items-center justify-content-between mt-2">
			<%--<a href="/" class="text-nowrap logo-img">
				<img src="/img/Front/common/Thor_logo.png" width="100%" alt="" />
			</a>--%>
			<div class="close-btn d-xl-none d-block sidebartoggler cursor-pointer" id="sidebarCollapse">
				<i class="ti ti-x fs-8"></i>
			</div>
		</div>
		<!-- Sidebar navigation-->
		<nav class="sidebar-nav scroll-sidebar" data-simplebar="">
			<ul id="sidebarnav">
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">슬롯</span>
				</li>
				<%
					if(m.getUSER_TYPE().contains("NS")){
				%>
				<li class="sidebar-item<%if("네이버쇼핑슬롯관리".equals(MENU)){%> selected<%}%>">
					<a class="sidebar-link" href="/naver-shopping-slots" aria-expanded="false">
						<div class="d-flex align-items-center gap-3">
							<span>
								<i class="ti ti-pin"></i>
							</span>
							<span class="hide-menu">네이버 쇼핑</span>
						</div>
					</a>
				</li>
				<%
					}
				%>
				<%
					if(m.getUSER_TYPE().contains("NP")){
				%>
				<li class="sidebar-item<%if("네이버플레이스슬롯관리".equals(MENU)){%> selected<%}%>">
					<a class="sidebar-link" href="/naver-place-slots" aria-expanded="false">
						<div class="d-flex align-items-center gap-3">
							<span>
								<i class="ti ti-pin"></i>
							</span>
							<span class="hide-menu">네이버 플레이스</span>
						</div>
					</a>
				</li>
				<%
					}
				%>
				<%
					if("M".equals(m.getUSER_PERM()) || "G".equals(m.getUSER_PERM())){
				%>
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">유저</span>
				</li>
				<%
					if(m.getUSER_TYPE().contains("NS")){
				%>
				<li class="sidebar-item<%if("네이버쇼핑유저관리".equals(MENU)){%> selected<%}%>">
					<a class="sidebar-link justify-content-between" href="/naver-shopping-users" aria-expanded="false">
						<div class="d-flex align-items-center gap-3">
							<span>
								<i class="ti ti-pin"></i>
							</span>
							<span class="hide-menu">네이버 쇼핑</span>
						</div>
					</a>
				</li>
				<%
					}
				%>
				<%
					if(m.getUSER_TYPE().contains("NP")){
				%>
				<li class="sidebar-item<%if("네이버플레이스유저관리".equals(MENU)){%> selected<%}%>">
					<a class="sidebar-link justify-content-between" href="/naver-place-users" aria-expanded="false">
						<div class="d-flex align-items-center gap-3">
							<span>
								<i class="ti ti-pin"></i>
							</span>
							<span class="hide-menu">네이버 플레이스</span>
						</div>
					</a>
				</li>
				<%
					}
				%>
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">로그</span>
				</li>
				<%
					if(m.getUSER_TYPE().contains("NS")){
				%>
				<li class="sidebar-item<%if("네이버쇼핑슬롯로그".equals(MENU)){%> selected<%}%>">
					<a class="sidebar-link" href="/naver-shopping-log-slots" aria-expanded="false">
						<div class="d-flex align-items-center gap-3">
							<span>
								<i class="ti ti-pin"></i>
							</span>
							<span class="hide-menu">네이버 쇼핑</span>
						</div>
					</a>
				</li>
				<%
					}
				%>
				<%
					if(m.getUSER_TYPE().contains("NP")){
				%>
				<li class="sidebar-item<%if("네이버플레이스슬롯로그".equals(MENU)){%> selected<%}%>">
					<a class="sidebar-link" href="/naver-place-log-slots" aria-expanded="false">
						<div class="d-flex align-items-center gap-3">
							<span>
								<i class="ti ti-pin"></i>
							</span>
							<span class="hide-menu">네이버 플레이스</span>
						</div>
					</a>
				</li>
				<%
					}
				%>
				<%
					}
				%>
			</ul>
		</nav>
		<!-- End Sidebar navigation -->
	</div>
	<!-- End Sidebar scroll-->
</aside>
<!--  Sidebar End -->
<%
		}catch(Exception e){ e.printStackTrace(); }
%>			