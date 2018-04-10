<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>

<fmt:bundle basename="i18n">
	<fmt:message key="requests.search_error" var="search_error_mess" />
	<fmt:message key="prescriptions.name" var="prescription_name" />
	<fmt:message key="prescriptions.creation_date" var="creation_date" />
	<fmt:message key="requests.doctor_data" var="doctor" />
	<fmt:message key="prescriptions.expiration_date" var="expiration_date" />
	<fmt:message key="requests.status" var="status_info" />
	<fmt:message key="requests.status_pending" var="status_pending" />
	<fmt:message key="requests.status_approved" var="status_approved" />
	<fmt:message key="requests.status_rejected" var="status_rejected" />
</fmt:bundle>

<my:designPattern role="${sessionScope.role}">
	<c:if test="${not empty search_error}">
		<p>${search_error_mess}</p>
	</c:if>
	<c:if test="${sessionScope.role == 'CLIENT'}">
		<c:forEach items="${requests}" var="request">
			<div class="drugsForm  by">
				<div class="drugsForm__header" style="background-color: #92aeb7">
					<div class="drugsForm__description">
						<h3 class="drugsForm__description-title">${request.requestId}</h3>
					</div>
					<div class="drugsForm__header-buttons">
						<i class="drugsForm__header-info" data-ui-drug-hint="hover"></i>
					</div>
				</div>
				<ul class="drugsForm__items">
					<li class="drugsForm__item sku-holder">
						<div class="drugsForm__item-left">
							<div class="drugsForm__item-left">
								<div class="drugsForm__item-description">
									<div>
										<span>${prescription_name} #: ${request.prescriptionId}</span>
									</div>
									<div>
										<span>${doctor}: ${request.userName}
											${request.userLastname}</span>
									</div>
									<div>
										<span>${creation_date}: ${request.creationDate}</span>
									</div>
									<div>
										<span>${expiration_date}: ${request.expirationDate}</span>
									</div>
								</div>
							</div>
							<span>${status_info}: </span>
							<c:if test="${request.status == 'PENDING'}">
								<div style="color: blue;">${status_pending}</div>
							</c:if>
							<c:if test="${request.status == 'APPROVED'}">
								<div style="color: green;">${status_approved}</div>
							</c:if>
							<c:if test="${request.status == 'REJECTED'}">
								<div style="color: red;">${status_rejected}</div>
							</c:if>
						</div>
					</li>
				</ul>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${sessionScope.role == 'DOCTOR'}">
		<c:forEach items="${requests}" var="request">
			<div class="drugsForm  by">
				<div class="drugsForm__header" style="background-color: #92aeb7">
					<div class="drugsForm__description">
						<h3 class="drugsForm__description-title">${request.requestId}</h3>
					</div>
					<div class="drugsForm__header-buttons">
						<i class="drugsForm__header-info" data-ui-drug-hint="hover"></i>
					</div>
				</div>
				<ul class="drugsForm__items">
					<li class="drugsForm__item sku-holder">
						<div class="drugsForm__item-left">
							<div class="drugsForm__item-left">
								<div class="drugsForm__item-description">
									<div>
										<span>${prescription_name} #: ${request.prescriptionId}</span>
									</div>
									<div>
										<span>CLIENT: ${request.userName}
											${request.userLastname}</span>
									</div>
									<div>
										<span>${creation_date}: ${request.creationDate}</span>
									</div>
									<div>
										<span>${expiration_date}: ${request.expirationDate}</span>
									</div>
								</div>
							</div>
							<span>${status_info}: </span>
							<c:choose>
								<c:when test="${request.status == 'PENDING'}">
									<div style="color: blue;">${status_pending}</div>
									<div class="drugsForm__item-buttons">
										<form action="extend-prescription" method="POST"
											name="extend-prescription">
											<input name="prescription_id" type="text"
												value="${request.prescriptionId}" hidden="hidden" />
											<input name="expiration_date" type="text"
												value="${request.expirationDate}" hidden="hidden" />
											<input name="request_id" type="text"
												value="${request.requestId}" hidden="hidden" />
												 <input
												type="submit" value="Extend prescription"
												class="drugsForm__paidButton" />
										</form>
									</div>
									<div class="drugsForm__item-buttons">
										<form action="reject-request" method="POST"
											name="reject-request">
											<input name="request_id" type="text"
												value="${request.requestId}" hidden="hidden" /> <input
												type="submit" value="Reject request"
												class="drugsForm__button" />
										</form>
									</div>
								</c:when>
								<c:when test="${request.status == 'REJECTED'}">
									<div style="color: red;">${status_rejected}</div>
								</c:when>
								<c:when test="${request.status == 'APPROVED'}">
									<div style="color: green;">${status_approved}</div>
								</c:when>
							</c:choose>
						</div>
					</li>
				</ul>
			</div>
		</c:forEach>
	</c:if>
</my:designPattern>