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
					<span class="hide-menu">SLOT</span>
				</li>
				<li class="sidebar-item<%if("슬롯관리".equals(MENU)){%> selected<%}%>">
					<a class="sidebar-link" href="/slots" aria-expanded="false">
						<div class="d-flex align-items-center gap-3">
							<span>
								<i class="ti ti-pin"></i>
							</span>
							<span class="hide-menu">슬롯 관리</span>
						</div>
					</a>
				</li>
				<%
					if("M".equals(m.getUSER_PERM()) || "G".equals(m.getUSER_PERM())){
				%>
				<li class="nav-small-cap">
					<i class="ti ti-dots nav-small-cap-icon fs-4"></i>
					<span class="hide-menu">CMS</span>
				</li>
				<li class="sidebar-item<%if("유저관리".equals(MENU)){%> selected<%}%>">
					<a class="sidebar-link justify-content-between" href="/users" aria-expanded="false">
						<div class="d-flex align-items-center gap-3">
							<span>
								<i class="ti ti-pin"></i>
							</span>
							<span class="hide-menu">유저 관리</span>
						</div>
					</a>
				</li>
				<li class="sidebar-item<%if("슬롯로그".equals(MENU)){%> selected<%}%>">
					<a class="sidebar-link" href="/log-slots" aria-expanded="false">
						<div class="d-flex align-items-center gap-3">
							<span>
								<i class="ti ti-pin"></i>
							</span>
							<span class="hide-menu">로그</span>
						</div>
					</a>
				</li>
				<%--<li class="sidebar-item<%if("LOGSLOTS".equals(MENU)){%> selected<%}%>">
					<a class="sidebar-link" href="/log-slot" aria-expanded="false">
						<span>
							<svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-device-desktop-search" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round"><path stroke="none" d="M0 0h24v24H0z" fill="none"></path><path d="M11.5 16h-7.5a1 1 0 0 1 -1 -1v-10a1 1 0 0 1 1 -1h16a1 1 0 0 1 1 1v6.5"></path><path d="M7 20h4"></path><path d="M9 16v4"></path><path d="M18 18m-3 0a3 3 0 1 0 6 0a3 3 0 1 0 -6 0"></path><path d="M20.2 20.2l1.8 1.8"></path></svg>
						</span>
						<span class="hide-menu">슬롯 로그</span>
					</a>
				</li>
				<li class="sidebar-item<%if("LOGUSERS".equals(MENU)){%> selected<%}%>">
					<a class="sidebar-link" href="/log-user" aria-expanded="false">
						<span>
							<svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-user-search" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round"><path stroke="none" d="M0 0h24v24H0z" fill="none"></path><path d="M8 7a4 4 0 1 0 8 0a4 4 0 0 0 -8 0"></path><path d="M6 21v-2a4 4 0 0 1 4 -4h1.5"></path><path d="M18 18m-3 0a3 3 0 1 0 6 0a3 3 0 1 0 -6 0"></path><path d="M20.2 20.2l1.8 1.8"></path></svg>
						</span>
						<span class="hide-menu">유저 로그</span>
					</a>
				</li>--%>
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