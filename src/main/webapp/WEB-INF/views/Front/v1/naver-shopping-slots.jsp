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
<%@ page import="com.slot.Model.Slot" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.slot.Model.NaverShoppingSlot" %>
<%@ page import="com.slot.Model.NaverShoppingSlotType" %>
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

	final String TAG = "NAVER_SHOPPING_SLOTS_PAGE";
	final String USER_TYPE = "NS_";
	request.setCharacterEncoding("utf-8");
	Member member = (Member) session.getAttribute(Protocol.Json.KEY_MEMBER);

	int setPage = 1;
	String m_SearchType = "", m_SearchValue = "", m_OrderType = "", Param = "";

	try{ m_SearchType =  (String)request.getAttribute("SearchType"); 			}catch(Exception e){ e.printStackTrace(); }
	try{ m_SearchValue = (String)request.getAttribute("SearchValue"); 			}catch(Exception e){ e.printStackTrace(); }
	try{ m_OrderType = (String)request.getAttribute("OrderType"); 			}catch(Exception e){ e.printStackTrace(); }

	Param += m_SearchType!=null && m_SearchType.length() > 0 ? "st=" + m_SearchType+"&" : "";
	Param += m_SearchValue!=null && m_SearchValue.length() > 0 ? "sv=" + m_SearchValue+"&" : "";
	Param += m_OrderType!=null && m_OrderType.length() > 0 ? "or=" + m_OrderType+"&" : "";
	Param = Param!=null && Param.length() > 0 ? "?" +Param : "";

	try{ setPage = Integer.parseInt( (String)request.getAttribute("page")); 	}catch(Exception e){ }


	int totalCount  = DBConnector.getNaverShoppingSlotListTotalCount(m_SearchType, m_SearchValue, member.getUSER_PERM(), member.getUSER_IDX());
	List<NaverShoppingSlot> slotList = DBConnector.getNaverShoppingSlotList(0, setPage - 1, m_SearchType, m_SearchValue, m_OrderType, member.getUSER_PERM(), member.getUSER_IDX());
	List<NaverShoppingSlotType> naverShoppingSlotTypeList = DBConnector.getNaverShoppingSlotType(0,"Y");
	int idx = totalCount;
%>
<%
	LocalDateTime currentDateTime = LocalDateTime.now();

	String Today = currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
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
		formData.append("st", document.getElementById("searchType").value);
		formData.append("sv", document.getElementById("searchValue").value);
		formData.append("or", document.getElementById("orderType").value);
		$.ajax({
			url: '/naverShoppingSearchSlot/1',
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
								<a href="/naver-shopping-slots" type="button" class="btn btn-outline-primary ms-2" _msttexthash="9814740" _msthash="28"><i class="ti ti-reload fs-6"></i></a>
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
							<div class="col-lg-4">
								<h5 class="card-title fw-semibold mb-4 col-lg-12">네이버 쇼핑<%=totalCount>0?"("+totalCount+")":""%></h5>
							</div>
							<div class="col-lg-7 text-end">
								<%if("M".equals(member.getUSER_PERM())){%>
								<button type="button" class="btn btn-outline-primary ms-2 mb-1" id="uploadBtn">
									대량등록
								</button>
								<%}%>
								<%if(slotList.size()>0){%>
								<%--<button type="button" class="btn btn-outline-primary mb-1" id="updatesBtn" &lt;%&ndash;data-bs-toggle="modal" data-bs-target="#updatesModal"&ndash;%&gt;>
									수정
								</button>--%>
								<%if("M".equals(member.getUSER_PERM())){%>
								<button type="button" class="btn btn-outline-primary ms-2 mb-1" onclick="setStatus()">
									선택확인
								</button>
								<%}%>
								<%if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){%>
								<button type="button" class="btn btn-outline-primary ms-2 mb-1" id="extendsBtn" <%--data-bs-toggle="modal" data-bs-target="#extendsModal"--%>>
									선택연장
								</button>
								<%}%>
								<%if("M".equals(member.getUSER_PERM())){%>
								<button type="button" class="btn btn-outline-primary ms-2 mb-1" onclick="setDelete()">
									선택삭제
								</button>
								<%}%>
								<%}%>
							</div>
							<div class="col-lg-1">
								<select class="form-select" id="orderType" name="orderType" onchange="javascript:changeQuery()">
									<option value="SLOT_IDX" <%="SLOT_IDX".equals(m_OrderType) ? "selected" : ""%>>등록순</option>
									<option value="SLOT_ENDT" <%="SLOT_ENDT".equals(m_OrderType) ? "selected" : ""%>>종료일순</option>
								</select>
							</div>
						</div>

						<%if(slotList.size()<1){%>
						<div class="d-flex align-items-center py-2 justify-content-center" style="min-height: 150px;">
							<h6 class="fw-semibold mb-0 ">조회된 슬롯이 없습니다.</h6>
						</div>
						<%}else{%>
						<div class="table-responsive">
							<table class="table text-nowrap mb-0 align-middle">
								<colgroup>
									<%if("M".equals(member.getUSER_PERM())){%>
									<col width="2.5%">
									<col width="2.5%">
									<col width="2.5%">
									<col width="11%">
									<col width="11%">

									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="5%">
									<%}else if("G".equals(member.getUSER_PERM())){%>
									<col width="2.5%">
									<col width="5%">
									<col width="11%">
									<col width="11%">

									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="5%">
									<%}else{%>
									<col width="7.5%">
									<col width="11%">
									<col width="11%">

									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="5%">
									<%}%>
								</colgroup>
								<thead class="text-dark fs-4 text-center bg-light-gray border-top">
								<tr>
									<%if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){%>
									<th class=" align-middle">
										<input type="checkbox" class="form-check-input" id="slotListAll" name="slotList" onclick="checkAll(this, 'slotList')">
									</th>
									<%}%>
									<%
										//////////////////////////////////////
										//////		Title Setting		//////
										//////////////////////////////////////
										String[] Title;

										if("M".equals(member.getUSER_PERM())){
											Title = new String[]{"번호", "상태", "아이디", "슬롯타입", "키워드", "URL", "묶음MID", "단품MID", "시작일", "종료일", "관리"};

										}else{
											Title = new String[]{"번호", "아이디", "슬롯타입", "키워드", "URL", "묶음MID", "단품MID", "시작일", "종료일", "관리"};

										}

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
								<% 	for(int i=0;i<slotList.size();i++){
									boolean WARN = false; //작업일 경고
									String strFormat = "yyyyMMdd";    //strStartDate 와 strEndDate 의 format

									SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
									try{
										Date todayDate =  sdf.parse(Today);
										Date startDate =  sdf.parse(slotList.get(i).getSLOT_STDT());
										Date endDate =  sdf.parse(slotList.get(i).getSLOT_ENDT());

										/* 작업일 경고 확인 */
										WARN = (endDate.getTime() - todayDate.getTime()) / (24*60*60*1000) < 3 ? true:false;

										/* 날짜 포맷 변경 */
										slotList.get(i).setSLOT_STDT(slotList.get(i).getSLOT_STDT().substring(0,4)+"-"+slotList.get(i).getSLOT_STDT().substring(4,6)+"-"+slotList.get(i).getSLOT_STDT().substring(6,8));
										slotList.get(i).setSLOT_ENDT(slotList.get(i).getSLOT_ENDT().substring(0,4)+"-"+slotList.get(i).getSLOT_ENDT().substring(4,6)+"-"+slotList.get(i).getSLOT_ENDT().substring(6,8));
									}catch(Exception e){
										e.printStackTrace();
									}
								%>

								<tr>
									<%if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){%>
									<td class=""><%--체크박스--%>
										<input type="checkbox" class="form-check-input" id="slotList<%=i%>" name="slotList" value="<%=slotList.get(i).getUSER_IDX()%>_<%=slotList.get(i).getSLOT_IDX()%>">
									</td>
									<%}%>
									<form id="updateNewForm<%=slotList.get(i).getSLOT_IDX()%>" name="updateNewForm">
									<td class=""><%--번호--%>
										<input type="text" class="form-control-plaintext text-center" value="<%=idx-- - (setPage-1)*100%>" readonly>
										<input type="hidden" id="IDX_<%=slotList.get(i).getSLOT_IDX()%>" name="IDX_<%=slotList.get(i).getSLOT_IDX()%>"  value="<%=slotList.get(i).getSLOT_IDX()%>" readonly>
										<input type="hidden" id="PAGE_<%=slotList.get(i).getSLOT_IDX()%>" name="PAGE_<%=slotList.get(i).getSLOT_IDX()%>" value="<%=setPage%>">
										<input type="hidden" id="PARAM_<%=slotList.get(i).getSLOT_IDX()%>" name="PARAM_<%=slotList.get(i).getSLOT_IDX()%>" value="<%=Param%>">
									</td>
									<%if("M".equals(member.getUSER_PERM())){%>
									<td class=""><%--상태--%>
										<span class="round-20 <%="".equals(slotList.get(i).getSLOT_STAT()) ? "bg-light-primary" : ("G".equals(slotList.get(i).getSLOT_STAT()) ? "bg-primary" : "bg-danger")%> rounded-circle d-inline-block"></span>
									</td>
									<%}%>
									<td class=""><%--아이디--%>
										<input type="text" class="form-control-plaintext text-center" id="ID_<%=slotList.get(i).getSLOT_IDX()%>" name="ID_<%=slotList.get(i).getSLOT_IDX()%>" value="<%=slotList.get(i).getUSER_ID()%>" title="<%=slotList.get(i).getUSER_ID()%>" readonly>
									</td>
									<td class=""><%--슬롯타입--%>
										<input type="text" class="form-control-plaintext text-center" id="TYPE_<%=slotList.get(i).getSLOT_IDX()%>" name="TYPE_<%=slotList.get(i).getSLOT_IDX()%>" value="<%=slotList.get(i).getTYPE_NAME()==null?"":slotList.get(i).getTYPE_NAME()%>" title="<%=slotList.get(i).getTYPE_NAME()==null?"":slotList.get(i).getTYPE_NAME()%>" readonly>
									</td>
									<td class=""><%--키워드--%>
										<input type="text" class="form-control text-center" id="KYWD_<%=slotList.get(i).getSLOT_IDX()%>" name="KYWD_<%=slotList.get(i).getSLOT_IDX()%>" value="<%=!"".equals(slotList.get(i).getPROD_KYWD()) ? slotList.get(i).getPROD_KYWD() : ""%>" title="<%=!"".equals(slotList.get(i).getPROD_KYWD()) ? slotList.get(i).getPROD_KYWD() : ""%>">
									</td>
									<td class=""><%--상품링크--%>
										<input type="text" class="form-control text-center" id="URL_<%=slotList.get(i).getSLOT_IDX()%>" name="URL_<%=slotList.get(i).getSLOT_IDX()%>" value="<%=!"".equals(slotList.get(i).getPROD_URL()) ? slotList.get(i).getPROD_URL() : ""%>" title="<%=!"".equals(slotList.get(i).getPROD_URL()) ? slotList.get(i).getPROD_URL() : ""%>">
									</td>
									<td class=""><%--묶음MID--%>
										<input type="text" class="form-control text-center" id="GID_<%=slotList.get(i).getSLOT_IDX()%>" name="GID_<%=slotList.get(i).getSLOT_IDX()%>" value="<%=!"".equals(slotList.get(i).getPROD_GID()) ? slotList.get(i).getPROD_GID() : ""%>" title="<%=!"".equals(slotList.get(i).getPROD_GID()) ? slotList.get(i).getPROD_GID() : ""%>">
									</td>
									<td class=""><%--단품MID--%>
										<input type="text" class="form-control text-center" id="MID_<%=slotList.get(i).getSLOT_IDX()%>" name="MID_<%=slotList.get(i).getSLOT_IDX()%>" value="<%=!"".equals(slotList.get(i).getPROD_MID()) ? slotList.get(i).getPROD_MID() : ""%>" title="<%=!"".equals(slotList.get(i).getPROD_MID()) ? slotList.get(i).getPROD_MID() : ""%>" >
									</td>
									<td class=""><%--시작일--%>
										<input type="text" class="form-control-plaintext text-center" id="STDT_<%=slotList.get(i).getSLOT_IDX()%>" name="STDT_<%=slotList.get(i).getSLOT_IDX()%>" value="<%=slotList.get(i).getSLOT_STDT()%>" title="<%=slotList.get(i).getSLOT_STDT()%>" readonly>
									</td>
									<td class=""><%--종료일--%> <%--종료일 3일전부터 빨개짐--%>
										<input type="text" class="form-control-plaintext text-center <%=WARN?"text-danger":""%>" id="ENDT_<%=slotList.get(i).getSLOT_IDX()%>" name="ENDT_<%=slotList.get(i).getSLOT_IDX()%>" value="<%=slotList.get(i).getSLOT_ENDT()%>" title="<%=slotList.get(i).getSLOT_ENDT()%>" readonly>
									</td>
									<td class=""><%--관리--%>
										<button type="button" class="btn btn-primary mb-1" onclick="setUpdateOne('<%=slotList.get(i).getUSER_IDX()%>','<%=slotList.get(i).getSLOT_IDX()%>')">
											수정
										</button>
									</td>
									</form>
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
						<a class="page-link d-flex border-0" href="/naver-shopping-slots/<%=setPage - 1%><%=Param%>">
							<i class="ti ti-arrow-left fs-6 me-2"></i>Prev
						</a>
						<% } %>
					</li>
				</ul>

				<ul class="pagination d-none d-sm-block d-sm-flex">
					<%
						if(prev){
							out.print("<li class=\"page-item \"><a class=\"page-link\" href=\"/naver-shopping-slots/"+(startPage - 10)+Param+"\"> << </a></li>");
						}
						for(int i=startPage; i<=endPage; i++){
							out.print("<li class=\"page-item "+ (i==setPage ? "active" : "") +" \"><a class=\"page-link\" href=\"/naver-shopping-slots/"+(i)+Param+"\">"+(i)+"</a></li>");
						}
						if(next){
							out.print("<li class=\"page-item \"><a class=\"page-link\" href=\"/naver-shopping-slots/"+(startPage + 10)+Param+"\"> >> </a></li>");
						}
					%>
				</ul>
				<ul class="pagination">
					<li class="page-item">
						<% if(setPage < tempEndPage){ %>
						<a class="page-link d-flex border-0" href="/naver-shopping-slots/<%=setPage + 1%><%=Param%>" aria-label="Next">
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

<%-- 업데이트 모달 --%>
<%--<div class="modal fade" id="updateModal" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="updateModalLabel"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="updateForm" name="updateForm">
					<input type="hidden" class="SLOT_IDX_UP" id="SLOT_IDX_UP" name="SLOT_IDX_UP">
					<input type="hidden" class="USER_IDX_UP" id="USER_IDX_UP" name="USER_IDX_UP">
					<input type="hidden" class="PAGE_UP" id="PAGE_UP" name="PAGE_UP">
					<input type="hidden" class="PARAM_UP" id="PARAM_UP" name="PARAM_UP">
					<div class="mb-3">
						<label for="PROD_GID_UP" class="col-form-label">묶음MID</label>
						<input type="text" class="form-control PROD_GID_UP" id="PROD_GID_UP" name="PROD_GID_UP">
					</div>
					<div class="mb-3">
						<label for="PROD_MID_UP" class="col-form-label">단품MID</label>
						<input type="text" class="form-control PROD_MID_UP" id="PROD_MID_UP" name="PROD_MID_UP">
					</div>
					<div class="mb-3">
						<label for="PROD_KYWD_UP" class="col-form-label">키워드</label>
						<input type="text" class="form-control PROD_KYWD_UP" id="PROD_KYWD_UP" name="PROD_KYWD_UP">
					</div>
					<div class="mb-3">
						<label for="PROD_URL_UP" class="col-form-label">상품링크</label>
						<input type="text" class="form-control PROD_URL_UP" id="PROD_URL_UP" name="PROD_URL_UP">
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" onclick="setUpdate()">수정하기</button>
			</div>
		</div>
	</div>
</div>--%>
<%-- 업데이트 모달 끝 --%>
<%-- 선택 업데이트 모달 --%>
<%--<div class="modal fade" id="updatesModal" tabindex="-1" aria-labelledby="updatesModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="updatesModalLabel"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="updatesForm" name="updatesForm">
					<input type="hidden" class="SLOT_IDX_UPS" id="SLOT_IDX_UPS" name="SLOT_IDX_UPS">
					<input type="hidden" class="USER_IDX_UPS" id="USER_IDX_UPS" name="USER_IDX_UPS">
					<input type="hidden" class="PAGE_UPS" id="PAGE_UPS" name="PAGE_UPS">
					<input type="hidden" class="PARAM_UPS" id="PARAM_UPS" name="PARAM_UPS">
					<div class="mb-3">
						<label for="PROD_GID_UPS" class="col-form-label">묶음MID</label>
						<input type="text" class="form-control PROD_GID_UPS" id="PROD_GID_UPS" name="PROD_GID_UPS">
					</div>
					<div class="mb-3">
						<label for="PROD_MID_UPS" class="col-form-label">단품MID</label>
						<input type="text" class="form-control PROD_MID_UPS" id="PROD_MID_UPS" name="PROD_MID_UPS">
					</div>
					<div class="mb-3">
						<label for="PROD_KYWD_UPS" class="col-form-label">키워드</label>
						<input type="text" class="form-control PROD_KYWD_UPS" id="PROD_KYWD_UPS" name="PROD_KYWD_UPS">
					</div>
					<div class="mb-3">
						<label for="PROD_URL_UPS" class="col-form-label">상품링크</label>
						<input type="text" class="form-control PROD_URL_UPS" id="PROD_URL_UPS" name="PROD_URL_UPS">
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" onclick="setUpdates()">수정하기</button>
			</div>
		</div>
	</div>
</div>--%>
<%-- 선택 업데이트 모달 끝 --%>
<%-- 연장 모달 --%>
<div class="modal fade" id="extendsModal" tabindex="-1" aria-labelledby="extendsModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="extendsModalLabel"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="extendsForm" name="extendsForm">
					<input type="hidden" class="SLOT_IDX_EX" id="SLOT_IDX_EX" name="SLOT_IDX_EX">
					<input type="hidden" class="USER_IDX_EX" id="USER_IDX_EX" name="USER_IDX_EX">
					<input type="hidden" class="PAGE_EX" id="PAGE_EX" name="PAGE_EX">
					<input type="hidden" class="PARAM_EX" id="PARAM_EX" name="PARAM_EX">
					<div class="mb-3">
						<label for="SLOT_DAYS_EX" class="col-form-label">연장일수</label>
						<input type="number" class="form-control SLOT_DAYS_EX" id="SLOT_DAYS_EX" name="SLOT_DAYS_EX" value="7" min="1">
						<%--<select class="form-select SLOT_DAYS_EX" id="SLOT_DAYS_EX" name="SLOT_DAYS_EX">
							<option value="7">7일</option>
							<option value="10">10일</option>
						</select>--%>
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" onclick="setExtends()">연장하기</button>
			</div>
		</div>
	</div>
</div>
<%-- 연장 모달 끝 --%>
<%-- 대량등록 모달 --%>
<div class="modal fade" id="uploadModal" tabindex="-1" aria-labelledby="uploadModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="uploadModalLabel"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="uploadForm" name="uploadForm">
					<div class="mb-3 text-end">
						<button type="button" class="btn btn-outline-primary ms-2 mb-1" onclick="location.href='/files/Excel/navershopping_sample.xlsx'">
							네이버 쇼핑 대량등록 양식 <i class="ti ti-download"></i>
						</button>
					</div>
					<div class="mb-3">
						<label for="uploadFile" class="col-form-label">네이버 쇼핑 엑셀 업로드</label>
						<input type="file" class="form-control" name="uploadFile" id="uploadFile" onchange="readExcel()" accept=".xlsx, .xls"/>
					</div>
					<div class="mb-3">
						<label for="uploadFile" class="col-form-label fs-2 text-danger">* 엑셀을 작성할 때, 아래 표를 참고하여 슬롯타입 대신 슬롯타입코드를 입력해 주세요.</label>
						<table class="table text-nowrap mb-0 align-middle">
							<thead class="text-dark fs-4 text-center bg-light-gray">
							<tr class="border-top">
								<th class=" align-middle">
									<h6 class="fw-semibold mb-0">슬롯타입코드</h6>
								</th>
								<th class="border-start align-middle">
									<h6 class="fw-semibold mb-0">슬롯타입</h6>
								</th>
							</tr>
							</thead>
							<tbody class="text-center">
							<%for(int i=0; i<naverShoppingSlotTypeList.size(); i++){%>
							<tr>
								<td class=""><%=naverShoppingSlotTypeList.get(i).getNS_SLOT_TYPE_IDX()%></td>
								<td class="border-start"><%=naverShoppingSlotTypeList.get(i).getTYPE_NAME()%></td>
							</tr>
							<%}%>
							</tbody>
						</table>
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" onclick="setUpload()">업로드</button>
			</div>
		</div>
	</div>
</div>
<%-- 대량등록 모달 끝 --%>

<jsp:include page="common/script.jsp" />
<script>
	$(function() {
		$('[data-toggle="tooltip"]').tooltip();
	})
	$(document).ready(function(){
		/*$("#updatesBtn").click(function(){
			const checkbox = $("input[name=slotList]:checked");
			if (checkbox.length < 1) {
				alert("슬롯을 선택하세요.");
				return;
			}
			$("#updatesModal").modal('show');
		});*/

		$("#extendsBtn").click(function(){
			const checkbox = $("input[name=slotList]:checked");
			if (checkbox.length < 1) {
				alert("슬롯을 선택하세요.");
				return;
			}
			$("#extendsModal").modal('show');
		});

		$("#uploadBtn").click(function(){
			$("#uploadModal").modal('show');
		});
	});

	/* 검색어 엔터 */
	$(document).keyup(function(event) {
		if (event.which === 13) {
			changeQuery();
		}
	});
	/* 검색어 엔터 끝 */

	/* 수정 모달 */
	/*var updateModal = document.getElementById('updateModal');
	updateModal.addEventListener('show.bs.modal', function(event) {
		var button = event.relatedTarget;
		var SLOT_IDX = button.getAttribute('data-bs-idx');
		var USER_IDX = button.getAttribute('data-bs-uidx');
		var PROD_GID = button.getAttribute('data-bs-gid');
		var PROD_MID = button.getAttribute('data-bs-mid');
		var PROD_KYWD = button.getAttribute('data-bs-kywd');
		var PROD_URL = button.getAttribute('data-bs-link');
		var PAGE = button.getAttribute('data-bs-page');
		var PARAM = button.getAttribute('data-bs-param');

		var SLOT_IDXInput = updateModal.querySelector('.modal-body .SLOT_IDX_UP');
		var USER_IDXInput = updateModal.querySelector('.modal-body .USER_IDX_UP');
		var PROD_GIDInput = updateModal.querySelector('.modal-body .PROD_GID_UP');
		var PROD_MIDInput = updateModal.querySelector('.modal-body .PROD_MID_UP');
		var PROD_KYWDInput = updateModal.querySelector('.modal-body .PROD_KYWD_UP');
		var PROD_URLInput = updateModal.querySelector('.modal-body .PROD_URL_UP');
		var PAGEInput = updateModal.querySelector('.modal-body .PAGE_UP');
		var PARAMInput = updateModal.querySelector('.modal-body .PARAM_UP');

		SLOT_IDXInput.value = SLOT_IDX;
		USER_IDXInput.value = USER_IDX;
		PROD_GIDInput.value = PROD_GID;
		PROD_MIDInput.value = PROD_MID;
		PROD_KYWDInput.value = PROD_KYWD;
		PROD_URLInput.value = PROD_URL;

		PAGEInput.value = PAGE;
		PARAMInput.value = PARAM;
	});*/
	/* 수정 모달 끝 */
</script>
</body>
<script>
	function checkAll(selectAll, target) {
		const checkboxes = document.getElementsByName(target);

		checkboxes.forEach((checkbox) => {
			checkbox.checked = selectAll.checked;
		})
	}

	function setDelete() {
		const checkbox = $("input[name=slotList]:checked");
		if (checkbox.length < 1) {
			alert("슬롯을 선택하세요.");
			return;
		}

		if(!confirm("삭제 하시겠습니까?")){
			return;
		}

		let jsonArray = new Array();

		checkbox.each(function(i) {
			// 전체선택 체크박스 제거
			if ($("#slotListAll").is(':checked') && i == 0) {
				return;
			}

			let json = new Object();

			let USER_IDX_SLOT_IDX = $(this).val().split('_');;
			json.USER_IDX = USER_IDX_SLOT_IDX[0];
			json.SLOT_IDX = USER_IDX_SLOT_IDX[1];
			json.PAGE = '<%=setPage%>';
			json.PARAM = '<%=Param%>';
			json.USER_TYPE = '<%=USER_TYPE%>';

			jsonArray.push(json);
		});

		let formData = new FormData();
		formData.append("PARAM", JSON.stringify(jsonArray));

		$.ajax({
			url: '/deleteSlot',
			type: "POST",
			datatype: "json",
			processData: false,
			data: formData,
			contentType : false,
			enctype: 'application/x-www-form-urlencoded',

			error: function(xhr, status, error) {
				alert("error:" + error);
			},
			success: function(response) {
				const json = JSON.parse(response)
				console.log('ajax: ' + json.value);
				alert(json.alert);

				location.href = "/naver-shopping-slots"+json.url;
			}
		});
	}

	/*function setUpdate(){
		var form = document.updateForm;

		form.setAttribute("charset", "UTF-8");
		form.setAttribute("method", "Post");  //Post 방식
		form.setAttribute("action", "/setSlot/"+form.USER_IDX_UP.value+"/"+form.SLOT_IDX_UP.value); //요청 보낼 주소
		form.submit();

	}*/

	/*function setUpdateNew(USER_IDX_UP, SLOT_IDX_UP){
		var form = document.getElementById('updateNewForm'+SLOT_IDX_UP);

		form.setAttribute("charset", "UTF-8");
		form.setAttribute("method", "Post");  //Post 방식
		form.setAttribute("action", "/setSlot/"+USER_IDX_UP+"/"+SLOT_IDX_UP); //요청 보낼 주소
		form.submit();

	}*/

	function setUpdateOne(USER_IDX_UP, SLOT_IDX_UP) {
		// let jsonArray = new Array();

		let json = new Object();

		json.USER_IDX = USER_IDX_UP;
		json.SLOT_IDX = SLOT_IDX_UP;
		json.PROD_GID = $("#GID_"+SLOT_IDX_UP).val();
		json.PROD_MID = $("#MID_"+SLOT_IDX_UP).val();
		json.PROD_KYWD = $("#KYWD_"+SLOT_IDX_UP).val();
		json.PROD_URL = $("#URL_"+SLOT_IDX_UP).val();
		<%if("M".equals(member.getUSER_PERM())){%>
		json.SLOT_TYPE = $("#TYPE_"+SLOT_IDX_UP).val();
		<%}%>
		json.PAGE = '<%=setPage%>';
		json.PARAM = '<%=Param%>';
		json.USER_TYPE = '<%=USER_TYPE%>';

		// jsonArray.push(json);

		let formData = new FormData();
		formData.append("PARAM", JSON.stringify(json));

		$.ajax({
			url: '/updateOneSlot',
			type: "POST",
			datatype: "json",
			processData: false,
			data: formData,
			contentType : false,
			enctype: 'application/x-www-form-urlencoded',

			error: function(xhr, status, error) {
				alert("error:" + error);
			},
			success: function(response) {
				const json = JSON.parse(response)
				alert(json.alert);

				location.href = "/naver-shopping-slots"+json.url;
			}
		});
	}

	/*function setUpdates() {
		const checkbox = $("input[name=slotList]:checked");
		if (checkbox.length < 1) {
			alert("슬롯을 선택하세요.");
			return;
		}

		let jsonArray = new Array();

		checkbox.each(function(i) {
			// 전체선택 체크박스 제거
			if ($("#slotListAll").is(':checked') && i == 0) {
				return;
			}

			let json = new Object();

			let USER_IDX_SLOT_IDX = $(this).val().split('_');;
			json.USER_IDX = USER_IDX_SLOT_IDX[0];
			json.SLOT_IDX = USER_IDX_SLOT_IDX[1];
			json.PROD_GID = $("#PROD_GID_UPS").val();
			json.PROD_MID = $("#PROD_MID_UPS").val();
			json.PROD_KYWD = $("#PROD_KYWD_UPS").val();
			json.PROD_URL = $("#PROD_URL_UPS").val();
			json.PAGE = '<%=setPage%>';
			json.PARAM = '<%=Param%>';

			jsonArray.push(json);
		});

		let formData = new FormData();
		formData.append("PARAM", JSON.stringify(jsonArray));

		$.ajax({
			url: '/updateSlot',
			type: "POST",
			datatype: "json",
			processData: false,
			data: formData,
			contentType : false,
			enctype: 'application/x-www-form-urlencoded',

			error: function(xhr, status, error) {
				alert("error:" + error);
			},
			success: function(response) {
				const json = JSON.parse(response)
				console.log('ajax: ' + json.value);
				alert(json.alert);

				location.href = "/naver-shopping-slots"+json.url;
			}
		});
	}*/

	function setExtends() {
		const checkbox = $("input[name=slotList]:checked");
		if (checkbox.length < 1) {
			alert("슬롯을 선택하세요.");
			return;
		}

		let jsonArray = new Array();

		checkbox.each(function(i) {
			// 전체선택 체크박스 제거
			if ($("#slotListAll").is(':checked') && i == 0) {
				return;
			}

			let json = new Object();

			let USER_IDX_SLOT_IDX = $(this).val().split('_');;
			json.USER_IDX = USER_IDX_SLOT_IDX[0];
			json.SLOT_IDX = USER_IDX_SLOT_IDX[1];
			json.SLOT_DAYS = $("#SLOT_DAYS_EX").val();
			json.PAGE = '<%=setPage%>';
			json.PARAM = '<%=Param%>';
			json.USER_TYPE = '<%=USER_TYPE%>';

			jsonArray.push(json);
		});

		let formData = new FormData();
		formData.append("PARAM", JSON.stringify(jsonArray));

		$.ajax({
			url: '/extendSlot',
			type: "POST",
			datatype: "json",
			processData: false,
			data: formData,
			contentType : false,
			enctype: 'application/x-www-form-urlencoded',

			error: function(xhr, status, error) {
				alert("error:" + error);
			},
			success: function(response) {
				const json = JSON.parse(response)
				console.log('ajax: ' + json.value);
				alert(json.alert);

				location.href = "/naver-shopping-slots"+json.url;
			}
		});
	}

	function setStatus() {
		const checkbox = $("input[name=slotList]:checked");
		if (checkbox.length < 1) {
			alert("슬롯을 선택하세요.");
			return;
		}

		let jsonArray = new Array();

		checkbox.each(function(i) {
			// 전체선택 체크박스 제거
			if ($("#slotListAll").is(':checked') && i == 0) {
				return;
			}

			let json = new Object();

			let USER_IDX_SLOT_IDX = $(this).val().split('_');;
			json.USER_IDX = USER_IDX_SLOT_IDX[0];
			json.SLOT_IDX = USER_IDX_SLOT_IDX[1];
			json.PAGE = '<%=setPage%>';
			json.PARAM = '<%=Param%>';
			json.USER_TYPE = '<%=USER_TYPE%>';

			jsonArray.push(json);
		});

		let formData = new FormData();
		formData.append("PARAM", JSON.stringify(jsonArray));

		$.ajax({
			url: '/statusSlot',
			type: "POST",
			datatype: "json",
			processData: false,
			data: formData,
			contentType : false,
			enctype: 'application/x-www-form-urlencoded',

			error: function(xhr, status, error) {
				alert("error:" + error);
			},
			success: function(response) {
				const json = JSON.parse(response)
				console.log('ajax: ' + json.value);
				alert(json.alert);

				location.href = "/naver-shopping-slots"+json.url;
			}
		});
	}

	function readExcel()
	{
		let input = event.target;
		let reader = new FileReader();
		reader.onload = function () {
			let data = reader.result;
			let workBook = XLSX.read(data, {type: 'binary'});
			workBook.SheetNames.forEach(function (sheetName, index) {
				let rows = XLSX.utils.sheet_to_json(workBook.Sheets[sheetName]);
				if(index==0){
					if(rows!=null && rows.length > 0){
						this._rows = rows;
						// console.log(JSON.stringify(rows));
					}
				}
			})
		};
		reader.readAsBinaryString(input.files[0]);
	}

	function setUpload() {
		let json = new Object();

		json.USER_TYPE = '<%=USER_TYPE%>';

		let formData = new FormData();
		formData.append("PARAM", JSON.stringify(this._rows));
		formData.append("USER_TYPE", JSON.stringify(json));

		$.ajax({
			url: '/uploadSlotExcel',
			type: "POST",
			datatype: "json",
			processData: false,
			data: formData,
			contentType : false,
			enctype: 'application/x-www-form-urlencoded',

			error: function(xhr, status, error) {
				alert("error:" + error);
			},
			success: function(response) {
				const json = JSON.parse(response);
				console.log(json.value);
				alert(json.alert);

				location.href = "/naver-shopping-slots";
			}
		});
	}

</script>
<script defer src="/js/Front/xlsx.full.min.js"></script>
</html>