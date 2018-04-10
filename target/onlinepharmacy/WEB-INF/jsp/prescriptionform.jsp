<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<c:url var="create_prescription" value="/dir/create-prescription"/>

<my:designPattern role="DOCTOR">
<div class="wrapper">
<form name="prescription-form" class="prescription-form" action="${create_prescription}" method="POST" >
			<div class="prescription_form_header">
        <span>Form to create prescription</span>
      </div>
      <ul>
  	    <li>
          <label for="client">Client</label>
  			<select size="3" multiple name="client_id" required>
  			  <c:forEach items="${clients}" var="client">
  			    <option value="${client.userId}">${client.userName} ${client.userLastname}</option>
  			  </c:forEach>
            </select>
        </li>
        <li>
  		  <label for="medicine">Medicine</label>
            <select size="3" name="medicine_id" required>
              <c:forEach items="${medicines}" var="medicine">
  			    <option value="${medicine.medicineId}">${medicine.medicineName}</option>
  			  </c:forEach>
            </select>
        </li>
          <li>
  				<label for="count">Date</label>
  				<input name="date" type="date" required />
          </li>
          <li>
  				<label for="comment">Comment</label>
  				<textarea name="comment" cols="40" rows="6"></textarea>
          </li>
          </ul>
			<div class="prescription_footer">
				<input type="submit" value="Create" class="prescription_button" />
			</div>
		</form>
	</div>
</my:designPattern>