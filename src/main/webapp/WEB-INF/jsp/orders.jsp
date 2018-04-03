<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
  <fmt:message key="orders.search_error" var="search_error_mess"/>
  <fmt:message key="orders.order" var="name_order"/>
  <fmt:message key="orders.order_date" var="order_date"/>
  <fmt:message key="orders.ordered_medicine" var="ordered_medicine"/>
  <fmt:message key="orders.total_amount" var="total_amount"/>
  <fmt:message key="orders.count_in_order" var="count_in_order"/>
  <fmt:message key="orders.status" var="status"/>
  <fmt:message key="orders.status_in_process" var="status_in_process"/>
  <fmt:message key="orders.status_canceled" var="status_canceled"/>
  <fmt:message key="orders.status_paid" var="status_paid"/>
</fmt:bundle>

<my:designPattern role="${sessionScope.role}">
  <div class="wrapper">	
	<c:if test="${not empty search_error}">
        <p>${search_error_mess}</p>
	</c:if>
	<c:forEach items="${orders}" var="order">
		<div class="drugsForm  by">
		    <div class="drugsForm__header" style="background-color: #92aeb7">
		        <div class="drugsForm__description">
		            <h3 class="drugsForm__description-title">${name_order} # ${order.orderId}</h3>
		        </div>        
		        <div class="drugsForm__header-buttons">
		            <i class="drugsForm__header-info" data-ui-drug-hint="hover"></i>
		        </div>
		    </div>
			    <ul class="drugsForm__items">
			    	<li class="drugsForm__item sku-holder" data-sku-id="60591" data-type="Dosage in mg" >
			    		<div class="drugsForm__item-left">
			        		<div class="drugsForm__item-description">
			            	  <div><span>${order_date}: ${order.date}</span></div>
			            	  <div><span>${ordered_medicine}: ${order.name}</span></div>
			            	  <div><span>${total_amount}: ${order.totalAmount}</span></div>
			            	  <div><span>${count_in_order}: ${order.count}</span></div>
			                </div>
			                <span>${status} - </span>
				        	<div class="drugsForm__item-price">
				        	  <c:if test="${order.status == 'IN_PROCESS'}">
				        	    ${status_in_process}
				        	  </c:if>
				        	  <c:if test="${order.status == 'CANCELED'}">
				        	    ${status_canceled}
				        	  </c:if>
				        	  <c:if test="${order.status == 'PAID'}">
				        	    ${status_paid}
				        	  </c:if>
				        	</div>
			   			</div>
					</li>
				</ul>
			</div>
</c:forEach>
</div>
</my:designPattern>