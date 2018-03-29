<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:url var="logout_url" value="/dir/logout"/>
<c:url var="medicine_list" value="/dir/medicine-list"/>
<c:url var="order_list" value="/dir/order-list"/>
<c:url var="main_page" value="/dir/main" />

<html>
<head>
<meta charset="UTF-8">
	<fmt:bundle basename="i18n">
		    <fmt:message key="navbar.hello" var="mainPage"/>
		    <fmt:message key="authentication.logout" var="logout"/>
		    <fmt:message key="medicines.serach_error" var="search_error_mess"/>
	</fmt:bundle>
	<title>${mainPage}</title>
	<style>
	    <jsp:directive.include file="/WEB-INF/css/style.css"/>
	</style>
	<script>
        <jsp:directive.include file="/WEB-INF/js/dialog.js"/>
    </script>
</head>

<body>

  <ul id="menu">
	    <li><a href="${medicine_list}">MEDICINE LIST</a></li>
	    <li><a href="${order_list}">ORDERS</a></li>
	    <li><a href="#">REQUESTS</a></li>
	    <li><a href="${main_page}">ABOUT US</a></li>
	    <li>
		    <form action="${logout_url}" method="POST">
				<input type="submit" value="${logout}" class="button_logout" />
		    </form>
	    </li>
	    <li><a href="#">LANGUAGE</a>
	      <ul class="submenu">
	      <li><a href="set-language?lang=en">EN</a></li>
	      <li><a href="set-language?lang=ru">RU</a></li>
	      </ul>
	    </li>
    </ul>
	
	<c:if test="${not empty search_error}">
        <p>${search_error_mess}</p>
	</c:if>
	
	<c:forEach items="${orders}" var="order">
	<c:url var="make-order" value="/dir/make-order" />
		<div class="drugsForm  by">
		    <div class="drugsForm__header" style="background-color: #92aeb7">
		        <div class="drugsForm__description">
		            <h3 class="drugsForm__description-title">Order # ${order.orderId}</h3>
		        </div>        
		        <div class="drugsForm__header-buttons">
		            <i class="drugsForm__header-info" data-ui-drug-hint="hover"></i>
		        </div>
		    </div>
			    <ul class="drugsForm__items">
			    	<li class="drugsForm__item sku-holder" data-sku-id="60591" data-type="Dosage in mg" >
			    		<div class="drugsForm__item-left">
			        		<div class="drugsForm__item-description">
			            	<span>Prescription # - </span>
			            	<div class="drugsForm__item-price">${order.prescriptionId}</div>
			            	<span>Order date: ${order.date}</span>
			                </div>
			                <span>Status - </span>
			        	<div class="drugsForm__item-price">${order.status}</div>
			   			</div>
			    		<div class="drugsForm__item-buttons">
			    		<!-- <form action="make-order" method="POST" name="make-order">
			    		  <input name="medicine_id" type="text"  value="${medicine.id}" hidden="hidden" />
						  <input name="order_count" type="text" id="return_value" value="" placeholder="Введите количество" required />
						  <input type="submit" value="ORDER" class="drugsForm__button js-drugsForm__button" />
			            </form>-->
			            </div>
					</li>
				</ul>
			</div>
	</c:forEach>
	<div id="footer" style="flex: 0 0 auto">
        <footer>
            <div style="margin-top:5px;">
                <p>Epam Systems Lab21 © 2018, All Rights Reserved
                    <br>
                    <span>Web Design By: Alexe  Sosenkov</span></p>
            </div>
        </footer>
     </div>
</body>
</html>