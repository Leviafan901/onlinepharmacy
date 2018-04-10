<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
	<fmt:message key="navbar.hello" var="mainPage"/>
	<fmt:message key="medicines.search_error" var="search_error_mess"/>
	<fmt:message key="medicines.dosage" var="dosage"/>
	<fmt:message key="medicines.count" var="medicine_count"/>
	<fmt:message key="medicines.count_in_store" var="count_in_store"/>
	<fmt:message key="medicines.price" var="price"/>
	<fmt:message key="medicines.make_order" var="order"/>
	<fmt:message key="medicines.form_placeholder" var="form_placeholder"/>
	<fmt:message key="medicines.need_prescription" var="need_prescription"/>
	<fmt:message key="medicines.has_prescription" var="has_prescription"/>
	<fmt:message key="medicines.no_prescription" var="no_prescription"/>
</fmt:bundle>

<my:designPattern role="${sessionScope.role}">

	<c:if test="${not empty search_error}">
	    <p>${search_error_mess}</p>
	</c:if>
	
	<c:if test="${sessionScope.role == 'CLIENT'}">
		<c:forEach items="${medicines}" var="medicine">
			<div class="drugsForm  by">
			    <div class="drugsForm__header" style="background-color: #92aeb7">
			        <div class="drugsForm__description">
			            <h3 class="drugsForm__description-title">${medicine.name}</h3>
			        </div>        
			        <div class="drugsForm__header-buttons">
			            <i class="drugsForm__header-info" data-ui-drug-hint="hover"></i>
			        </div>
			    </div>
				    <ul class="drugsForm__items">
				    	<li class="drugsForm__item sku-holder">
				    		<div class="drugsForm__item-left">
				        		<div class="drugsForm__item-description">
				        		  <div><span>${dosage}: ${medicine.dosageMg}</span></div>
				            	  <div><span>${medicine_count}: ${medicine.count}</span></div>
				            	  <div><span>${count_in_store}: ${medicine.countInStore}</span></div>
				            	<div>${need_prescription}: 
								  <c:if test="${medicine.needPrescription}">
								    <c:out value="${has_prescription}"/>
								  </c:if>
								  <c:if test="${not medicine.needPrescription}">
								    <c:out value="${no_prescription}"/>
								  </c:if>
				            	</div>
				                </div>
				                <span>${price}: </span>
				        	<div><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${medicine.price}" /></div>
				   			</div>
				    		<div class="drugsForm__item-buttons">
				    		<form action="make-order" method="POST" name="make-order">
				    		  <input name="medicine_id" type="text"  value="${medicine.id}" hidden="hidden" />
							  <input name="order_count" type="text" id="return_value" value="" placeholder="${form_placeholder}" required />
							  <input type="submit" value="${order}" class="drugsForm__button js-drugsForm__button" />
				            </form>
				            </div>
						</li>
					</ul>
				</div>
		</c:forEach>
	</c:if>
	<c:if test="${sessionScope.role == 'ADMIN'}">

		<div class="drugsForm__item-buttons">
			<form action="creation-form" method="GET" name="creation-form">
			 <input type="submit" value="Create new medicine..."
					class="drugsForm__button js-drugsForm__button" />
			</form>
		</div>

		<c:forEach items="${medicines}" var="medicine">
			<div class="drugsForm  by">
			    <div class="drugsForm__header" style="background-color: #92aeb7">
			        <div class="drugsForm__description">
			            <h3 class="drugsForm__description-title">${medicine.name}</h3>
			        </div>        
			        <div class="drugsForm__header-buttons">
			            <i class="drugsForm__header-info" data-ui-drug-hint="hover"></i>
			        </div>
			    </div>
				    <ul class="drugsForm__items">
				    	<li class="drugsForm__item sku-holder">
				    		<div class="drugsForm__item-left">
				        		<div class="drugsForm__item-description">
				        		  <div><span>${dosage}: ${medicine.dosageMg}</span></div>
				            	  <div><span>${medicine_count}: ${medicine.count}</span></div>
				            	  <div><span>${count_in_store}: ${medicine.countInStore}</span></div>
				            	<div>${need_prescription}: 
								  <c:if test="${medicine.needPrescription}">
								    <c:out value="${has_prescription}"/>
								  </c:if>
								  <c:if test="${not medicine.needPrescription}">
								    <c:out value="${no_prescription}"/>
								  </c:if>
				            	</div>
				                </div>
				                <span>${price}: </span>
				        	<div><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${medicine.price}" /></div>
				   			</div>
				   			<div class="drugsForm__item-buttons">
				    		<form action="delete-medicine" method="POST" name="delete-medicine">
				    		  <input name="medicine_id" type="text"  value="${medicine.id}" hidden="hidden" />
							  <input type="submit" value="Delete" class="drugsForm__button js-drugsForm__button" />
				            </form>
				            </div>
				            <div class="drugsForm__item-buttons">
				    		<form action="change-form" method="GET" name="change-form">
				    		  <input name="medicine_id" type="text"  value="${medicine.id}" hidden="hidden" />
							  <input type="submit" value="Change" class="drugsForm__button js-drugsForm__button" />
				            </form>
				            </div>
						</li>
					</ul>
				</div>
		</c:forEach>
	</c:if>
</my:designPattern>