<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<c:url var="change_medicine" value="/dir/change-medicine" />
<c:url var="create_medicine" value="/dir/create-medicine" />

<fmt:bundle basename="i18n">
	<fmt:message key="medicines.dosage" var="dosage"/>
	<fmt:message key="medicines.count" var="medicine_count"/>
	<fmt:message key="medicines.count_in_store" var="count_in_store"/>
	<fmt:message key="medicines.price" var="price"/>
	<fmt:message key="medicines.need_prescription" var="need_prescription"/>
	<fmt:message key="medicines.has_prescription" var="has_prescription"/>
	<fmt:message key="medicines.no_prescription" var="no_prescription"/>
</fmt:bundle>

<my:designPattern role="ADMIN">
  <c:if test="${requestScope.actualForm == 'changeForm'}" >
  <c:set var="medicine" value="${requestScope.medicine}" />
		<div class="wrapper">
			<form name="medicine-form" class="medicine-form" action="${change_medicine}" method="POST" >
				<div class="medicine_form_header">Form to change medicine</div>
	        <div class="medicine_content">
	  			  <div class="medicine-input">
	          <label for="name">Medicine name</label>
	  				<input name="name" type="text" value="${medicine.name}" required />
	          </div>
	          <div class="medicine-input">
	  				<label for="countInStore">${count_in_store}</label>
	  				<input name="countInStore" type="text" value="${medicine.countInStore}" required />
	          </div>
	          <div class="medicine-input">
	  				<label for="count">${medicine_count}</label>
	  				<input name="count" type="text" value="${medicine.count}" required />
	          </div>
	          <div class="medicine-input">
	  				<label for="dosageMg">${dosage}</label>
	  				<input name="dosageMg" type="text" value="${medicine.dosageMg}"  required />
	          </div>
	          <div class="medicine-input">
	  				<label for="price">${price}</label>
	  				<input name="price" type="text"  value="${medicine.price}" required />
	          </div>
	  				<div class="medicine-input">${need_prescription}: ${medicine.needPrescription}</div>
	  				<div class="medicine-input">
	              <label for=""choice>Change need in prescription:</label>
	              <div  class="choice" name="choice">
	  				    <input type="radio"  name="needPrescription" value="true">
	  				    <label for="contactChoice1">${has_prescription}</label>
	  				    <input type="radio" id="contactChoice2"
	  				     name="needPrescription" value="false">
	  				    <label for="contactChoice2">${no_prescription}</label>
	              </div>
					 </div>
				</div>
				<input name="medicine_id" type="text"  value="${medicine.id}" hidden="hidden" /> 
				<div class="medicine_footer">
					<input type="submit" value="Change" class="medicine_button" />
				</div>
			</form>
		</div>
	</c:if>
	<c:if test="${requestScope.actualForm == 'creationForm'}" >
		<div class="wrapper">
			<form name="medicine-form" class="medicine-form" action="${create_medicine}" method="POST" >
				<div class="medicine_form_header">Form to create medicine</div>
	        <div class="medicine_content">
	  			  <div class="medicine-input">
	          <label for="name">Medicine name</label>
	  				<input name="name" type="text" required />
	          </div>
	          <div class="medicine-input">
	  				<label for="countInStore">${count_in_store}</label>
	  				<input name="countInStore" type="text" required />
	          </div>
	          <div class="medicine-input">
	  				<label for="count">${medicine_count}</label>
	  				<input name="count" type="text" required />
	          </div>
	          <div class="medicine-input">
	  				<label for="dosageMg">${dosage}</label>
	  				<input name="dosageMg" type="text" required />
	          </div>
	          <div class="medicine-input">
	  				<label for="price">${price}</label>
	  				<input name="price" type="text" required />
	          </div>
	  				<div class="medicine-input">${need_prescription}: </div>
	  				<div class="medicine-input">
	              <label for=""choice>Change need in prescription:</label>
	              <div  class="choice" name="choice">
	  				    <input type="radio"  name="needPrescription" value="true">
	  				    <label for="contactChoice1">${has_prescription}</label>
	  				    <input type="radio" id="contactChoice2"
	  				     name="needPrescription" value="false">
	  				    <label for="contactChoice2">${no_prescription}</label>
	              </div>
					 </div>
				</div> 
				<div class="medicine_footer">
					<input type="submit" value="Create" class="medicine_button" />
				</div>
			</form>
		</div>
	</c:if>
</my:designPattern>