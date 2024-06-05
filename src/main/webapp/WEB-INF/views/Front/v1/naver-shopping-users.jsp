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

	final String TAG = "NAVER_SHOPPING_USERS_PAGE";
	final String USER_TYPE = "NS_";
	request.setCharacterEncoding("utf-8");
	Member member = (Member) session.getAttribute(Protocol.Json.KEY_MEMBER);

	int setPage = 1;
	String m_SearchType = "", m_SearchValue = "", Param = "";

	try{ m_SearchType =  (String)request.getAttribute("SearchType"); 			}catch(Exception e){ e.printStackTrace(); }
	try{ m_SearchValue = (String)request.getAttribute("SearchValue"); 			}catch(Exception e){ e.printStackTrace(); }

	Param += m_SearchType!=null && m_SearchType.length() > 0 ? "st=" + m_SearchType+"&" : "";
	Param += m_SearchValue!=null && m_SearchValue.length() > 0 ? "sv=" + m_SearchValue+"&" : "";
	Param = Param!=null && Param.length() > 0 ? "?" +Param : "";

	try{ setPage = Integer.parseInt( (String)request.getAttribute("page")); 	}catch(Exception e){ }


	int totalCount  = DBConnector.getMemberListTotalCount(m_SearchType, m_SearchValue, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX());
	List<Member> memberList = DBConnector.getMemberList(0, setPage - 1, m_SearchType, m_SearchValue, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX());

	int idx = totalCount;

	List<NaverShoppingSlotType> naverShoppingSlotTypeList = DBConnector.getNaverShoppingSlotType(0,"Y");
//		int totalCount  = "ADMN".equals(member.getPART_CODE()) ? DBConnector.getQuestionDataTotalCount(null, null, null) : DBConnector.getQuestionDataTotalCount(null, null, member.getUSER_IDX());
//		List<Member> memberList = "ADMN".equals(member.getPART_CODE()) ? DBConnector.getQuestionDataList(0, setPage - 1, null, null, null) : DBConnector.getQuestionDataList(0, setPage - 1, null, null, member.getUSER_IDX());
%>
<%
	LocalDateTime currentDateTime = LocalDateTime.now();

	String Today = currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
	int current_Year = currentDateTime.getYear();

	DecimalFormat df = new DecimalFormat("###,###");
%>

<style>
	.form-select {
		background-position: right 5px center;
	}

	#loader{
		display: none;
		margin: 0 auto;
		padding: 30px;
		max-width: 1170px;
	}

	#text{
		/*margin: 0 auto;*/
		width: 100px;
		height: 100px;
		position: absolute;
		top: 50%;
		left: 50%;
		font-size: 50px;
		color: white;
		transform: translate(-50%,-50%);
		-ms-transform: translate(-50%,-50%);
		z-index: 1056;
	}

	#text img{
		width: 100%;
		height: 100%;
	}
</style>
<script type="text/javascript">
	function changeQuery() {

		let formData = new FormData();
		formData.append("st", document.getElementById("searchType").value);
		formData.append("sv", document.getElementById("searchValue").value);
		$.ajax({
			url: '/naverShoppingSearchUser/1',
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
									<option value="USER_MEMO" <%="USER_MEMO".equals(m_SearchType) ? "selected" : ""%>>메모</option>
								</select>
							</div>
							<div class="col-lg-7 mt-3 px-2 mb-lg-0 px-lg-0 ps-lg-2">
								<input type="text" class="form-control" id="searchValue" name="searchValue" placeholder="검색어 입력" value="<%=m_SearchValue != null && !"".equals(m_SearchValue) ? m_SearchValue : ""%>">
							</div>
							<div class="col-lg-2 d-flex align-items-center justify-content-end justify-content-lg-start mt-3 px-2 mb-lg-0 px-lg-0 ps-lg-1 pe-lg-2">
								<button type="button" class="btn btn-primary ms-2 d-flex align-items-center" _msttexthash="9814740" _msthash="28" onclick="javascript:changeQuery()">
									<i class="ti ti-search fs-4 me-2"></i>검색
								</button>
								<a href="/naver-shopping-users" type="button" class="btn btn-outline-primary ms-2" _msttexthash="9814740" _msthash="28"><i class="ti ti-reload fs-6"></i></a>
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
								<h5 class="card-title fw-semibold mb-4">네이버 쇼핑<%=totalCount>0?"("+totalCount+")":""%></h5>
							</div>
							<div class="col-lg-8 text-end">
								<button type="button" class="btn btn-outline-primary mb-1" data-bs-toggle="modal" data-bs-target="#insertModal" data-bs-inidx="<%=member.getUSER_IDX()%>">
									유저 추가
								</button>
								<%if(memberList.size()>0 && "M".equals(member.getUSER_PERM())){%>
								<button type="button" class="btn btn-outline-primary ms-2 mb-1" onclick="setDelete()">
									삭제
								</button>
								<%}%>
							</div>
						</div>

						<%if(memberList.size()<1){%>
						<div class="d-flex align-items-center py-2 justify-content-center" style="min-height: 150px;">
							<h6 class="fw-semibold mb-0 ">조회된 유저가 없습니다.</h6>
						</div>
						<%}else{%>
						<div class="table-responsive">
							<table class="table text-nowrap mb-0 align-middle">
								<colgroup>
									<%if("M".equals(member.getUSER_PERM())){%>
									<col width="5%">
									<col width="5%">
									<col width="20%">
									<col width="20%">
									<col width="20%">
									<col width="20%">
									<col width="5%">
									<col width="5%">
									<col width="5%">
									<col width="5%">
									<%}else{%>
									<col width="10%">
									<col width="20%">
									<col width="20%">
									<col width="20%">
									<col width="10%">
									<col width="10%">
									<col width="10%">
									<%}%>

								</colgroup>
								<thead class="text-dark fs-4 text-center bg-light-gray border-top">
								<tr>
									<%if("M".equals(member.getUSER_PERM())){%>
									<th class=" align-middle">
										<input type="checkbox" class="form-check-input" id="memberListAll" name="memberList" onclick="checkAll(this, 'memberList')">
									</th>
									<%}%>
									<%
										//////////////////////////////////////
										//////		Title Setting		//////
										//////////////////////////////////////
										String[] Title;

										if("M".equals(member.getUSER_PERM())){
											Title = new String[]{"번호", "아이디", "비밀번호", "권한", "생성자", "슬롯", "슬롯추가", "메모", "수정"};
										}else{
											Title = new String[]{"번호", "아이디", "비밀번호", "슬롯", "슬롯추가", "메모", "수정"};
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
								<% 	for(int i=0;i<memberList.size();i++){ %>
								<tr>
									<%if("M".equals(member.getUSER_PERM())){%>
									<td class="">
										<input type="checkbox" class="form-check-input" id="memberList<%=i%>" name="memberList" value="<%=memberList.get(i).getUSER_IDX()%>">
									</td>
									<%}%>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" value="<%=idx-- - (setPage-1)*100%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="ID_<%=memberList.get(i).getUSER_IDX()%>" name="ID_<%=memberList.get(i).getUSER_IDX()%>" value="<%=memberList.get(i).getUSER_ID()%>" title="<%=memberList.get(i).getUSER_ID()%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="PWD_<%=memberList.get(i).getUSER_IDX()%>" name="PWD_<%=memberList.get(i).getUSER_IDX()%>" value="<%=memberList.get(i).getUSER_PWD()%>" title="<%=memberList.get(i).getUSER_PWD()%>" readonly>
									</td>
									<%if("M".equals(member.getUSER_PERM())){%>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="PERM_<%=memberList.get(i).getUSER_IDX()%>" name="PERM_<%=memberList.get(i).getUSER_IDX()%>" value="<%="S".equals(memberList.get(i).getUSER_PERM()) ? "셀러" : ("G".equals(memberList.get(i).getUSER_PERM()) ? "총판" : "마스터")%>" title="<%="S".equals(memberList.get(i).getUSER_PERM()) ? "셀러" : ("G".equals(memberList.get(i).getUSER_PERM()) ? "총판" : "마스터")%>" readonly>
									</td>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="INST_<%=memberList.get(i).getUSER_IDX()%>" name="INST_<%=memberList.get(i).getUSER_IDX()%>" value="<%=memberList.get(i).getINST_ADMN()%>" title="<%=memberList.get(i).getINST_ADMN()%>" readonly>
									</td>
									<%}%>
									<td class="">
										<input type="text" class="form-control-plaintext text-center" id="SLOT_<%=memberList.get(i).getUSER_IDX()%>" name="SLOT_<%=memberList.get(i).getUSER_IDX()%>" value="<%=memberList.get(i).getSLOT_EA()%>" title="<%=memberList.get(i).getSLOT_EA()%>" readonly>
									</td>
									<td class="">
										<a class="text-body cursor-pointer" data-bs-toggle="modal" data-bs-target="#slotModal" data-bs-idx="<%=memberList.get(i).getUSER_IDX()%>" data-bs-page="<%=setPage%>" data-bs-param="<%=Param%>">
                                                        <span>
                                                            <i class="ti ti-circle-plus fs-6"></i>
                                                        </span>
										</a>
									</td>
									<td class="">
										<span data-toggle="tooltip" data-placement="top" title="<%=memberList.get(i).getUSER_MEMO()%>"><i class="ti ti-message-2 fs-6"></i></span>
									</td>
									<td class="">
										<a class="text-body cursor-pointer" data-bs-toggle="modal" data-bs-target="#updateModal" data-bs-idx="<%=memberList.get(i).getUSER_IDX()%>" data-bs-id="<%=memberList.get(i).getUSER_ID()%>" data-bs-pwd="<%=memberList.get(i).getUSER_PWD()%>" data-bs-perm="<%=memberList.get(i).getUSER_PERM()%>" data-bs-memo="<%=memberList.get(i).getUSER_MEMO()%>" data-bs-page="<%=setPage%>" data-bs-param="<%=Param%>">
                                                        <span>
                                                            <i class="ti ti-edit fs-6"></i>
                                                        </span>
										</a>
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
						<a class="page-link d-flex border-0" href="/naver-shopping-users/<%=setPage - 1%><%=Param%>">
							<i class="ti ti-arrow-left fs-6 me-2"></i>Prev
						</a>
						<% } %>
					</li>
				</ul>

				<ul class="pagination d-none d-sm-block d-sm-flex">
					<%
						if(prev){
							out.print("<li class=\"page-item \"><a class=\"page-link\" href=\"/naver-shopping-users/"+(startPage - 10)+Param+"\"> << </a></li>");
						}
						for(int i=startPage; i<=endPage; i++){
							out.print("<li class=\"page-item "+ (i==setPage ? "active" : "") +" \"><a class=\"page-link\" href=\"/naver-shopping-users/"+(i)+Param+"\">"+(i)+"</a></li>");
						}
						if(next){
							out.print("<li class=\"page-item \"><a class=\"page-link\" href=\"/naver-shopping-users/"+(startPage + 10)+Param+"\"> >> </a></li>");
						}
					%>
				</ul>
				<ul class="pagination">
					<li class="page-item">
						<% if(setPage < tempEndPage){ %>
						<a class="page-link d-flex border-0" href="/naver-shopping-users/<%=setPage + 1%><%=Param%>" aria-label="Next">
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

<%-- 추가 모달 --%>
<div class="modal fade" id="insertModal" tabindex="-1" aria-labelledby="insertModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="insertModalLabel"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="insertForm" name="insertForm">
					<input type="hidden" class="INST_ADMN_IN" id="INST_ADMN_IN" name="INST_ADMN_IN">
					<div class="mb-3">
						<label for="USER_ID_IN" class="col-form-label">아이디</label>
						<input type="text" class="form-control USER_ID_IN" id="USER_ID_IN" name="USER_ID_IN">
					</div>
					<div class="mb-3">
						<label for="USER_PWD_IN" class="col-form-label">비밀번호</label>
						<input type="text" class="form-control USER_PWD_IN" id="USER_PWD_IN" name="USER_PWD_IN">
					</div>
					<%if("M".equals(member.getUSER_PERM())){%>
					<div class="mb-3">
						<label for="USER_PERM_IN" class="col-form-label">권한</label>
						<select class="form-select USER_PERM_IN" id="USER_PERM_IN" name="USER_PERM_IN">
							<option value="S">셀러</option>
							<option value="G">총판</option>
							<option value="M">마스터</option>
						</select>
					</div>
					<%}%>
					<div class="mb-3">
						<label for="USER_MEMO_IN" class="col-form-label">메모</label>
						<textarea class="form-control USER_MEMO_IN" id="USER_MEMO_IN" name="USER_MEMO_IN" rows="4"></textarea>
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" onclick="setInsert()">저장하기</button>
			</div>
		</div>
	</div>
</div>
<%-- 추가 모달 끝 --%>
<%-- 업데이트 모달 --%>
<div class="modal fade" id="updateModal" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="updateModalLabel"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="updateForm" name="updateForm">
					<input type="hidden" class="USER_IDX_UP" id="USER_IDX_UP" name="USER_IDX_UP">
					<input type="hidden" class="PAGE_UP" id="PAGE_UP" name="PAGE_UP">
					<input type="hidden" class="PARAM_UP" id="PARAM_UP" name="PARAM_UP">
					<div class="mb-3">
						<label for="USER_ID_UP" class="col-form-label">아이디</label>
						<input type="text" class="form-control USER_ID_UP" id="USER_ID_UP" name="USER_ID_UP" readonly>
					</div>
					<div class="mb-3">
						<label for="USER_PWD_UP" class="col-form-label">비밀번호</label>
						<input type="text" class="form-control USER_PWD_UP" id="USER_PWD_UP" name="USER_PWD_UP">
					</div>
					<%if("M".equals(member.getUSER_PERM())){%>
					<div class="mb-3">
						<label for="USER_PERM_UP" class="col-form-label">권한</label>
						<select class="form-select USER_PERM_UP" id="USER_PERM_UP" name="USER_PERM_UP">
							<option value="S">셀러</option>
							<option value="G">총판</option>
							<option value="M">마스터</option>
						</select>
					</div>
					<%}%>
					<div class="mb-3">
						<label for="USER_MEMO_UP" class="col-form-label">메모</label>
						<textarea class="form-control USER_MEMO_UP" id="USER_MEMO_UP" name="USER_MEMO_UP" rows="4"></textarea>
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" onclick="setUpdate()">수정하기</button>
			</div>
		</div>
	</div>
</div>
<%-- 업데이트 모달 끝 --%>
<%-- 슬롯 모달 --%>
<div class="modal fade" id="slotModal" tabindex="-1" aria-labelledby="slotModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="slotModalLabel"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="slotForm" name="slotForm">
					<input type="hidden" class="USER_IDX_SL" id="USER_IDX_SL" name="USER_IDX_SL">
					<input type="hidden" class="PAGE_SL" id="PAGE_SL" name="PAGE_SL">
					<input type="hidden" class="PARAM_SL" id="PARAM_SL" name="PARAM_SL">
<%--					<input type="hidden" class="SLOT_ENDT_SL" id="SLOT_ENDT_SL" name="SLOT_ENDT_SL">--%>
					<div class="mb-3">
						<label for="SLOT_EA_SL" class="col-form-label">추가개수</label>
						<input type="number" class="form-control SLOT_EA_SL" id="SLOT_EA_SL" name="SLOT_EA_SL" value="1" min="1">
					</div>
					<div class="mb-3">
						<label for="SLOT_TYPE_SL" class="col-form-label">슬롯타입</label>
						<select class="form-select SLOT_TYPE_SL" id="SLOT_TYPE_SL" name="SLOT_TYPE_SL">
							<%for(int i=0; i< naverShoppingSlotTypeList.size(); i++){%>
							<option value="<%=naverShoppingSlotTypeList.get(i).getNS_SLOT_TYPE_IDX()%>"><%=naverShoppingSlotTypeList.get(i).getTYPE_NAME()%></option>
							<%}%>
						</select>
					</div>
					<div class="mb-3">
						<label for="SLOT_DAYS_SL" class="col-form-label">작업일수</label>
						<input type="number" class="form-control SLOT_DAYS_SL" id="SLOT_DAYS_SL" name="SLOT_DAYS_SL" value="7" min="1" oninput="setEndDate()">
						<%--<select class="form-select SLOT_DAYS_SL" id="SLOT_DAYS_SL" name="SLOT_DAYS_SL">
							<option value="7">7일</option>
							<option value="10">10일</option>
						</select>--%>
					</div>
					<div class="mb-3">
						<label for="SLOT_STDT_SL" class="col-form-label">시작일</label>
						<div class="input-group me-2">
							<input class="form-control rounded-end pe-5" type="text" id="SLOT_STDT_SL" name="SLOT_STDT_SL" onchange="setEndDate()" readonly>
						</div>
					</div>
					<div class="mb-3">
						<label for="SLOT_ENDT_SL" class="col-form-label">종료일</label>
						<input type="text" class="form-control pe-5 SLOT_ENDT_SL" class="SLOT_ENDT_SL" id="SLOT_ENDT_SL" name="SLOT_ENDT_SL" readonly>
						<%--<div class="input-group me-2">
							<input class="form-control rounded-end pe-5" type="text" id="SLOT_STDT_SL" name="SLOT_STDT_SL" readonly>
						</div>--%>
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" onclick="setSlot()">추가하기</button>
			</div>
		</div>
	</div>
</div>
<%-- 슬롯 모달 끝 --%>
<div id="loader">
	<div id="text"><img src="/img/Front/common/loading.gif" alt="Loading..."/></div>
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

	/* 추가 모달 */
	var insertModal = document.getElementById('insertModal');
	insertModal.addEventListener('show.bs.modal', function(event) {
		// Button that triggered the modal
		var button = event.relatedTarget;
		// Extract info from data-bs-* attributes
		var USER_IDX = button.getAttribute('data-bs-inidx');

		$("#INST_ADMN_IN").val(USER_IDX);

	});
	/* 추가 모달 끝 */

	/* 수정 모달 */
	var updateModal = document.getElementById('updateModal');
	updateModal.addEventListener('show.bs.modal', function(event) {
		// Button that triggered the modal
		var button = event.relatedTarget;
		// Extract info from data-bs-* attributes
		var USER_IDX = button.getAttribute('data-bs-idx');
		var USER_ID = button.getAttribute('data-bs-id');
		var USER_PWD = button.getAttribute('data-bs-pwd');
		var USER_MEMO = button.getAttribute('data-bs-memo');
		var PAGE = button.getAttribute('data-bs-page');
		var PARAM = button.getAttribute('data-bs-param');

		// var modalTitle = updateModal.querySelector('.modal-title');
		var USER_IDXInput = updateModal.querySelector('.modal-body .USER_IDX_UP');
		var USER_IDInput = updateModal.querySelector('.modal-body .USER_ID_UP');
		var USER_PWDInput = updateModal.querySelector('.modal-body .USER_PWD_UP');
		var USER_MEMOInput = updateModal.querySelector('.modal-body .USER_MEMO_UP');
		var PAGEInput = updateModal.querySelector('.modal-body .PAGE_UP');
		var PARAMInput = updateModal.querySelector('.modal-body .PARAM_UP');

		// modalTitle.textContent = USER_ID;
		USER_IDXInput.value = USER_IDX;
		USER_IDInput.value = USER_ID;
		USER_PWDInput.value = USER_PWD;

		USER_MEMOInput.innerHTML = USER_MEMO;

		PAGEInput.value = PAGE;
		PARAMInput.value = PARAM;

		<%if("M".equals(member.getUSER_PERM())){%>
		var USER_PERM = button.getAttribute('data-bs-perm');
		var USER_PERMSelectbox = updateModal.querySelector('.modal-body .USER_PERM_UP');
		for (var i = 0; i < USER_PERMSelectbox.options.length; i++) {
			if (USER_PERMSelectbox.options[i].value == USER_PERM) {
				USER_PERMSelectbox.options[i].selected = true;
			}
		}
		<%}%>
	});
	/* 수정 모달 끝 */

	/* 슬롯 모달 */
	var slotModal = document.getElementById('slotModal');
	slotModal.addEventListener('show.bs.modal', function(event) {
		// Button that triggered the modal
		var button = event.relatedTarget;
		// Extract info from data-bs-* attributes
		var USER_IDX = button.getAttribute('data-bs-idx');
		var PAGE = button.getAttribute('data-bs-page');
		var PARAM = button.getAttribute('data-bs-param');

		$("#USER_IDX_SL").val(USER_IDX);
		$("#PAGE_SL").val(PAGE);
		$("#PARAM_SL").val(PARAM);
	});
	/* 슬롯 모달 끝 */
</script>
<script>
	$(function() {
		$("#SLOT_STDT_SL").datepicker({
			showOn:"both" /*both*/
			, buttonImage: "/img/Front/common/calendar.png"
			,buttonImageOnly: true
			,changeMonth:true
			,changeYear:true
			,dateFormat:"yy-mm-dd"
			,dayNames : ['일요일','월요일','화요일','수요일','목요일','금요일','토요일']
			,dayNamesMin : ['일','월','화','수','목','금','토']
			,monthNamesShort:  [ "1월", "2월", "3월", "4월", "5월", "6월","7월", "8월", "9월", "10월", "11월", "12월" ]
			,beforeShowDay: function(date) {
				var today = new Date();
				var tomorrow = new Date();
				tomorrow.setDate(today.getDate() + 1);
				// 비교할 때 시간 부분을 제거합니다.
				today.setHours(0, 0, 0, 0);
				tomorrow.setHours(0, 0, 0, 0);
				return [date >= tomorrow, ""];
			}
			,showOtherMonths:true
		});

		$(".ui-datepicker-trigger").css("width","25px");
		$(".ui-datepicker-trigger").css("height","25px");
		$(".ui-datepicker-trigger").css("align-self","center");
		$(".ui-datepicker-trigger").css("opacity","0.5");
		$(".ui-datepicker-trigger").css("display","inline-block");
		$(".ui-datepicker-trigger").css("position","absolute");
		$(".ui-datepicker-trigger").css("right","10px");
		$(".ui-datepicker-trigger").css("cursor","pointer");
		$("#ui-datepicker-div").css("z-index","1056");

		$("#SLOT_STDT_SL").click( function() {
			$("#ui-datepicker-div").css("z-index","1056");
		} );

		$('#SLOT_STDT_SL').datepicker('setDate', '+1D'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)

		// $('#SLOT_STDT_SL').datepicker('disable').removeAttr('disabled')

		setEndDate();
	});
</script>
</body>
<script>
	function checkAll(selectAll, target) {
		const checkboxes = document.getElementsByName(target);

		checkboxes.forEach((checkbox) => {
			checkbox.checked = selectAll.checked;
		})
	}

	/* 삭제 후 원래 페이지로 돌아감 */
	function setDelete() {
		const checkbox = $("input[name=memberList]:checked");
		if (checkbox.length < 1) {
			alert("유저를 선택하세요.");
			return;
		}

		if(!confirm("삭제 하시겠습니까?")){
			return;
		}

		$("#loader").show();

		let jsonArray = new Array();

		checkbox.each(function(i) {
			// 전체선택 체크박스 제거
			if ($("#memberListAll").is(':checked') && i == 0) {
				return;
			}

			let json = new Object();

			let USER_IDX = $(this).val();
			json.USER_IDX = USER_IDX;
			json.PAGE = '<%=setPage%>';
			json.PARAM = '<%=Param%>';
			jsonArray.push(json);
		});

		let formData = new FormData();
		formData.append("PARAM", JSON.stringify(jsonArray));

		$.ajax({
			url: '/deleteUser',
			type: "POST",
			datatype: "json",
			processData: false,
			data: formData,
			contentType : false,
			enctype: 'application/x-www-form-urlencoded',

			error: function(xhr, status, error) {
				$("#loader").hide();
				alert("error:" + error);
			},
			success: function(response) {
				const json = JSON.parse(response)
				console.log('ajax: ' + json.value);

				alert(json.alert);
				location.href = "/naver-shopping-users"+json.url;
			}
		});
	}

	/* 추가 후 첫 페이지로 감 */
	function setInsert(){
		var form = document.insertForm;

		if (!form.USER_ID_IN.value) {
			alert("아이디를 입력하세요.");
			form.USER_ID_IN.focus();
			return;
		}

		if (!form.USER_PWD_IN.value) {
			alert("비밀번호를 입력하세요.");
			form.USER_PWD_IN.focus();
			return;
		}

		$("#loader").show();

		const hiddenInput = document.createElement('input');
		hiddenInput.type = 'hidden';
		hiddenInput.name = 'USER_TYPE_IN';
		hiddenInput.value = '<%=USER_TYPE%>';
		<%if("M".equals(member.getUSER_PERM())){%>
			if(form.USER_PERM_IN.value == 'M'){
				hiddenInput.value = 'NS_NP_';
			}
		<%}%>
		form.appendChild(hiddenInput);

		form.setAttribute("charset", "UTF-8");
		form.setAttribute("method", "Post");  //Post 방식
		form.setAttribute("action", "/setUser"); //요청 보낼 주소

		form.submit();
	}

	/* 수정 후 원래 페이지로 돌아감 */
	function setUpdate(){
		var form = document.updateForm;

		if (!form.USER_PWD_UP.value) {
			alert("비밀번호를 입력하세요.");
			form.USER_PWD_UP.focus();
			return;
		}

		$("#loader").show();

		const hiddenInput = document.createElement('input');
		hiddenInput.type = 'hidden';
		hiddenInput.name = 'USER_TYPE_UP';
		hiddenInput.value = '<%=USER_TYPE%>';
		<%if("M".equals(member.getUSER_PERM())){%>
		if(form.USER_PERM_UP.value == 'M'){
			hiddenInput.value = 'NS_NP_';
		}
		<%}%>
		form.appendChild(hiddenInput);

		form.setAttribute("charset", "UTF-8");
		form.setAttribute("method", "Post");  //Post 방식
		form.setAttribute("action", "/setUser/"+form.USER_IDX_UP.value); //요청 보낼 주소
		form.submit();

	}

	/* 슬롯 추가 후 원래 페이지로 돌아감 */
	function setSlotOld(){
		var form = document.slotForm;

		if (!form.SLOT_EA_SL.value) {
			alert("추가개수를 입력하세요.");
			form.SLOT_EA_SL.focus();
			return;
		}

		if (!form.SLOT_DAYS_SL.value) {
			alert("작업일수를 입력하세요.");
			form.SLOT_DAYS_SL.focus();
			return;
		}

		if (!form.SLOT_STDT_SL.value) {
			alert("시작일 선택하세요.");
			form.SLOT_STDT_SL.focus();
			return;
		}

		$("#loader").show();

		/*const newDate = new Date(form.SLOT_STDT_SL.value)
		newDate.setDate(newDate.getDate() + Number(form.SLOT_DAYS_SL.value) - 1);*/

		form.SLOT_STDT_SL.value = form.SLOT_STDT_SL.value.replaceAll("-","");
		form.SLOT_ENDT_SL.value = form.SLOT_ENDT_SL.value.replaceAll("-","");
		// form.SLOT_ENDT_SL.value = newDate.toISOString().substring(0,10).replaceAll("-","");

		const hiddenInput = document.createElement('input');
		hiddenInput.type = 'hidden';
		hiddenInput.name = 'USER_TYPE_SL';
		hiddenInput.value = '<%=USER_TYPE%>';
		form.appendChild(hiddenInput);

		form.setAttribute("charset", "UTF-8");
		form.setAttribute("method", "Post");  //Post 방식
		form.setAttribute("action", "/setSlot/"+form.USER_IDX_SL.value); //요청 보낼 주소
		form.submit();

	}

	/* 슬롯 추가 후 원래 페이지로 돌아감 */
	function setSlot(){
		var form = document.slotForm;

		if (!form.SLOT_EA_SL.value) {
			alert("추가개수를 입력하세요.");
			form.SLOT_EA_SL.focus();
			return;
		}

		if (!form.SLOT_DAYS_SL.value) {
			alert("작업일수를 입력하세요.");
			form.SLOT_DAYS_SL.focus();
			return;
		}

		if (!form.SLOT_STDT_SL.value) {
			alert("시작일 선택하세요.");
			form.SLOT_STDT_SL.focus();
			return;
		}

		$("#loader").show();

		let json = new Object();

		json.USER_IDX_SL = form.USER_IDX_SL.value;
		json.PAGE_SL = form.PAGE_SL.value;
		json.PARAM_SL = form.PARAM_SL.value;
		json.SLOT_EA_SL = form.SLOT_EA_SL.value;
		json.SLOT_TYPE_SL = form.SLOT_TYPE_SL.value;
		json.SLOT_DAYS_SL = form.SLOT_DAYS_SL.value;
		json.SLOT_STDT_SL = form.SLOT_STDT_SL.value.replaceAll("-","");
		json.SLOT_ENDT_SL = form.SLOT_ENDT_SL.value.replaceAll("-","");
		json.USER_TYPE_SL = '<%=USER_TYPE%>';

		// jsonArray.push(json);

		let formData = new FormData();
		formData.append("PARAM", JSON.stringify(json));

		$.ajax({
			url: "/setSlot",
			type: "POST",
			datatype: "json",
			processData: false,
			data: formData,
			contentType : false,
			enctype: 'application/x-www-form-urlencoded',

			error: function(xhr, status, error) {
				$("#loader").hide();
				alert("error:" + error);
			},
			success: function(response) {
				const json = JSON.parse(response)
				alert(json.alert);

				location.href = "/naver-shopping-users"+json.url;
			}
		});
	}

	function setEndDate(){
		var form = document.slotForm;

		const newDate = new Date(form.SLOT_STDT_SL.value)
		newDate.setDate(newDate.getDate() + Number(form.SLOT_DAYS_SL.value) - 1);

		form.SLOT_ENDT_SL.value = newDate.toISOString().substring(0,10);
	}
</script>

</html>