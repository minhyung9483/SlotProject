<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.slot.Common.Protocol" %>
<%@ page import="java.util.List" %>
<%@ page import="com.slot.Common.DBConnector" %>
<%@ page import="com.slot.Model.Member" %>
<%@ page import="com.slot.Common.ContextUtil" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.slot.Model.NaverShoppingLogSlot" %>
<!doctype html>
<html lang="ko">

<jsp:include page="common/head.jsp" />

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

	final String TAG = "NAVER_SHOPPING_LOG_SLOT_PAGE";
	final String USER_TYPE = "NS_";
	request.setCharacterEncoding("utf-8");
	Member member = (Member) session.getAttribute(Protocol.Json.KEY_MEMBER);

	int setPage = 1;
	String m_SearchType = "", m_SearchValue = "", m_StartDate = "", m_EndDate = "", Param = "";

	try{ m_SearchType =  (String)request.getAttribute("SearchType"); 			}catch(Exception e){ e.printStackTrace(); }
	try{ m_SearchValue = (String)request.getAttribute("SearchValue"); 			}catch(Exception e){ e.printStackTrace(); }
	try{ m_StartDate = (String)request.getAttribute("StartDate"); 			}catch(Exception e){ e.printStackTrace(); }
	try{ m_EndDate = (String)request.getAttribute("EndDate"); 			}catch(Exception e){ e.printStackTrace(); }

	Param += m_SearchType!=null && m_SearchType.length() > 0 ? "st=" + m_SearchType+"&" : "";
	Param += m_SearchValue!=null && m_SearchValue.length() > 0 ? "sv=" + m_SearchValue+"&" : "";
	Param += m_StartDate!=null && m_StartDate.length() > 0 ? "sd=" + m_StartDate+"&" : "";
	Param += m_EndDate!=null && m_EndDate.length() > 0 ? "ed=" + m_EndDate+"&" : "";
	Param = Param!=null && Param.length() > 0 ? "?" +Param : "";

	try{ setPage = Integer.parseInt( (String)request.getAttribute("page")); 	}catch(Exception e){ }

	//오늘 날짜
	LocalDateTime currentDateTime = LocalDateTime.now();
	String Today = currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

	if(m_StartDate == null || "".equals(m_StartDate))m_StartDate = Today;
	if(m_EndDate == null || "".equals(m_EndDate))m_EndDate = Today;

	int totalCount  = DBConnector.getNaverShoppingLogSlotListTotalCount(m_SearchType, m_SearchValue, m_StartDate, m_EndDate, member.getUSER_PERM(), member.getUSER_IDX());
	List<NaverShoppingLogSlot> logSlotList = DBConnector.getNaverShoppingLogSlotList(setPage - 1, m_SearchType, m_SearchValue, m_StartDate, m_EndDate, member.getUSER_PERM(), member.getUSER_IDX());

	if(m_SearchType!= null && "INST_ACTN".equals(m_SearchType) && (m_SearchValue!=null && m_SearchValue.length() > 0)){
		if("C".equals(m_SearchValue)){
			m_SearchValue = "신규";
		}else if("E".equals(m_SearchValue)){
			m_SearchValue = "연장";
		}else if("D".equals(m_SearchValue)){
			m_SearchValue = "삭제";
		}else if("U".equals(m_SearchValue)){
			m_SearchValue = "수정";
		}else if("G".equals(m_SearchValue)){
			m_SearchValue = "작업재개";
		}else if("R".equals(m_SearchValue)){
			m_SearchValue = "일시정지";
		}
	}

	int idx = totalCount;

%>
<%
	int current_Year = currentDateTime.getYear();

	DecimalFormat df = new DecimalFormat("###,###");
%>

<style>
	.form-select {
		background-position: right 5px center;
	}
</style>
<script type="text/javascript">
	function changeQuery() {

		let formData = new FormData();
		if(document.getElementById("SLOG_STDT").value.length>8){
			document.getElementById("SLOG_STDT").value = document.getElementById("SLOG_STDT").value.replaceAll("-","");
		}
		if(document.getElementById("SLOG_ENDT").value.length>8){
			document.getElementById("SLOG_ENDT").value = document.getElementById("SLOG_ENDT").value.replaceAll("-","");
		}
		formData.append("st", document.getElementById("searchType").value);
		formData.append("sv", document.getElementById("searchValue").value);
		formData.append("sd", document.getElementById("SLOG_STDT").value);
		formData.append("ed", document.getElementById("SLOG_ENDT").value);
		$.ajax({
			url: '/naverShoppingSearchLogSlot/1',
			type: "POST",
			data: formData,
			datatype: "json",
			enctype: 'application/x-www-form-urlencoded',
			cache: false,
			processData: false,
			contentType : false,

			error: function(xhr, status, error) {
				alert("error:" + error);
			},
			success: function(response) {
				console.log(response);
				window.location.href = response;
			}
		});
	}

	function downloadExcel() {

		let formData = new FormData();
		if(document.getElementById("SLOG_STDT").value.length>8){
			document.getElementById("SLOG_STDT").value = document.getElementById("SLOG_STDT").value.replaceAll("-","");
		}
		if(document.getElementById("SLOG_ENDT").value.length>8){
			document.getElementById("SLOG_ENDT").value = document.getElementById("SLOG_ENDT").value.replaceAll("-","");
		}
		formData.append("st", document.getElementById("searchType").value);
		formData.append("sv", document.getElementById("searchValue").value);
		formData.append("sd", document.getElementById("SLOG_STDT").value);
		formData.append("ed", document.getElementById("SLOG_ENDT").value);
		formData.append("USER_TYPE", '<%=USER_TYPE%>');
		$.ajax({
			url: '/downloadLogSlot',
			type: "POST",
			data: formData,
			datatype: "json",
			enctype: 'application/x-www-form-urlencoded',
			cache: false,
			processData: false,
			contentType : false,

			error: function(xhr, status, error) {
				alert("error:" + error);
			},
			success: function(response) {
				const json = JSON.parse(response)
				makeExcel(json.value, json.search);
			}
		});
	}

	function makeExcel(jsonResult, jsonSearch){
		let data = JSON.parse(jsonResult);

		// 2차원 배열 생성
		let row = [];

		let searchmenu = [];
		let search =[];
		searchmenu.push("로그 시작일");
		searchmenu.push("로그 종료일");
		search.push(jsonSearch.StartDate);
		search.push(jsonSearch.EndDate);
		if(jsonSearch.SearchValue != ""){
			searchmenu.push("검색구분");
			searchmenu.push("검색어");

			if (jsonSearch.SearchType == "USER_ID") {
				jsonSearch.SearchType = "아이디";
			} else if (jsonSearch.SearchType == "INST_ACTN") {
				jsonSearch.SearchType = "구분";
			} else if (jsonSearch.SearchType == "PROD_GID") {
				jsonSearch.SearchType = "묶음MID";
			} else if (jsonSearch.SearchType == "PROD_MID") {
				jsonSearch.SearchType = "단품MID";
			} else if (jsonSearch.SearchType == "PROD_KYWD") {
				jsonSearch.SearchType = "키워드";
			}

			search.push(jsonSearch.SearchType);
			search.push(jsonSearch.SearchValue);
		}
		row.push(searchmenu);
		row.push(search);

		row.push("");

		let menu = [];
		menu.push("번호");
		menu.push("아이디");
		menu.push("구분");
		menu.push("기간");
		menu.push("개수");
		menu.push("슬롯타입");
		menu.push("키워드");
		menu.push("묶음MID");
		menu.push("단품MID");
		menu.push("생성자");
		menu.push("생성일");

		row.push(menu);

		let cnt = data.length;

		for(let i =0; i<data.length; i++){
			let bottom = [];

			let INST_ACTN = "";
			if(data[i].INST_ACTN != null && data[i].INST_ACTN != "") {
				if (data[i].INST_ACTN == "C") {
					INST_ACTN = "신규";
				} else if (data[i].INST_ACTN == "E") {
					INST_ACTN = "연장";
				} else if (data[i].INST_ACTN == "D") {
					INST_ACTN = "삭제";
				} else if (data[i].INST_ACTN == "U") {
					INST_ACTN = "수정";
				} else if (data[i].INST_ACTN == "G") {
					INST_ACTN = "작업재개";
				} else if (data[i].INST_ACTN == "R") {
					INST_ACTN = "일시정지";
				}
			}

			bottom.push(cnt--);
			bottom.push(data[i].USER_ID);
			bottom.push(INST_ACTN);
			bottom.push(data[i].SLOT_DAYS);
			bottom.push(data[i].SLOT_EA);
			bottom.push(data[i].TYPE_NAME);
			bottom.push(data[i].PROD_KYWD);
			bottom.push(data[i].PROD_GID);
			bottom.push(data[i].PROD_MID);
			bottom.push(data[i].INST_USER_ID);
			bottom.push(data[i].INST_DT);
			row.push(bottom);
		}

		// 시트 생성
		const ws = XLSX.utils.aoa_to_sheet(row);
		var wscols = [{"width" : 15},
				{"width" : 15},
				{"width" : 10},
				{"width" : 10},
				{"width" : 10},
				{"width" : 20},
				{"width" : 20},
				{"width" : 20},
				{"width" : 20},
				{"width" : 15},
				{"width" : 20}];
		ws['!cols'] = wscols;
		// 엑셀 파일 생성
		const wb = XLSX.utils.book_new();
		XLSX.utils.book_append_sheet(wb, ws, "네이버쇼핑로그");

		// 파일 저장
		const fileName = "네이버쇼핑로그_<%=Today%>.xlsx";
		XLSX.writeFile(wb, fileName);

	}

</script>

<body>
<!--  Body Wrapper -->
<div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed">
	<!-- Sidebar Start -->
	<jsp:include page="common/sidebar.jsp" />
	<!--  Sidebar End -->

	<!--  Main wrapper -->
	<div class="body-wrapper">
		<!--  Header Start -->
		<jsp:include page="common/header.jsp" />
		<!--  Header End -->
		<div class="container-fluid">

			<%--1--%>
			<div class="d-flex align-items-stretch">
				<div class="card w-100">
					<div class="card-body p-4">
						<div class="row d-flex align-items-center">
							<div class="col-lg-3 mt-3 px-2 mb-lg-0 px-lg-0 ps-lg-2 pe-lg-0">
								<select class="form-select" id="searchType" name="searchType">
									<option value="USER_ID" <%="USER_ID".equals(m_SearchType) ? "selected" : ""%>>아이디</option>
									<option value="INST_ACTN" <%="INST_ACTN".equals(m_SearchType) ? "selected" : ""%>>구분</option>
									<option value="PROD_GID" <%="PROD_GID".equals(m_SearchType) ? "selected" : ""%>>묶음MID</option>
									<option value="PROD_MID" <%="PROD_MID".equals(m_SearchType) ? "selected" : ""%>>단품MID</option>
									<option value="PROD_KYWD" <%="PROD_KYWD".equals(m_SearchType) ? "selected" : ""%>>키워드</option>
								</select>
							</div>
							<div class="col-lg-7 mt-3 px-2 mb-lg-0 px-lg-0 ps-lg-2">
								<input type="text" class="form-control" id="searchValue" name="searchValue" placeholder="검색어 입력" value="<%=m_SearchValue != null && !"".equals(m_SearchValue) ? m_SearchValue : ""%>">
							</div>
							<div class="col-lg-2 d-flex align-items-center justify-content-end justify-content-lg-start mt-3 px-2 mb-lg-0 px-lg-0 ps-lg-1 pe-lg-2">
								<button type="button" class="btn btn-primary ms-2 d-flex align-items-center" _msttexthash="9814740" _msthash="28" onclick="javascript:changeQuery()">
									<i class="ti ti-search fs-4 me-2"></i>검색
								</button>
								<a href="/naver-shopping-log-slots" type="button" class="btn btn-outline-primary ms-2" _msttexthash="9814740" _msthash="28"><i class="ti ti-reload fs-6"></i></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%--1End--%>
			<%--2--%>
			<div class="d-flex align-items-stretch">
				<div class="card w-100">
					<div class="card-body p-4">
						<div class="row">
							<div class="col-lg-6">
								<div class="row">
									<div class="col-lg-3">
										<h5 class="card-title fw-semibold mb-4">네이버 쇼핑<%=totalCount>0?"("+totalCount+")":""%></h5>
									</div>
									<div class="col-lg-4">
										<div class="input-group">
											<input class="form-control rounded-end px-4" type="text" id="SLOG_STDT" name="SLOG_STDT" <%= m_StartDate!=null && m_StartDate.length() > 0 ? "value=\""+m_StartDate+"\"" : "" %> readonly onchange="javascript:changeQuery()">
										</div>
									</div>
									<div class="col-lg-1  text-center align-middle px-0">
										<span class="fw-semibold">~</span>
									</div>
									<div class="col-lg-4">
										<div class="input-group">
											<input class="form-control rounded-end px-4" type="text" id="SLOG_ENDT" name="SLOG_ENDT" <%= m_EndDate!=null && m_EndDate.length() > 0 ? "value=\""+m_EndDate+"\"" : "" %> readonly onchange="javascript:changeQuery()">
										</div>
									</div>
								</div>
							</div>
							<div class="col-lg-6 text-end">
								<%if(logSlotList.size()>0 && "M".equals(member.getUSER_PERM())){%>
								<button type="button" class="btn btn-primary ms-2 mb-1" onclick="downloadExcel()">
									엑셀로 내려받기
								</button>
								<%}%>
							</div>
						</div>

						<%if(logSlotList.size()<1){%>
						<div class="d-flex align-items-center py-2 justify-content-center" style="min-height: 150px;">
							<h6 class="fw-semibold mb-0 ">조회된 로그가 없습니다.</h6>
						</div>
						<%}else{%>
						<div class="table-responsive">
							<table class="table text-nowrap mb-0 align-middle">
								<colgroup>
									<col width="6%">
									<col width="10.5%">
									<col width="7%">
									<col width="6%">
									<col width="6%">

									<col width="10.5%">
									<col width="10.5%">
									<col width="10.5%">
									<col width="10.5%">
									<col width="10.5%">
									<col width="10.5%">
								</colgroup>
								<thead class="text-dark fs-4 text-center bg-light-gray border-top">
								<tr>
									<%
										//////////////////////////////////////
										//////		Title Setting		//////
										//////////////////////////////////////
										String[] Title = new String[]{"번호", "아이디", "구분", "기간", "개수", "슬롯타입", "키워드", "묶음MID", "단품MID", "생성자", "생성일"};

										for(int i=0;i<Title.length;i++){
											out.print("<th class=\" align-middle\">");
											out.print("<h6 class=\"fw-semibold mb-0\">"+Title[i]+"</h6>");
											out.print("</th>");
										}
										out.print("</tr><tr>");
									%>
								</tr>
								</thead>

								<tbody class="text-center">
								<% 	for(int i=0;i<logSlotList.size();i++){
									String INST_ACTN = "";
								if(logSlotList.get(i).getINST_ACTN() != null && !"".equals(logSlotList.get(i).getINST_ACTN())){
									if("C".equals(logSlotList.get(i).getINST_ACTN())){
										INST_ACTN = "신규";
									}else if("E".equals(logSlotList.get(i).getINST_ACTN())){
										INST_ACTN = "연장";
									}else if("D".equals(logSlotList.get(i).getINST_ACTN())){
										INST_ACTN = "삭제";
									}else if("U".equals(logSlotList.get(i).getINST_ACTN())){
										INST_ACTN = "수정";
									}else if("G".equals(logSlotList.get(i).getINST_ACTN())){
										INST_ACTN = "작업재개";
									}else if("R".equals(logSlotList.get(i).getINST_ACTN())){
										INST_ACTN = "일시정지";
									}
								}else{
									INST_ACTN = "-";
								}
								%>

								<tr>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" value="<%=idx-- - (setPage-1)*100%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="ID_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" name="ID_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" value="<%=logSlotList.get(i).getUSER_ID()%>" title="<%=logSlotList.get(i).getUSER_ID()%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center <%="C".equals(logSlotList.get(i).getINST_ACTN()) ? "text-primary" : ("D".equals(logSlotList.get(i).getINST_ACTN()) ? "text-danger" : "")%>" id="ACTN_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" name="ACTN_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" value="<%=INST_ACTN%>" title="<%=INST_ACTN%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="DAYS_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" name="DAYS_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" value="<%=logSlotList.get(i).getSLOT_DAYS()%>" title="<%=logSlotList.get(i).getSLOT_DAYS()%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="EA_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" name="EA_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" value="<%=logSlotList.get(i).getSLOT_EA()%>" title="<%=logSlotList.get(i).getSLOT_EA()%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="NAME_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" name="NAME_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" value="<%=!"".equals(logSlotList.get(i).getTYPE_NAME())&&logSlotList.get(i).getTYPE_NAME()!=null ? logSlotList.get(i).getTYPE_NAME() : "-"%>" title="<%=!"".equals(logSlotList.get(i).getTYPE_NAME())&&logSlotList.get(i).getTYPE_NAME()!=null ? logSlotList.get(i).getTYPE_NAME() : "-"%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="KYWD_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" name="KYWD_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" value="<%=!"".equals(logSlotList.get(i).getPROD_KYWD()) ? logSlotList.get(i).getPROD_KYWD() : "-"%>" title="<%=!"".equals(logSlotList.get(i).getPROD_KYWD()) ? logSlotList.get(i).getPROD_KYWD() : "-"%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="GID_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" name="GID_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" value="<%=!"".equals(logSlotList.get(i).getPROD_GID()) ? logSlotList.get(i).getPROD_GID() : "-"%>" title="<%=!"".equals(logSlotList.get(i).getPROD_GID()) ? logSlotList.get(i).getPROD_GID() : "-"%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="MID_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" name="MID_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" value="<%=!"".equals(logSlotList.get(i).getPROD_MID()) ? logSlotList.get(i).getPROD_MID() : "-"%>" title="<%=!"".equals(logSlotList.get(i).getPROD_MID()) ? logSlotList.get(i).getPROD_MID() : "-"%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="INST_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" name="INST_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" value="<%=logSlotList.get(i).getINST_USER_ID()%>" title="<%=logSlotList.get(i).getINST_USER_ID()%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="DT_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" name="DT_<%=logSlotList.get(i).getLOG_SLOT_IDX()%>" value="<%=logSlotList.get(i).getINST_DT()%>" title="<%=logSlotList.get(i).getINST_DT()%>" readonly>
									</td>
								<tr />
								<%}%>
								</tbody>
							</table>
						</div>
						<%}%>

					</div>
				</div>
			</div>
			<%--2End--%>
			<%-- 페이징 --%>
			<%
				int displayPageNum = 10;
				int perPageNum = 100;
				int endPage = (int) (Math.ceil(setPage / (double) displayPageNum) * displayPageNum);
				int startPage = (endPage - displayPageNum) + 1;
				int tempEndPage = (int) (Math.ceil(totalCount / (double) perPageNum));

				if (endPage > tempEndPage) {
					endPage = tempEndPage;
				}

				boolean prev = startPage == 1 ? false : true;

				boolean next = endPage * perPageNum >= totalCount ? false : true;
//				System.out.println("*********************************");
//				System.out.println("setPage : "+setPage);
//				System.out.println("totalCount : "+totalCount);
//				System.out.println("endPage : "+endPage);
//				System.out.println("startPage : "+startPage);
//				System.out.println("tempEndPage : "+tempEndPage);
//				System.out.println("prev : "+prev);
//				System.out.println("next : "+next);
//				System.out.println("*********************************");
			%>
			<nav class="d-flex justify-content-between pt-3" aria-label="Page navigation">
				<ul class="pagination">
					<li class="page-item">
						<% 	if(setPage > 1){ %>
						<a class="page-link d-flex border-0" href="/naver-shopping-log-slots/<%=setPage - 1%><%=Param%>">
							<i class="ti ti-arrow-left fs-6 me-2"></i>Prev
						</a>
						<% } %>
					</li>
				</ul>

				<ul class="pagination d-none d-sm-block d-sm-flex">
					<%
						if(prev){
							out.print("<li class=\"page-item \"><a class=\"page-link\" href=\"/naver-shopping-log-slots/"+(startPage - 10)+Param+"\"> << </a></li>");
						}
						for(int i=startPage; i<=endPage; i++){
							out.print("<li class=\"page-item "+ (i==setPage ? "active" : "") +" \"><a class=\"page-link\" href=\"/naver-shopping-log-slots/"+(i)+Param+"\">"+(i)+"</a></li>");
						}
						if(next){
							out.print("<li class=\"page-item \"><a class=\"page-link\" href=\"/naver-shopping-log-slots/"+(startPage + 10)+Param+"\"> >> </a></li>");
						}
					%>
				</ul>
				<ul class="pagination">
					<li class="page-item">
						<% if(setPage < tempEndPage){ %>
						<a class="page-link d-flex border-0" href="/naver-shopping-log-slots/<%=setPage + 1%><%=Param%>" aria-label="Next">
							Next<i class="ti ti-arrow-right fs-6 ms-2"></i>
						</a>
						<% } %>
					</li>
				</ul>
			</nav>
			<%-- 페이징 끝 --%>

		</div>
	</div>
</div>

<jsp:include page="common/script.jsp" />
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css">

<!--한국어  달력 쓰려면 추가 로드-->
<%--<script src="resources/js/plugin/datepicker/bootstrap-datepicker.ko.min.js"></script>--%>
<script>
	$(function() {
		$('[data-toggle="tooltip"]').tooltip();
	})

	/* 검색어 엔터 */
	$(document).keyup(function(event) {
		if (event.which === 13) {
			changeQuery();
		}
	});
	/* 검색어 엔터 끝 */
</script>
<script>
	$(function() {
		$("#SLOG_STDT,#SLOG_ENDT").datepicker({
			showOn:"both"
			, buttonImage: "/img/Front/common/calendar.png"
			,buttonImageOnly: true
			,changeMonth:true
			,changeYear:true
			,dateFormat:"yy-mm-dd"
			,dayNames : ['일요일','월요일','화요일','수요일','목요일','금요일','토요일']
			,dayNamesMin : ['일','월','화','수','목','금','토']
			,monthNamesShort:  [ "1월", "2월", "3월", "4월", "5월", "6월","7월", "8월", "9월", "10월", "11월", "12월" ]
			,showOtherMonths:true
		});

		$(".ui-datepicker-trigger").css("width","25px");
		$(".ui-datepicker-trigger").css("height","25px");
		$(".ui-datepicker-trigger").css("align-self","center");
		$(".ui-datepicker-trigger").css("opacity","0.5");
		$(".ui-datepicker-trigger").css("display","inline-block");
		$(".ui-datepicker-trigger").css("position","absolute");
		$(".ui-datepicker-trigger").css("right","10px");

		// $('#SLOG_STDT').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
		// $('#SLOG_ENDT').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)

	});
</script>
</body>
<script defer src="/js/Front/xlsx.full.min.js"></script>
</html>