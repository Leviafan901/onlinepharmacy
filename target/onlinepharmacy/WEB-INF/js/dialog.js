var dialog = document.querySelector('dialog');
document.querySelector('#show').onclick = function() {
	dialog.show(); // открыть диалоговое окно
};

document.querySelector('#close').onclick = function() {
	dialog.close(); // закрыть диалоговое окно
};

function myFunction() {
	document.getElementById("return_value").required = true;
	document.getElementById("input_username").required = true;
	document.getElementById("input_password").required = true;
};