<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>

<fmt:bundle basename="i18n">
	<fmt:message key="prescriptions.search_error" var="search_error_mess" />
	<fmt:message key="prescriptions.name" var="prescription_name" />
	<fmt:message key="prescriptions.author" var="author" />
	<fmt:message key="prescriptions.medicine_name" var="medicine_name" />
	<fmt:message key="prescriptions.creation_date" var="creation_date" />
	<fmt:message key="prescriptions.expiration_date" var="expiration_date" />
	<fmt:message key="prescriptions.text" var="text" />
	<fmt:message key="prescriptions.extend" var="extend" />
</fmt:bundle>

<div class="wrapper">
<my:designPattern role="${sessionScope.role}">
	<div class="content">
		<c:if test="${not empty search_error}">
			<p>${search_error_mess}</p>
		</c:if>
		<c:if test="${sessionScope.role == 'CLIENT'}">
			<c:forEach items="${prescriptions}" var="prescription">
				<div class="drugsForm  by">
					<div class="drugsForm__header" style="background-color: #92aeb7">
						<div class="drugsForm__description">
							<h3 class="drugsForm__description-title">${prescription_name}
								# ${prescription.id}</h3>
						</div>
						<div class="drugsForm__header-buttons">
							<i class="drugsForm__header-info" data-ui-drug-hint="hover"></i>
						</div>
					</div>
					<ul class="drugsForm__items">
						<li class="drugsForm__item">
							<div class="drugsForm__item-left">
								<div class="drugsForm__item-description">
									<div>
										<span>${author}:
											${prescription.userName}&nbsp;${prescription.userLastname}</span>
									</div>
									<div>
										<span>${medicine_name}: ${prescription.medicineName}</span>
									</div>
									<div>
										<span>${creation_date}: ${prescription.creationDate}</span>
									</div>
									<div>
										<span>${expiration_date}:
											${prescription.expirationDate}</span>
									</div>
									<div>${text}:${prescription.comment}</div>
								</div>
							</div>
							<form action="request-extention-prescription" method="POST"
								name="request-extend-prescription" style="float: right;">
								<input name="doctor_id" type="text"
									value="${prescription.doctorId}" hidden="hidden" /> <input
									name="prescription_id" type="text" value="${prescription.id}"
									hidden="hidden" /> <input type="submit" value="${extend}"
									class="drugsForm__changeButton" />
							</form>
						</li>
					</ul>
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${sessionScope.role == 'DOCTOR'}">
			<c:forEach items="${prescriptions}" var="prescription">
				<div class="drugsForm  by">
					<div class="drugsForm__header" style="background-color: #92aeb7">
						<div class="drugsForm__description">
							<h3 class="drugsForm__description-title">${prescription_name}
								# ${prescription.id}</h3>
						</div>
						<div class="drugsForm__header-buttons">
							<i class="drugsForm__header-info" data-ui-drug-hint="hover"></i>
						</div>
					</div>
					<ul class="drugsForm__items">
						<li class="drugsForm__item">
							<div class="drugsForm__item-left">
								<div class="drugsForm__item-description">
									<div>
										<span>For whom:
											${prescription.userName}&nbsp;${prescription.userLastname}</span>
									</div>
									<div>
										<span>${medicine_name}: ${prescription.medicineName}</span>
									</div>
									<div>
										<span>${creation_date}: ${prescription.creationDate}</span>
									</div>
									<div>
										<span>${expiration_date}:
											${prescription.expirationDate}</span>
									</div>
									<div>${text}:${prescription.comment}</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</c:forEach>
		</c:if>
	</div>
</my:designPattern>
</div>