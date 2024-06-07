<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.slot.Common.Protocol" %>
<%@ page import="java.util.List" %>
<%@ page import="com.slot.Common.DBConnector" %>
<%@ page import="com.slot.Common.ContextUtil" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.slot.Model.*" %>
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

	final String TAG = "SLOT_TYPE_MANAGE_PAGE";
	request.setCharacterEncoding("utf-8");
	Member member = (Member) session.getAttribute(Protocol.Json.KEY_MEMBER);

	List<NaverShoppingSlotType> naverShoppingSlotTypeList = DBConnector.getNaverShoppingSlotType(0,"Y");
	List<NaverPlaceSlotType> naverPlaceSlotTypeList = DBConnector.getNaverPlaceSlotType(0,"Y");
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
			<div class="row">
				<%--1--%>
				<div class="d-flex align-items-stretch col-lg-6">
					<div class="card w-100">
						<div class="card-body p-4">
							<div class="row">
								<div class="col-lg-4">
									<h5 class="card-title fw-semibold mb-4 col-lg-12">네이버 쇼핑<%=naverShoppingSlotTypeList.size()>0?"("+naverShoppingSlotTypeList.size()+")":""%></h5>
								</div>
								<div class="col-lg-8 text-end">
									<button type="button" class="btn btn-outline-primary ms-2 mb-1" data-bs-toggle="modal" data-bs-target="#naverShoppingInsertModal" data-bs-inidx="<%=member.getUSER_IDX()%>">
										추가
									</button>
								</div>
							</div>

							<%if(naverShoppingSlotTypeList.size()<1){%>
							<div class="d-flex align-items-center py-2 justify-content-center" style="min-height: 150px;">
								<h6 class="fw-semibold mb-0 ">등록된 슬롯타입이 없습니다.</h6>
							</div>
							<%}else{%>
							<div class="table-responsive">
								<table class="table text-nowrap mb-0 align-middle">
									<colgroup>
										<col width="20%">
										<col width="40%">
										<col width="20%">
										<col width="20%">
									</colgroup>
									<thead class="text-dark fs-4 text-center bg-light-gray border-top">
									<tr>
										<%
											//////////////////////////////////////
											//////		Title Setting		//////
											//////////////////////////////////////
											String[] Title;

											Title = new String[]{"슬롯타입코드", "슬롯타입", "시작시간", "종료시간", "수정", "삭제"};


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
									<% 	for(int i=0;i<naverShoppingSlotTypeList.size();i++){%>
									<tr>
										<form id="naverShoppingForm<%=naverShoppingSlotTypeList.get(i).getNS_SLOT_TYPE_IDX()%>" name="naverShoppingForm">
											<td class=""><%--슬롯타입코드--%>
												<input type="text" class="form-control-plaintext text-center" value="<%=naverShoppingSlotTypeList.get(i).getNS_SLOT_TYPE_IDX()%>" readonly>
											</td>
											<td class=""><%--슬롯타입--%>
												<input type="text" class="form-control-plaintext text-center" value="<%=naverShoppingSlotTypeList.get(i).getTYPE_NAME()%>" readonly>
											</td>
											<td class=""><%--시작시간--%>
												<input type="text" class="form-control-plaintext text-center" value="<%=naverShoppingSlotTypeList.get(i).getSLOT_STTM().substring(0,2)+":"+naverShoppingSlotTypeList.get(i).getSLOT_STTM().substring(2)%>" readonly>
											</td>
											<td class=""><%--종료시간--%>
												<input type="text" class="form-control-plaintext text-center" value="<%=naverShoppingSlotTypeList.get(i).getSLOT_ENTM().substring(0,2)+":"+naverShoppingSlotTypeList.get(i).getSLOT_ENTM().substring(2)%>" readonly>
											</td>
											<td class=""><%--종료일--%> <%--종료일 3일전부터 빨개짐--%>
												<a class="text-body cursor-pointer" data-bs-toggle="modal" data-bs-target="#naverShoppingUpdateModal" data-bs-idx="<%=naverShoppingSlotTypeList.get(i).getNS_SLOT_TYPE_IDX()%>" data-bs-name="<%=naverShoppingSlotTypeList.get(i).getTYPE_NAME()%>" data-bs-sttm="<%=naverShoppingSlotTypeList.get(i).getSLOT_STTM()%>" data-bs-entm="<%=naverShoppingSlotTypeList.get(i).getSLOT_ENTM()%>">
											<span>
												<i class="ti ti-edit fs-6"></i>
											</span>
												</a>
											</td>
											<td class=""><%--관리--%>
												<a class="text-body cursor-pointer" href="javascript:setNaverShoppingDelete('<%=naverShoppingSlotTypeList.get(i).getNS_SLOT_TYPE_IDX()%>')">
											<span>
												<i class="ti ti-trash fs-6"></i>
											</span>
												</a>
												<%--<button type="button" class="btn btn-primary mb-1" onclick="deleteSlotType('<%=naverShoppingSlotTypeList.get(i).getNS_SLOT_TYPE_IDX()%>')">
                                                    삭제
                                                </button>--%>
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
				<%--1End--%>

				<%--2--%>
				<div class="d-flex align-items-stretch col-lg-6">
					<div class="card w-100">
						<div class="card-body p-4">
							<div class="row">
								<div class="col-lg-4">
									<h5 class="card-title fw-semibold mb-4 col-lg-12">네이버 플레이스<%=naverPlaceSlotTypeList.size()>0?"("+naverPlaceSlotTypeList.size()+")":""%></h5>
								</div>
								<div class="col-lg-8 text-end">
									<button type="button" class="btn btn-outline-primary ms-2 mb-1" data-bs-toggle="modal" data-bs-target="#naverPlaceInsertModal" data-bs-inidx="<%=member.getUSER_IDX()%>">
										추가
									</button>
								</div>
							</div>

							<%if(naverPlaceSlotTypeList.size()<1){%>
							<div class="d-flex align-items-center py-2 justify-content-center" style="min-height: 150px;">
								<h6 class="fw-semibold mb-0 ">등록된 슬롯타입이 없습니다.</h6>
							</div>
							<%}else{%>
							<div class="table-responsive">
								<table class="table text-nowrap mb-0 align-middle">
									<colgroup>
										<col width="20%">
										<col width="40%">
										<col width="20%">
										<col width="20%">
									</colgroup>
									<thead class="text-dark fs-4 text-center bg-light-gray border-top">
									<tr>
										<%
											//////////////////////////////////////
											//////		Title Setting		//////
											//////////////////////////////////////
											String[] Title;

											Title = new String[]{"슬롯타입코드", "슬롯타입", "시작시간", "종료시간", "수정", "삭제"};


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
									<% 	for(int i=0;i<naverPlaceSlotTypeList.size();i++){%>
									<tr>
										<form id="naverPlaceForm<%=naverPlaceSlotTypeList.get(i).getNP_SLOT_TYPE_IDX()%>" name="naverPlaceForm">
											<td class=""><%--슬롯타입코드--%>
												<input type="text" class="form-control-plaintext text-center" value="<%=naverPlaceSlotTypeList.get(i).getNP_SLOT_TYPE_IDX()%>" readonly>
											</td>
											<td class=""><%--슬롯타입--%>
												<input type="text" class="form-control-plaintext text-center" value="<%=naverPlaceSlotTypeList.get(i).getTYPE_NAME()%>" readonly>
											</td>
											<td class=""><%--시작시간--%>
												<input type="text" class="form-control-plaintext text-center" value="<%=naverPlaceSlotTypeList.get(i).getSLOT_STTM().substring(0,2)+":"+naverPlaceSlotTypeList.get(i).getSLOT_STTM().substring(2)%>" readonly>
											</td>
											<td class=""><%--종료시간--%>
												<input type="text" class="form-control-plaintext text-center" value="<%=naverPlaceSlotTypeList.get(i).getSLOT_ENTM().substring(0,2)+":"+naverPlaceSlotTypeList.get(i).getSLOT_ENTM().substring(2)%>" readonly>
											</td>
											<td class=""><%--종료일--%> <%--종료일 3일전부터 빨개짐--%>
												<a class="text-body cursor-pointer" data-bs-toggle="modal" data-bs-target="#naverPlaceUpdateModal" data-bs-idx="<%=naverPlaceSlotTypeList.get(i).getNP_SLOT_TYPE_IDX()%>" data-bs-name="<%=naverPlaceSlotTypeList.get(i).getTYPE_NAME()%>" data-bs-sttm="<%=naverPlaceSlotTypeList.get(i).getSLOT_STTM()%>" data-bs-entm="<%=naverPlaceSlotTypeList.get(i).getSLOT_ENTM()%>">
										<span>
											<i class="ti ti-edit fs-6"></i>
										</span>
												</a>
											</td>
											<td class=""><%--관리--%>
												<a class="text-body cursor-pointer" href="javascript:setNaverPlaceDelete('<%=naverPlaceSlotTypeList.get(i).getNP_SLOT_TYPE_IDX()%>')">
										<span>
											<i class="ti ti-trash fs-6"></i>
										</span>
												</a>
												<%--<button type="button" class="btn btn-primary mb-1" onclick="deleteSlotType('<%=naverPlaceSlotTypeList.get(i).getNP_SLOT_TYPE_IDX()%>')">
													삭제
												</button>--%>
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
			</div>
		</div>
	</div>
</div>

<%-- 네이버 쇼핑 추가 모달 --%>
<div class="modal fade" id="naverShoppingInsertModal" tabindex="-1" aria-labelledby="naverShoppingInsertModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="naverShoppingInsertModalLabel"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="naverShoppingInsertForm" name="naverShoppingInsertForm">
					<input type="hidden" class="NS_INST_ADMN_IN" id="NS_INST_ADMN_IN" name="NS_INST_ADMN_IN">
					<div class="mb-3">
						<label for="NS_TYPE_NAME_IN" class="col-form-label">네이버 쇼핑 슬롯타입</label>
						<input type="text" class="form-control NS_TYPE_NAME_IN" id="NS_TYPE_NAME_IN" name="NS_TYPE_NAME_IN">
					</div>
					<div class="mb-3">
						<label for="NS_SLOT_STTM_H_IN" class="col-form-label">시작시간</label>
						<div class="row">
							<div class="col-lg-3">
								<select class="form-select NS_SLOT_STTM_H_IN" id="NS_SLOT_STTM_H_IN" name="NS_SLOT_STTM_H_IN">
									<%for(int i=0; i<24; i++){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
							<div class="col-lg-1">
								<input input type="text" class="form-control-plaintext text-center" value=":" readonly>
							</div>
							<div class="col-lg-3">
								<select class="form-select NS_SLOT_STTM_M_IN" id="NS_SLOT_STTM_M_IN" name="NS_SLOT_STTM_M_IN">
									<%for(int i=0; i<60; i+=30){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
						</div>
					</div>
					<div class="mb-3">
						<label for="NS_SLOT_ENTM_H_IN" class="col-form-label">종료시간</label>
						<div class="row">
							<div class="col-lg-3">
								<select class="form-select NS_SLOT_ENTM_H_IN" id="NS_SLOT_ENTM_H_IN" name="NS_SLOT_ENTM_H_IN">
									<%for(int i=0; i<24; i++){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
							<div class="col-lg-1">
								<input input type="text" class="form-control-plaintext text-center" value=":" readonly>
							</div>
							<div class="col-lg-3">
								<select class="form-select NS_SLOT_ENTM_M_IN" id="NS_SLOT_ENTM_M_IN" name="NS_SLOT_ENTM_M_IN">
									<%for(int i=0; i<60; i+=30){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" onclick="setNaverShoppingInsert()">저장하기</button>
			</div>
		</div>
	</div>
</div>
<%-- 네이버 쇼핑 추가 모달 끝 --%>
<%-- 네이버 쇼핑 업데이트 모달 --%>
<div class="modal fade" id="naverShoppingUpdateModal" tabindex="-1" aria-labelledby="naverShoppingUpdateModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="naverShoppingUpdateModalLabel"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="naverShoppingUpdateForm" name="naverShoppingUpdateForm">
					<input type="hidden" class="NS_SLOT_TYPE_IDX_UP" id="NS_SLOT_TYPE_IDX_UP" name="NS_SLOT_TYPE_IDX_UP">
					<div class="mb-3">
						<label for="NS_TYPE_NAME_UP" class="col-form-label">네이버 쇼핑 슬롯타입</label>
						<input type="text" class="form-control NS_TYPE_NAME_UP" id="NS_TYPE_NAME_UP" name="NS_TYPE_NAME_UP">
					</div>
					<div class="mb-3">
						<label for="NS_SLOT_STTM_H_UP" class="col-form-label">시작시간</label>
						<div class="row">
							<div class="col-lg-3">
								<select class="form-select NS_SLOT_STTM_H_UP" id="NS_SLOT_STTM_H_UP" name="NS_SLOT_STTM_H_UP">
									<%for(int i=0; i<24; i++){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
							<div class="col-lg-1">
								<input input type="text" class="form-control-plaintext text-center" value=":" readonly>
							</div>
							<div class="col-lg-3">
								<select class="form-select NS_SLOT_STTM_M_UP" id="NS_SLOT_STTM_M_UP" name="NS_SLOT_STTM_M_UP">
									<%for(int i=0; i<60; i+=30){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
						</div>
					</div>
					<div class="mb-3">
						<label for="NS_SLOT_ENTM_H_UP" class="col-form-label">종료시간</label>
						<div class="row">
							<div class="col-lg-3">
								<select class="form-select NS_SLOT_ENTM_H_UP" id="NS_SLOT_ENTM_H_UP" name="NS_SLOT_ENTM_H_UP">
									<%for(int i=0; i<24; i++){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
							<div class="col-lg-1">
								<input input type="text" class="form-control-plaintext text-center" value=":" readonly>
							</div>
							<div class="col-lg-3">
								<select class="form-select NS_SLOT_ENTM_M_UP" id="NS_SLOT_ENTM_M_UP" name="NS_SLOT_ENTM_M_UP">
									<%for(int i=0; i<60; i+=30){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" onclick="setNaverShoppingUpdate()">수정하기</button>
			</div>
		</div>
	</div>
</div>
<%-- 네이버 쇼핑 업데이트 모달 끝 --%>

<%-- 네이버 플레이스 추가 모달 --%>
<div class="modal fade" id="naverPlaceInsertModal" tabindex="-1" aria-labelledby="naverPlaceInsertModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="naverPlaceInsertModalLabel"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="naverPlaceInsertForm" name="naverPlaceInsertForm">
					<input type="hidden" class="NP_INST_ADMN_IN" id="NP_INST_ADMN_IN" name="NP_INST_ADMN_IN">
					<div class="mb-3">
						<label for="NP_TYPE_NAME_IN" class="col-form-label">네이버 플레이스 슬롯타입</label>
						<input type="text" class="form-control NP_TYPE_NAME_IN" id="NP_TYPE_NAME_IN" name="NP_TYPE_NAME_IN">
					</div>
					<div class="mb-3">
						<label for="NP_SLOT_STTM_H_IN" class="col-form-label">시작시간</label>
						<div class="row">
							<div class="col-lg-3">
								<select class="form-select NP_SLOT_STTM_H_IN" id="NP_SLOT_STTM_H_IN" name="NP_SLOT_STTM_H_IN">
									<%for(int i=0; i<24; i++){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
							<div class="col-lg-1">
								<input input type="text" class="form-control-plaintext text-center" value=":" readonly>
							</div>
							<div class="col-lg-3">
								<select class="form-select NP_SLOT_STTM_M_IN" id="NP_SLOT_STTM_M_IN" name="NP_SLOT_STTM_M_IN">
									<%for(int i=0; i<60; i+=30){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
						</div>
					</div>
					<div class="mb-3">
						<label for="NP_SLOT_ENTM_H_IN" class="col-form-label">종료시간</label>
						<div class="row">
							<div class="col-lg-3">
								<select class="form-select NP_SLOT_ENTM_H_IN" id="NP_SLOT_ENTM_H_IN" name="NP_SLOT_ENTM_H_IN">
									<%for(int i=0; i<24; i++){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
							<div class="col-lg-1">
								<input input type="text" class="form-control-plaintext text-center" value=":" readonly>
							</div>
							<div class="col-lg-3">
								<select class="form-select NP_SLOT_ENTM_M_IN" id="NP_SLOT_ENTM_M_IN" name="NP_SLOT_ENTM_M_IN">
									<%for(int i=0; i<60; i+=30){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" onclick="setNaverPlaceInsert()">저장하기</button>
			</div>
		</div>
	</div>
</div>
<%-- 네이버 플레이스 추가 모달 끝 --%>
<%-- 네이버 플레이스 업데이트 모달 --%>
<div class="modal fade" id="naverPlaceUpdateModal" tabindex="-1" aria-labelledby="naverPlaceUpdateModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="naverPlaceUpdateModalLabel"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="naverPlaceUpdateForm" name="naverPlaceUpdateForm">
					<input type="hidden" class="NP_SLOT_TYPE_IDX_UP" id="NP_SLOT_TYPE_IDX_UP" name="NP_SLOT_TYPE_IDX_UP">
					<div class="mb-3">
						<label for="NP_TYPE_NAME_UP" class="col-form-label">네이버 플레이스 슬롯타입</label>
						<input type="text" class="form-control NP_TYPE_NAME_UP" id="NP_TYPE_NAME_UP" name="NP_TYPE_NAME_UP">
					</div>
					<div class="mb-3">
						<label for="NP_SLOT_STTM_H_UP" class="col-form-label">시작시간</label>
						<div class="row">
							<div class="col-lg-3">
								<select class="form-select NP_SLOT_STTM_H_UP" id="NP_SLOT_STTM_H_UP" name="NP_SLOT_STTM_H_UP">
									<%for(int i=0; i<24; i++){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
							<div class="col-lg-1">
								<input input type="text" class="form-control-plaintext text-center" value=":" readonly>
							</div>
							<div class="col-lg-3">
								<select class="form-select NP_SLOT_STTM_M_UP" id="NP_SLOT_STTM_M_UP" name="NP_SLOT_STTM_M_UP">
									<%for(int i=0; i<60; i+=30){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
						</div>
					</div>
					<div class="mb-3">
						<label for="NP_SLOT_ENTM_H_UP" class="col-form-label">종료시간</label>
						<div class="row">
							<div class="col-lg-3">
								<select class="form-select NP_SLOT_ENTM_H_UP" id="NP_SLOT_ENTM_H_UP" name="NP_SLOT_ENTM_H_UP">
									<%for(int i=0; i<24; i++){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
							<div class="col-lg-1">
								<input input type="text" class="form-control-plaintext text-center" value=":" readonly>
							</div>
							<div class="col-lg-3">
								<select class="form-select NP_SLOT_ENTM_M_UP" id="NP_SLOT_ENTM_M_UP" name="NP_SLOT_ENTM_M_UP">
									<%for(int i=0; i<60; i+=30){%>
									<option value="<%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%>"><%=Integer.toString(i).length()==2?Integer.toString(i):"0"+Integer.toString(i)%></option>
									<%}%>
								</select>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer justify-content-center">
				<button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" onclick="setNaverPlaceUpdate()">수정하기</button>
			</div>
		</div>
	</div>
</div>
<%-- 네이버 플레이스 업데이트 모달 끝 --%>
<jsp:include page="common/script.jsp" />
<script>
	$(function() {
		$('[data-toggle="tooltip"]').tooltip();
	})

	/* 네이버 쇼핑 추가 모달 */
	var naverShoppingInsertModal = document.getElementById('naverShoppingInsertModal');
	naverShoppingInsertModal.addEventListener('show.bs.modal', function(event) {
		// Button that triggered the modal
		var button = event.relatedTarget;
		// Extract info from data-bs-* attributes
		var USER_IDX = button.getAttribute('data-bs-inidx');

		$("#NS_INST_ADMN_IN").val(USER_IDX);

	});
	/* 네이버 쇼핑 추가 모달 끝 */

	/* 네이버 쇼핑 수정 모달 */
	var naverShoppingUpdateModal = document.getElementById('naverShoppingUpdateModal');
	naverShoppingUpdateModal.addEventListener('show.bs.modal', function(event) {
		// Button that triggered the modal
		var button = event.relatedTarget;
		// Extract info from data-bs-* attributes
		var NS_SLOT_TYPE_IDX = button.getAttribute('data-bs-idx');
		var NS_TYPE_NAME = button.getAttribute('data-bs-name');
		var NS_SLOT_STTM = button.getAttribute('data-bs-sttm');
		var NS_SLOT_ENTM = button.getAttribute('data-bs-entm');

		// var modalTitle = naverShoppingUpdateModal.querySelector('.modal-title');
		var NS_SLOT_TYPE_IDXInput = naverShoppingUpdateModal.querySelector('.modal-body .NS_SLOT_TYPE_IDX_UP');
		var NS_TYPE_NAMEInput = naverShoppingUpdateModal.querySelector('.modal-body .NS_TYPE_NAME_UP');
		var USER_PWDInput = naverShoppingUpdateModal.querySelector('.modal-body .USER_PWD_UP');

		var NS_SLOT_STTM_H_UPInput = naverShoppingUpdateModal.querySelector('.modal-body .NS_SLOT_STTM_H_UP');
		var NS_SLOT_STTM_M_UPInput = naverShoppingUpdateModal.querySelector('.modal-body .NS_SLOT_STTM_M_UP');
		var NS_SLOT_ENTM_H_UPInput = naverShoppingUpdateModal.querySelector('.modal-body .NS_SLOT_ENTM_H_UP');
		var NS_SLOT_ENTM_M_UPInput = naverShoppingUpdateModal.querySelector('.modal-body .NS_SLOT_ENTM_M_UP');

		// modalTitle.textContent = NS_TYPE_NAME;
		NS_SLOT_TYPE_IDXInput.value = NS_SLOT_TYPE_IDX;
		NS_TYPE_NAMEInput.value = NS_TYPE_NAME;
		NS_SLOT_STTM_H_UPInput.value = NS_SLOT_STTM.substring(0,2);
		NS_SLOT_STTM_M_UPInput.value = NS_SLOT_STTM.substring(2,4);
		NS_SLOT_ENTM_H_UPInput.value = NS_SLOT_ENTM.substring(0,2);
		NS_SLOT_ENTM_M_UPInput.value = NS_SLOT_ENTM.substring(2,4);

	});
	/* 네이버 쇼핑 수정 모달 끝 */

	/* 네이버 플레이스 추가 모달 */
	var naverPlaceInsertModal = document.getElementById('naverPlaceInsertModal');
	naverPlaceInsertModal.addEventListener('show.bs.modal', function(event) {
		// Button that triggered the modal
		var button = event.relatedTarget;
		// Extract info from data-bs-* attributes
		var USER_IDX = button.getAttribute('data-bs-inidx');

		$("#NP_INST_ADMN_IN").val(USER_IDX);

	});
	/* 네이버 플레이스 추가 모달 끝 */

	/* 네이버 플레이스 수정 모달 */
	var naverPlaceUpdateModal = document.getElementById('naverPlaceUpdateModal');
	naverPlaceUpdateModal.addEventListener('show.bs.modal', function(event) {
		// Button that triggered the modal
		var button = event.relatedTarget;
		// Extract info from data-bs-* attributes
		var NP_SLOT_TYPE_IDX = button.getAttribute('data-bs-idx');
		var NP_TYPE_NAME = button.getAttribute('data-bs-name');
		var NP_SLOT_STTM = button.getAttribute('data-bs-sttm');
		var NP_SLOT_ENTM = button.getAttribute('data-bs-entm');

		// var modalTitle = naverPlaceUpdateModal.querySelector('.modal-title');
		var NP_SLOT_TYPE_IDXInput = naverPlaceUpdateModal.querySelector('.modal-body .NP_SLOT_TYPE_IDX_UP');
		var NP_TYPE_NAMEInput = naverPlaceUpdateModal.querySelector('.modal-body .NP_TYPE_NAME_UP');
		var USER_PWDInput = naverPlaceUpdateModal.querySelector('.modal-body .USER_PWD_UP');

		var NP_SLOT_STTM_H_UPInput = naverPlaceUpdateModal.querySelector('.modal-body .NP_SLOT_STTM_H_UP');
		var NP_SLOT_STTM_M_UPInput = naverPlaceUpdateModal.querySelector('.modal-body .NP_SLOT_STTM_M_UP');
		var NP_SLOT_ENTM_H_UPInput = naverPlaceUpdateModal.querySelector('.modal-body .NP_SLOT_ENTM_H_UP');
		var NP_SLOT_ENTM_M_UPInput = naverPlaceUpdateModal.querySelector('.modal-body .NP_SLOT_ENTM_M_UP');

		// modalTitle.textContent = NP_TYPE_NAME;
		NP_SLOT_TYPE_IDXInput.value = NP_SLOT_TYPE_IDX;
		NP_TYPE_NAMEInput.value = NP_TYPE_NAME;
		NP_SLOT_STTM_H_UPInput.value = NP_SLOT_STTM.substring(0,2);
		NP_SLOT_STTM_M_UPInput.value = NP_SLOT_STTM.substring(2,4);
		NP_SLOT_ENTM_H_UPInput.value = NP_SLOT_ENTM.substring(0,2);
		NP_SLOT_ENTM_M_UPInput.value = NP_SLOT_ENTM.substring(2,4);

	});
	/* 네이버 플레이스 수정 모달 끝 */
</script>
</body>
<script>
	function setNaverShoppingInsert() {
		var form = document.naverShoppingInsertForm;

		if (!form.NS_TYPE_NAME_IN.value) {
			alert("쇼핑타입을 입력하세요.");
			form.NS_TYPE_NAME_IN.focus();
			return;
		}

		const hiddenInput = document.createElement('input');
		hiddenInput.type = 'hidden';
		hiddenInput.name = 'USER_TYPE_IN';
		hiddenInput.value = 'NS_';
		form.appendChild(hiddenInput);

		form.setAttribute("charset", "UTF-8");
		form.setAttribute("method", "Post");  //Post 방식
		form.setAttribute("action", "/setSlotType"); //요청 보낼 주소

		form.submit();
	}

	function setNaverShoppingUpdate() {
		var form = document.naverShoppingUpdateForm;

		if (!form.NS_TYPE_NAME_UP.value) {
			alert("쇼핑타입을 입력하세요.");
			form.NS_TYPE_NAME_UP.focus();
			return;
		}

		const hiddenInput = document.createElement('input');
		hiddenInput.type = 'hidden';
		hiddenInput.name = 'USER_TYPE_UP';
		hiddenInput.value = 'NS_';
		form.appendChild(hiddenInput);

		form.setAttribute("charset", "UTF-8");
		form.setAttribute("method", "Post");  //Post 방식
		form.setAttribute("action", "/setSlotType/"+form.NS_SLOT_TYPE_IDX_UP.value); //요청 보낼 주소

		form.submit();
	}

	function setNaverShoppingDelete(SLOT_TYPE_IDX) {
		if(!confirm("삭제 하시겠습니까?")){
			return;
		}

		let json = new Object();

		json.SLOT_TYPE_IDX = SLOT_TYPE_IDX;
		json.USER_TYPE = 'NS_';

		let formData = new FormData();
		formData.append("PARAM", JSON.stringify(json));

		$.ajax({
			url: '/deleteSlotType',
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

				location.href = "/slot-type-manage";
			}
		});
	}

	function setNaverPlaceInsert() {
		var form = document.naverPlaceInsertForm;

		if (!form.NP_TYPE_NAME_IN.value) {
			alert("쇼핑타입을 입력하세요.");
			form.NP_TYPE_NAME_IN.focus();
			return;
		}

		const hiddenInput = document.createElement('input');
		hiddenInput.type = 'hidden';
		hiddenInput.name = 'USER_TYPE_IN';
		hiddenInput.value = 'NP_';
		form.appendChild(hiddenInput);

		form.setAttribute("charset", "UTF-8");
		form.setAttribute("method", "Post");  //Post 방식
		form.setAttribute("action", "/setSlotType"); //요청 보낼 주소

		form.submit();
	}

	function setNaverPlaceUpdate() {
		var form = document.naverPlaceUpdateForm;

		if (!form.NP_TYPE_NAME_UP.value) {
			alert("쇼핑타입을 입력하세요.");
			form.NP_TYPE_NAME_UP.focus();
			return;
		}

		const hiddenInput = document.createElement('input');
		hiddenInput.type = 'hidden';
		hiddenInput.name = 'USER_TYPE_UP';
		hiddenInput.value = 'NP_';
		form.appendChild(hiddenInput);

		form.setAttribute("charset", "UTF-8");
		form.setAttribute("method", "Post");  //Post 방식
		form.setAttribute("action", "/setSlotType/"+form.NP_SLOT_TYPE_IDX_UP.value); //요청 보낼 주소

		form.submit();
	}

	function setNaverPlaceDelete(SLOT_TYPE_IDX) {
		if(!confirm("삭제 하시겠습니까?")){
			return;
		}

		let json = new Object();

		json.SLOT_TYPE_IDX = SLOT_TYPE_IDX;
		json.USER_TYPE = 'NP_';

		let formData = new FormData();
		formData.append("PARAM", JSON.stringify(json));

		$.ajax({
			url: '/deleteSlotType',
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

				location.href = "/slot-type-manage";
			}
		});
	}
</script>
<script defer src="/js/Front/xlsx.full.min.js"></script>
</html>